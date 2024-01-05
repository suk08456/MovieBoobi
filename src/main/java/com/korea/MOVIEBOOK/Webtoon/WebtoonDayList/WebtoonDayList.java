package com.korea.MOVIEBOOK.Webtoon.WebtoonDayList;


import com.korea.MOVIEBOOK.Webtoon.Days.Day;
import com.korea.MOVIEBOOK.Webtoon.WebtoonList.Webtoon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class WebtoonDayList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Day webtoonDay;

    @ManyToOne
    private Webtoon webtoonList;
}
