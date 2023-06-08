package com.recipe.gpt.app.domain.recipe.procedure;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Procedure {

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ProcedureItem> procedure = new ArrayList<>();

    public static Procedure empty() {
        return new Procedure();
    }

    public List<ProcedureItem> getProcedureItems() {
        return procedure;
    }

    public void addProcedureItem(ProcedureItem procedureItem) {
        procedure.add(procedureItem);
    }

}
