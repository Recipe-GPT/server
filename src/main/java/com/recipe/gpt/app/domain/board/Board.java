package com.recipe.gpt.app.domain.board;

import com.recipe.gpt.app.domain.BaseTimeEntity;
import com.recipe.gpt.app.domain.board.ingredient.Ingredient;
import com.recipe.gpt.app.domain.board.recipe.Recipe;
import com.recipe.gpt.app.domain.board.seasoning.Seasoning;
import com.recipe.gpt.app.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private Long serving;

    @Column(nullable = false)
    private Long time;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Lob
    private String description;

    @Lob
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Embedded
    private Ingredient ingredient = Ingredient.empty();

    @Embedded
    private Seasoning seasoning = Seasoning.empty();

    @Embedded
    private Recipe recipe = Recipe.empty();

}
