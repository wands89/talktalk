package com.study.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {


    //绑定键
    public final static String product = "topic.product";
    public final static String consumer = "topic.consumer";
    public final static String exchange_name = "topicExchange";
    @Bean
    public Queue firstQueue() {
        return new Queue(this.product);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(this.consumer);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(this.exchange_name);
    }


//    将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
//    这样只要是消息携带的路由键是topic.man,才会分发到该队列

    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(product);
    }

    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with("topic.#");
    }

}