package com.my.study.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
        RabbitMessagingTemplate template = new RabbitMessagingTemplate();
        template.setRabbitTemplate(rabbitTemplate);
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Binding userAddBinding() {
        return BindingBuilder
                .bind(userAddQueue())
                .to(userAddExchange())
                .with("user.add.common")
                .noargs();
    }

    @Bean
    public Exchange userAddExchange() {
        return ExchangeBuilder.directExchange("user_add").durable(true).build();
    }

    @Bean
    public Queue userAddQueue() {
        return QueueBuilder.durable("user_add_common").build();
    }


    @Bean
    public Binding userDeleteBinding() {
        return BindingBuilder
                .bind(userDeleteQueue())
                .to(userDeleteExchange())
                .with("user.delete.common")
                .noargs();
    }

    @Bean
    public Exchange userDeleteExchange() {
        return ExchangeBuilder.directExchange("user_delete").durable(true).build();
    }

    @Bean
    public Queue userDeleteQueue() {
        return QueueBuilder.durable("user_delete_common").build();
    }

}
