package com.multimodule.service;

import com.multimodule.annotations.SendLogForRabbit;
import com.multimodule.dto.IfrsPaymentRequestDto;
import com.multimodule.dto.ParentDtoWithChildrenDto;
import com.multimodule.model.IfrsPayment;
import com.multimodule.repository.IfrsPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
//@SendLogForRabbit
public class IfrsPaymentService {

    private final IfrsPaymentRepository ifrsPaymentRepository;


//    @SendLogForRabbit
    public void saveService(IfrsPaymentRequestDto requestDto) {
        IfrsPayment savedIrfs = ifrsPaymentRepository.save(new IfrsPayment(requestDto.userId(), requestDto.ifrsName()));
        log.info("Irfs saved -> {}", savedIrfs);
    }

    @SendLogForRabbit
    public void testService() {
        innerTest();
        log.info("TEST service finished.");
    }

    public void innerTest() {
        log.info("Inner TEST finished.");
//        log.debug("Inner Test is called.");
    }

//    @SendLogForRabbit
    public void test2Service() {
        innerTest2();
        log.info("TEST-2 service finished.");
    }

    @SendLogForRabbit
    public void innerTest2() {
        log.info("Inner TEST-2 finished.");
    }

    public void testExistsBy() {
        //select count(*) from ifrs_payment i1_0 where i1_0.id=?
        ifrsPaymentRepository.existsById(1L);

        ifrsPaymentRepository.testExistByUserId("user-2");
        ifrsPaymentRepository.existsByUserId("user-2");
        ifrsPaymentRepository.testForConditionalQuery("user-2");

//        ifrsPaymentRepository.testForConditionalQuery2("user-2");
//        ifrsPaymentRepository.findSecondEntitiesByIfrsUserId("user-22");
    }
}
