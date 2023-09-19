package com.goalddae.dto.admin;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardReportProcessDTO {
    List<Long> boardList;
}
