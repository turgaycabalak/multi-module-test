package com.multimodule.repository;

import com.multimodule.model.IfrsPayment;
import com.multimodule.model.SecondEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IfrsPaymentRepository extends JpaRepository<IfrsPayment, Long> {

    //Hibernate: select count(*) from second_entity s1_0
    long count();


    //Hibernate: select count(*)>0 from ifrs_payment i1_0 where i1_0.user_id=?
    @Query("select count(*)>0 from IfrsPayment ifrs where ifrs.userId=:userid")
//    @Query("select 1 from IfrsPayment ifrs where ifrs.userId=:userid")
//    @Query("select count(*) from IfrsPayment ifrs where ifrs.userId=:userid")
//              @Query("select (select * from User) from IfrsPayment ifrs where ifrs.userId=:userid")
    boolean testExistByUserId(@Param("userid") String userId);


    //Hibernate: select i1_0.id from ifrs_payment i1_0 where i1_0.user_id=? fetch first ? rows only
    boolean existsByUserId(String userId);

    //Hibernate: select 'VAR' from ifrs_payment i1_0 where i1_0.user_id=?
    @Query("select 'VAR' from IfrsPayment ifrs where ifrs.userId=:userid")
    String testForConditionalQuery(@Param("userid") String userId);


    //FAILED!
    //Hibernate: select (select s1_0.id from second_entity s1_0) from ifrs_payment i1_0 where i1_0.user_id=?
//    @Query("select (select se from SecondEntity se where se.id=1) from IfrsPayment ifrs where ifrs.userId=:userid")
//    @Query("select (select se from SecondEntity se) from IfrsPayment ifrs where ifrs.userId=:userid")
//    List<SecondEntity> testForConditionalQuery2(@Param("userid") String userId);


//    //Hibernate: select s1_0.id,s1_0.first_name,s1_0.last_name from second_entity s1_0 where exists(select 1 from ifrs_payment i1_0 where i1_0.user_id=?)
//    @Query("SELECT se FROM SecondEntity se WHERE EXISTS (SELECT 1 FROM IfrsPayment ifrs WHERE ifrs.userId =:userid)")
//    List<SecondEntity> findSecondEntitiesByIfrsUserId(@Param("userid") String userId);


//    @Query("select new com.multimodule.dto.IfrsPaymentQueryDto() from IfrsPayment ifp where")
//    List<IfrsPayment> testForConditionalQuery2(@Param("userid") String userId);



    //  EXAMPLE FOR COMPLEX CONDITIONAL QUERY
//    @Query(value = "select new tr.com.mvc.reinsurance.agreement.ifrs.entity.IfrsReceivedPremiumPaymentDto(" +
//            "irpp.gocId, " +//gocId
//
//            "case when isp.d_augbl is not null and (isp.s_dmbtr - isp.d_dmbtr) = 0 then cast(isp.d_augdt as date) " +//period
//            "when isp.d_augbl is null and isp.a_belnr > '' then cast(isp.a_budat as date) end, " +
//
//            "sum(" +
//            "case when isp.d_augbl is not null and (isp.s_dmbtr - isp.d_dmbtr) = 0 then isp.s_dmbtr " +//dmbtrSum
//            "when isp.d_augbl is null and isp.a_belnr > '' then isp.a_dmbtr end " +
//            ")) " +
//
//            "from IfrsReceivedPremiumsPatterns irpp " +
//
//            "inner join IfrsSapPayment isp on isp.bktxt = irpp.bktxt and isp.d_augdt <> '00000000' and irpp.gocId = :gocId " +
//
//            "inner join IfrsDonemDnm ifd on ifd.period1 < (case when isp.d_augbl is not null and (isp.s_dmbtr - isp.d_dmbtr) = 0 then cast(isp.d_augdt as date) when isp.d_augbl is null and isp.a_belnr > '' then cast(isp.a_budat as date) end) " +
//            "and ifd.period2 >= (case when isp.d_augbl is not null and (isp.s_dmbtr - isp.d_dmbtr) = 0 then cast(isp.d_augdt as date) when isp.d_augbl is null and isp.a_belnr > '' then cast(isp.a_budat as date) end) " +
//
//            "group by irpp.gocId, (case when isp.d_augbl is not null and (isp.s_dmbtr - isp.d_dmbtr) = 0 then cast(isp.d_augdt as date) when isp.d_augbl is null and isp.a_belnr > '' then cast(isp.a_budat as date) end) " +
//            "order by irpp.gocId, (case when isp.d_augbl is not null and (isp.s_dmbtr - isp.d_dmbtr) = 0 then cast(isp.d_augdt as date) when isp.d_augbl is null and isp.a_belnr > '' then cast(isp.a_budat as date) end)")
//    List<IfrsReceivedPremiumPaymentDto> getIfrsReceivedPremiumPaymentDto(@Param("gocId") String gocId);

}
