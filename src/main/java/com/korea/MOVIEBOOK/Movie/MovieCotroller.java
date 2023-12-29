package com.korea.MOVIEBOOK.Movie;

import com.korea.MOVIEBOOK.Movie.Daily.MovieDaily;
import com.korea.MOVIEBOOK.Movie.Daily.MovieDailyAPI;
import com.korea.MOVIEBOOK.Movie.Daily.MovieDailyService;
import com.korea.MOVIEBOOK.Movie.Weekly.MovieWeekly;
import com.korea.MOVIEBOOK.Movie.Weekly.MovieWeeklyAPI;
import com.korea.MOVIEBOOK.Movie.Weekly.MovieWeeklyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Controller
public class MovieCotroller {

    private final MovieDailyService movieDailyService;
    private final MovieDailyAPI movieDailyAPI;
    private final MovieWeeklyService movieWeeklyService;
    private final MovieWeeklyAPI movieWeeklyAPI;
    LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
    String date = yesterday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    LocalDateTime weeksago = LocalDateTime.now().minusDays(7);
    String weeks = weeksago.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

//    @GetMapping("movie")
//    public String movie(Model model) throws ParseException {
//        List<MovieDaily> movieDailyList = this.movieDailyService.findDailyMovie(date);  // movieDaily data 확인
//        List<MovieWeekly> movieWeekList = this.movieWeeklyService.findWeeklyMovie(weeks);
////        Collections.sort(movieDailyList, new movieRankComparator());
////        Collections.sort(movieWeekList, new movieWeeklyRankComparator());
//
//        if (movieDailyList.isEmpty()) {
//            List<Map> failedMovieList = this.movieDailyAPI.movieDaily(date);
//            movieDailySize(failedMovieList);
//        }
//        if (movieWeekList.isEmpty()) {
//            List<Map> failedWeeklyMovieList = this.movieWeeklyAPI.movieWeekly(weeks);
//            movieWeeklySize(failedWeeklyMovieList);
//        }
//
//        LocalDateTime localDateTime = weeksago;
//        Date weekdate = Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
//        String week = getCurrentWeekOfMonth(weekdate);
//
//        List<List<MovieDaily>> movieDailyListList = new ArrayList<>();
//
//        Integer startIndex = 0;
//        Integer endIndex = 5;
//
//        for (int i = 0; i < movieDailyList.size() / 5; i++) {
//            movieDailyListList.add(movieDailyList.subList(startIndex, Math.min(endIndex, movieDailyList.size())));
//            startIndex += 5;
//            endIndex += 5;
//        }
//
//        List<List<MovieWeekly>> movieWeekListList = new ArrayList<>();
//
//        Integer startIndex2 = 0;
//        Integer endIndex2 = 5;
//
//        for (int i = 0; i < movieWeekList.size() / 5; i++) {
//            movieWeekListList.add(movieWeekList.subList(startIndex2, Math.min(endIndex2, movieWeekList.size())));
//            startIndex2 += 5;
//            endIndex2 += 5;
//        }
//
//        model.addAttribute("movieDailyDate", date);
//        model.addAttribute("movieDailyListList", movieDailyListList);
//        model.addAttribute("movieWeeklyDate", week);
//        model.addAttribute("movieWeekListList", movieWeekListList);
//
//
//        return "Movie/movie";
//    }
//
//    @PostMapping("movie/detail")
////    public String movieDetail(Model model, String date, String title) {
////        MovieDaily movieDaily = this.movieDailyService.findmovie(date, title);
////
////        Integer runtime = Integer.valueOf(movieDaily.getRuntime());
////        Integer hour = (int) Math.floor((double) runtime / 60);
////        Integer minutes = runtime % 60;
////        String movieruntime = String.valueOf(hour) + "시간" + String.valueOf(minutes) + "분";
////
////        model.addAttribute("movieDailyDetail", movieDaily);
////        model.addAttribute("movieruntime", movieruntime);
////
////        return "Movie/movie_detail";
////    }
//
//    public void movieDailySize(List<Map> failedMovieList) {
//        if (failedMovieList != null && !failedMovieList.isEmpty()) {
//            List<Map> failedMoiveList = movieDailyAPI.saveDailyMovieDataByAPI(failedMovieList);
//            movieDailySize(failedMoiveList);
//        }
//    }
//
//    public void movieWeeklySize(List<Map> failedMovieList) throws ParseException {
//        if (failedMovieList != null && !failedMovieList.isEmpty()) {
//            List<Map> failedMoiveList = movieWeeklyAPI.saveWeeklyMovieDataByAPI(failedMovieList,weeks);
//            movieDailySize(failedMoiveList);
//        }
//    }
//
//    public static String getCurrentWeekOfMonth(Date date) {
//        Calendar calendar = Calendar.getInstance(Locale.KOREA);
//        calendar.setTime(date);
//        int month = calendar.get(Calendar.MONTH) + 1; // calendar에서의 월은 0부터 시작
//        int day = calendar.get(Calendar.DATE);
//
//        // 한 주의 시작은 월요일이고, 첫 주에 4일이 포함되어있어야 첫 주 취급 (목/금/토/일)
//        calendar.setFirstDayOfWeek(Calendar.MONDAY);
//        calendar.setMinimalDaysInFirstWeek(4);
//
//        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
//
//        // 첫 주에 해당하지 않는 주의 경우 전 달 마지막 주차로 계산
//        if (weekOfMonth == 0) {
//            calendar.add(Calendar.DATE, -day); // 전 달의 마지막 날 기준
//            return getCurrentWeekOfMonth(calendar.getTime());
//        }
//
//        // 마지막 주차의 경우
//        if (weekOfMonth == calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)) {
//            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE)); // 이번 달의 마지막 날
//            int lastDaysDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 이번 달 마지막 날의 요일
//
//            // 마지막 날이 월~수 사이이면 다음달 1주차로 계산
//            if (lastDaysDayOfWeek >= Calendar.MONDAY && lastDaysDayOfWeek <= Calendar.WEDNESDAY) {
//                calendar.add(Calendar.DATE, 1); // 마지막 날 + 1일 => 다음달 1일
//                return getCurrentWeekOfMonth(calendar.getTime());
//            }
//        }
//
//        return month + "월 " + weekOfMonth + "주차";
//    }

//    class movieRankComparator implements  Comparator<MovieDaily>{
//        @Override
//        public int compare(MovieDaily f1, MovieDaily f2){
//            return f1.getRank().compareTo(f2.getRank());
//        }
//    }
//
//    class movieWeeklyRankComparator implements  Comparator<MovieWeekly>{
//        @Override
//        public int compare(MovieWeekly f1, MovieWeekly f2){
//            return f1.getRank().compareTo(f2.getRank());
//        }
//    }


}

