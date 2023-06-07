package com.recipe.gpt.app.domain.recipe;

import com.recipe.gpt.app.domain.recipe.ingredient.Ingredient;
import com.recipe.gpt.app.domain.recipe.procedure.Procedure;
import com.recipe.gpt.app.domain.recipe.seasoning.Seasoning;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Lob
    private String description;

    @Embedded
    private Ingredient ingredient = Ingredient.empty();

    @Embedded
    private Seasoning seasoning = Seasoning.empty();

    @Embedded
    private Procedure procedure = Procedure.empty();

}
