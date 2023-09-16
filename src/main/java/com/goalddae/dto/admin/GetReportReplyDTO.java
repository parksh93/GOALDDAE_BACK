package com.goalddae.dto.admin;

import java.time.LocalDateTime;

public interface GetReportReplyDTO {
    Long getId();
    String getContent();
    String getWriter();
    LocalDateTime getReplyWriteDate();
    LocalDateTime getReportDate();
    String getReason();
    String getReporter();
}
