package com.project.pet.controller;

import com.project.pet.dto.requestdto.CommentRequestDto;
import com.project.pet.dto.responsedto.ResponseDto;
import com.project.pet.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/comments")
public class CommentController {

    private final CommentService commentService;
    // 댓글 작성
    @PostMapping(value = "/create")
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.createComment(requestDto, request);
    }
}
