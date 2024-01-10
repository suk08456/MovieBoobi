package com.korea.MOVIEBOOK.webtoon.webtoonList;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class WebtoonDTO {

    private String _id;

    private Integer fanCount;

    private Long webtoonId;

    private String title;

    private String author;

    private String img;

    @Column(name = "update_days")
    private List<String> updateDays;

    private String searchKeyword;

    private String detailUrl;

}
