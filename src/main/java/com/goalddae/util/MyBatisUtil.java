package com.goalddae.util;

import java.util.regex.Pattern;

public class MyBatisUtil {
    private static final Pattern SAFE_TABLE_NAME_PATTERN =
            Pattern.compile("[a-zA-Z0-9_]+");

    public static String safeTable(String input) {
        if (SAFE_TABLE_NAME_PATTERN.matcher(input).matches()) {
            return input;
        } else {
            throw new IllegalArgumentException("올바르지 않은 테이블 이름이 입력되었습니다.");
        }
    }

    // Long 타입의 입력에 대해 동작하는 새로운 메소드 추가
    public static String safeTable(Long input) {
        return input.toString();
    }
}
