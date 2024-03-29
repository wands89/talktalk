package com.study.service.sender;

import com.study.service.config.TopicRabbitConfig;
import com.study.service.dto.basic.JsonResult;
import com.study.service.dto.talk.ReqTalkMessageInfoDto;
import com.study.service.reciever.TopicConsumer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/talk")
public class SendMessageController {

    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @PostMapping("/sendMessage")
    public JsonResult sendDirectMessage(@RequestBody ReqTalkMessageInfoDto dto) {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        LocalDateTime createTime = LocalDateTime.now();
        ReqTalkMessageInfoDto  messageInfoDto= ReqTalkMessageInfoDto.builder()
                .messageId(messageId)
                .messageData(messageData)
                .createTime(createTime)
                .build();
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
       String routingKey=dto.getReceiver();
       String queueName=dto.getSender()+"SEND"+dto.getReceiver()+LocalDateTime.now().toString();
       rabbitTemplate.convertAndSend("topicExchange", "topic.product", messageInfoDto);
       return JsonResult.success(HttpStatus.OK.value(),"success");

    }


}