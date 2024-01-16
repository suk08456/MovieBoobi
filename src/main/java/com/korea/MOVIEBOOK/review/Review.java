package com.korea.MOVIEBOOK.review;

import com.korea.MOVIEBOOK.drama.Drama;
import com.korea.MOVIEBOOK.movie.movie.Movie;
import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import com.korea.MOVIEBOOK.member.Member;
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
    private Member member;

    @ManyToOne
    Movie movie;

    @ManyToOne
    Book book;

    @ManyToOne
    Drama drama;

    @ManyToOne
    Webtoon webtoon;

}
