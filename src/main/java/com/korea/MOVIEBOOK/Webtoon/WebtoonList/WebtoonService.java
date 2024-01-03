package com.korea.MOVIEBOOK.Webtoon.WebtoonList;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WebtoonService {
    private final WebtoonRepository webtoonRepository;
    private Day day;
    public void getWebtoonAPI(String day) {
        List<WebtoonDTO> webtoonDTOList = new ArrayList<>();

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);
            UriComponents uri = UriComponentsBuilder.fromHttpUrl("https://korea-webtoon-api.herokuapp.com/?service=naver&updateDay=" + day).build();


            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);

            ArrayList<Map> webtoonsList = (ArrayList<Map>) resultMap.getBody().get("webtoons");
            for (Map<String, Object> webtoonData : webtoonsList) {
                WebtoonDTO webtoonDTO = createWebtoonDTOFromMap(webtoonData);
                saveWebtoonFromDTO(webtoonDTO, day);
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println(e.toString());
            // 예외 처리 로직 추가
        } catch (Exception e) {
            System.out.println(e.toString());
            // 예외 처리 로직 추가
        }
    }


    private WebtoonDTO createWebtoonDTOFromMap(Map<String, Object> webtoonData) {
        try {
            return WebtoonDTO.builder()
                    ._id((String) webtoonData.get("_id"))
                    .fanCount((Integer) webtoonData.get("fanCount"))
                    .webtoonId((Long) webtoonData.get("webtoonId"))
                    .title((String) webtoonData.get("title"))
                    .author((String) webtoonData.get("author"))
                    .img((String) webtoonData.get("img"))
                    .updateDays((List<String>) webtoonData.get("updateDays"))
                    .searchKeyword((String) webtoonData.get("searchKeyword"))
                    .detailUrl((String) webtoonData.get("detailUrl"))
                    .build();
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            return null;
        }
    }

    public void saveWebtoonFromDTO(WebtoonDTO webtoonDTO, String updateDays) {
        Webtoon webtoon = new Webtoon();
//        webtoon.setid(webtoonDTO.getid());
        webtoon.setFanCount(webtoonDTO.getFanCount());
        webtoon.setWebtoonId(webtoonDTO.getWebtoonId());
        webtoon.setTitle(webtoonDTO.getTitle());
        webtoon.setAuthor(webtoonDTO.getAuthor());
        webtoon.setImg(webtoonDTO.getImg());
        webtoon.setUpdateDays(updateDays);
        System.out.println("UpdateDays: " + webtoon.getUpdateDays()); // 디버깅용 로그
        webtoon.setDetailUrl(webtoonDTO.getDetailUrl());
        webtoonRepository.save(webtoon);
    }


//    public void setDay(Day day) {
//        this.day = day;
//    }

    public List<Webtoon> getAllDayList(String day) {
        List<Webtoon> allWebtoonList = webtoonRepository.findByUpdateDays(day);

        if (allWebtoonList.isEmpty()) {
            getWebtoonAPI(day);
        }
//        allWebtoonList.addAll(webtoonRepository.findByUpdateDays(day));
        allWebtoonList = webtoonRepository.findByUpdateDays(day);
        return allWebtoonList;
    }


    public Optional<Webtoon> createSampleWebtoonDetail(Long webtoonId) {
        return webtoonRepository.findById(webtoonId);
    }
}


