package com.korea.MOVIEBOOK.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

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



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String commentDelete(Principal principal, @PathVariable("id") Long commentid) {
        Comment comment = this.commentService.getComment(commentid);
        if (!comment.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.commentService.deleteComment(comment);
        return "redirect:/";
    }

}
