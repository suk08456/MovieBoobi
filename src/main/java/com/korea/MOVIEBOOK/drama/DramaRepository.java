package com.korea.MOVIEBOOK.drama;

import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DramaRepository extends JpaRepository<Drama, Long> {



    Page<Drama> findAll(Pageable pageable);


    @Query("SELECT d FROM Drama d " +
            "WHERE d.title LIKE %:kw% " +
            "   OR d.actor LIKE %:kw% ")
    Page<Drama> findAllByDramaKeyword(@Param("kw") String kw, Pageable pageable);
}

