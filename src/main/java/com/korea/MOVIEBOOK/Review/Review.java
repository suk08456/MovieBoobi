package com.korea.MOVIEBOOK.Review;

import com.korea.MOVIEBOOK.Movie.Movie.Movie;
<<<<<<< HEAD
import com.korea.MOVIEBOOK.book.Book;
=======
import com.korea.MOVIEBOOK.Webtoon.WebtoonList.Webtoon;
>>>>>>> 4ef340bfc4f6dca1be1d580eb15b1dafa1fa88be
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Category;         // movie, tv, book, webtoon

    @Column(length = 1000)            // 1000 char로 제한
    private String comment;         // 리뷰 내용

    private Double rating;            // 리뷰 평점

    @ManyToOne
    Movie movie;

    @ManyToOne
    Book book;


//
//    @ManyToOne
//    Drama drama;
//
    @ManyToOne
    Webtoon webtoon;

}
