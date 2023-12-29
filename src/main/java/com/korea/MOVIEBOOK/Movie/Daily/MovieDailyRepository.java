package com.korea.MOVIEBOOK.Movie.Daily;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieDailyRepository extends JpaRepository<MovieDaily,Long> {
    List<MovieDaily> findBydate(String date);

}
