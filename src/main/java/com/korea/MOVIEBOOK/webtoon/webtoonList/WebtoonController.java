package com.korea.MOVIEBOOK.webtoon.webtoonList;

import com.korea.MOVIEBOOK.review.Review;
import com.korea.MOVIEBOOK.review.ReviewService;
import com.korea.MOVIEBOOK.webtoon.days.Day;
import com.korea.MOVIEBOOK.webtoon.days.DayService;
import com.korea.MOVIEBOOK.webtoon.webtoonDayList.WebtoonDayList;
import com.korea.MOVIEBOOK.webtoon.webtoonDayList.WebtoonDayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/webtoon")
public class WebtoonController {

    private final WebtoonService webtoonService;
    private final DayService dayService;
    private final WebtoonDayListService webtoonDayListService;
    private final ReviewService reviewService;



    @GetMapping("/list")
    public String CreateDayList(Model model) {
        Day day = this.dayService.findByDay("tue");     // 그냥 DAY 테이블이 있는지 확인용
        if (day == null) {
            dayService.FindByWeek();
        }

        List<Day> days = this.dayService.findAll();
        List<List<List<Webtoon>>> allList = new ArrayList<>();//  월,화,수,목,금,토,일이라는 값을 가져오기 위함
        List<Webtoon> webtoonList = new ArrayList<>();

//        Collections.sort(webtoonList, Comparator.comparingInt(Webtoon::getFanCount).reversed());



        for (Day day1 : days) {
            List<WebtoonDayList> webtoonDayLists = webtoonDayListService.findBywebtoonDay(day1);
            if(webtoonDayLists.isEmpty()){
                List<Long> webtoon = webtoonService.getWebtoonAPI(day1.getUpdateDays());
                webtoonDayListService.SaveWebtoonDayList(day1.getId(), webtoon);
            }
            webtoonDayLists = webtoonDayListService.findBywebtoonDay(day1);
            for (WebtoonDayList webtoonDayList : webtoonDayLists) {
                Webtoon webtoon2 = webtoonDayList.getWebtoonList();
                webtoonList.add(webtoon2);
            }
        }

        List<List<Webtoon>> dayListList = getListList(webtoonList);
        List<List<Webtoon>> mondayListList = getListListList(dayListList.get(0));
        List<List<Webtoon>> tuesdayListList = getListListList(dayListList.get(1));
        List<List<Webtoon>> wednesdayListList = getListListList(dayListList.get(2));
        List<List<Webtoon>> thursdayListList = getListListList(dayListList.get(3));
        List<List<Webtoon>> fridayListList = getListListList(dayListList.get(4));
        List<List<Webtoon>> saturdayListList = getListListList(dayListList.get(5));
        List<List<Webtoon>> sundayListList = getListListList(dayListList.get(6));

        allList.add(mondayListList);
        allList.add(tuesdayListList);
        allList.add(wednesdayListList);
        allList.add(thursdayListList);
        allList.add(fridayListList);
        allList.add(saturdayListList);
        allList.add(sundayListList);
        model.addAttribute("allList", allList);
        return "webtoon/webtoon_list";
    }


    public List<List<Webtoon>> getListListList(List<Webtoon> webtoonList){
        int startIndex = 0;
        int endIndex = 5;
        List<List<Webtoon>> result = new ArrayList<>();
        for (int i = 1; i <= webtoonList.size() / 5; i++) {
            result.add(webtoonList.subList(startIndex, Math.min(endIndex, webtoonList.size())));
            startIndex += 5;
            endIndex += 5;
        }
        return result;
    }


    public List<List<Webtoon>> getListList(List<Webtoon> webtoonList) {
        int startIndex = 0;
        int endIndex = 10;
        List<List<Webtoon>> webtoonListList = new ArrayList<>();
        for (int i = 1; i <= webtoonList.size() / 10; i++) {
            webtoonListList.add(webtoonList.subList(startIndex, Math.min(endIndex, webtoonList.size())));
            startIndex += 10;
            endIndex += 10;
        }
        return webtoonListList;
    }

    @PostMapping("/detail")
    public String WebtoonDetail1(Model model, Long webtoonId) {
        Webtoon webtoon = this.webtoonService.findWebtoonByWebtoonId(webtoonId);
        List<Review> reviews = reviewService.findWebtoonReview(webtoon.getWebtoonId());


//       List<WebtoonDayList> webtoonDayLists = this.webtoonDayListService.findBywebtoon(webtoon);
//       webtoonDayLists.get(0).getWebtoonDay().getUpdateDays();

        model.addAttribute("WebtoonDetail", webtoon);
        model.addAttribute("reviews", reviews);
        return "webtoon/webtoon_detail";
    }

    @GetMapping("/detail")
    public String WebtoonDetail2(Model model, @RequestParam("webtoonId") Long webtoonId){
        Webtoon webtoon = this.webtoonService.findWebtoonByWebtoonId(webtoonId);
        List<Review> reviews = reviewService.findWebtoonReview(webtoon.getWebtoonId());

        model.addAttribute("WebtoonDetail", webtoon);
        model.addAttribute("reviews", reviews);
        return "webtoon/webtoon_detail";
    }
}


