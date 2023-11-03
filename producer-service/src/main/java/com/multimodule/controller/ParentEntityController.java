package com.multimodule.controller;

import com.multimodule.dto.*;
import com.multimodule.model.ParentEntity;
import com.multimodule.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/parents")
public class ParentEntityController {

    private final ParentEntityRepository parentEntityRepository;
    private final EntityManager entityManager;
    private final ParentDtoTransformer parentDtoTransformer;
    private final ParentDtoTransformerWithoutAlias parentDtoTransformerWithoutAlias;
    private final ParentDto3Transformer parentDto3Transformer;


    /**
     * Hibernate: select p1_0.id,p1_0.parent_name from parents p1_0
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     *
     * @return
     */
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<ParentEntity> all = parentEntityRepository.findAll();
        return ResponseEntity.ok(all);
    }

    /**
     * Hibernate: select distinct p1_0.id,c1_0.parent_id,c1_0.id,c1_0.child_name,p1_0.parent_name from parents p1_0 join children c1_0 on p1_0.id=c1_0.parent_id
     *
     * @return
     */
    @GetMapping("/findAllParents")//CUSTOM
    public ResponseEntity<?> findAllParents() {
        List<ParentEntity> all = parentEntityRepository.findAllParents();
        return ResponseEntity.ok(all);
    }
// ------------------------------------------------------

    /**
     * Hibernate: select p1_0.id,p1_0.parent_name from parents p1_0 where p1_0.id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     *
     * @param parentId
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long parentId) {
        ParentEntity parentEntity = parentEntityRepository.findById(parentId)
                .orElseThrow(() -> new IllegalStateException("Parent id not found!" + parentId));
        return ResponseEntity.ok(parentEntity);
    }

    /**
     * Hibernate: select p1_0.id,c1_0.parent_id,c1_0.id,c1_0.child_name,p1_0.parent_name from parents p1_0 join children c1_0 on p1_0.id=c1_0.parent_id where p1_0.id=?
     *
     * @param parentId
     * @return
     */
    @GetMapping("/findParentById/{id}")//CUSTOM
    public ResponseEntity<?> findParentById(@PathVariable("id") Long parentId) {
        ParentEntity parentEntity = parentEntityRepository.findParentById(parentId)
                .orElseThrow(() -> new IllegalStateException("Parent id not found!" + parentId));
        System.out.println(parentEntity.getParentName());
        return ResponseEntity.ok(parentEntity);
    }
//------------------------------------------------------------

    @GetMapping("/findParentQueryDtoById/{id}")//CUSTOM
    public ResponseEntity<?> findParentQueryDtoById(@PathVariable("id") Long parentId) {
        ParentQueryDto parentQueryDto = parentEntityRepository.findParentQueryDtoById(parentId)
                .orElseThrow(() -> new IllegalStateException("Parent id not found!" + parentId));
        return ResponseEntity.ok(parentQueryDto);
    }


