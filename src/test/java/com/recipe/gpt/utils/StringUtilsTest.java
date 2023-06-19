package com.recipe.gpt.utils;

import com.recipe.gpt.common.util.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @Test
    public void testRemoveNumberedListPrefix() {

        // 숫자 접두사가 있는 경우
        String input1 = "1. 참치를 섞습니다";
        String expected1 = "참치를 섞습니다";
        String result1 = StringUtils.removeNumberedListPrefix(input1);
        Assertions.assertEquals(expected1, result1);

        // 숫자 접두사가 없는 경우
        String input2 = "참치를 섞습니다";
        String expected2 = "참치를 섞습니다";
        String result2 = StringUtils.removeNumberedListPrefix(input2);
        Assertions.assertEquals(expected2, result2);
    }
}