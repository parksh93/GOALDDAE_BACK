package com.goalddae;

public enum Citys {
    SEOUL("서울"), GYEONGGI("경기도"), INCHEON("인천"), GANG_WON("강원"), DAEJEON("대전"), CHUNGNAM("충남"), CHUNGBUG("충북"),
    DAEGU("대구"), GYEONGBUG("경북"), BUSAN("부산"), ULSAN("울산"), GYEONGNAM("경상남도"), GWANGJU("광주"), JEONNAM("전남"), JEONBUG("전북"), JEJU("제주");
    final private String city;

    Citys(String city){
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
