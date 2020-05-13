package com.turbid.explore.repository;

import com.turbid.explore.pojo.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,String> {

    @Query("select count(a) from Answer a where a.qaacode=:code")
    int answerCount(@Param("code") String code);

    @QueryHints(value = { @QueryHint(name = "query", value = "a query for pageable")})
    @Query("SELECT a from Answer a where a.qaacode=:code ")
    Page<Answer> answersByQaacode(Pageable pageable,@Param("code")String code);

    @QueryHints(value = { @QueryHint(name = "query", value = "a query for pageable")})
    @Query("SELECT a from Answer a where a.userSecurity.phonenumber=:name ")
    Page<Answer> listByUser(Pageable pageable, @Param("name")String name);
}
