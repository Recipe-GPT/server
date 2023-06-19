package com.recipe.gpt.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.gpt.common.exception.ApiResponseParseException;
import java.io.IOException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils {

    public <T> T readValue(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ApiResponseParseException();
        }
    }

}
