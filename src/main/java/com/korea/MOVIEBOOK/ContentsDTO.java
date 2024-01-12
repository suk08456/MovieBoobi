package com.korea.MOVIEBOOK;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ContentsDTO {

    private String movieCode;           // 영화 구분 코드

    private String isbn;                // 책 구분 코드

    private Long dramaCode;             // 드라마 구분 코드

    private Long wetoonCode;            // 웹툰 구분 코드

    private String title;               // 영화 제목  & 책 제목 & 드라마 제목 & 웹툰 제목

    private String author;              // 책 작가 & 웹툰 작가

    private String director;            // 영화 감독 & 드라마 감독

    private String actor;               // 영화 배우 & 드라마 배우

    private String runtime;             // 영화 상영 시간

    private String plot;                // 영화 줄거리 & 드라마 줄거리 & 책 줄거리

    private String genre;               // 영화 장르 & 드라마 장르

    private String releaseDate;         // 영화 개봉 일자 & 드라마 개봉 일자

    private String company;             // 영화 제작사 & 드라마 제작사

    private String nations;             // 영화 제작 국가

    private Long audiAcc;               // 영화 누적 관객수

    private String viewingRating;       // 영화 관람 등급 & 드라마 연령 등급

    private String publisher;           // 책 출판사

    private String imageUrl;            // 영화 포스터 & 책 표지 & 드라마 포스터  & 웹툰 포스터

    private Integer pricestandard;      // 책 가격

    private LocalDate pubdate;          // 책  출간일

    private Integer fanCount;           // 웹툰 팬


}
