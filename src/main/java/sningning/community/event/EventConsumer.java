package sningning.community.event;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sningning.community.entity.DiscussPost;
import sningning.community.entity.Event;
import sningning.community.entity.Message;
import sningning.community.service.DiscussPostService;
import sningning.community.service.ElasticsearchService;
import sningning.community.service.MessageService;
import sningning.community.util.CommunityConstant;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 事件消费者
 * @author: Song Ningning
 * @date: 2020-08-15 22:10
 */
@Component
public class EventConsumer implements CommunityConstant  {

    private static Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Value("${wk.image.command}")
    private String wkImageCommand;

    @Value("${wk.image.storage}")
    private String wkImageStorage;

    @KafkaListener(topics = {TOPIC_COMMENT, TOPIC_LIKE, TOPIC_FOLLOW})
    public void handleCommentMessage(ConsumerRecord record) {

        if (record == null || record.value() == null) {
            LOGGER.error("消息的内容为空！");
            return;
        }
        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            LOGGER.error("消息格式错误！");
            return;
        }

        // 发送站内通知

        // 构造消息
        Message message = new Message();
        message.setFromId(SYSTEM_USER_ID);
        message.setToId(event.getEntityUserId());
        message.setConversationId(event.getTopic());
        message.setCreateTime(new Date());
        // 利用 map 存储消息具体内容
        Map<String, Object> content = new HashMap<>();
        content.put("userId", event.getUserId());
        content.put("entityType", event.getEntityType());
        content.put("entityId", event.getEntityId());
        // event 中的其他数据也存入 map
        if (!event.getData().isEmpty()) {
            for (Map.Entry<String, Object> entry : event.getData().entrySet()) {
                content.put(entry.getKey(), entry.getValue());
            }
        }
        // 将内容存入 message
        message.setContent(JSONObject.toJSONString(content));

        messageService.addMessage(message);
    }

    /**
     * 消费发帖事件
     * @param record
     */
    @KafkaListener(topics = {TOPIC_PUBLISH})
    public void handlePublishMessage(ConsumerRecord record) {

        if (record == null || record.value() == null) {
            LOGGER.error("消息的内容为空！");
            return;
        }
        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            LOGGER.error("消息格式错误！");
            return;
        }

        DiscussPost post = discussPostService.findDiscussPostById(event.getEntityId());
        elasticsearchService.saveDiscussPost(post);
    }

    /**
     * 消费删帖事件
     * @param record
     */
    @KafkaListener(topics = {TOPIC_DELETE})
    public void handleDeleteMessage(ConsumerRecord record) {

        if (record == null || record.value() == null) {
            LOGGER.error("消息的内容为空！");
            return;
        }
        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            LOGGER.error("消息格式错误！");
            return;
        }

        elasticsearchService.deleteDiscussPost(event.getEntityId());
    }

    @KafkaListener(topics = TOPIC_SHARE)
    public void handleShareMessage(ConsumerRecord record) {
        if (record == null || record.value() == null) {
            LOGGER.error("消息的内容为空！");
            return;
        }
        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            LOGGER.error("消息格式错误！");
            return;
        }

        String htmlUrl = (String) event.getData().get("htmlUrl");
        String filename = (String) event.getData().get("fileName");
        String suffix = (String) event.getData().get("suffix");

        String cmd = wkImageCommand + " --quality 75 "
                + htmlUrl + " " + wkImageStorage + "/" + filename + suffix;
        try {
            Runtime.getRuntime().exec(cmd);
            LOGGER.info("生成长图成功： " + cmd);
        } catch (IOException e) {
            LOGGER.error("生成长图失败： " + e.getMessage());
        }
    }
}
