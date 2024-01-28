package com.korea.MOVIEBOOK.heart;

import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.book.BookRepository;
import com.korea.MOVIEBOOK.drama.Drama;
import com.korea.MOVIEBOOK.drama.DramaRepository;
import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.member.MemberService;
import com.korea.MOVIEBOOK.movie.movie.Movie;
import com.korea.MOVIEBOOK.movie.movie.MovieRepository;
import com.korea.MOVIEBOOK.review.Review;
import com.korea.MOVIEBOOK.review.ReviewRepository;
import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import com.korea.MOVIEBOOK.webtoon.webtoonList.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class HeartService {
    private final HeartRepository heartRepository;
    private final ReviewRepository reviewRepository;
    private final MemberService memberService;
    private final BookRepository bookRepository;
    private final DramaRepository dramaRepository;
    private final MovieRepository movieRepository;
    private final WebtoonRepository webtoonRepository;
    public void plusReviewHeart(Principal principal, String category, String contentsID, String reviewId){
        Review review = this.reviewRepository.findById(Long.valueOf(reviewId)).get();
        String providerID = principal.getName();
        Member member = this.memberService.findByproviderId(providerID);
        if (member == null) {
            member = this.memberService.getMember(providerID);
        }
        Heart heart = new Heart();
        heart.setCategory(category);
        heart.setContentsID(contentsID);
        heart.setReview(review);
        heart.setMember(member);
        this.heartRepository.save(heart);
    }

    public void minusReviewHeart(Principal principal, String reviewId){
        Review review = this.reviewRepository.findById(Long.valueOf(reviewId)).get();
        String providerID = principal.getName();
        Member member = this.memberService.findByproviderId(providerID);
        if (member == null) {
            member = this.memberService.getMember(providerID);
        }
        Heart heart = this.heartRepository.findByMemberAndReview(member,review);
        this.heartRepository.delete(heart);
    }

    public void plusContentsHeart(Principal principal, String category, String contentsID){
        String providerID = principal.getName();
        Member member = this.memberService.findByproviderId(providerID);
        if (member == null) {
            member = this.memberService.getMember(providerID);
        }
        Heart heart = new Heart();
        heart.setCategory(category);
        heart.setContentsID(contentsID);
        heart.setMember(member);
        if(Objects.equals(category, "book")){
            heart.setBook(this.bookRepository.findByIsbn(contentsID));
        } else if (Objects.equals(category, "drama")){
            heart.setDrama(this.dramaRepository.findById(Long.valueOf(contentsID)).get());
        } else if (Objects.equals(category, "movie")){
            heart.setMovie(this.movieRepository.findBymovieCode(contentsID));
        } else if (Objects.equals(category, "webtoon")){
            heart.setWebtoon(this.webtoonRepository.findByWebtoonId(Long.valueOf(contentsID)));
        }
        this.heartRepository.save(heart);
    }

    public void minusContentsHeart(Principal principal, String category, String contentsID){
        String providerID = principal.getName();
        Member member = this.memberService.findByproviderId(providerID);
        if (member == null) {
            member = this.memberService.getMember(providerID);
        }

        Heart heart = null;
        if(Objects.equals(category, "book")){
            Book book = this.bookRepository.findByIsbn(contentsID);
            heart = this.heartRepository.findByMemberAndBook(member,book);
        } else if (Objects.equals(category, "drama")){
            Drama drama = this.dramaRepository.findById(Long.valueOf(contentsID)).get();
            heart = this.heartRepository.findByMemberAndDrama(member,drama);
        } else if (Objects.equals(category, "movie")){
            Movie movie = this.movieRepository.findBymovieCode(contentsID);
            heart = this.heartRepository.findByMemberAndMovie(member,movie);
        } else if (Objects.equals(category, "webtoon")){
            Webtoon webtoon = this.webtoonRepository.findByWebtoonId(Long.valueOf(contentsID));
            heart = this.heartRepository.findByMemberAndWebtoon(member, webtoon);
        }
        this.heartRepository.delete(heart);
    }


    public List<Heart> findHeartList(Review review){
       return this.heartRepository.findByReview(review);
    }

}
