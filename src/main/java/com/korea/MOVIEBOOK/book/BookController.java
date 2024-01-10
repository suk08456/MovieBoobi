package com.korea.MOVIEBOOK.book;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/mainPage")
    public String mainPage(Model model) {
        List<Book> bestSellerList = bookService.getBestSellerList();
        bestSellerList.sort(Comparator.comparing(Book::getBestRank));
        List<List<Book>> bestSellerListList = new ArrayList<>();
        int startIndex = 0;
        int endIndex = 5;
        for (int i = 1; i <= bestSellerList.size()/5; i++) {
            bestSellerListList.add(bestSellerList.subList(startIndex, Math.min(endIndex, bestSellerList.size())));
            startIndex+=5;
            endIndex+=5;
        }

        List<Book> newSpecialBookList = bookService.getNewSpecialBookList();
        List<List<Book>> newSpecialBookListList = new ArrayList<>();
        startIndex = 0;
        endIndex = 5;
        for (int i = 1; i <= newSpecialBookList.size()/5; i++) {
            newSpecialBookListList.add(newSpecialBookList.subList(startIndex, Math.min(endIndex, newSpecialBookList.size())));
            startIndex+=5;
            endIndex+=5;
        }

        List<Book> recommendList = bookService.getRecommendationList();
        List<List<Book>> recommendListList = new ArrayList<>();
        startIndex = 0;
        endIndex = 5;
        for (int i = 1; i <= recommendList.size()/5; i++) {
            recommendListList.add(recommendList.subList(startIndex, Math.min(endIndex, recommendList.size())));
            startIndex+=5;
            endIndex+=5;
        }
        System.out.println("=================================== 새로고침 ===================================");
        List<List<List<Book>>> allList = new ArrayList<>();
        allList.add(bestSellerListList);
        allList.add(newSpecialBookListList);
        allList.add(recommendListList);
        model.addAttribute("allList", allList);
        return "book/bookMainPage";
    }

    @PostMapping("/detail")
    public String bookDetail(String isbn, Model model) {
        Book book = bookService.findByIsbn(isbn);
        List<List<String>> authorListList = bookService.getAuthorListList(book);
        model.addAttribute("book", book);
        model.addAttribute("reviews", book.getReviewList());
        model.addAttribute("authorListList", authorListList);
        return "book/bookDetail";
    }

    @GetMapping("/detail/{isbn}")
    public String bookDetail1(@PathVariable("isbn") String isbn, Model model) {
        Book book = bookService.findByIsbn(isbn);
        List<List<String>> authorListList = bookService.getAuthorListList(book);
        model.addAttribute("book", book);
        model.addAttribute("reviews", book.getReviewList());
        model.addAttribute("authorListList", authorListList);
        return "book/bookDetail";
    }
}
