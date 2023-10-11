package com.multimodule.service;

import com.multimodule.model.IfrsPaymentLog;
import com.multimodule.repository.IfrsPaymentLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class IfrsPaymentLogService {

    private final IfrsPaymentLogRepository ifrsPaymentLogRepository;


    public IfrsPaymentLog savePaymentLog(IfrsPaymentLog ifrsPaymentLog) {
        return ifrsPaymentLogRepository.save(ifrsPaymentLog);
    }

}
