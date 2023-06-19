package com.recipe.gpt.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

    private final Pattern PATTERN_IN_PARENTHESES = Pattern.compile("\\((.*?)\\)");

    private final Pattern PATTERN_NUMBERED_LIST = Pattern.compile("^[0-9]+\\.\\s");

    // 괄호 안의 문자열 추출
    public String extractStringInsideParentheses(String value) {
        Matcher matcher = PATTERN_IN_PARENTHESES.matcher(value);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    // 괄호 밖의 문자열 추출
    public String extractStringOutsideParentheses(String value) {
        int index = value.indexOf('(');
        if (index == -1) {
            return value;
        }
        return value.substring(0, index);
    }

    // 레시피 앞 숫자 제거
    public String removeNumberedListPrefix(String value) {
        Matcher matcher = PATTERN_NUMBERED_LIST.matcher(value);
        if (matcher.find()) {
            return value.substring(matcher.end());
        }
        return value;
    }

}
