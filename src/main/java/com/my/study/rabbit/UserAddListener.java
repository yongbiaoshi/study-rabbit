package com.my.study.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserAddListener {

    @RabbitListener(queues = {"user_add_common"}, group = "user-add-listener")
    public void userAddListener(@Payload User user) {
        log.info("消费开始");
        log.info("消费内容：{}", user);
        log.info("消费结束");
    }

}
