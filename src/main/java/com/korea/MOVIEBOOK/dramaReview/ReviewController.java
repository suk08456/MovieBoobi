package com.korea.MOVIEBOOK.dramaReview;

import com.korea.MOVIEBOOK.Drama.Drama;
import com.korea.MOVIEBOOK.Drama.DramaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final DramaService dramaService;

    @GetMapping("/reviews/create")
    public String showReviewForm(@RequestParam("dramaId") Long dramaId, Model model) {
        Drama drama = dramaService.getDramaById(dramaId);
        Review newReview = new Review();
        model.addAttribute("drama", drama);
        model.addAttribute("newReview", newReview);
        return "/drama_review/review_form";
    }

    @PostMapping("/reviews/save")
    public String saveReview(@ModelAttribute("newReview") Review review,
                             @RequestParam("dramaId") Long dramaId,
                             @RequestParam("title") String title,
                             @RequestParam("comment") String comment,
                             @RequestParam("rating") Integer rating) {

        // Drama 객체 가져오기
        Drama drama = dramaService.getDramaById(dramaId);
        review.setDrama(drama);
        review.setTitle(title);     // 리뷰 객체의 title 필드에 값을 설정합니다.
        review.setComment(comment); // 리뷰 객체의 comment 필드에 값을 설정합니다.
        review.setRating(rating);   // 리뷰 객체의 rating 필드에 값을 설정합니다.
        reviewService.saveReview(review);

        return "redirect:/reviews/detail/" + review.getId();
    }

    @GetMapping("/reviews/detail/{id}")
    public String getReviewDetail(@PathVariable("id") Long reviewId, Model model) {
        // 리뷰 ID를 사용하여 리뷰 정보를 가져옵니다.
        Review review = reviewService.findReviewById(reviewId);
        // 만약 리뷰 정보가 null이면, 적절한 처리를 해주는 것이 좋습니다. (예: 오류 메시지 표시 등)
        if (review == null) {
            // 리뷰 정보가 없는 경우에 대한 처리 (예: 404 페이지로 리다이렉트)
            return "redirect:/error";  // 이 부분은 실제로 존재하는 404 에러 페이지 경로로 변경해야 합니다.
        }
        // 가져온 리뷰 정보를 모델에 추가하여 템플릿에 전달합니다.
        model.addAttribute("review", review);
        // 해당 리뷰의 상세 정보를 보여주는 템플릿 경로를 반환합니다.
        return "/drama_review/review_detail";
    }

    @GetMapping("/drama/{id}/review_list")
    public String showAllReviewForDrama(@PathVariable("id") Long id, @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size, Model model) {
        Page<Review> reviews = reviewService.getReviewsByDramaIdWithPagination(id, page, size);
        if (reviews.isEmpty()) {
            model.addAttribute("errorMessage", "더 이상 리뷰가 없습니다.");
            // 다른 필요한 처리 로직...
            return "drama/drama_detail"; // 또는 해당 페이지의 뷰 이름
        }
        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("totalPages", reviews.getTotalPages());
        model.addAttribute("currentPage", page);
        return "drama_review/review_list";
    }

    @GetMapping("/reviews/delete/{id}")
    public String deleteReview(@PathVariable("id") Long id) {
        Long dramaId = reviewService.getDramaIdByReviewId(id); // ReviewService에서 해당 ID의 리뷰의 드라마 ID를 가져옵니다.
        reviewService.deleteReviewById(id); // ReviewService에서 해당 ID의 리뷰를 삭제합니다.
        return "redirect:/drama/" + dramaId; // 해당 드라마 상세 페이지로 리다이렉트합니다.
    }

    @GetMapping("/reviews/edit/{id}")
    public String showEditReviewForm(@PathVariable Long id, Model model) {
        Review review = reviewService.findReviewById(id);
        model.addAttribute("review", review);
        return "drama_review/review_edit";
    }

    @PostMapping("/reviews/update")
    public String updateReview(@ModelAttribute Review updateReview) {
        reviewService.updateReview(updateReview);
        return "redirect:/reviews/detail/" + updateReview.getId();
    }
}
