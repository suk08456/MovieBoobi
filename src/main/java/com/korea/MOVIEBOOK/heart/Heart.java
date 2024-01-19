package com.korea.MOVIEBOOK.heart;

import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.review.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String category;

    private String contentsID;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Review review;
}
