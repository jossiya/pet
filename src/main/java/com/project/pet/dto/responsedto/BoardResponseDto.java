package com.project.pet.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {
        private Long id;
        private String title;
        private String author;
        private List<PostResponseDto> postResponseDtoList;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
}
