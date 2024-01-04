package com.korea.MOVIEBOOK.Movie.Movie;

import com.korea.MOVIEBOOK.Movie.Daily.MovieDailyAPI;
import com.korea.MOVIEBOOK.Movie.MovieDTO;
import com.korea.MOVIEBOOK.Movie.Weekly.MovieWeeklyAPI;
import com.korea.MOVIEBOOK.Review.Review;
import com.korea.MOVIEBOOK.Review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Controller
public class MovieController {

    private final MovieDailyAPI movieDailyAPI;
    private final MovieWeeklyAPI movieWeeklyAPI;
    private final MovieService movieService;
    private final ReviewService reviewService;
    LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
    String date = yesterday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    LocalDateTime weeksago = LocalDateTime.now().minusDays(7);
    String weeks = weeksago.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);

    // LocalDate를 Date로 변환
    Date oneWeekAgoDate = Date.from(oneWeekAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
    String weekInfo = getCurrentWeekOfMonth(oneWeekAgoDate);


    @GetMapping("movie")
    public String movie(Model model) throws ParseException {

        List<MovieDTO> movieDTOS = this.movieService.listOfMovieDailyDTO();
        List<MovieDTO> movieDTOS2 = this.movieService.listOfMovieWeeklyDTO(weeks);

        if (movieDTOS.isEmpty()) {
            List<Map> failedMovieList = this.movieDailyAPI.movieDaily(date);
            movieDailySize(failedMovieList);
            this.movieService.listOfMovieDailyDTO();
        }

        if (movieDTOS2.isEmpty()) {
            List<Map> failedMovieList2 = this.movieWeeklyAPI.movieWeekly(weeks);
            movieWeeklySize(failedMovieList2);
            this.movieService.listOfMovieWeeklyDTO(weeks);
        }

        movieDTOS = this.movieService.listOfMovieDailyDTO();
        List<List<MovieDTO>> movieListList = new ArrayList<>();

        Integer startIndex = 0;
        Integer endIndex = 5;

        for (int i = 0; i < movieDTOS.size() / 5; i++) {
            movieListList.add(movieDTOS.subList(startIndex, Math.min(endIndex, movieDTOS.size())));
            startIndex += 5;
            endIndex += 5;
        }

        for (List<MovieDTO> movieList : movieListList) {
            movieList.sort(new MovieDTOComparator());
        }

        movieDTOS2 = this.movieService.listOfMovieWeeklyDTO(weeks);
        List<List<MovieDTO>> movieWeeklyListList = new ArrayList<>();

        startIndex = 0;
        endIndex = 5;

        for (int i = 0; i < movieDTOS2.size() / 5; i++) {
            movieWeeklyListList.add(movieDTOS2.subList(startIndex, Math.min(endIndex, movieDTOS2.size())));
            startIndex += 5;
            endIndex += 5;
        }

        model.addAttribute("movieDailyDate", date);
        model.addAttribute("movieListList", movieListList);
        model.addAttribute("movieWeeklyDate", weekInfo);
        model.addAttribute("movieWeeklyListList", movieWeeklyListList);

        return "Movie/movie";
    }
    @PostMapping("movie/detail")
    public String movieDetail(Model model, String movieCD) {
        Movie movie = this.movieService.findMovieByCD(movieCD);
        List<Review> reviews = reviewService.findReviews(movie.getId());

        Integer runtime = Integer.valueOf(movie.getRuntime());
        Integer hour = (int) Math.floor((double) runtime / 60);
        Integer minutes = runtime % 60;
        String movieruntime = String.valueOf(hour) + "시간" + String.valueOf(minutes) + "분";

        String director = "";
        if(!movie.getDirector().isEmpty()) {
            director = movie.getDirector() + "(감독)";
        }

        String[] actors = movie.getActor().split(",");

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

        model.addAttribute("movieDailyDetail", movie);
        model.addAttribute("actorListList", actorListList);
        model.addAttribute("movieruntime", movieruntime);
        model.addAttribute("actorList", actorList);
        model.addAttribute("reviews", reviews);

        return "Movie/movie_detail";
    }

    @GetMapping("movie/detail")
    public String movieDetail2(Model model, @RequestParam("movieCD") String movieCD) {
        Movie movie = this.movieService.findMovieByCD(movieCD);
        List<Review> reviews = reviewService.findReviews(movie.getId());

        Integer runtime = Integer.valueOf(movie.getRuntime());
        Integer hour = (int) Math.floor((double) runtime / 60);
        Integer minutes = runtime % 60;
        String movieruntime = String.valueOf(hour) + "시간" + String.valueOf(minutes) + "분";

        String director = "";
        if(!movie.getDirector().isEmpty()) {
            director = movie.getDirector() + "(감독)";
        }

        String[] actors = movie.getActor().split(",");

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

        model.addAttribute("movieDailyDetail", movie);
        model.addAttribute("actorListList", actorListList);
        model.addAttribute("movieruntime", movieruntime);
        model.addAttribute("actorList", actorList);
        model.addAttribute("reviews", reviews);

        return "Movie/movie_detail";
    }
    //
    public void movieDailySize(List<Map> failedMovieList) {
        if (failedMovieList != null && !failedMovieList.isEmpty()) {
            List<Map> failedMoiveList = movieDailyAPI.saveDailyMovieDataByAPI(failedMovieList);
            movieDailySize(failedMoiveList);
        }
    }

    public void movieWeeklySize(List<Map> failedMovieList) throws ParseException {
        if (failedMovieList != null && !failedMovieList.isEmpty()) {
            List<Map> failedMoiveList = movieWeeklyAPI.saveWeeklyMovieDataByAPI(failedMovieList, weeks);
            movieDailySize(failedMoiveList);
        }
    }

    public static String getCurrentWeekOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1; // calendar에서의 월은 0부터 시작
        int day = calendar.get(Calendar.DATE);

        // 한 주의 시작은 월요일이고, 첫 주에 4일이 포함되어있어야 첫 주 취급 (목/금/토/일)
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);

        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);

        // 첫 주에 해당하지 않는 주의 경우 전 달 마지막 주차로 계산
        if (weekOfMonth == 0) {
            calendar.add(Calendar.DATE, -day); // 전 달의 마지막 날 기준
            return getCurrentWeekOfMonth(calendar.getTime());
        }

        // 마지막 주차의 경우
        if (weekOfMonth == calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)) {
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE)); // 이번 달의 마지막 날
            int lastDaysDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 이번 달 마지막 날의 요일

            // 마지막 날이 월~수 사이이면 다음달 1주차로 계산
            if (lastDaysDayOfWeek >= Calendar.MONDAY && lastDaysDayOfWeek <= Calendar.WEDNESDAY) {
                calendar.add(Calendar.DATE, 1); // 마지막 날 + 1일 => 다음달 1일
                return getCurrentWeekOfMonth(calendar.getTime());
            }
        }

        return month + "월 " + weekOfMonth + "주차";
    }

    class MovieDTOComparator implements Comparator<MovieDTO> {
        @Override
        public int compare(MovieDTO m1, MovieDTO m2) {
            // movieDailyRank 기준으로 정렬
            return m1.getDailyRank().compareTo(m2.getDailyRank());
        }
    }


//    class movieWeeklyRankComparator implements  Comparator<MovieWeekly>{
//        @Override
//        public int compare(MovieWeekly f1, MovieWeekly f2){
//            return f1.getRank().compareTo(f2.getRank());
//        }
//    }


}

