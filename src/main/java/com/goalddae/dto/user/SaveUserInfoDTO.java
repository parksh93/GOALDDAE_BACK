package com.goalddae.dto.user;

import com.goalddae.entity.User;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserInfoDTO {
    private long id;
    private String loginId;
    private String email;
    private String password;
    private String userCode;
    private String name;
    private String nickname;
    private String gender;
    private String profileImgUrl;
    private String phoneNumber;
    private Date birth;
    private int matchesCnt;
    private String level;
    private LocalDateTime signupDate;
    private LocalDateTime profileUpdateDate;
    private boolean accountSuspersion;
    private int noShowCnt;
    private String preferredCity;
    private String preferredArea;
    private int activityClass;
    private String authority;
    private Long teamId;

    public User toEntity() {
        return User.builder()
                .id(id)
                .loginId(loginId)
                .email(email)
                .password(password)
                .userCode(userCode)
                .name(name)
                .nickname(nickname)
                .gender(gender)
                .profileImgUrl(profileImgUrl)
                .phoneNumber(phoneNumber)
                .birth(birth)
                .matchesCnt(matchesCnt)
                .level(level)
                .signupDate(signupDate)
                .profileUpdateDate(profileUpdateDate)
                .accountSuspersion(accountSuspersion)
                .noShowCnt(noShowCnt)
                .preferredCity(preferredCity)
                .preferredArea(preferredArea)
                .activityClass(activityClass)
                .authority(authority)
                .teamId(teamId)
                .build();
    }
}
