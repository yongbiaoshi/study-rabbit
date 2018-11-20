package com.my.study.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class UserPublishTest {

    private static Connection connection;
    private static Channel channel;

    @BeforeAll
    public static void init() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/study");
        connection = factory.newConnection();
        channel = connection.createChannel();

    }

    @AfterAll
    public static void destroy() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    @Nested
    class UserPublish {
        @RepeatedTest(value = 10)
        public void publish() throws IOException {
            User u = User.builder().name(RandomStringUtils.randomAlphabetic(10)).age(RandomUtils.nextInt(18, 50)).address("北京").build();
            Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
            Message message = converter.toMessage(u, null);
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
            builder.contentType(message.getMessageProperties().getContentType());
            AMQP.BasicProperties props = builder.build();
            channel.basicPublish("user_add", "user.add.common", props, message.getBody());
        }
    }


}
