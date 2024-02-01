package com.korea.MOVIEBOOK;

import com.korea.MOVIEBOOK.movie.movie.Movie;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentsReviewService {


    public ContentsReviewDTO setContentsReviewDTOList(List<List<Movie>> movie, List<Integer> reviewCountOfMovie) {
        return ContentsReviewDTO.builder()
                .movieList(movie)
                .reviewCountOfMovie(reviewCountOfMovie)
                .build();
    }
}
