package com.multimodule.controller;


import com.multimodule.service.SecondService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/second")
public class SecondController {

    private final SecondService secondService;


    @GetMapping("test/{id}")
    public ResponseEntity<?> test(@PathVariable("id") Long parentId) {
        return ResponseEntity.ok(secondService.callParentById(parentId));
    }

//    @GetMapping("testfeign/{id}")
//    public ResponseEntity<?> testFeign(@PathVariable("id") Long parentId) {
//        secondService.callParentByIdFeign(parentId);
//        return ResponseEntity.ok("success");
//    }

}
