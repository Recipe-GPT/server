package com.recipe.gpt.app.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_sequence_gen")
    @SequenceGenerator(
        name = "member_sequence_gen",
        sequenceName = "member_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private String password;

    @Builder
    private Member(String email, String name, String imageUrl, String password) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.password = password;
    }

}
