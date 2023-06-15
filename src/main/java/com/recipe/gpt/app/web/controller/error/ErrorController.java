package com.recipe.gpt.app.web.controller.error;

import com.recipe.gpt.app.web.path.ApiPath;
import com.recipe.gpt.common.exception.JwtException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class ErrorController {

    @GetMapping(ApiPath.ERROR)
    public void errorAuth(@RequestParam(value = "message") String message) {
        throw new JwtException(message);
    }

}
