package com.project.pet.service;

import com.project.pet.domain.Comment;
import com.project.pet.domain.Member;
import com.project.pet.domain.Post;
import com.project.pet.dto.requestdto.CommentRequestDto;
import com.project.pet.dto.responsedto.CommentResponseDto;
import com.project.pet.dto.responsedto.ResponseDto;
import com.project.pet.jwt.TokenProvider;
import com.project.pet.repository.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    private final TokenProvider tokenProvider;
    private final PostService postService;
    @Transactional
    public ResponseDto<?> createComment(CommentRequestDto requestDto, HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail("INVALID_TOKEN",
                    "로그인이 필요합니다.");
        }

        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(requestDto.getPostId());
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }
        if (requestDto.getResponseTo()==null){
            Comment comment = Comment.builder()
                    .member(member)
                    .post(post)
                    .content(requestDto.getContent())

                    .build();
            commentRepository.save(comment);
            return ResponseDto.success(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .author(comment.getMember().getNickname())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }
        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .responseTo(requestDto.getResponseTo())
                .content(requestDto.getContent())
                .build();
        commentRepository.save(comment);
        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .author(comment.getMember().getNickname())
                        .content(comment.getContent())
                        .responseTo(requestDto.getResponseTo())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build());
    }
}
