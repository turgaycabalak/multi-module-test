package com.multimodule.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
public class IfrsPaymentLog {

    @Id
    private String id;

    private String userId;
    private String className;
    private String methodName;
    private LocalDateTime logDate;
    private String url;
    private LocalDateTime createdAt;

    public IfrsPaymentLog(String userId, String className, String methodName, LocalDateTime logDate, String url) {
        this.userId = userId;
        this.className = className;
        this.methodName = methodName;
        this.logDate = logDate;
        this.url = url;
        this.createdAt = LocalDateTime.now();
    }
}
