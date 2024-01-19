package com.korea.MOVIEBOOK.heart;

import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart,Long> {
    Heart findByMemberAndReview(Member member, Review review);

    List<Heart> findByReview(Review review);
}
