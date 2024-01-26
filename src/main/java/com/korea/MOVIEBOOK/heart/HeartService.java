package com.korea.MOVIEBOOK.heart;

import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.member.MemberService;
import com.korea.MOVIEBOOK.review.Review;
import com.korea.MOVIEBOOK.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HeartService {
    private final HeartRepository heartRepository;
    private final ReviewRepository reviewRepository;
    private final MemberService memberService;

    public void plusHeart(Principal principal, String category, String contentsID, String reviewId){
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

    public void minusHeart(Principal principal, String reviewId){
        Review review = this.reviewRepository.findById(Long.valueOf(reviewId)).get();
        String providerID = principal.getName();
        Member member = this.memberService.findByproviderId(providerID);
        if (member == null) {
            member = this.memberService.getMember(providerID);
        }
        Heart heart = this.heartRepository.findByMemberAndReview(member,review);
        this.heartRepository.delete(heart);
    }

    public List<Heart> findHeartList(Review review){
       return this.heartRepository.findByReview(review);
    }

}
