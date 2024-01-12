package com.korea.MOVIEBOOK.review;

import com.korea.MOVIEBOOK.drama.Drama;
import com.korea.MOVIEBOOK.drama.DramaService;
import com.korea.MOVIEBOOK.movie.movie.Movie;
import com.korea.MOVIEBOOK.movie.movie.MovieService;
import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.book.BookRepository;
import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import com.korea.MOVIEBOOK.webtoon.webtoonList.WebtoonService;
import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieService movieService;
    private final BookRepository bookRepository;
    private final WebtoonService webtoonService;
    private final MemberService memberService;
    private final DramaService dramaService;

    public void saveMovieReview(String movieCD, String comment, Double rating){
        Movie movie =  this.movieService.findMovieByCD(movieCD);
        Review review = new Review();
        review.setMovie(movie);
        review.setComment(comment);
        review.setCategory("movie");
        review.setRating(rating);
        this.reviewRepository.save(review);
    }

    public void saveDramaReview(Long dramaId, String comment, Double rating){
        Drama drama = this.dramaService.getDramaById(dramaId);
        Review review = new Review();
        review.setDrama(drama);
        review.setComment(comment);
        review.setCategory("drama");
        review.setRating(rating);
        this.reviewRepository.save(review);
    }

    public void saveBookReview(String isbn, String comment, Double rating){
        Book book = bookRepository.findByIsbn(isbn);
        Review review = new Review();
        review.setBook(book);
        review.setComment(comment);
        review.setCategory("book");
        review.setRating(rating);
        this.reviewRepository.save(review);
    }

    public List<Review> findReviews(Long id){
       return this.reviewRepository.findReviewsByMovieId(id);
    }

    public void saveWebtoonReview(Long webtoonId, String comment, Double rating) {
        Webtoon webtoon = this.webtoonService.findWebtoonByWebtoonId(webtoonId);
        Review review = new Review();
        review.setWebtoon(webtoon);
        review.setComment(comment);
        review.setCategory("webtoon");
        review.setRating(rating);
        this.reviewRepository.save(review);
    }

    public List<Review> findWebtoonReview(Long webtoonId){
        Webtoon webtoon = webtoonService.findWebtoonByWebtoonId(webtoonId);
//        WebtoonService.findById();
        List<Review> reviews = this.reviewRepository.findReviewsByWebtoonId(webtoon.getId());
        return reviews;
    }

    public Long getReivewCount(Member member) {
        return reviewRepository.countByMember(member);
    }

    public List<Review> getReviewByDramaId(Long dramaId) {
        return reviewRepository.findByDramaId(dramaId);
    }
    public Review findReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }
    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }


    public Page<Review> getReviewByDramaIdWithPagination(Long dramaId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return reviewRepository.findByDramaId(dramaId, pageable);
    }

    public Long getDramaIdByReviewId(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다.")); // 해당 ID의 리뷰를 찾습니다.
        return review.getDrama().getId(); // 리뷰가 속한 드라마의 ID를 반환합니다.
    }

    public void updateReview(Review updateReview) {
        Review existingReview = reviewRepository.findById(updateReview.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + updateReview.getId()));
        existingReview.setComment(updateReview.getComment());
        existingReview.setRating(updateReview.getRating());
        reviewRepository.save(existingReview);
    }

}
