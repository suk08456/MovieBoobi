package com.korea.MOVIEBOOK.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        startIndex = 0;
        endIndex = 5;
        List<Book> newSpecialBookList = bookService.getNewSpecialBookList();
        List<List<Book>> newSpecialBookListList = new ArrayList<>();
        for (int i = 1; i <= newSpecialBookList.size()/5; i++) {
            newSpecialBookListList.add(newSpecialBookList.subList(startIndex, Math.min(endIndex, newSpecialBookList.size())));
            startIndex+=5;
            endIndex+=5;
        }
        System.out.println("=================================== 새로고침 ===================================");
        model.addAttribute("bestSellerListList", bestSellerListList);
        model.addAttribute("newSpecialBookListList", newSpecialBookListList);
        return "book/bookMainPage";
    }

    @GetMapping("/test")
    public String test(Model model) {
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
        startIndex = 0;
        endIndex = 5;
        List<Book> newSpecialBookList = bookService.getNewSpecialBookList();
        List<List<Book>> newSpecialBookListList = new ArrayList<>();
        for (int i = 1; i <= newSpecialBookList.size()/5; i++) {
            newSpecialBookListList.add(newSpecialBookList.subList(startIndex, Math.min(endIndex, newSpecialBookList.size())));
            startIndex+=5;
            endIndex+=5;
        }
        System.out.println("=================================== 새로고침 ===================================");
        model.addAttribute("bestSellerListList", bestSellerListList);
        model.addAttribute("newSpecialBookListList", newSpecialBookListList);
        return "book/bookTest";
    }

    @GetMapping("/detail")
    @ResponseBody
    public String detailPage() {

        return "안녕하세요";
    }

}
