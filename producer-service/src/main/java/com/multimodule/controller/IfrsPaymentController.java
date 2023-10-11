package com.multimodule.controller;

import com.multimodule.annotations.SendLogForRabbit;
import com.multimodule.dto.IfrsPaymentRequestDto;
import com.multimodule.repository.IfrsPaymentRepository;
import com.multimodule.service.IfrsPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ifrspayments")
//@SendLogForRabbit
public class IfrsPaymentController {

    private final IfrsPaymentService ifrsPaymentService;


    @PostMapping("/save")
    public ResponseEntity<?> saveController(@RequestBody IfrsPaymentRequestDto requestDto) {
        ifrsPaymentService.saveService(requestDto);
        return ResponseEntity.ok("Saved successfully!");
    }

    @SendLogForRabbit
    @GetMapping("/test")
    public ResponseEntity<?> testController() {
        ifrsPaymentService.testService();
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/test2")
    public ResponseEntity<?> test2Controller() {
        ifrsPaymentService.test2Service();
        return ResponseEntity.ok("ok2");
    }


    @GetMapping("/test-existsby")
    public ResponseEntity<Void> testExistsBy() {
        ifrsPaymentService.testExistsBy();
        return ResponseEntity.ok().build();
    }
}
