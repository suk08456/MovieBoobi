package com.korea.MOVIEBOOK.Movie.Movie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    Movie findByTitleAndNationsAndReleaseDate(String movieNm, String nations, String releaseDate);
    Movie findByTitle(String title);

    Movie findBymovieCode(String movieCode);
}
