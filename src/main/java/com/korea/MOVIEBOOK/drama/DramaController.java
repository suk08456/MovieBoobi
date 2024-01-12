package com.korea.MOVIEBOOK.drama;
import com.korea.MOVIEBOOK.review.Review;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
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

    @GetMapping("/drama/{id}")
    public String dramaDetail(@PathVariable Long id, Model model) {
        Drama drama = dramaService.getDramaById(id);
        // dramaService 에서 DramaById를 조회하여 drama에 저장
        List<Review> reviewList = dramaService.getReviewByDramaId(id).stream().limit(10).collect(Collectors.toList());
        // dramaService 에서 reviewsByDramaId를 10개까지만 조회하여 reviewList에 저장
        String director = "";
        if(!drama.getDirector().isEmpty()) {
            director = drama.getDirector() + "(감독)";
        }
        String actor = drama.getActor();
        String[] actors = new String[]{};
        if(!actor.isEmpty()) {
            actors = actor.split(",");
        }
        List<String> actorList = new ArrayList<>(Arrays.asList(actors));
        actorList.add(director);
        List<List<String>>actorListList = new ArrayList<>();
        Integer chunkSize = 8;
        Integer totalElements = actorList.size();

        for (int i = 0; i < (totalElements + chunkSize - 1) / chunkSize; i++) {
            int start = i * chunkSize;
            int end = Math.min((i + 1) * chunkSize, totalElements);
            actorListList.add(actorList.subList(start, end));
        }

        double avgRating = reviewList.stream() // reviewList에서 stream 생성
                .filter(review -> review.getRating() != null) // rating이 null인 review는 제외
                .mapToDouble(Review::getRating) // 리뷰 객체에서 평점만 추출하여 정수 스트림 생성
                .average() // 평점의 평균값 계산
                .orElse(0); // 리뷰가 없을 경우 0.0출력
        model.addAttribute("drama", drama); // model 객체에 drama 전달
        model.addAttribute("reviewList", reviewList); // model 객체에 reviewList 전달
        model.addAttribute("newReview", new Review()); // model 객체에 new Review 전달
        model.addAttribute("avgRating", String.format("%.1f", avgRating));  // 소수점 첫째 자리까지만 표시
        model.addAttribute("actorListList", actorListList);
        model.addAttribute("actorList", actorList);
        return "drama/drama_detail";
    }

}