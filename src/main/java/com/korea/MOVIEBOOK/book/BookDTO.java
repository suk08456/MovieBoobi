package com.korea.MOVIEBOOK.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BookDTO {
    private String title;   //  제목
    private String author;  //  작가
    private String plot; //  요약
    private String isbn;    //  10자리 코드
    private String isbn13;  //  13자리 코드(가급적 13자리 코드사용)
    private String imageUrl;   //  표지
    private String publisher;   //  출판사
    private Boolean isNewBook;    //  신간인지 확인하는 변수
    private Integer priceStandard;   //  정가
    private Integer bestRank;  //  베스트셀러 순위
    private LocalDate pubDate; //  출간일.
}
