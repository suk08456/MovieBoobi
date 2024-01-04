package com.korea.MOVIEBOOK.Webtoon.WebtoonList;

import com.korea.MOVIEBOOK.Webtoon.Days.Day;
import com.korea.MOVIEBOOK.Webtoon.Days.DayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/webtoon")
public class WebtoonController {

    private final WebtoonService webtoonService;
    private final DayRepository dayRepository;

    @GetMapping("/list")
    public String WebtoonList(Model model) {
        List<Webtoon> mondayList = webtoonService.getWebtoonList("mon");
        Day day = this.dayRepository.findByDay("mon");
        day.setWebtoonList(mondayList);
        this.dayRepository.save(day);

        mondayList.sort(
                Comparator.comparing(
                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );
        List<Webtoon> tuesdayList = webtoonService.getWebtoonList("tue");
        tuesdayList.sort(
                Comparator.comparing(
                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );
        List<Webtoon> wednesdayList = webtoonService.getWebtoonList("wed");
        wednesdayList.sort(
                Comparator.comparing(
                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );
        List<Webtoon> thursdayList = webtoonService.getWebtoonList("thu");
        thursdayList.sort(
                Comparator.comparing(
                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );
        List<Webtoon> fridayList = webtoonService.getWebtoonList("fri");
        fridayList.sort(
                Comparator.comparing(
                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );
        List<Webtoon> saturdayList = webtoonService.getWebtoonList("sat");
        saturdayList.sort(
                Comparator.comparing(
                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );
        List<Webtoon> sundayList = webtoonService.getWebtoonList("sun");
        sundayList.sort(
                Comparator.comparing(
                        webtoon -> webtoon.getFanCount() == null ? 0 : webtoon.getFanCount(),
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );

        List<List<Webtoon>> mondayListList = getListList(mondayList);
        List<List<Webtoon>> tuesdayListList = getListList(tuesdayList);
        List<List<Webtoon>> wednesdayListList = getListList(wednesdayList);
        List<List<Webtoon>> thursdayListList = getListList(thursdayList);
        List<List<Webtoon>> fridayListList = getListList(fridayList);
        List<List<Webtoon>> saturdayListList = getListList(saturdayList);
        List<List<Webtoon>> sundayListList = getListList(sundayList);

        List<List<List<Webtoon>>> allList = new ArrayList<>();
        allList.add(mondayListList);
        allList.add(tuesdayListList);
        allList.add(wednesdayListList);
        allList.add(thursdayListList);
        allList.add(fridayListList);
        allList.add(saturdayListList);
        allList.add(sundayListList);
        model.addAttribute("allList", allList);


        System.out.println("=================================== 새로고침 ===================================");
        return "webtoon/webtoon_list";
    }



    public List<List<Webtoon>> getListList(List<Webtoon> webtoonList) {
        int startIndex = 0;
        int endIndex = 5;
        List<List<Webtoon>> webtoonListList = new ArrayList<>();
        for (int i = 1; i <= webtoonList.size() / 5; i++) {
            webtoonListList.add(webtoonList.subList(startIndex, Math.min(endIndex, webtoonList.size())));
            startIndex += 5;
            endIndex += 5;
        }
        return webtoonListList;
    }

    @PostMapping("/detail")
    public String WebtoonDetail1(Model model, Long webtoonId){
        Webtoon webtoon = this.webtoonService.findWebtoonByWebtoonId(webtoonId);

        model.addAttribute("WebtoonDetail", webtoon);
        return "webtoon/webtoon_detail";
    }
}


