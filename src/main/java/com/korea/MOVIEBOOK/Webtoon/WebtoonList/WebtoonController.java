package com.korea.MOVIEBOOK.Webtoon.WebtoonList;

import com.korea.MOVIEBOOK.Webtoon.Days.Day;
import com.korea.MOVIEBOOK.Webtoon.Days.DayRepository;
import com.korea.MOVIEBOOK.Webtoon.Days.DayService;
import com.korea.MOVIEBOOK.Webtoon.WebtoonDayList.WebtoonDayList;
import com.korea.MOVIEBOOK.Webtoon.WebtoonDayList.WebtoonDayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/webtoon")
public class WebtoonController {

    private final WebtoonService webtoonService;
    private final DayService dayService;
    private final WebtoonDayListService webtoonDayListService;



    @GetMapping("/list")
    public String CreateDayList(Model model) {
        Day day = this.dayService.findByDay("tue");     // 그냥 DAY 테이블이 있는지 확인용
        if (day == null) {
            dayService.FindByWeek();
        }

        List<Day> days = this.dayService.findAll();
        List<List<List<Webtoon>>> allList = new ArrayList<>();//  월,화,수,목,금,토,일이라는 값을 가져오기 위함
        List<Webtoon> webtoonList = new ArrayList<>();

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

//    @GetMapping("/list")
//    public String WebtoonList(Model model) {
//        Day mondayList;
//        mondayList = this.dayService.findByDay("mon");
//        if(mondayList == null){
//            mondayList = this.dayService.setDays("mon");
//        }
//
//        List<Webtoon> monday = webtoonService.getWebtoonList(mondayList,"mon");
//
//        mondayList.setWebtoonList(monday);

//        mondayList.sort(
//                Comparator.comparing(
//                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
//                        Comparator.nullsLast(Comparator.reverseOrder())
//                )
//        );
//        List<Webtoon> tuesdayList = webtoonService.getWebtoonList("tue");
//        tuesdayList.sort(
//                Comparator.comparing(
//                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
//                        Comparator.nullsLast(Comparator.reverseOrder())
//                )
//        );
//        List<Webtoon> wednesdayList = webtoonService.getWebtoonList("wed");
//        wednesdayList.sort(
//                Comparator.comparing(
//                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
//                        Comparator.nullsLast(Comparator.reverseOrder())
//                )
//        );
//        List<Webtoon> thursdayList = webtoonService.getWebtoonList("thu");
//        thursdayList.sort(
//                Comparator.comparing(
//                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
//                        Comparator.nullsLast(Comparator.reverseOrder())
//                )
//        );
//        List<Webtoon> fridayList = webtoonService.getWebtoonList("fri");
//        fridayList.sort(
//                Comparator.comparing(
//                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
//                        Comparator.nullsLast(Comparator.reverseOrder())
//                )
//        );
//        List<Webtoon> saturdayList = webtoonService.getWebtoonList("sat");
//        saturdayList.sort(
//                Comparator.comparing(
//                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
//                        Comparator.nullsLast(Comparator.reverseOrder())
//                )
//        );
//        List<Webtoon> sundayList = webtoonService.getWebtoonList("sun");
//        sundayList.sort(
//                Comparator.comparing(
//                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
//                        Comparator.nullsLast(Comparator.reverseOrder())
//                )
//        );

//        List<List<Webtoon>> mondayListList = getListList(monday);
//        List<List<Webtoon>> tuesdayListList = getListList(tuesdayList);
//        List<List<Webtoon>> wednesdayListList = getListList(wednesdayList);
//        List<List<Webtoon>> thursdayListList = getListList(thursdayList);
//        List<List<Webtoon>> fridayListList = getListList(fridayList);
//        List<List<Webtoon>> saturdayListList = getListList(saturdayList);
//        List<List<Webtoon>> sundayListList = getListList(sundayList);

//        List<List<List<Webtoon>>> allList = new ArrayList<>();
//        allList.add(mondayListList);
//        allList.add(tuesdayListList);
//        allList.add(wednesdayListList);
//        allList.add(thursdayListList);
//        allList.add(fridayListList);
//        allList.add(saturdayListList);
//        allList.add(sundayListList);
//        model.addAttribute("allList", allList);


//        System.out.println("=================================== 새로고침 ===================================");
//        return "webtoon/webtoon_list";
//    }
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
//       List<WebtoonDayList> webtoonDayLists = this.webtoonDayListService.findBywebtoon(webtoon);
//       webtoonDayLists.get(0).getWebtoonDay().getUpdateDays();

        model.addAttribute("WebtoonDetail", webtoon);
        return "webtoon/webtoon_detail";
    }
}


