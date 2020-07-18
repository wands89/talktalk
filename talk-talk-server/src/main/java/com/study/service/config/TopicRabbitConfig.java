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
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private String queueName;
    private String routingKey;

    private String exchange;

    public TopicRabbitConfig() {

    }

    public TopicRabbitConfig(String exchange,String queueName, String routingKey) {
        this.queueName = queueName;
        this.routingKey = routingKey;
        this.exchange=exchange;
        this.rabbitTemplate=rabbitTemplate();
        RabbitAdmin admin = new RabbitAdmin(this.rabbitTemplate.getConnectionFactory());
        admin.declareQueue(new Queue(this.queueName));
        admin.declareExchange(new TopicExchange(exchange));
//        admin.setAutoStartup(true);
    }

        public RabbitTemplate rabbitTemplate() {
        //The routing key is set to the name of the queue by the broker for the default exchange.
        rabbitTemplate.setRoutingKey(this.routingKey);
        //Where we will synchronously receive messages from
        rabbitTemplate.setQueue(this.queueName);
        // template.setMessageConverter(new JsonMessageConverter());
        return rabbitTemplate;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }
    public String getQueueName() {
        return queueName;
    }

    public String getRoutingKey() {
        return routingKey;
    }


    public void send(String s) {

        this.rabbitTemplate.convertAndSend(s);
    }


//    //绑定键
//    public final static String product = "topic.product";
//    public final static String consumer = "topic.consumer";
//    public final static String exchange_name = "topicExchange";
//    @Bean
//    public Queue firstQueue() {
//        return new Queue(this.product);
//    }
//
//    @Bean
//    public Queue secondQueue() {
//        return new Queue(this.consumer);
//    }
//
//    @Bean
//    TopicExchange exchange() {
//        return new TopicExchange(this.exchange_name);
//    }
//
//
//    //将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
//    //这样只要是消息携带的路由键是topic.man,才会分发到该队列
//
////    @Bean
////    Binding bindingExchangeMessage() {
////        return BindingBuilder.bind(firstQueue()).to(exchange()).with(product);
////    }
////
////    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
////    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
////    @Bean
////    Binding bindingExchangeMessage2() {
////        return BindingBuilder.bind(secondQueue()).to(exchange()).with("topic.#");
////    }

}