package com.goalddae.dto.user;

import com.goalddae.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetUserInfoDTO {
    private String loginId;
    private String nickname;
    private String userCode;
    private String gender;
    private String profileImgUrl;
    private String phoneNumber;
    private Date birth;
    private int matchesCnt;
    private String level;
    private LocalDateTime signupDate;
    private int noShowCnt;
    private String preferredCity;
    private String preferredArea;
    private int activityClass;

    public GetUserInfoDTO(User user){
        this.loginId = user.getLoginId();
        this.nickname = user.getNickname();
        this.userCode = user.getUserCode();
        this.gender = user.getGender();
        this.profileImgUrl = user.getProfileImgUrl();
        this.phoneNumber = user.getPhoneNumber();
        this.birth = user.getBirth();
        this.matchesCnt = user.getMatchesCnt();
        this.level = user.getLevel();
        this.signupDate = user.getSignupDate();
        this.noShowCnt = user.getNoShowCnt();
        this.preferredCity = user.getPreferredCity();
        this.preferredArea = user.getPreferredArea();
        this.activityClass = user.getActivityClass();
    }
}