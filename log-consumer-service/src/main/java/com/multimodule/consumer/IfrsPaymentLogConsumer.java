package com.multimodule.consumer;

import com.multimodule.model.IfrsPaymentLog;
import com.multimodule.model.IfrsPaymentLogPublish;
import com.multimodule.service.IfrsPaymentLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class IfrsPaymentLogConsumer {

    private final IfrsPaymentLogService ifrsPaymentLogService;

    @RabbitListener(queues = "#{T(com.multimodule.config.RabbitMQConfig).IFRS_LOG_QUEUE}")
    public void consumer(IfrsPaymentLogPublish ifrsPaymentLogPublish) {
        IfrsPaymentLog ifrsPaymentLog = new IfrsPaymentLog(
                null,
                ifrsPaymentLogPublish.className(),
                ifrsPaymentLogPublish.methodName(),
                LocalDateTime.parse(ifrsPaymentLogPublish.logDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                ifrsPaymentLogPublish.url()
        );
        IfrsPaymentLog consumed = ifrsPaymentLogService.savePaymentLog(ifrsPaymentLog);
        log.info("Consumed {} from queue", consumed);
    }

}
