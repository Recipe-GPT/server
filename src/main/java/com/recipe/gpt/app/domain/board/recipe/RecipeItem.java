package com.recipe.gpt.app.domain.board.recipe;

import com.recipe.gpt.app.domain.board.Board;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RecipeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sequence;

    @Lob
    @Column(nullable = false, length = 200)
    private String description;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    private RecipeItem(Board board, Long sequence, String description) {
        this.board = board;
        this.sequence = sequence;
        this.description = description;
    }

}
