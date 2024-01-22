package com.korea.MOVIEBOOK.comment;

import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.review.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String content;                 // 댓글 내용

    private LocalDateTime dateTime;   // 댓글 작성 일자

    @ManyToOne
    private Member member;

    @ManyToOne
    private Review review;
}
