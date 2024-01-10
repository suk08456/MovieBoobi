package com.korea.MOVIEBOOK.webtoon.days;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DayRepository extends JpaRepository<Day, Long> {
    Day findByupdateDays(String updateDays);
    Optional<Day> findByUpdateDays(String updateDays);
    Optional<Day> findById(Long Id);
}
