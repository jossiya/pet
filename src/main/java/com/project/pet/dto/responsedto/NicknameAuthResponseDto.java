package com.project.pet.dto.responsedto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NicknameAuthResponseDto {
    private Long id;
    private String nickname;

}
