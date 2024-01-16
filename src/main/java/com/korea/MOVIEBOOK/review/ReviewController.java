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
    public String createDramaReview(@RequestParam("dramaId") Long dramaId, @RequestParam("comment") String comment, @RequestParam("rating") Double rating) {
        this.reviewService.saveDramaReview(dramaId, comment, rating);
        return "redirect:/drama/detail/" + dramaId;
    }

    @GetMapping("/create/movie")
    public String createMovieReview(@RequestParam("movieCD") String movieCD, @RequestParam("comment") String comment, @RequestParam("rating") Double rating) {
        this.reviewService.saveMovieReview(movieCD, comment, rating);
        return "redirect:/movie/detail/" + movieCD;
    }

    @GetMapping("/create/book")
    public String createBookReview(@RequestParam("isbn") String isbn, @RequestParam("comment") String comment, @RequestParam("rating") Double rating) {
        reviewService.saveBookReview(isbn, comment, rating);
        return "redirect:/book/detail/" + isbn;
    }

    @GetMapping("/create/webtoon")
    public String createWebtoonReview(@RequestParam("webtoonId") Long webtoonId, @RequestParam("comment") String comment, @RequestParam("rating") Double rating) {
        this.reviewService.saveWebtoonReview(webtoonId, comment, rating);
        return "redirect:/webtoon/detail/" + webtoonId;
    }

//<<<<<<< HEAD
//    @GetMapping("/detail/{id}")
//=======
////    @GetMapping("/webtoon/review/create")
////    public String createWebtoonReview(@RequestParam("webtoonId") Long webtoonId, @RequestParam("comment") String
////            comment, @RequestParam("rating") Double rating) {
////        this.reviewService.saveWebtoonReview(webtoonId, comment, rating);
////        return "redirect:/webtoon/detail?webtoonId=" + webtoonId;
////    }
//
//    @GetMapping("/review/detail/{id}")
//>>>>>>> pay
//    public String DramaReviewDetail(@PathVariable("id") Long reviewId, Model model) {
//        Review review = reviewService.findReviewById(reviewId);
//        if (review == null) {
//            return "redirect:/error";
//        }
//        model.addAttribute("review", review);
//<<<<<<< HEAD
//        // 해당 리뷰의 상세 정보를 보여주는 템플릿 경로를 반환합니다.
//        return "/review/review_detail";
//    }
//
//    @GetMapping("/list/{dramaId}")
//    public String reviewList(@PathVariable("dramaId") Long dramaId, Model model) {
//        if (dramaId == null) {
//            model.addAttribute("error", "error");
//            return "error";
//=======
//        return "/drama_review/review_detail";
//    }
//
//    @GetMapping("/drama/{id}/review_list")
//    public String showAllReviewForDrama(@PathVariable("id") Long id, @PathVariable("page") int page,
//                                        @PathVariable("size") int size, Model model) {
//        Page<Review> reviews = reviewService.getReviewByDramaIdWithPagination(id, page, size);
//        if (reviews.isEmpty()) {
//            model.addAttribute("errorMessage", "더 이상 리뷰가 없습니다.");
//            return "drama/drama_detail";
//>>>>>>> pay
//        }
//        List<Review> review = reviewService.getReviewByDramaId(dramaId);
//        model.addAttribute("review", review);
//        return "review/review_list";
//    }
//
//<<<<<<< HEAD
//    @GetMapping("/delete/{id}")
//=======
//    @GetMapping("/review/delete/{id}")
//>>>>>>> pay
//    public String deleteDramaReview(@PathVariable("id") Long id) {
//        Long dramaId = reviewService.getDramaIdByReviewId(id);
//        reviewService.deleteReviewById(id);
//        return "redirect:/drama/" + dramaId;
//    }
//<<<<<<< HEAD
////    @GetMapping("/review/edit/{id}")
////    public String showEditDramaReviewForm(@PathVariable Long id, Model model) {
////        Review review = reviewService.findReviewById(id);
////        model.addAttribute("review", review);
////        return "drama_review/review_edit";
////    }
////
////    @PostMapping("/review/update")
////    public String updateDramaReview(@ModelAttribute Review updateReview) {
////        reviewService.updateReview(updateReview);
////        return "redirect:/review/detail/" + updateReview.getId();
////    }
//=======
//
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
