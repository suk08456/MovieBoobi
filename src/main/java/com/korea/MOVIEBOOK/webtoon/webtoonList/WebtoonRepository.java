package com.korea.MOVIEBOOK.webtoon.webtoonList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

//    List<Webtoon> findBywebtoonDay(String day);

    Webtoon findByWebtoonId(Long webtoonId);


}
