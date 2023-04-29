package com.recipe.gpt.app.web.dto.recipe.openAi;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenAiRequestDto {

    String prompt;

    Integer maxTokens;

    Double temperature;

    Double frequencyPenalty;

    Double presencePenalty;

    Double topP;

    Integer bestOf;

    Boolean stream;

    List<String> stop;

}
