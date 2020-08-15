package sningning.community.event;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sningning.community.entity.Event;

/**
 * 事件生产者
 * @author: Song Ningning
 * @date: 2020-08-15 22:07
 */
@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 处理事件
     * @param event
     */
    public void fireEvent(Event event) {
        // 将事件发布到指定主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
}
