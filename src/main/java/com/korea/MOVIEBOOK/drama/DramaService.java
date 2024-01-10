package com.korea.MOVIEBOOK.drama;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DramaService {

    private final DramaRepository dramaRepository;

    public List<Drama> dramaList() {
        return dramaRepository.findAll();
    } // drama 에 모든 엔티티 조회 List<Drama>에 반환

    public Drama getDramaById(Long id) {
        return dramaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 드라마는 존재하지 않습니다. " + id));
    } // drama 에 특정 id 조회 만약 존재하지 않는다면 예외처리

//    public List<Review> getReviewsByDramaId(Long dramaId) {
//        Drama drama = dramaRepository.findById(dramaId)
//                .orElseThrow(() -> new RuntimeException("해당 드라마는 존재하지 않습니다. " + dramaId));
//        return drama.getReviews();
//    } // 특정 dramaId를 조회하여 List<Review>에 반환
//      // 존재 하지 않는다면 예외처리


}



