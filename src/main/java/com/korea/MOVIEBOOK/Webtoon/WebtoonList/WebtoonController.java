package com.korea.MOVIEBOOK.Webtoon.WebtoonList;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/webtoon")
public class WebtoonController {

    private final WebtoonService webtoonService;

//    @GetMapping("/list")
//    public String getApi(Model model) {
//
//        Day[] values = Day.values();
//
//        for (Day value : values) {
//            List<Webtoon> mondayList = webtoonService.getWebtoonList("mon");
//            model.addAttribute("mondayList", mondayList);
//        }
//        for (Day value : values) {
//            List<Webtoon> tuesdayList  = webtoonService.getWebtoonList("tue");
//            model.addAttribute("tuesdayList", tuesdayList);
//        }for (Day value : values) {
//            List<Webtoon> wednesdayList = webtoonService.getWebtoonList("wed");
//            model.addAttribute("wednesdayList", wednesdayList);
//        }for (Day value : values) {
//            List<Webtoon> thursdayList  = webtoonService.getWebtoonList("thu");
//            model.addAttribute("thursdayList", thursdayList);
//        }for (Day value : values) {
//            List<Webtoon> fridayList = webtoonService.getWebtoonList("fri");
//            model.addAttribute("fridayList", fridayList);
//        }for (Day value : values) {
//            List<Webtoon> saturdayList = webtoonService.getWebtoonList("sat");
//            model.addAttribute("saturdayList", saturdayList);
//        }for (Day value : values) {
//            List<Webtoon> sundayList = webtoonService.getWebtoonList("sun");
//            model.addAttribute("sundayList", sundayList);
//        }
//
//
//        return "webtoon/webtoon_list";
//    }


    @GetMapping("/list")
    public String WebtoonList(Model model) {
        String[] Days = new String[]{"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
        List<List<Webtoon>> allDaysListList = new ArrayList<>();
        for(String day : Days) {
            List<Webtoon> allDayList = webtoonService.getAllDayList(day);
            allDayList.sort(Comparator.comparing(Webtoon::getFanCount, Comparator.nullsLast(Comparator.naturalOrder())));


            int startIndex = 0;
            int endIndex = 5;

            for (int i = 1; i <= allDayList.size() / 5; i++) {
                allDaysListList.add(allDayList.subList(startIndex, Math.min(endIndex, allDayList.size())));
                startIndex += 5;
                endIndex += 5;
            }
        }
        System.out.println("=================================== 새로고침 ===================================");
        model.addAttribute("allDaysListList", allDaysListList);
        return "webtoon/webtoon_list";
    }

//    @GetMapping("/list")
//    public String WebtoonList(Model model) {
//        List<Webtoon> allDayList = webtoonService.getAllDayList(Day.mon);
//        allDayList.sort(Comparator.comparing(Webtoon::getFanCount));
//        List<List<Webtoon>> allDaysListList = new ArrayList<>();
//
//            int startIndex = 0;
//            int endIndex = 5;
//
//            for (int i = 1; i <= allDayList.size() / 5; i++) {
//                allDaysListList.add(allDayList.subList(startIndex, Math.min(endIndex, allDayList.size())));
//                startIndex += 5;
//                endIndex += 5;
//            }
//        System.out.println("=================================== 새로고침 ===================================");
//        model.addAttribute("allDaysListList", allDaysListList);
//        return "webtoon/webtoon_list";
//    }

    @GetMapping("/detail/{webtoonId}")
    public String WebtoonDetail(Model model, @PathVariable Long webtoonId) {
        Optional<Webtoon> webtoonDTO = webtoonService.createSampleWebtoonDetail(webtoonId);
        model.addAttribute("webtoonDTO", webtoonDTO);
        return "webtoon/webtoon_detail";
    }
}


