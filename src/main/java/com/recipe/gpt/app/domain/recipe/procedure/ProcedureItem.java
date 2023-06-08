package com.recipe.gpt.app.domain.recipe.procedure;

import com.recipe.gpt.app.domain.recipe.Recipe;
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
public class ProcedureItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false, length = 200)
    private String description;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Builder
    private ProcedureItem(Recipe recipe, String description) {
        this.recipe = recipe;
        this.description = description;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        recipe.getProcedure().addProcedureItem(this);
    }

}
