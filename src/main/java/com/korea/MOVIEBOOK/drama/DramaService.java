package com.korea.MOVIEBOOK.drama;

import com.korea.MOVIEBOOK.movie.movie.Movie;
import com.korea.MOVIEBOOK.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    public List<Review> getReviewByDramaId(Long dramaId) {
        Drama drama = dramaRepository.findById(dramaId)
                .orElseThrow(() -> new RuntimeException("해당 드라마는 존재하지 않습니다. " + dramaId));
        return drama.getReviewList();
    } // 특정 dramaId를 조회하여 List<Review>에 반환
    // 존재 하지 않는다면 예외처리

    public Drama findByDramaId(Long dramaId){
        return this.dramaRepository.findById(dramaId).get();
    }

    public List<List<String>> getActorListList(Drama drama) {

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

        return actorListList;
    }

}






