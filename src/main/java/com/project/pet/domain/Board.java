package com.project.pet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pet.dto.requestdto.BoardRequestDto;
import com.project.pet.dto.requestdto.PostRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "board")
    private List<Post> post;

    public void update(BoardRequestDto requestDto){
        this.title=requestDto.getTitle();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
}
