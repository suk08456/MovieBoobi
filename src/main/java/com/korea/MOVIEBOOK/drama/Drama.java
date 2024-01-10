package com.korea.MOVIEBOOK.drama;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter

public class Drama {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id; // 고유 값
    private String title; // 제목
    private String imageUrl; // 포스터
    private String genre; // 장르
    private String releaseDate; // 개봉일
    private String company; // 제작사
    private String director; // 감독
    private String viewingRating; // 연령등급
    private String actor; // 배우
    private int rating; // 평점
    private int rank; // 순위

//    @OneToMany(mappedBy = "drama", cascade = CascadeType.ALL)
//    private List<Review> reviews;

}
