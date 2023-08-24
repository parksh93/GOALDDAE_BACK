package com.goalddae.util;

public class MyBatisUtil {

    // 입력받은 테이블 이름이 유효한지 검사하는 메소드
    public static Long safeTable(Long input) {
        if (input != null && input > 0) {
            return input;
        } else {
            throw new IllegalArgumentException("올바르지 않은 테이블 이름이 입력되었습니다.");
        }
    }
}
