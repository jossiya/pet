package com.project.pet.service;

import com.project.pet.domain.Member;
import com.project.pet.dto.requestdto.EmailAuthRequestDto;
import com.project.pet.dto.requestdto.NicknameAuthRequestDto;
import com.project.pet.dto.responsedto.EmailAuthResponseDto;
import com.project.pet.dto.responsedto.NicknameAuthResponseDto;
import com.project.pet.dto.responsedto.ResponseDto;
import com.project.pet.repository.MemberRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    // 회원가입 전 닉네임 인증 체크
    @Transactional
    public ResponseDto<?> nickname(@Valid NicknameAuthRequestDto requestDto) {
        if(null != isPresentNickname(requestDto.getNickname())) {
            return ResponseDto.fail("DUPLICATED_NICKNAME","중복된 닉네임입니다.");
        }
        if (requestDto.getNickname().equals("")) {
            return ResponseDto.success("닉네임을 입력해주세요.");
        }
        Member member = Member.builder()
                .nickname(requestDto.getNickname())
                .build();
        return ResponseDto.success(
                NicknameAuthResponseDto.builder()
                        .nickname(member.getNickname())
                        .build()
        );
    }
    // 이메일 인증
    @Transactional
    public ResponseDto<?> emailAuth(EmailAuthRequestDto requestDto) {
        //이메일 중복 체크
        if (null != isPresentMember(requestDto.getEmail())) {
            return ResponseDto.fail("DUPLICATED_EMAIL",
                    "중복된 이메일입니다.");
        }

        if(!requestDto.getEmail().contains("@")) {
            return ResponseDto.fail("INVALID_EMAIL",
                    "이메일 형식을 확인해주세요.");
        }

        if (requestDto.getEmail().equals("")) {
            return ResponseDto.success("이메일을 입력해주세요.");
        }

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .build();
        return ResponseDto.success(
                EmailAuthResponseDto.builder()
                        .email(member.getEmail())
                        .build()
        );
    }
    // 닉네임 인증
    @Transactional
    public Object isPresentNickname(String nickname) {
        Optional<Member> Member = memberRepository.findByNickname(nickname);
        return Member.orElse(null);
    }
    //회원 이메일 유효성 인증
    @Transactional
    public Member isPresentMember(String email) {
        Optional<Member> Member = memberRepository.findByEmail(email);
        return Member.orElse(null);
    }
}
