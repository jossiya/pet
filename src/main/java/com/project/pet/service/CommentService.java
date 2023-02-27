package com.project.pet.service;

import com.project.pet.domain.Comment;
import com.project.pet.domain.Member;
import com.project.pet.domain.Post;
import com.project.pet.dto.requestdto.CommentRequestDto;
import com.project.pet.dto.responsedto.CommentResponseDto;
import com.project.pet.dto.responsedto.ResponseDto;
import com.project.pet.error.ErrorCode;
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
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }

        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND.name(),ErrorCode.MEMBER_NOT_FOUND.getMessage());
        }

        Post post = postService.isPresentPost(requestDto.getPostId());
        if (null == post) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_BOARD.name(),ErrorCode.NOT_EXIST_BOARD.getMessage());
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
