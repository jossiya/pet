package com.project.pet.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Member member = (Member) o;
        return id != null && Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    public void nameUpdate(String nickname){
        this.nickname=nickname;
    }
    public void passwordUpdate(String password){
        this.password=password;
    }
    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }

}
