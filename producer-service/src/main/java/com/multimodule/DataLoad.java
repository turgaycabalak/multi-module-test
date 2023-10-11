package com.multimodule;

import com.multimodule.model.ChildEntity;
import com.multimodule.model.IfrsPayment;
import com.multimodule.model.ParentEntity;
import com.multimodule.model.SecondEntity;
import com.multimodule.repository.ChildEntityRepository;
import com.multimodule.repository.IfrsPaymentRepository;
import com.multimodule.repository.ParentEntityRepository;
import com.multimodule.repository.SecondEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoad implements ApplicationRunner {

    private final SecondEntityRepository secondEntityRepository;
    private final IfrsPaymentRepository ifrsPaymentRepository;
    private final ParentEntityRepository parentEntityRepository;
    private final ChildEntityRepository childEntityRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadDummyIfrses();
        loadDummySecondEntities();
        loadDummyParentsAndChildren();
    }


    private <T> List<List<T>> splitList(List<T> list, int batchSize) {
        return IntStream.range(0, (list.size() + batchSize - 1) / batchSize)
                .mapToObj(i -> list.subList(i * batchSize, Math.min((i + 1) * batchSize, list.size())))
                .toList();
    }

    private <T, K extends JpaRepository<T, ?>> void saveAllGeneric(List<T> list, K genericRepository) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<List<T>> partitions = splitList(list, 1000);
        AtomicInteger paymentIndex = new AtomicInteger(0);
        partitions.forEach(listPart -> {
            executorService.submit(() -> genericRepository.saveAll(listPart));
            log.info(String.format("Partition %s working.", paymentIndex.get()));
            paymentIndex.getAndIncrement();
        });
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

//    private <T> List<List<T>> splitList(List<T> list, int batchSize) {
//        List<List<T>> partitions = new ArrayList<>();
//        for (int i = 0; i < list.size(); i += batchSize) {
//            partitions.add(list.subList(i, Math.min(i + batchSize, list.size())));
//        }
//        return partitions;
//    }

    private void loadDummyIfrses() throws InterruptedException {
        if (ifrsPaymentRepository.count() == 0) {
            List<IfrsPayment> ifrsPayments = List.of(
                    new IfrsPayment("user-1", "ifrs-1"),
                    new IfrsPayment("user-2", "ifrs-2"),
                    new IfrsPayment("user-3", "ifrs-3"),
                    new IfrsPayment("user-4", "ifrs-4")
            );
            saveAllGeneric(ifrsPayments, ifrsPaymentRepository);
        }
    }

    private void loadDummySecondEntities() throws InterruptedException {
        if (secondEntityRepository.count() == 0) {
            List<SecondEntity> secondEntities = List.of(
                    new SecondEntity("secondFirst-1", "secondLast-1"),
                    new SecondEntity("secondFirst-2", "secondLast-2"),
                    new SecondEntity("secondFirst-3", "secondLast-3"),
                    new SecondEntity("secondFirst-4", "secondLast-4")
            );
            saveAllGeneric(secondEntities, secondEntityRepository);
        }
    }

    private void loadDummyParentsAndChildren() throws InterruptedException {
        if (parentEntityRepository.count() == 0) {
            List<ParentEntity> parentEntities = List.of(
                    new ParentEntity("parent-unique-1", "parent-name-1", "parent-description-1", BigDecimal.valueOf(1), true),
                    new ParentEntity("parent-unique-2", "parent-name-2", "parent-description-2", BigDecimal.valueOf(2), true),
                    new ParentEntity("parent-unique-3", "parent-name-3", "parent-description-3", BigDecimal.valueOf(3), true),
                    new ParentEntity("parent-unique-4", "parent-name-4", "parent-description-4", BigDecimal.valueOf(4), true),
                    new ParentEntity("parent-unique-5", "parent-name-5", "parent-description-5", BigDecimal.valueOf(5), true),
                    new ParentEntity("parent-unique-6", "parent-name-6", "parent-description-6", BigDecimal.valueOf(6), true)
//                    new ParentEntity("parent-unique-1"),
//                    new ParentEntity("parent-unique-2"),
//                    new ParentEntity("parent-unique-3"),
//                    new ParentEntity("parent-unique-4"),
//                    new ParentEntity("parent-unique-5"),
//                    new ParentEntity("parent-unique-6")
            );
            parentEntities.get(0).setChildEntities(List.of(
                    new ChildEntity("child-unique-1", "child-name-1", "child-description-1", BigDecimal.valueOf(1), true, parentEntities.get(0)),
                    new ChildEntity("child-unique-2", "child-name-2", "child-description-2", BigDecimal.valueOf(2), true, parentEntities.get(0))
            ));
            parentEntities.get(1).setChildEntities(List.of(
                    new ChildEntity("child-unique-3", "child-name-3", "child-description-3", BigDecimal.valueOf(3), true, parentEntities.get(1)),
                    new ChildEntity("child-unique-4", "child-name-4", "child-description-4", BigDecimal.valueOf(4), true, parentEntities.get(1))
            ));
            parentEntities.get(2).setChildEntities(List.of(
                    new ChildEntity("child-unique-5", "child-name-5", "child-description-5", BigDecimal.valueOf(5), true, parentEntities.get(2))
            ));
            parentEntities.get(3).setChildEntities(List.of(
                    new ChildEntity("child-unique-6", "child-name-6", "child-description-6", BigDecimal.valueOf(6), true, parentEntities.get(3))
            ));
            parentEntities.get(4).setChildEntities(List.of(
                    new ChildEntity("child-unique-7", "child-name-7", "child-description-7", BigDecimal.valueOf(7), true, parentEntities.get(4)),
                    new ChildEntity("child-unique-8", "child-name-8", "child-description-8", BigDecimal.valueOf(8), true, parentEntities.get(4)),
                    new ChildEntity("child-unique-9", "child-name-9", "child-description-9", BigDecimal.valueOf(9), true, parentEntities.get(4)),
                    new ChildEntity("child-unique-10", "child-name-10", "child-description-10", BigDecimal.valueOf(10), true, parentEntities.get(4))
            ));

//            parentEntities.get(0).setChildEntities(List.of(
//                    new ChildEntity("child-unique-1", parentEntities.get(0)),
//                    new ChildEntity("child-unique-2", parentEntities.get(0))
//            ));
//            parentEntities.get(1).setChildEntities(List.of(
//                    new ChildEntity("child-unique-3", parentEntities.get(1)),
//                    new ChildEntity("child-unique-4", parentEntities.get(1))
//            ));
//            parentEntities.get(2).setChildEntities(List.of(
//                    new ChildEntity("child-unique-5", parentEntities.get(2))
//            ));
//            parentEntities.get(3).setChildEntities(List.of(
//                    new ChildEntity("child-unique-6", parentEntities.get(3))
//            ));
//            parentEntities.get(4).setChildEntities(List.of(
//                    new ChildEntity("child-unique-7", parentEntities.get(4)),
//                    new ChildEntity("child-unique-8", parentEntities.get(4)),
//                    new ChildEntity("child-unique-9", parentEntities.get(4)),
//                    new ChildEntity("child-unique-10", parentEntities.get(4))
//            ));
            saveAllGeneric(parentEntities, parentEntityRepository);
        }
    }
}
