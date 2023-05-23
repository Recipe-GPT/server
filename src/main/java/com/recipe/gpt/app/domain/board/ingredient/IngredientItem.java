package com.recipe.gpt.app.domain.board.ingredient;

import com.recipe.gpt.app.domain.board.Board;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class IngredientItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 10)
    private String quantity;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    private IngredientItem(Board board, String name, String quantity) {
        this.board = board;
        this.name = name;
        this.quantity = quantity;
    }

}
