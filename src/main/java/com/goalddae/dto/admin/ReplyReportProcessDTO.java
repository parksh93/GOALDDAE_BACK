package com.goalddae.dto.admin;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReplyReportProcessDTO {
    private List<Long> replyList;
}
