package com.korea.MOVIEBOOK.Webtoon.WebtoonDayList;

import com.korea.MOVIEBOOK.Webtoon.Days.Day;
import com.korea.MOVIEBOOK.Webtoon.WebtoonList.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebtoonDayListRepository extends JpaRepository<WebtoonDayList, Long> {

    List<WebtoonDayList> findBywebtoonDay(Day day);

//    List<WebtoonDayList> findByWebtoon(Webtoon webtoon);
}
