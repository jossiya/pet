package com.project.pet.repository;

import com.project.pet.domain.Comment;
import com.project.pet.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
//    List<Comment> findAllByPost(Post post);
}
