package com.my.study.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class UserAddListener {

    private AtomicInteger ai = new AtomicInteger();

    @RabbitListener(queues = {"user_add_common"}, group = "user-add-listener")
    public void userAddListener(@Payload User user) {
        log.info("消费内容：{}", user);
        if (ai.incrementAndGet() % 2 == 0) {
            throw new RuntimeException("测试异常消费");
        }
    }

}
