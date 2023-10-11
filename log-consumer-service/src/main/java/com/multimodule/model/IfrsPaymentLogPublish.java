package com.multimodule.model;

import java.time.LocalDateTime;

public record IfrsPaymentLogPublish(
        String userId,
        String className,
        String methodName,

        //TODO: LocalDateTime tipinde almayı test et!
        String logDate,
//        LocalDateTime logDate

        String url
) {
}
