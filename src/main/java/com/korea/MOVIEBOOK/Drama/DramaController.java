package com.korea.MOVIEBOOK.Drama;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DramaController {

    private final DramaService dramaService;

    @GetMapping("/drama/dramaList")
    public String dramaList (Model model) {
        List<Drama> dramaList = dramaService.dramaList();
        // dramaService에서 드라마 목록을 조회하여 dramaList에 저장
        model.addAttribute("dramaList", dramaList);
        // model 객체에 dramaList를 전달
        return "drama/drama_list";
    }

//    @GetMapping("/drama/{id}")
//    public String dramaDetail(@PathVariable Long id, Model model) {
//        Drama drama = dramaService.getDramaById(id);
//        // dramaService 에서 DramaById를 조회하여 drama에 저장
//        List<Review> reviews = dramaService.getReviewsByDramaId(id).stream().limit(10).collect(Collectors.toList());
//        // dramaService 에서 reviewsByDramaId를 10개까지만 조회하여 reviews에 저장
//        double avgRating = reviews.stream() // reviews에서 stream 생성
//                .filter(review -> review.getRating() != null) // rating이 null인 review는 제외
//                .mapToInt(Review::getRating) // 리뷰 객체에서 평점만 추출하여 정수 스트림 생성
//                .average() // 평점의 평균값 계산
//                .orElse(0); // 리뷰가 없을 경우 0.0출력
//        model.addAttribute("drama", drama); // model 객체에 drama 전달
//        model.addAttribute("reviews", reviews); // model 객체에 reviews 전달
//        model.addAttribute("newReview", new Review()); // model 객체에 new Review 전달
//        model.addAttribute("avgRating", String.format("%.1f", avgRating));  // 소수점 첫째 자리까지만 표시
//        return "drama/drama_detail";
//    }

}



