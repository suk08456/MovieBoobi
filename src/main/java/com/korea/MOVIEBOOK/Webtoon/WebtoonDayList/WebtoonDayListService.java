package com.korea.MOVIEBOOK.Webtoon.WebtoonDayList;


import com.korea.MOVIEBOOK.Webtoon.Days.Day;
import com.korea.MOVIEBOOK.Webtoon.Days.DayService;
import com.korea.MOVIEBOOK.Webtoon.WebtoonList.Webtoon;
import com.korea.MOVIEBOOK.Webtoon.WebtoonList.WebtoonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class WebtoonDayListService {

    private final WebtoonService webtoonService;
    private final DayService dayService;
    private final WebtoonDayListRepository webtoonDayListRepository;

    public void SaveWebtoonDayList(Long WebtoonDayId, List<Long> WebtoonListId){
        Day day1 = dayService.findById(WebtoonDayId);
        for(Long webtoonList : WebtoonListId){
            WebtoonDayList webtoonDayList = new WebtoonDayList();
            Webtoon webtoon = webtoonService.findWebtoonByWebtoonId(webtoonList);
            webtoonDayList.setWebtoonList(webtoon);
            webtoonDayList.setWebtoonDay(day1);
            webtoonDayListRepository.save(webtoonDayList);
        }
    }


    public List<WebtoonDayList> findBywebtoonDay(Day day) {
        List<WebtoonDayList> webtoonDayLists = webtoonDayListRepository.findBywebtoonDay(day);

        // 명시적으로 WebtoonDayList 타입으로 캐스팅
        webtoonDayLists.sort(Comparator.comparingInt(
                webtoonDayList -> {
                    Integer fanCount = ((WebtoonDayList) webtoonDayList).getWebtoonList().getFanCount();
                    return fanCount != null ? fanCount : 0;
                }).reversed());

        return webtoonDayLists;
    }

//    public List<WebtoonDayList> findBywebtoonDay(Day day){
//       return webtoonDayListRepository.findBywebtoonDay(day);
//    }

//    public List<WebtoonDayList> findBywebtoon(Webtoon webtoon){
//        return webtoonDayListRepository.findByWebtoon(webtoon);
//    }
}
