package com.project.pet.repository;

import com.project.pet.domain.Board;
import com.project.pet.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findAllByOrderByModifiedAtDesc();
}
