package com.goalddae.util;

public class MyBatisUtil {

    // 입력받은 테이블 이름이 유효한지 검사하는 메소드
    public static Long safeTable(Long input) {
        // userId가 1 이상 1000000 미만인 경우에만 유효
        if (input != null && input > 0 && input < 1000000) {
            return input;
        } else {
            throw new IllegalArgumentException("올바르지 않은 테이블 이름이 입력되었습니다.");
        }
    }
}
