package com.multimodule.repository;

import com.multimodule.dto.ParentDto;
import com.multimodule.dto.ParentDtoWithChildrenDto;
import com.multimodule.dto.ParentQueryDto;
import com.multimodule.model.ParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ParentEntityRepository extends JpaRepository<ParentEntity, Long> {

    @Query("select distinct pe from ParentEntity pe join fetch pe.childEntities ce")
    List<ParentEntity> findAllParents();

    @Query("select pe from ParentEntity pe join fetch pe.childEntities ce where pe.id = :parentid")
    Optional<ParentEntity> findParentById(@Param("parentid") Long parentId);

    @Query("select new com.multimodule.dto.ParentQueryDto(pe.id, pe.parentName) from ParentEntity pe where pe.id = :parentid")
    Optional<ParentQueryDto> findParentQueryDtoById(@Param("parentid") Long parentId);
//----------------------------------------------------------------------------------

    //HATA
    @Query("select new com.multimodule.dto.ParentDtoWithChildrenDto(pe.id, pe.parentName, ce.childName) " +
            "from ParentEntity pe " +
            "join pe.childEntities ce " +
            "where pe.id = :parentid")
    Optional<ParentDtoWithChildrenDto> findParentDtoWithChildrenDtoById(@Param("parentid") Long parentId);

//    @Query("select new com.multimodule.dto.ParentDto(pe.id, pe.parentName, ce.id, ce.childName) " +
//            "from ChildEntity ce " +
//            "join ce.parentEntity pe")
//    List<ParentDto> findAllParentDtos();

    /**
     * [
     *     {
     *         "parentId": 1,
     *         "parentName": "parent-unique-1",
     *         "childName": "child-unique-1"
     *     },
     *     {
     *         "parentId": 1,
     *         "parentName": "parent-unique-1",
     *         "childName": "child-unique-2"
     *     }
     * ]
     * @param parentId
     * @return
     */
    @Query("select new com.multimodule.dto.ParentDtoWithChildrenDto(pe.id, pe.parentName, ce.childName) " +
            "from ChildEntity ce " +
            "join ce.parentEntity pe " +
            "where pe.id = :parentid")
    List<ParentDtoWithChildrenDto> findParentDtoWithChildrenDtoById2(@Param("parentid") Long parentId);

    /**
     * [
     *     {
     *         "parentId": 1,
     *         "parentName": "parent-unique-1",
     *         "childName": "child-unique-1"
     *     },
     *     {
     *         "parentId": 1,
     *         "parentName": "parent-unique-1",
     *         "childName": "child-unique-2"
     *     },
     *     {
     *         "parentId": 2,
     *         "parentName": "parent-unique-2",
     *         "childName": "child-unique-3"
     *     },
     *     ......
     *     }
     * ]
     * @return
     */
    @Query("select new com.multimodule.dto.ParentDtoWithChildrenDto(pe.id, pe.parentName, ce.childName) " +
            "from ChildEntity ce " +
            "join ce.parentEntity pe")
    List<ParentDtoWithChildrenDto> findParentDtoWithChildrenDtoById3();


    /**
     * Projection Interfaces
     */
    ParentProjection findByParentName(String parentName);

    interface ParentProjection {
        Long getId();
        String getParentName();
        boolean getParentStatus();
        List<ChildProjection> getChildEntities();


        interface ChildProjection {
            Long getId();
            String getChildName();
            boolean getChildStatus();
            BigDecimal getChildNumber();
        }
    }




}
