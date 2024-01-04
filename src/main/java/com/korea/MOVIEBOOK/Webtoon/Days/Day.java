package com.korea.MOVIEBOOK.Webtoon.Days;


import com.korea.MOVIEBOOK.Webtoon.WebtoonDayList.WebtoonDayList;
import com.korea.MOVIEBOOK.Webtoon.WebtoonList.Webtoon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "update_days")
    private String updateDays;

    @OneToMany(mappedBy = "webtoonDay")
    private List<Webtoon> webtoonList;

    @ManyToOne
    private WebtoonDayList webtoonDayList;
}
