package com.korea.MOVIEBOOK.Review;

import com.korea.MOVIEBOOK.Drama.Drama;
import com.korea.MOVIEBOOK.Movie.Daily.MovieDaily;
import com.korea.MOVIEBOOK.Movie.Movie.Movie;
import com.korea.MOVIEBOOK.Webtoon.WebtoonList.Webtoon;
import com.korea.MOVIEBOOK.book.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import java.util.List;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Category;         // movie, tv, book, webtoon

    @Column(length=1000)            // 1000 char로 제한
    private String comment;         // 리뷰 내용

    private Double rating;            // 리뷰 평점

    @ManyToOne
    Movie movie;

//    @ManyToOne
//    Book book;
//
//    @ManyToOne
//    Drama drama;
//
//    @ManyToOne
//    Webtoon webtoon;

}
