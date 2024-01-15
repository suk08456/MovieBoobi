package com.korea.MOVIEBOOK.review;

import com.korea.MOVIEBOOK.drama.Drama;
import com.korea.MOVIEBOOK.drama.DramaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final DramaService dramaService;

    @GetMapping("/create")
    public String createReview() {
        return "Review/review";
    }

    @GetMapping("/create/drama")
    public String createDramaReview(@RequestParam("dramaId") Long dramaId, @RequestParam("comment") String comment, @RequestParam("rating") Double rating, Model model) {
        this.reviewService.saveDramaReview(dramaId, comment, rating);
        return "redirect:/drama/" + dramaId;  // 수정된 부분
    }

    @GetMapping("/create/movie")
    public String createMovieReview(@RequestParam("movieCD") String movieCD, @RequestParam("comment") String comment, @RequestParam("rating") Double rating) {
        this.reviewService.saveMovieReview(movieCD, comment, rating);
        return "redirect:/movie/detail?movieCD=" + movieCD;
    }

    @GetMapping("/create/book")
    public String createBookReview(@RequestParam("isbn") String isbn, @RequestParam("comment") String comment, @RequestParam("rating") Double rating, Model model) {
        reviewService.saveBookReview(isbn, comment, rating);
        return "redirect:/book/detail/" + isbn;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/webtoon/review/create")
    public String createWebtoonReview(@RequestParam("webtoonId") Long webtoonId, @RequestParam("comment") String
            comment, @RequestParam("rating") Double rating, Principal principal, BindingResult bindingResult) {
        this.reviewService.saveWebtoonReview(webtoonId, comment, rating);
        return "redirect:/webtoon/detail?webtoonId=" + webtoonId;
    }

    @GetMapping("/detail/{id}")
    public String DramaReviewDetail(@PathVariable("id") Long reviewId, Model model) {
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
        return "/review/review_detail";
    }

    @GetMapping("/list/{dramaId}")
    public String reviewList(@PathVariable("dramaId") Long dramaId, Model model) {
        if (dramaId == null) {
            model.addAttribute("error", "error");
            return "error";
        }
        List<Review> review = reviewService.getReviewByDramaId(dramaId);
        model.addAttribute("review", review);
        return "review/review_list";
    }

    @GetMapping("/delete/{id}")
    public String deleteDramaReview(@PathVariable("id") Long id) {
        Long dramaId = reviewService.getDramaIdByReviewId(id); // ReviewService에서 해당 ID의 리뷰의 드라마 ID를 가져옵니다.
        reviewService.deleteReviewById(id); // ReviewService에서 해당 ID의 리뷰를 삭제합니다.
        return "redirect:/drama/" + dramaId; // 해당 드라마 상세 페이지로 리다이렉트합니다.
    }
//    @GetMapping("/review/edit/{id}")
//    public String showEditDramaReviewForm(@PathVariable Long id, Model model) {
//        Review review = reviewService.findReviewById(id);
//        model.addAttribute("review", review);
//        return "drama_review/review_edit";
//    }
//
//    @PostMapping("/review/update")
//    public String updateDramaReview(@ModelAttribute Review updateReview) {
//        reviewService.updateReview(updateReview);
//        return "redirect:/review/detail/" + updateReview.getId();
//    }
}
