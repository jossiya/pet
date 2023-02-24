package com.project.pet.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NicknameAuthRequestDto {
    @NotBlank(message = "닉네임에 공백은 허용되지 않습니다.")
    @Size(min=1,max=40, message= "닉네임은 최소 1자이상 최대 40자미만으로 만들어주세요.")
    @Pattern(regexp = "[0-9a-zA-Zㄱ-ㅎ가-힣]*${1,40}", message = "닉네임 형식을 확인해 주세요.")
    private String nickname;

}
