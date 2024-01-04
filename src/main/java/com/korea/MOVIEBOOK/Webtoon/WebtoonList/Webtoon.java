package com.korea.MOVIEBOOK.Webtoon.WebtoonList;


import com.korea.MOVIEBOOK.Webtoon.WebtoonDayList.WebtoonDayList;
import com.korea.MOVIEBOOK.Webtoon.Days.Day;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Webtoon {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer fanCount;

    private Long webtoonId;

    private String title;

    private String author;

    private String img;

    private String searchKeyword;

    private String detailUrl;

    @ManyToOne
    private Day day;

    @ManyToOne
    private WebtoonDayList webtoonDayList;
}
