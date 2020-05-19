package com.turbid.explore.repository;

import com.turbid.explore.pojo.Study;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

@Repository
public interface StudyRepository extends JpaRepository<Study,String> {

    @QueryHints(value = { @QueryHint(name = "query", value = "a query for pageable")})
    @Query("SELECT new Study (s.code,s.create_time,s.indeximgurl,s.seecount,s.price,s.pricetype,s.shb,s.title,s.type) from Study s where (s.type =:style or :style is null ) ")
    Page<Study> listByPage(Pageable pageable,@Param("style") String style);

    @QueryHints(value = { @QueryHint(name = "query", value = "a query for pageable")})
    @Query("SELECT new Study (s.code,s.create_time,s.indeximgurl,s.seecount,s.price,s.pricetype,s.shb,s.title,s.type) from Study s ")
    Page<Study> hatstudyByPage(Pageable pageable);
}