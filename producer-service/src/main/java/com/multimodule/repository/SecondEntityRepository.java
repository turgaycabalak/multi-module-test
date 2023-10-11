package com.multimodule.repository;

import com.multimodule.model.SecondEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SecondEntityRepository extends JpaRepository<SecondEntity, Long> {

    //Hibernate: select count(*) from second_entity s1_0
    long count();


    //Hibernate: select s1_0.id,s1_0.first_name,s1_0.last_name from second_entity s1_0 where exists(select 1 from ifrs_payment i1_0 where i1_0.user_id=?)
    @Query("SELECT se FROM SecondEntity se WHERE EXISTS (SELECT 1 FROM IfrsPayment ifrs WHERE ifrs.userId =:userid)")
    List<SecondEntity> findSecondEntitiesByIfrsUserId(@Param("userid") String userId);

}
