package com.korea.MOVIEBOOK.book;

import com.korea.MOVIEBOOK.review.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;   //  제목
    private String author;  //  작가
    private String description; //  요약
    private String isbn;    //  10자리 코드
    private String isbn13;  //  13자리 코드(가급적 13자리 코드사용)
    private String cover;   //  표지
    private String publisher;   //  출판사
    private Boolean isNewBook;    //  신간인지 확인하는 변수
    private Integer pricestandard;   //  정가
    private Integer bestRank;  //  베스트셀러 순위
    private LocalDate pubdate; //  출간일
    private LocalDate addDate;  //  추가일자
    @OneToMany(mappedBy = "book")
    private List<Review> reviewList;
}
