package com.multimodule.repository;

import com.multimodule.model.IfrsPaymentLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IfrsPaymentLogRepository extends MongoRepository<IfrsPaymentLog, String> {
}
