package com.project.pet.service;

import com.project.pet.domain.Board;
import com.project.pet.domain.Member;
import com.project.pet.dto.requestdto.PostRequestDto;
import com.project.pet.dto.responsedto.BoardResponseDto;
import com.project.pet.dto.responsedto.ResponseDto;
import com.project.pet.error.ErrorCode;
import com.project.pet.jwt.TokenProvider;
import com.project.pet.repository.BoardRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final TokenProvider tokenProvider;
    private final BoardRepository boardRepository;

    //게시판 등록
    @Transactional
    public ResponseDto<?> createBoard(PostRequestDto requestDto, HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());

        }

        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND.name(),ErrorCode.MEMBER_NOT_FOUND.getMessage());
        }
        Board board = Board.builder()
                .title(requestDto.getTitle())
                .member(member)
                .build();
        boardRepository.save(board);

        return ResponseDto.success(
                BoardResponseDto.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .author(board.getMember().getNickname())
                        .createdAt(board.getCreatedAt())
                        .modifiedAt(board.getModifiedAt())
                        .build());

    }

    //게시판 하나 조회
    @Transactional
    public ResponseDto<?> getBoard(Long id) {
        Board board = isPresentBoard(id);
        if (null == board) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_BOARD.name(), ErrorCode.NOT_EXIST_BOARD.getMessage());
        }
        return ResponseDto.success(board);
    }
    
    //게시판 전체 조회
    @Transactional
    public ResponseDto<?> getBoardAll() {
        return ResponseDto.success(boardRepository.findAllByOrderByModifiedAtDesc());
    }

    //게시판 수정
    @Transactional
    public ResponseDto<?> updateBoard(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());

        }
        Board board = isPresentBoard(id);
        if (null == board) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_BOARD.name(), ErrorCode.NOT_EXIST_BOARD.getMessage());
        }
        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (board.validateMember(member)) {
            return ResponseDto.fail(ErrorCode.BOARD_UPDATE_WRONG_ACCESS.name(), ErrorCode.BOARD_UPDATE_WRONG_ACCESS.getMessage());
        }
        board.update(requestDto);
        return ResponseDto.success(board);
    }

    //게시판 삭제
    @Transactional
    public ResponseDto<?> deleteBoard(Long id, HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());

        }
        Board board = isPresentBoard(id);
        if (null == board) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_BOARD.name(), ErrorCode.NOT_EXIST_BOARD.getMessage());
        }
        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (board.validateMember(member)) {
            return ResponseDto.fail(ErrorCode.BOARD_DELETE_WRONG_ACCESS.name(), ErrorCode.BOARD_DELETE_WRONG_ACCESS.getMessage());
        }
        boardRepository.delete(board);
        return ResponseDto.success("게시글이 삭제되었습니다.");
    }

    //기시판 유효성 검사
    @Transactional
    public Board isPresentBoard(Long id) {
        Optional<Board> Board = boardRepository.findById(id);
        return Board.orElse(null);
    }
}
