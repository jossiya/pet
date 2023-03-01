package com.project.pet.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private Long boardId;
    private String title;
    private String content;
}
