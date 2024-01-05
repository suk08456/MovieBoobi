package com.korea.MOVIEBOOK.Webtoon.Days;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DayService {
    private final DayRepository dayRepository;
    public Day setDays(String date) {
        Day day = new Day();
//        day.setWebtoonList(null);
//        day.setWebtoonDayList(null);
        day.setUpdateDays(date);
        return this.dayRepository.save(day);
    }

    public Day findByDay(String day) {
        return this.dayRepository.findByupdateDays(day);
    }

    public void FindByWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("E", java.util.Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();

        // 영어로 된 요일 출력
        for (int i = 1; i <= 7; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, i);
            sdf.format(calendar.getTime());


            Day day1 = new Day();
            day1.setUpdateDays(sdf.format(calendar.getTime()));
            dayRepository.save(day1);
        }
    }
    public List<Day> findAll(){
        return this.dayRepository.findAll();
    }

    public Day findById(Long id){
        return dayRepository.findById(id).get();
    }

    public Day findIdByupdateDays(String updateDays){
        return this.dayRepository.findByUpdateDays(updateDays).get();
    }
}
