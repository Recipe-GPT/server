package com.recipe.gpt.app.domain.board;

import com.recipe.gpt.app.domain.BaseTimeEntity;
import com.recipe.gpt.app.domain.member.Member;
import com.recipe.gpt.app.domain.recipe.Recipe;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long serving;

    @Column(nullable = false)
    private Long time;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Lob
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Builder
    private Board(Member member,
        Long serving,
        Long time,
        Difficulty difficulty,
        String imageUrl,
        Recipe recipe) {
        this.member = member;
        this.serving = serving;
        this.time = time;
        this.difficulty = difficulty;
        this.imageUrl = imageUrl;
        this.recipe = recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public boolean isAccessibleToBoard(Member member) {
        if (member == null) {
            return false;
        }

        Long loginMemberId = member.getId();
        Long memberId = this.member.getId();
        return memberId.equals(loginMemberId);
    }

    public void update(Board requestBoard) {
        this.serving = requestBoard.serving;
        this.time = requestBoard.time;
        this.difficulty = requestBoard.difficulty;
    }

}