/////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/findAllParentDtos")
    public ResponseEntity<?> findAllParentDtos() {
        List<ParentDto> resultList = entityManager.createQuery(
                        "select pe.id as parent_id," +
                                "pe.parentName as parent_name, " +
                                "ce.id as child_id, " +
                                "ce.childName as child_name " +
                                "from ChildEntity ce join ce.parentEntity pe order by pe.id")
                .unwrap(Query.class)
                .setResultListTransformer(parentDtoTransformer)
                .setTupleTransformer(parentDtoTransformer)
                .getResultList();
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/findParentDtoById/{id}")
    public ResponseEntity<?> findParentDtoById(@PathVariable("id") Long parentId) {
        ParentDto parentDto = (ParentDto) entityManager.createQuery(
                        "select pe.id as parent_id," +
                                "pe.parentName as parent_name, " +
                                "ce.id as child_id, " +
                                "ce.childName as child_name " +
                                "from ChildEntity ce join ce.parentEntity pe " +
                                "where pe.id = :parentid", ParentDto.class)
                .setParameter("parentid", parentId)
                .unwrap(Query.class)
                .setResultListTransformer(parentDtoTransformer)
                .setTupleTransformer(parentDtoTransformer)
                .getSingleResult();
        return ResponseEntity.ok(parentDto);
    }

    @GetMapping("/findParentDtoById2/{id}")
    public ResponseEntity<?> findParentDtoById2(@PathVariable("id") Long parentId) {
        TypedQuery<ParentDto> query = entityManager.createQuery(
                "select pe.id as parent_id," +
                        "pe.parentName as parent_name, " +
                        "ce.id as child_id, " +
                        "ce.childName as child_name " +
                        "from ChildEntity ce join ce.parentEntity pe " +
                        "where pe.id = :parentid", ParentDto.class);

        TypedQuery<ParentDto> parentid = query.setParameter("parentid", parentId);

        Query unwrap = query.unwrap(Query.class);

        Query query1 = unwrap.setTupleTransformer(parentDtoTransformer);

        Object singleResult = unwrap.getSingleResult();//burada parentDtoTransformer (transformTuple methoduna) içine gidiyor

        ParentDto parentDto = (ParentDto) singleResult;

        return ResponseEntity.ok(parentDto);
    }

    //    @GetMapping("/test/{id}")
//    public ResponseEntity<?> test(@PathVariable("id") Long parentId) {
//        ParentDto parentDto = (ParentDto) entityManager.createQuery(
//                        "select pe.id as parent_id," +
//                                "pe.parentName as parent_name, " +
//                                "ce.id as child_id, " +
//                                "ce.childName as child_name " +
//                                "from ChildEntity ce join ce.parentEntity pe " +
//                                "where pe.id = :parentid", ParentDto.class)
//                .setParameter("parentid", parentId)
//                .unwrap(Query.class)
//                .setResultListTransformer(parentDtoTransformer)
//                .setTupleTransformer(parentDtoTransformer)
//                .getSingleResult();
//        return ResponseEntity.ok(parentDto);
//    }
    @GetMapping("/test/{id}")
    public ResponseEntity<?> test(@PathVariable("id") Long parentId) {
        ParentDto parentDto = (ParentDto) entityManager.createQuery(
                        "select pe.id," +
                                "pe.parentName, " +
                                "ce.id, " +
                                "ce.childName " +
                                "from ChildEntity ce join ce.parentEntity pe " +
                                "where pe.id = :parentid", ParentDto.class)
                .setParameter("parentid", parentId)
                .unwrap(Query.class)
//                .setResultListTransformer(parentDtoTransformerWithoutAlias)
                .setTupleTransformer(parentDtoTransformerWithoutAlias)
                .getSingleResult();
        return ResponseEntity.ok(parentDto);
    }

    @GetMapping("/test2/{id}")
    public ResponseEntity<?> test2(@PathVariable("id") Long parentId) {
        TypedQuery<ParentDto> query = entityManager.createQuery(
                "select pe.id," +
                        "pe.parentName, " +
                        "ce.id, " +
                        "ce.childName " +
                        "from ChildEntity ce join ce.parentEntity pe " +
                        "where pe.id = :parentid", ParentDto.class);

        query.setParameter("parentid", parentId);

        Query unwrap = query.unwrap(Query.class);

        Query query1 = unwrap.setTupleTransformer(parentDtoTransformerWithoutAlias);

        Object singleResult = unwrap.getSingleResult();

        ParentDto parentDto = (ParentDto) singleResult;


//        ParentDto parentDto = (ParentDto) entityManager.createQuery(
//                        "select pe.id," +
//                                "pe.parentName, " +
//                                "ce.id, " +
//                                "ce.childName " +
//                                "from ChildEntity ce join ce.parentEntity pe " +
//                                "where pe.id = :parentid", ParentDto.class)
//                .setParameter("parentid", parentId)
//                .unwrap(Query.class)
////                .setResultListTransformer(parentDtoTransformerWithoutAlias)
//                .setTupleTransformer(parentDtoTransformerWithoutAlias)
//                .getSingleResult();
        return ResponseEntity.ok(parentDto);
    }

    // TODO: No result found for query [select pe.id, pe.parentName, pe.parentDescription from ParentEntity pe where pe.id =:parentId]
    // for the id which does not exist!
    @GetMapping("/test3/{id}")
    public ResponseEntity<?> test3(@PathVariable("id") Long parentId) {
        TypedQuery<ParentDto3> query = entityManager.createQuery(
                "select " +

                        "pe.id, " +
                        "pe.parentName, " +
                        "pe.parentDescription " +

                        "from ParentEntity pe " +
                        "where pe.id =:parentId",
                ParentDto3.class
        );

        query.setParameter("parentId", parentId);

        Query unwrap = query.unwrap(Query.class);
        // TODO: no need new transformer object for every request. we can inject it.
//        unwrap.setTupleTransformer(new ParentDto3Transformer());
        unwrap.setTupleTransformer(parentDto3Transformer);

        Object singleResult = unwrap.getSingleResult();
        ParentDto3 singleResult1 = (ParentDto3) singleResult;

        return ResponseEntity.ok(singleResult1);
    }

    private final ParentDto4WithChildrenTransformer parentDto4WithChildrenTransformer;
    @GetMapping("/test4/{id}")
    public ResponseEntity<?> test4(@PathVariable("id") Long parentId) {
        // Both joining works well..
//        TypedQuery<ParentDto4WithChildren> query = entityManager.createQuery(
//                "select " +
//
//                        "pe.id, " +
//                        "pe.parentName, " +
//                        "pe.parentDescription, " +
//                        "ce.id," +
//                        "ce.childName," +
//                        "ce.childStatus " +
//
//                        "from ChildEntity ce " +
//                        "join ce.parentEntity pe " +
//                        "where pe.id =:parentId",
//                ParentDto4WithChildren.class
//        );
        TypedQuery<ParentDto4WithChildren> query = entityManager.createQuery(
                "select " +

                        "pe.id, " +
                        "pe.parentName, " +
                        "pe.parentDescription, " +
                        "ce.id," +
                        "ce.childName," +
                        "ce.childStatus " +

                        "from ParentEntity pe " +
                        "join pe.childEntities ce " +
                        "where pe.id =:parentId",
                ParentDto4WithChildren.class
        );

        query.setParameter("parentId", parentId);

        Query unwrap = query.unwrap(Query.class);
//        unwrap.setTupleTransformer(new ParentDto4WithChildrenTransformer());
        unwrap.setTupleTransformer(parentDto4WithChildrenTransformer);

        Object singleResult = unwrap.getSingleResult();
        ParentDto4WithChildren singleResult1 = (ParentDto4WithChildren) singleResult;

        parentDto4WithChildrenTransformer.setParentDto(new ParentDto4WithChildren());
        return ResponseEntity.ok(singleResult1);
    }

    @GetMapping("/testlist")
    public ResponseEntity<?> testList() {
        List<ParentDto> resultList = entityManager.createQuery(
                        "select pe.id," +
                                "pe.parentName, " +
                                "ce.id, " +
                                "ce.childName " +
                                "from ChildEntity ce join ce.parentEntity pe order by pe.id")
                .unwrap(Query.class)
//                .setResultListTransformer(parentDtoTransformerWithoutAlias)
                .setTupleTransformer(parentDtoTransformerWithoutAlias)
                .getResultList();
        return ResponseEntity.ok(resultList);
    }


    //---------------------------------------------------------------------------------
    @GetMapping("/findParentDtoWithChildrenDtoById/{id}")//CUSTOM
    public ResponseEntity<?> findParentDtoWithChildrenDtoById() {
        List<ParentDtoWithChildrenDto> parentDtoWithChildrenDto = parentEntityRepository.findParentDtoWithChildrenDtoById3();
//                .orElseThrow(() -> new IllegalStateException("Parent id not found!" + parentId));
        return ResponseEntity.ok(parentDtoWithChildrenDto);
    }


//    @GetMapping("/dtos/{id}")
//    public ResponseEntity<?> getDtosById(@PathVariable("id") Long parentId) {
//        ParentDtoWithChildrenDto dtos = parentEntityRepository.getParentDtosById(parentId);
//        return ResponseEntity.ok(dtos);
//    }

//    @GetMapping("/manualdto/{id}")
//    public ResponseEntity<?> getDtoManuallyById(@PathVariable("id") Long parentId) {
//        ParentEntity parentEntity = parentEntityRepository.findById(parentId)
//                .orElseThrow(() -> new IllegalStateException("Parent id not found!" + parentId));
//        ParentDtoWithChildrenDto parentDtoWithChildrenDto = new ParentDtoWithChildrenDto(
//                parentEntity.getId(),
//                parentEntity.getParentName(),
//                parentEntity.isParentStatus(),
//                parentEntity.getChildEntities().stream()
//                        .map(childEntity -> new ChildDto(
//                                childEntity.getId(),
//                                childEntity.getChildUnique(),
//                                childEntity.getChildName(),
//                                childEntity.getChildDescription(),
//                                childEntity.getChildNumber(),
//                                childEntity.isChildStatus()
//                        ))
//                        .toList()
//        );
//        return ResponseEntity.ok(parentDtoWithChildrenDto);
//    }


}
