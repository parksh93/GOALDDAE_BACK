package com.goalddae.dto.admin;

import java.time.LocalDateTime;

public interface GetReportBoardDTO {
    long getId();
    String getWriter();
    String getTitle();
    String getContent();
    LocalDateTime getWriteDate();
    LocalDateTime getReportDate();
    String getReportUser();
    String getImg1();
    String getImg2();
    String getImg3();
    String getImg4();
    String getImg5();
    String getReason();
}
