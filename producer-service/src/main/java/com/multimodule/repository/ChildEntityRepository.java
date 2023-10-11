package com.multimodule.repository;

import com.multimodule.model.ChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChildEntityRepository extends JpaRepository<ChildEntity, Long> {

    @Query("select distinct ce from ChildEntity ce join fetch ce.parentEntity pe")
    List<ChildEntity> findAllChildren();

    @Query("select ce from ChildEntity ce join fetch ce.parentEntity pe where ce.id = :childid")
    Optional<ChildEntity> findChildById(@Param("childid") Long childId);



//    @Query("select new com.multimodule.dto.ChildDto(" +
//            "ce.id, " +
//            "ce.childUnique, " +
//            "ce.childName, " +
//            "ce.childDescription, " +
//            "ce.childNumber, " +
//            "ce.childStatus) " +
//            "from ChildEntity ce where ce.parentEntity.id = :parentid")
//    List<ChildDto> getChildrendDtoByParentId(@Param("parentid") Long parentId);





}
