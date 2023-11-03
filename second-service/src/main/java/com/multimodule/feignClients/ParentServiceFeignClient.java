package com.multimodule.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "parent-service", url = "http://localhost:8091/parents")
public interface ParentServiceFeignClient {

    @GetMapping("/findParentById/{id}")
    ParentEntityFeign findParentById(@PathVariable("id") Long parentId);

}
