package com.korea.MOVIEBOOK.Webtoon.WebtoonList;


import com.korea.MOVIEBOOK.Webtoon.WebtoonDayList.WebtoonDayList;
import com.korea.MOVIEBOOK.Webtoon.Days.Day;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

//    @Column(name = "update_days")
//    private String updateDays;


    private String detailUrl;

    @OneToMany(mappedBy = "webtoonList")
    private List<WebtoonDayList> webtoonDayLists;
}
