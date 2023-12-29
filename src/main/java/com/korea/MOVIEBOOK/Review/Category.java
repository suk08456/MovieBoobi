package com.korea.MOVIEBOOK.Review;

import lombok.Getter;

@Getter
public enum Category {
    movie("영화"),
    tv("TV"),
    book("책"),
    webtoon("웹툰");

    Category(String value){
        this.value = value;
    }

    private String value;
}
