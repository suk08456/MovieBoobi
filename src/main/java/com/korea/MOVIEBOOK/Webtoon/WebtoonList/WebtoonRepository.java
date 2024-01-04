package com.korea.MOVIEBOOK.Webtoon.WebtoonList;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

    List<Webtoon> findByUpdateDays(String day);

    Webtoon findByWebtoonId(Long webtoonId);
}
