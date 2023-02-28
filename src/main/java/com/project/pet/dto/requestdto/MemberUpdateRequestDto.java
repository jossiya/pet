package com.project.pet.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요!")
    @Size(min=4,max=32, message= "비밀번호는 최소 4자이상 최대 32자미만으로 만들어주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{4,32}$"
            , message = "비밀번호에 영어대소문자, 숫자, 특수문자를 모두 포함해주세요")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요!")
    @Size(min=1,max=40, message= "닉네임은 최소 1자이상 최대 40자미만으로 만들어주세요.")
    @Pattern(regexp = "[0-9a-zA-Zㄱ-ㅎ가-힣]*${1,40}", message = "닉네임 형식을 확인해 주세요.")
    private String nickname;
}
