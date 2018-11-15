package com.my.study.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@SpringBootTest
public class StudyRabbitApplicationTests {

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    @RepeatedTest(value = 10)
    public void userAdd() {
        User u = User.builder().name(RandomStringUtils.randomAlphabetic(10)).age(RandomUtils.nextInt(18, 50)).address("北京").build();
        rabbitMessagingTemplate.convertAndSend("user_add", "user.add.common", u);
    }

    @RepeatedTest(value = 10)
    public void userDelete() {
        rabbitMessagingTemplate.convertAndSend("user_delete", "user.delete.common", RandomStringUtils.randomAlphabetic(10));

    }

    @Test
    public void delay() throws InterruptedException {
        Thread.sleep(3000);
    }
}
