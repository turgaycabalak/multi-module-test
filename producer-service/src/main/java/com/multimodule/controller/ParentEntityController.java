package com.multimodule.controller;

import com.multimodule.dto.ParentDto;
import com.multimodule.dto.ParentDtoWithChildrenDto;
import com.multimodule.dto.ParentQueryDto;
import com.multimodule.model.ParentEntity;
import com.multimodule.repository.ChildEntityRepository;
import com.multimodule.repository.ParentDtoTransformer;
import com.multimodule.repository.ParentEntityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Query;
import org.springframework.data.repository.query.Param;
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
//        ParentDtoTransformer parentDtoTransformer = new ParentDtoTransformer();
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
//        ParentDtoTransformer parentDtoTransformer = new ParentDtoTransformer();
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

    @GetMapping("/test/{id}")
    public ResponseEntity<?> test(@PathVariable("id") Long parentId) {
        ParentDtoTransformer parentDtoTransformer = new ParentDtoTransformer();
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
