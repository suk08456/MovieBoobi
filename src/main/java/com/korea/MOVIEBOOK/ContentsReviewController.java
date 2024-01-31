package com.korea.MOVIEBOOK;

import com.korea.MOVIEBOOK.movie.movie.Movie;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ContentsReviewController {


    public ContentsReviewDTO setContentsReviewDTOList(List<List<Movie>> movie, List<Integer> reviewCountOfMovie) {
        return ContentsReviewDTO.builder()
                .movieList(movie)
                .reviewCountOfMovie(reviewCountOfMovie)
                .build();
    }
}
