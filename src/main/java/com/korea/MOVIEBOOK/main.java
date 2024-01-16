package com.korea.MOVIEBOOK;

import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class main {

    private final BookService bookService;
    @GetMapping("/")
    public String test(Model model){
        List<Book> bestSellerList = bookService.getBestSellerList();
        bestSellerList.sort(Comparator.comparing(Book::getBestRank));
        List<List<Book>> bookListList = new ArrayList<>();
        int startIndex = 0;
        int endIndex = 5;
        for (int i = 1; i <= bestSellerList.size()/5; i++) {
            bookListList.add(bestSellerList.subList(startIndex, Math.min(endIndex, bestSellerList.size())));
            startIndex+=5;
            endIndex+=5;
        }
        System.out.println("=================================== 새로고침 ===================================");
        model.addAttribute("bookListList", bookListList);
        return "mainPage";
    }
    @GetMapping("/2")
    public String test2(){
        return "layout2";
    }

    @GetMapping("3")
    public String test3() {
        return "test";
    }
}
