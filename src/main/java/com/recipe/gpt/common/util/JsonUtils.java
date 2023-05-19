package com.recipe.gpt.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.gpt.common.exception.ApiResponseParseException;
import java.io.IOException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {

    public static <T> T readValue(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ApiResponseParseException();
        }
    }

}
