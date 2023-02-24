package com.project.pet.controller;

import com.project.pet.domain.UserDetailsImpl;
import com.project.pet.dto.requestdto.LoginRequestDto;
import com.project.pet.dto.requestdto.MemberRequestDto;
import com.project.pet.dto.responsedto.ResponseDto;
import com.project.pet.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/members")
public class MemberController {
    private final MemberService memberService;
    //회원가입
    @PostMapping(value="/signup")
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) throws IOException {
        return memberService.createMember(requestDto);
    }
    //로그인
    @PostMapping(value = "/login")
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }
    //로그아웃
    @PostMapping(value = "/logout")
    public ResponseDto<?> logout(HttpServletRequest request) {
        return memberService.logout(request);
    }

    //토큰재발급
    @PostMapping(value = "/reissue")
    public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return memberService.reissue(request, response);
    }
    //회원탈퇴
    @DeleteMapping(value="/withdrawl/{memberId}")
    public ResponseDto<?> withdrawal(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.withdrawMember(memberId, userDetails);
    }
}
