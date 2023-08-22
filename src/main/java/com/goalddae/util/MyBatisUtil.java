package com.goalddae.util;

import java.util.regex.Pattern;

public class MyBatisUtil {
    private static final Pattern SAFE_TABLE_NAME_PATTERN =
            Pattern.compile("[a-zA-Z0-9_가-힣\uAC00-\uD7A3]+");

    // 입력받은 테이블 이름이 유효한지 검사하는 메소드
    public static String safeTable(String input) {
        if (SAFE_TABLE_NAME_PATTERN.matcher(input).matches()) {
            return input;
        } else {
            throw new IllegalArgumentException("올바르지 않은 테이블 이름이 입력되었습니다.");
        }
    }
}
