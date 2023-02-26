package com.project.pet.controller;

import com.project.pet.dto.requestdto.PostRequestDto;
import com.project.pet.dto.responsedto.ResponseDto;
import com.project.pet.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/boards")
public class BoardController {

        private final BoardService boardService;
    // 게시글 작성
    @PostMapping(value = "/create")
    public ResponseDto<?> createBoard(@RequestBody PostRequestDto requestDto,
                                     HttpServletRequest request) {
        return boardService.createBoard(requestDto, request);
    }
    // 게시글 조회
    @GetMapping(value = "/{id}")
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    //전체 게시글 조회
    @GetMapping(value = "/all")
    public ResponseDto<?> getBoardAll(){
        return boardService.getBoardAll();
    }

    // 게시글 수정
    @PutMapping(value = "/{id}")
    public ResponseDto<?> updateBoard(@PathVariable Long id,
                                     @RequestBody PostRequestDto postRequestDto,
                                     HttpServletRequest request) {
        return boardService.updateBoard(id, postRequestDto, request);
    }
    //게시글 삭제
    @DeleteMapping("withdrawl/{id}")
    public ResponseDto<?>deleteBoard(@PathVariable Long id,HttpServletRequest request){
        return boardService.deleteBoard(id,request);
    }
}
