package com.project.pet.service;

import com.project.pet.domain.Comment;
import com.project.pet.domain.Member;
import com.project.pet.domain.Post;
import com.project.pet.dto.requestdto.PostRequestDto;
import com.project.pet.dto.responsedto.PostResponseDto;
import com.project.pet.dto.responsedto.ResponseDto;
import com.project.pet.jwt.TokenProvider;
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
    private final TokenProvider tokenProvider;

    //게시물 작성
    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto, HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail("INVALID_TOKEN",
                    "Token이 유효하지 않습니다.");

        }

        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }
        Post post = Post.builder()
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
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
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
            return ResponseDto.fail("INVALID_TOKEN",
                    "Token이 유효하지 않습니다.");

        }
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }
        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }
        post.update(requestDto);
        return ResponseDto.success(post);
    }

    //게시글 삭제
    @Transactional
    public ResponseDto<?> deletePost(Long id, HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail("INVALID_TOKEN",
                    "Token이 유효하지 않습니다.");

        }
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }
        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
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
