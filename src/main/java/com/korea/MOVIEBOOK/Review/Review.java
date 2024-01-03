package com.korea.MOVIEBOOK.Review;

import com.korea.MOVIEBOOK.Drama.Drama;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gubun;           // movie, tv, book, webtoon

    private String title;           // 리뷰 제목

    private String comment;         // 리뷰 내용

    private Long rating;            // 리뷰 평점

//    @ManyToOne
//    MovieDaily movieDaily;

}
