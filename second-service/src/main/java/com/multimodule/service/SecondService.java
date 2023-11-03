package com.multimodule.service;


import com.multimodule.feignClients.ParentEntityFeign;
import com.multimodule.feignClients.ParentServiceFeignClient;
import com.parentmodule.common.ParentEntity;
import com.parentmodule.common.ParentId;
import com.parentmodule.common.ParentResponse;
import com.parentmodule.common.ParentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SecondService {

    private static final Logger logger = LoggerFactory.getLogger(SecondService.class);
//    private final ParentServiceFeignClient parentServiceFeignClient;
    @GrpcClient("parent-service")
    private ParentServiceGrpc.ParentServiceBlockingStub parentServiceBlockingStub;


//    public SecondService(ParentServiceFeignClient parentServiceFeignClient) {
//        this.parentServiceFeignClient = parentServiceFeignClient;
//    }

    public ParentResponse callParentById(Long parentId) {
        ParentId buildParentId = ParentId.newBuilder()
                .setParentId(parentId)
                .build();

        ParentResponse parentResponseById = parentServiceBlockingStub.getParentResponseById(buildParentId);



        logger.info("Parent fetched successfully by id: " + parentId);
        System.out.println(parentResponseById);

        return parentResponseById;
    }

//    public void callParentByIdFeign(Long parentId) {
//        ParentEntityFeign parentById = parentServiceFeignClient.findParentById(parentId);
//        System.out.println(parentById);
//    }
}
