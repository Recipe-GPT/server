package com.recipe.gpt.app.web.controller.error;

import com.recipe.gpt.app.web.path.ApiPath;
import com.recipe.gpt.common.exception.JwtException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class ErrorController {

    @RequestMapping(
        value = ApiPath.ERROR_AUTH,
        method = {
            RequestMethod.GET,
            RequestMethod.DELETE,
            RequestMethod.PUT,
            RequestMethod.POST
        }
    )
    public void errorAuth(@RequestParam(value = "message") String message) {
        throw new JwtException(message);
    }

}