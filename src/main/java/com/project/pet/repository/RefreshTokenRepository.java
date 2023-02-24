package com.project.pet.repository;


import com.project.pet.domain.Member;
import com.project.pet.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);

    Optional<RefreshToken> findByValue(String value);

    void deleteByMemberId(Long memberId);
}
