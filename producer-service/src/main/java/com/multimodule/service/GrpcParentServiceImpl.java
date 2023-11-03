package com.multimodule.service;

import com.multimodule.model.ParentEntity;
import com.multimodule.repository.ParentEntityRepository;
import com.parentmodule.common.ParentId;
import com.parentmodule.common.ParentResponse;
import com.parentmodule.common.ParentServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@GrpcService
@RequiredArgsConstructor
public class GrpcParentServiceImpl extends ParentServiceGrpc.ParentServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(GrpcParentServiceImpl.class);
    private final ParentEntityRepository parentEntityRepository;


    @Override
    public void getParentResponseById(ParentId request, StreamObserver<ParentResponse> responseObserver) {
        logger.info("Grpc call received: " + request.getParentId());
        ParentEntity parentEntity = parentEntityRepository.findParentById(request.getParentId())
                .orElseThrow(() -> new IllegalStateException(String.format("Parent not found by id: %s", request.getParentId())));

        responseObserver.onNext(
                ParentResponse.newBuilder()
                        .setParentId(parentEntity.getId())
                        .setParentName(parentEntity.getParentName())
                        .setParentDescription(parentEntity.getParentDescription())
                        .build()
        );

        responseObserver.onCompleted();
    }
}
