package com.project.pet.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "이메일과 비밀번호를 모두 입력해주세요.")
    private String email;

    @NotBlank(message = "이메일과 비밀번호를 모두 입력해주세요.")
    private String password;
}
