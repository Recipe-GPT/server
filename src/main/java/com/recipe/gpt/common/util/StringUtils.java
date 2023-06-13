package com.recipe.gpt.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

    private static final Pattern PATTERN_IN_PARENTHESES = Pattern.compile("\\((.*?)\\)");

    // 괄호 안의 문자열 추출
    public static String extractStringInsideParentheses(String value) {
        Matcher matcher = PATTERN_IN_PARENTHESES.matcher(value);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    // 괄호 밖의 문자열 추출
    public static String extractStringOutsideParentheses(String value) {
        int index = value.indexOf('(');
        if (index == -1) {
            return value;
        }
        return value.substring(0, index);
    }

}
