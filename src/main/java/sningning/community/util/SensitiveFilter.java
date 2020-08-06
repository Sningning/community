package sningning.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 敏感词过滤器
 *
 * @author: Song Ningning
 * @date: 2020-08-06 11:32
 */
@Component
public class SensitiveFilter {

    /**
     * 前缀树内部类
     */
    private class TrieNode {

        // 标记是否是一个敏感词
        private boolean isWord;

        // 子节点(key 是下级节点字符，value 是下级节点)
        private Map<Character, TrieNode> next;

        public TrieNode(boolean isWord) {
            this.isWord = isWord;
            this.next = new HashMap<>();
        }

        public TrieNode() {
            this(false);
        }

        public boolean isWord() {
            return isWord;
        }

        public void setWord(boolean word) {
            isWord = word;
        }

        // 添加子节点
        public void addNext(Character c, TrieNode node) {
            next.put(c, node);
        }
    }

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveFilter.class);

    /**
     * 敏感词替换符
     */
    private static final String REPLACEMENT = "***";

    /**
     * 根节点
     */
    private TrieNode root = new TrieNode();

    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String word;
            while ((word = reader.readLine()) != null) {
                // 添加到前缀树
                this.addWord(word);
            }
        } catch (IOException e) {
            LOGGER.error("加载敏感词文件失败：" + e.getMessage());
        }
    }

    /**
     * 将敏感词添加到前缀树
     *
     * @param word 敏感词
     */
    private void addWord(String word) {
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                // 添加字符
                cur.next.put(c, new TrieNode());
            }
            // 指向子节点,进入下一轮循环
            cur = cur.next.get(c);
        }
        // 设置结束标识
        if (!cur.isWord()) {
            cur.isWord = true;
        }
    }

    /**
     * 在 Trie 中查找，过滤敏感词
     *
     * @param text 待过滤文本
     * @return 过滤后的文本
     */
    public String filter(String text) {

        // 判空
        if (StringUtils.isBlank(text)) {
            return null;
        }

        // 指针 1，指向前缀树中当前节点
        TrieNode curNode = root;
        // 指针 2，指向字符串
        int begin = 0;
        // 指针 3，以 begin 位置字符开头，寻找单词结尾
        int position = 0;

        // 存储过滤后的文本内容
        StringBuilder sb = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);

            // 跳过符号
            // 例如：☆开☆票☆
            if (isSymbol(c)) {
                // 若指针 1 处于根节点,将此符号计入结果,让指针 2 向下走一步
                if (curNode == root) {
                    sb.append(c);
                    begin++;
                }
                // 无论符号在开头或中间,指针 3 都向下走一步
                position++;
                continue;
            }

            // 检查下级节点
            curNode = curNode.next.get(c);

            if (curNode == null) {
                // 以 begin 开头的字符串不是敏感词
                sb.append(c);
                // 进入下一个位置
                begin++;
                position = begin;
                // 重新指向根节点
                curNode = root;
            } else if (curNode.isWord) {
                // 发现敏感词,将 begin~position 字符串替换掉
                sb.append(REPLACEMENT);
                // 进入下一个位置
                position++;
                begin = position;
                // 重新指向根节点
                curNode = root;
            } else {
                // 检查下一个字符
                position++;
            }
        }

        // 将最后一批字符计入结果
        sb.append(text.substring(begin));

        return sb.toString();
    }

    /**
     * 判断字符是否是符号
     *
     * @param c 待判断的字符
     * @return
     */
    private boolean isSymbol(char c) {
        // 0x2E80~0x9FFF 是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

}
