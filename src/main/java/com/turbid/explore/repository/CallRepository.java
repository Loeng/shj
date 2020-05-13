package com.turbid.explore.repository;

import com.turbid.explore.pojo.Call;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

@Repository
public interface CallRepository extends JpaRepository<Call,String> {

    @QueryHints(value = { @QueryHint(name = "query", value = "a query for pageable")})
    @Query("SELECT c from Call c where c.usercode =:name ")
    Page<Call> listByUserMy(Pageable pageable,@Param("name") String name);

    @QueryHints(value = { @QueryHint(name = "query", value = "a query for pageable")})
    @Query("SELECT c from Call c where c.callusercode =:name ")
    Page<Call> listByUserMe(Pageable pageable,@Param("name") String name);
}
