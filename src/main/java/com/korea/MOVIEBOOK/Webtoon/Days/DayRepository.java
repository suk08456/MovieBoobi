package com.korea.MOVIEBOOK.Webtoon.Days;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Long> {
    Day findByDay(String day);
}
