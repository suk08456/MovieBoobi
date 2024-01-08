package com.korea.MOVIEBOOK.Webtoon.WebtoonList;

import com.korea.MOVIEBOOK.Webtoon.Days.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

//    List<Webtoon> findBywebtoonDay(String day);

    Webtoon findByWebtoonId(Long webtoonId);
}
