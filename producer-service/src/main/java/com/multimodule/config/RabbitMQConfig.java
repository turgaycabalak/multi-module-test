package com.multimodule.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static String IFRS_LOG_QUEUE = "ifrslog.queue";
    public static String IFRS_LOG_EXCHANGE = "ifrslog.exchange";
    public static String IFRS_LOG_ROUTING_KEY = "ifrslog.routingKey";

    @Bean
    public Queue queue() {
        return new Queue(IFRS_LOG_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(IFRS_LOG_EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(IFRS_LOG_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        // It allows us to automatically convert the objects we send to RabbitMQ, not just byte[] or String,
        // but also an object we have made ourselves, into JSON.
        return new Jackson2JsonMessageConverter();
    }


    // ConnectionFactory
    // RabbitTemplate
    // RabbitAdmin
    // --> These are the basically infrastructure beans that required SB app to work with RMQ broker.
    // --> But SB autoconfiguration will automatically configure these three beans. So we don't have to manually crate these beans.


//    @Bean
//    public AmqpTemplate template(ConnectionFactory connectionFactory) {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(messageConverter());
//        return  template;
//    }


}
