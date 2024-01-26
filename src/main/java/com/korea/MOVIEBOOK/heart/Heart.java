package com.korea.MOVIEBOOK.heart;

import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.drama.Drama;
import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.movie.movie.Movie;
import com.korea.MOVIEBOOK.review.Review;
import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String category;

    private String contentsID;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Review review;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Drama drama;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Webtoon webtoon;
}
