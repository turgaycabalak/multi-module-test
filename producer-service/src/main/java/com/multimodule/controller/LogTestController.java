package com.multimodule.controller;


import com.multimodule.annotations.SendLogForRabbit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/logtest")
public class LogTestController {

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("ok");
    }

    @SendLogForRabbit
    @GetMapping("/test2")
    public ResponseEntity<?> test2() {
        return ResponseEntity.ok("ok2");
    }


}
