package com.korea.MOVIEBOOK.Movie.Daily;

import com.korea.MOVIEBOOK.Movie.Movie.Movie;
import com.korea.MOVIEBOOK.Movie.Movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;

@RequiredArgsConstructor
@Service
public class MovieDailyService {
    private final MovieDailyRepository movieDailyRepository;
    private final MovieService movieService;

    public void add(String title, Long rank, String date){
        Movie movie = this.movieService.findMovie(title);
        MovieDaily movieDaily = new MovieDaily();
        movieDaily.setMovie(movie);
        movieDaily.setRank(rank);
        movieDaily.setDate(date);
        this.movieDailyRepository.save(movieDaily);
    }
    public List<MovieDaily> findMovies(String date){
        return this.movieDailyRepository.findBydate(date);
    }


}
