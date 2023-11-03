package com.multimodule.controller;

import com.multimodule.model.ChildEntity;
import com.multimodule.repository.ChildEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/children")
public class ChildrenController {

    private final ChildEntityRepository childEntityRepository;


    /**
     * Hibernate: select c1_0.id,c1_0.child_name,c1_0.parent_id from children c1_0
     * Hibernate: select p1_0.id,p1_0.parent_name from parents p1_0 where p1_0.id=?
     * Hibernate: select p1_0.id,p1_0.parent_name from parents p1_0 where p1_0.id=?
     * Hibernate: select p1_0.id,p1_0.parent_name from parents p1_0 where p1_0.id=?
     * Hibernate: select p1_0.id,p1_0.parent_name from parents p1_0 where p1_0.id=?
     * Hibernate: select p1_0.id,p1_0.parent_name from parents p1_0 where p1_0.id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * @return
     */
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<ChildEntity> all = childEntityRepository.findAll();
        return ResponseEntity.ok(all);
    }

    /**
     * Hibernate: select distinct c1_0.id,c1_0.child_name,c1_0.parent_id,p1_0.id,p1_0.parent_name from children c1_0 join parents p1_0 on p1_0.id=c1_0.parent_id
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * @return
     */
    @GetMapping("/findAllChildren")//CUSTOM
    public ResponseEntity<?> findAllChildren() {
        List<ChildEntity> all = childEntityRepository.findAllChildren();
        return ResponseEntity.ok(all);
    }

// ------------------------------------------------------

    /**
     * Hibernate: select c1_0.id,c1_0.child_name,c1_0.parent_id,p1_0.id,p1_0.parent_name from children c1_0 join parents p1_0 on p1_0.id=c1_0.parent_id where c1_0.id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * @param childId
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long childId) {
        ChildEntity childEntity = childEntityRepository.findById(childId)
                .orElseThrow(() -> new IllegalStateException("Child id not found!" + childId));
        return ResponseEntity.ok(childEntity);
    }

    /**
     * Hibernate: select c1_0.id,c1_0.child_name,c1_0.parent_id,p1_0.id,p1_0.parent_name from children c1_0 join parents p1_0 on p1_0.id=c1_0.parent_id where c1_0.id=?
     * Hibernate: select c1_0.parent_id,c1_0.id,c1_0.child_name from children c1_0 where c1_0.parent_id=?
     * @param childId
     * @return
     */
    @GetMapping("/findChildById/{id}")//CUSTOM
    public ResponseEntity<?> findChildById(@PathVariable("id") Long childId) {
        ChildEntity childEntity = childEntityRepository.findChildById(childId)
                .orElseThrow(() -> new IllegalStateException("Child id not found!" + childId));
        return ResponseEntity.ok(childEntity);
    }

/////////////////////////////////////////////////////////////////////////////////

//    @GetMapping("/dtos/{id}")
//    public ResponseEntity<?> getChildDtosByParentId(@PathVariable("id") Long parentId) {
//        List<ChildDto> childDtos = childEntityRepository.getChildrendDtoByParentId(parentId);
//        return ResponseEntity.ok(childDtos);
//    }


}
