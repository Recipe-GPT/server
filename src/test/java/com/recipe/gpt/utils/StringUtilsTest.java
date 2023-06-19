package com.recipe.gpt.utils;

import com.recipe.gpt.common.util.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @Test
    public void removeNumberedListPrefixTest() {
        // given
        String input1 = "1. 참치를 섞습니다";
        String expected1 = "참치를 섞습니다";
        String result1 = StringUtils.removeNumberedListPrefix(input1);

        String input2 = "1394. 참치를 섞습니다";
        String expected2 = "참치를 섞습니다";
        String result2 = StringUtils.removeNumberedListPrefix(input2);

        String input3 = "참치를 섞습니다";
        String expected3 = "참치를 섞습니다";
        String result3 = StringUtils.removeNumberedListPrefix(input3);

        // when & then
        Assertions.assertEquals(expected1, result1);
        Assertions.assertEquals(expected2, result2);
        Assertions.assertEquals(expected3, result3);
    }

}