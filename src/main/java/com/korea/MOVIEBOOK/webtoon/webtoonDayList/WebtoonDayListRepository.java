package com.korea.MOVIEBOOK.webtoon.webtoonDayList;

import com.korea.MOVIEBOOK.webtoon.days.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebtoonDayListRepository extends JpaRepository<WebtoonDayList, Long> {

    List<WebtoonDayList> findBywebtoonDay(Day day);

//    List<WebtoonDayList> findByWebtoon(Webtoon webtoon);
}
