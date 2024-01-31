package com.korea.MOVIEBOOK;

import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.drama.Drama;
import com.korea.MOVIEBOOK.movie.movie.Movie;
import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentsService {
    public ContentsDTO setBookContentsDTO(Book book) {
        return ContentsDTO.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .plot(book.getPlot())
                .publisher(book.getPublisher())
                .imageUrl(book.getImageUrl())
                .pricestandard(book.getPricestandard())
                .pubdate(book.getPubdate())
                .gubun("저자/역자")
                .build();
    }

    public ContentsDTO setMovieContentsDTO(Movie movie) {
        return ContentsDTO.builder()
                .movieCode(movie.getMovieCode())
                .title(movie.getTitle())
                .director(movie.getDirector())
                .actor(movie.getActor())
                .plot(movie.getPlot())
                .runtime(movie.getRuntime())
                .genre(movie.getGenre())
                .releaseDate(movie.getReleaseDate())
                .company(movie.getCompany())
                .nations(movie.getNations())
                .audiAcc(movie.getAudiAcc())
                .viewingRating(movie.getViewingRating())
                .imageUrl(movie.getImageUrl())
                .gubun("출연/제작")
                .build();
    }


    public ContentsDTO setWetoonContentsDTO(Webtoon webtoon) {

        List<String> days = new ArrayList<>();
        for (int i = 0; i < webtoon.getWebtoonDayLists().size(); i++) {
            String dateStr = webtoon.getWebtoonDayLists().get(i).getWebtoonDay().getUpdateDays();
            days.add(dateStr);
        }

        return ContentsDTO.builder()
                .wetoonCode(webtoon.getWebtoonId())
                .title(webtoon.getTitle())
                .author(webtoon.getAuthor())
                .imageUrl(webtoon.getImageUrl())
                .fanCount(webtoon.getFanCount())
                .updateDays(days.toString())
                .gubun("글/그림")
                .build();
    }

    public ContentsDTO setDramaContentsDTO(Drama drama) {
        return ContentsDTO.builder()
                .dramaCode(drama.getId())
                .title(drama.getTitle())
                .director(drama.getDirector())
                .actor(drama.getActor())
                .plot(drama.getPlot())
                .genre(drama.getGenre())
                .releaseDate(drama.getReleaseDate())
                .company(drama.getCompany())
                .viewingRating(drama.getViewingRating())
                .imageUrl(drama.getImageUrl())
                .gubun("출연/제작")
                .build();
    }

}
