package sningning.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sningning.community.util.SensitiveFilter;

/**
 * @author: Song Ningning
 * @date: 2020-08-06 16:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTest {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {

        String text = "这是一个关于敏感词的测试，这里可以赌博、嫖娼、吸毒、买卖毒品、开票";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

        text = "这是一个关于敏感词的测试，这里可以☆赌☆博☆、☆嫖☆娼☆、☆吸☆毒☆、买卖☆毒☆品☆、☆开☆票☆";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
