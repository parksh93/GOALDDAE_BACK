package com.goalddae.dto.manager;

import com.goalddae.entity.User;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManagerUserInfoDTO {
    private long id;
    private String name;
    private String nickname;
    private String gender;
    private String phoneNumber;
    private Date birth;
    private int matchesCnt;
    private String level;
    private LocalDateTime signupDate;
    private int noShowCnt;

}
