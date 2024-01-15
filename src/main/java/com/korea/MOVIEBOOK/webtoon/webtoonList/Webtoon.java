package com.korea.MOVIEBOOK.webtoon.webtoonList;


import com.korea.MOVIEBOOK.review.Review;
import com.korea.MOVIEBOOK.webtoon.webtoonDayList.WebtoonDayList;
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

    @Getter
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

    @OneToMany(mappedBy = "webtoon")
    private List<Review> reviewList;
}
