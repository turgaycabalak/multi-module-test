package com.multimodule.annotations;


import com.multimodule.config.RabbitMQConfig;
import com.multimodule.model.IfrsPaymentLogPublish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SendLogForRabbitAspect {

    private final RabbitTemplate rabbitTemplate;

    //    @Before("@annotation(com.multimodule.annotations.SendLogForRabbit) || @within(com.multimodule.annotations.SendLogForRabbit)")
//    @Before("execution(* com.multimodule.service.*.*(..))")
//    @Before("execution(* com.multimodule.controller.*.*(..)) || @annotation(com.multimodule.annotations.SendLogForRabbit) || @within(com.multimodule.annotations.SendLogForRabbit)")
//    @Before("execution(* com.multimodule.*.*(..)) && @annotation(com.multimodule.annotations.SendLogForRabbit)")
    /*
     *   execution => it works on classes or methods which are under com.multimodule.* package.
     * */
    @Before("execution(* com.multimodule.service.*.*(..)) && (@annotation(com.multimodule.annotations.SendLogForRabbit) || @within(com.multimodule.annotations.SendLogForRabbit))")
//    @Before("execution(* com.multimodule.service.*.*(..)) && @annotation(com.multimodule.annotations.SendLogForRabbit)")
    public void sendLog(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String logDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        log.info(String.format("className: %s, methodName: %s, logDate: %s", className, methodName, logDate));

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String url = null;
        if (Objects.nonNull(requestAttributes)) {
            url = requestAttributes.getRequest().getRequestURL().toString();
        }

        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.IFRS_LOG_EXCHANGE,
                    RabbitMQConfig.IFRS_LOG_ROUTING_KEY,
                    new IfrsPaymentLogPublish(className, methodName, logDate, url)
            );
            String formatted = String.format("Published -->\n\tLog date : %s\n\tClass name : %s\n\tMethod name : %s\n\tUrl : %s",
                    logDate, className, methodName, url);
            log.info(formatted);

        } catch (Exception e) {
            log.warn("RabbitMQ connection failed!");
        }
    }

}
