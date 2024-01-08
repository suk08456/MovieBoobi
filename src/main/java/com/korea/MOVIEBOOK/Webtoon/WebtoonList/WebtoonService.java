package com.korea.MOVIEBOOK.Webtoon.WebtoonList;


import com.korea.MOVIEBOOK.Webtoon.Days.Day;
import com.korea.MOVIEBOOK.Webtoon.Days.DayRepository;
import com.korea.MOVIEBOOK.Webtoon.Days.DayService;
import com.korea.MOVIEBOOK.Webtoon.WebtoonDayList.WebtoonDayList;
import com.korea.MOVIEBOOK.Webtoon.WebtoonDayList.WebtoonDayListRepository;
import com.korea.MOVIEBOOK.Webtoon.WebtoonDayList.WebtoonDayListService;
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
    private final DayService dayService;
    private final WebtoonDayListRepository webtoonDayListRepository;

    public List<Long> getWebtoonAPI(String day) {
        List<WebtoonDTO> webtoonDTOList = new ArrayList<>();
        List<Long> webtoonID = new ArrayList<>();

        String date = day.toLowerCase();
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);
            UriComponents uri = UriComponentsBuilder.fromHttpUrl("https://korea-webtoon-api.herokuapp.com/?service=naver&updateDay=" + date).build();


            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);

            ArrayList<Map> webtoonsList = (ArrayList<Map>) resultMap.getBody().get("webtoons");
//            List<Webtoon> webtoonList = new ArrayList<>();
            for (Map<String, Object> webtoonData : webtoonsList) {
                WebtoonDTO webtoonDTO = createWebtoonDTOFromMap(webtoonData);
                Webtoon webtoon = webtoonRepository.findByWebtoonId(webtoonDTO.getWebtoonId());
                if(webtoon == null){
                    webtoonID.add((Long) webtoonData.get("webtoonId"));
                    Webtoon webtoon1 = saveWebtoonFromDTO(webtoonDTO);
                }
                else{
                    Webtoon webtoon1 = findWebtoonByWebtoonId(webtoonDTO.getWebtoonId());
                    Day day1 = this.dayService.findIdByupdateDays(day);
                    WebtoonDayList webtoonDayList = new WebtoonDayList();
                    webtoonDayList.setWebtoonList(webtoon1);
                    webtoonDayList.setWebtoonDay(day1);
                    this.webtoonDayListRepository.save(webtoonDayList);
                }
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println(e.toString());
            // 예외 처리 로직 추가
        } catch (Exception e) {
            System.out.println(e.toString());
            // 예외 처리 로직 추가
        }
        return webtoonID;
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

    public Webtoon saveWebtoonFromDTO(WebtoonDTO webtoonDTO) {

        Webtoon webtoon = new Webtoon();
//        webtoon.setid(webtoonDTO.getid());
        webtoon.setFanCount(webtoonDTO.getFanCount());
        webtoon.setWebtoonId(webtoonDTO.getWebtoonId());
        webtoon.setTitle(webtoonDTO.getTitle());
        webtoon.setAuthor(webtoonDTO.getAuthor());
        webtoon.setImg(webtoonDTO.getImg());
//        webtoon.setWebtoonDay(day);
//        System.out.println("UpdateDays: " + webtoon.getWebtoonDay()); // 디버깅용 로그
        webtoon.setDetailUrl(webtoonDTO.getDetailUrl());
        return webtoonRepository.save(webtoon);

    }


//    public void setDay(Day day) {
//        this.day = day;
//    }



//    public List<Webtoon> getWebtoonList(Day day,String date) {
//        Day day1 = this.dayRepository.findByupdateDays(date);
////        List<Webtoon> webtoonList = day1.getWebtoonList();
//        if (day1 == null) {
//            getWebtoonAPI(date);
//        }
////        allWebtoonList.addAll(webtoonRepository.findByUpdateDays(day));
//        day1 = this.dayRepository.findByupdateDays(date);
////        List<Webtoon> webtoonList = day1.getWebtoonList();
////        return webtoonList;
//    }


    public Webtoon findWebtoonByWebtoonId(Long webtoonId) {
        return this.webtoonRepository.findByWebtoonId(webtoonId);
    }


}


