package com.study.service.reciever;

import com.study.service.dto.talk.ReqTalkMessageInfoDto;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RabbitListener(queues = "topic.man")
public class TopicConsumer {

    @RabbitHandler
    public void process(ReqTalkMessageInfoDto testMessage) {
        System.out.println("TopicManReceiver消费者收到消息  : " + testMessage.getMessageData());
    }
}
