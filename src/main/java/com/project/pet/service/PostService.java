package com.project.pet.service;

import com.project.pet.domain.Board;
import com.project.pet.domain.Comment;
import com.project.pet.domain.Member;
import com.project.pet.domain.Post;
import com.project.pet.dto.requestdto.PostRequestDto;
import com.project.pet.dto.responsedto.PostResponseDto;
import com.project.pet.dto.responsedto.ResponseDto;
import com.project.pet.error.ErrorCode;
import com.project.pet.jwt.TokenProvider;
import com.project.pet.repository.BoardRepository;
import com.project.pet.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final TokenProvider tokenProvider;

    //게시물 작성
    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto, HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());

        }

        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND.name(),ErrorCode.MEMBER_NOT_FOUND.getMessage());
        }
        Board boardId= boardRepository.findById(requestDto.getBoardId()).orElse(null);
        Post post = Post.builder()
                .board(boardId)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .build();
        postRepository.save(post);

        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getMember().getNickname())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build());

    }
    //게시물 하나 조회
    @Transactional
    public ResponseDto<?> getPost(Long id) {
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_POST.name(),ErrorCode.NOT_EXIST_POST.getMessage());
        }
        return ResponseDto.success(post);
    }

    @Transactional
    public ResponseDto<?> getPostAll() {
    return ResponseDto.success(postRepository.findAllByOrderByModifiedAtDesc());
    }

    //게시글 수정
    @Transactional
    public ResponseDto<?> updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());

        }
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_POST.name(),ErrorCode.NOT_EXIST_POST.getMessage());
        }
        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (post.validateMember(member)) {
            return ResponseDto.fail(ErrorCode. POST_UPDATE_WRONG_ACCESS.name(),ErrorCode. POST_UPDATE_WRONG_ACCESS.getMessage());
        }
        post.update(requestDto);
        return ResponseDto.success(post);
    }

    //게시글 삭제
    @Transactional
    public ResponseDto<?> deletePost(Long id, HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());

        }
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_POST.name(),ErrorCode.NOT_EXIST_POST.getMessage());
        }
        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (post.validateMember(member)) {
            return ResponseDto.fail(ErrorCode.POST_DELETE_WRONG_ACCESS.name() ,ErrorCode.POST_DELETE_WRONG_ACCESS.getMessage());
        }
        postRepository.delete(post);
        return ResponseDto.success("게시글이 삭제되었습니다.");
    }
    @Transactional
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

}
