package com.goalddae.dto.admin;

import com.goalddae.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.sql.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetUserListDTO {
    private long id;
    private String loginId;
    private String name;
    private String nickname;
    private String email;
    private String userCode;
    private String gender;
    private String profileImgUrl;
    private String phoneNumber;
    private Date birth;
    private int matchesCnt;
    private String level;
    private String signupDate;
    private int noShowCnt;
    private String preferredCity;
    private String preferredArea;
    private int activityClass;
    private Long teamId;
    private boolean accountSuspersion;

    public GetUserListDTO(User user){
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.userCode = user.getUserCode();
        this.gender = user.getGender();
        this.profileImgUrl = user.getProfileImgUrl();
        this.phoneNumber = user.getPhoneNumber();
        this.birth = user.getBirth();
        this.matchesCnt = user.getMatchesCnt();
        this.level = user.getLevel();
        this.noShowCnt = user.getNoShowCnt();
        this.preferredCity = user.getPreferredCity();
        this.preferredArea = user.getPreferredArea();
        this.activityClass = user.getActivityClass();
        this.teamId = user.getTeamId();
        this.accountSuspersion = user.isAccountSuspersion();
    }
}