package com.recipe.gpt.common.util;

import java.util.Date;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtils {

    public static Date now() {
        return new Date();
    }

    public static Date addTime(Date date, Long second) {
        return new Date(date.getTime() + second);
    }

}
