package com.korea.MOVIEBOOK.review;

import com.korea.MOVIEBOOK.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByDramaId(Long dramaId);
    Page<Review> findByDramaId(Long dramaId, Pageable pageable);
    List<Review> findReviewsByMovieId(Long id);

    List<Review> findReviewsByWebtoonId(Long webtoonId);

    Long countByMember(Member member);
}
