package com.korea.MOVIEBOOK.heart;

import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.drama.Drama;
import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.movie.movie.Movie;
import com.korea.MOVIEBOOK.review.Review;
import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart,Long> {
    Heart findByMemberAndReview(Member member, Review review);
    List<Heart> findByReview(Review review);

    Heart findByMemberAndBook(Member member, Book book);
    Heart findByMemberAndMovie(Member member, Movie movie);
    Heart findByMemberAndDrama(Member member, Drama drama);
    Heart findByMemberAndWebtoon(Member member, Webtoon webtoon);
}
