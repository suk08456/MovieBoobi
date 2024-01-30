package com.korea.MOVIEBOOK;

import com.korea.MOVIEBOOK.movie.movie.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ContentsReviewDTO {
    List<List<Movie>> movieList;

    List<Integer> reviewCountOfMovie;
}
