package com.korea.MOVIEBOOK.review;

import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.drama.Drama;
import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.movie.movie.Movie;
import com.korea.MOVIEBOOK.payment.Payment;
import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByDramaId(Long dramaId);
    List<Review> findReviewsByMovieId(Long id);
    List<Review> findReviewsByWebtoonId(Long webtoonId);
    List<Review> findReviewsByBookId(Long bookId);
    Long countByMember(Member member);
    Page<Review> findByMovie(Movie movie, Pageable pageable);
    Page<Review> findByBook(Book book, Pageable pageable);
    Page<Review> findByDrama(Drama drama, Pageable pageable);
    Page<Review> findByWebtoon(Webtoon webtoon, Pageable pageable);

}
