package com.korea.MOVIEBOOK.webtoon.days;


import com.korea.MOVIEBOOK.webtoon.webtoonDayList.WebtoonDayList;
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

    private String updateDays;

    @OneToMany(mappedBy = "webtoonDay")
    private List<WebtoonDayList> webtoonDayLists;
}
