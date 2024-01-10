package com.korea.MOVIEBOOK.review;

import com.korea.MOVIEBOOK.movie.movie.Movie;
import com.korea.MOVIEBOOK.movie.movie.MovieService;
import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.book.BookRepository;
import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import com.korea.MOVIEBOOK.webtoon.webtoonList.WebtoonService;
import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.member.MemberService;
import lombok.RequiredArgsConstructor;
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

    public void saveMovieReview(String movieCD, String comment, Double rating){
        Movie movie =  this.movieService.findMovieByCD(movieCD);
        Review review = new Review();
        review.setMovie(movie);
        review.setComment(comment);
        review.setCategory("movie");
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

//    public List<com.korea.MOVIEBOOK.dramaReview.Review> getReviewsByDramaId(Long dramaId) {
//        return reviewRepository.findByDramaId(dramaId);
//    }
//
//    public com.korea.MOVIEBOOK.dramaReview.Review findReviewById(Long id) {
//        return reviewRepository.findById(id).orElse(null);
//    }
//
//    public void addReview(com.korea.MOVIEBOOK.dramaReview.Review review) {
//        reviewRepository.save(review);
//    }
//
//    public void saveReview(Review review) {
//        // Review 저장 로직
//        reviewRepository.save(review);
}
