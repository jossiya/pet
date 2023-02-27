package com.project.pet.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //==========================sign up========================================

    SIGNUP_WRONG_MEMBERID(400, "멤버 아이디가 존재하지 않습니다"),
    SIGNUP_WRONG_LOGINID(400, "이메일 형식에 맞춰 주세요"),
    SIGNUP_WRONG_NICKNAME(400, "닉네임 형식을 맞춰주세요"),
    SIGNUP_WRONG_PASSWORD(400, "비밀번호 형식을 맞춰주세요"),
    ALREADY_SAVED_ID(409, "중복된 아이디입니다."),
    ALERADY_SAVED_NICKNAME(409,"중복된 닉네임입니다."),
    PASSWORDS_NOT_MATCHED(400,"비밀번호와 비밀번호 확인이 일치하지 않습니다."),

    //=============================login=======================================
    MEMBER_NOT_FOUND(404,"사용자를 찾을 수 없습니다."),
    INVALID_MEMBER(404,"사용자를 찾을 수 없습니다."),
    LOGINID_EMPTY(400,"아이디를 입력해주세요"),
    PASSWORD_EMPTY(400,"비밀번호를 입력해주세요"),
    LOGINID_MISMATCH(404,"아이디가 일치하지 않습니다"),
    PASSWORD_MISMATCH(404,"비밀번호가 일치하지 않습니다"),

    //================================token========================================
    INVALID_TOKEN(404,"Token이 유효하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(401, "토근이 만료되었습니다. 다시 로그인 하세요."),

    //================================게시판 =========================================

    NOT_EXIST_BOARD(404,"게시판이 존재하지 않습니다."),
    BOARD_UPDATE_WRONG_ACCESS(400, "본인의 글만 수정할 수 있습니다"),
    BOARD_DELETE_WRONG_ACCESS(400, "본인의 글만 삭제할 수 있습니다"),

    //================================게시판 =========================================

    NOT_EXIST_POST(404,"게시글이 존재하지 않습니다."),
    POST_UPDATE_WRONG_ACCESS(400, "본인의 글만 수정할 수 있습니다"),
    POST_DELETE_WRONG_ACCESS(400, "본인의 글만 삭제할 수 있습니다"),
    //==============================500 INTERNAL SERVER ERROR========================

    INTERNAL_SERVER_ERROR(500, "서버 에러입니다. 고객센터에 문의해주세요"),
    BIND_Fails(500,"서버 에러입니다. 고객센터에 문의해주세요"),
    NOT_VALUE_AT(500,"서버 에러입니다. 고객센터에 문의해주세요"),
    NO_ELEMENT(500,"서버 에러입니다. 고객센터에 문의해주세요");
    //BIND_Fails(500,"서버 에러입니다. 고객센터에 문의해주세요");

    private final int status;
    private final String message;
}