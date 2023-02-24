package com.project.pet.controller;

import com.project.pet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/members")
public class MemberController {
    private final MemberService memberService;

}
