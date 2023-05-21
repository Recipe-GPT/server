package com.recipe.gpt.app.domain.recipe;

import com.recipe.gpt.app.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_sequence_gen")
    @SequenceGenerator(
        name = "recipe_sequence_gen",
        sequenceName = "recipe_sequence"
    )
    private Long id;

    private String name;

    @Lob
    private String description;







}
