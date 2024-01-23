package com.korea.MOVIEBOOK.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/save")
    public String saveComment (@RequestParam String category, @RequestParam String contentsId, @RequestParam Long reviewId, @RequestParam Long memberId, @RequestParam String comment){

        this.commentService.saveComment(comment,memberId,reviewId);

        return "redirect:/review/detail?category="+category+"&contentsID=" + contentsId + "&reviewId=" + reviewId;
    }

}
