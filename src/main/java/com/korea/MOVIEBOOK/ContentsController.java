package com.korea.MOVIEBOOK;

import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.movie.MovieDTO;
import com.korea.MOVIEBOOK.movie.daily.MovieDaily;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContentsController {
    public List<ContentsDTO> setBookContentsDTO(Book book) {
        List<ContentsDTO> contentsDTOS = new ArrayList<>();
        ContentsDTO contentsDTO = ContentsDTO.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .plot(book.getPlot())
                .publisher(book.getPublisher())
                .imageUrl(book.getImageUrl())
                .pricestandard(book.getPricestandard())
                .pubdate(book.getPubdate())
                .build();
        contentsDTOS.add(contentsDTO);
        return contentsDTOS;
    }

}
