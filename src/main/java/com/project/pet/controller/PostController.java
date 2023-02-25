package com.project.pet.controller;

import com.project.pet.dto.requestdto.PostRequestDto;
import com.project.pet.dto.responsedto.ResponseDto;
import com.project.pet.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/posts")
public class PostController {
    private final PostService postService;

    // 게시글 작성
    @PostMapping(value = "/create")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto,
                                     HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }
    // 게시글 조회
    @GetMapping(value = "/{id}")
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    //전체 게시글 조회
    @GetMapping(value = "/all")
    public ResponseDto<?> getPostAll(){
        return postService.getPostAll();
    }

    // 게시글 수정
    @PutMapping(value = "/{id}")
    public ResponseDto<?> updatePost(@PathVariable Long id,
                                     @RequestBody PostRequestDto postRequestDto,
                                     HttpServletRequest request) {
        return postService.updatePost(id, postRequestDto, request);
    }
    //게시글 삭제
    @DeleteMapping("withdrawl/{id}")
    public ResponseDto<?>deletePost(@PathVariable Long id,HttpServletRequest request){
        return postService.deletePost(id,request);
    }

}
