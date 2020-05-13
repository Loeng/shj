package com.turbid.explore.repository;

import com.turbid.explore.pojo.NeedsRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NeedsRelationRepositroy extends JpaRepository<NeedsRelation,String> {

    @Query("select count(n) from NeedsRelation n where n.phone=:name and n.needsorderno=:code and n.status=1")
    int findneedsR(@Param("name") String name,@Param("code") String code);

    @Modifying
    @Query("update NeedsRelation p set p.status=1 where p.orderno=:orderno")
    int updateSEE(@Param("orderno") String orderno);
}
