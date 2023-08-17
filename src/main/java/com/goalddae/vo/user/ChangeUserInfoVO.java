package com.goalddae.vo.user;

import com.goalddae.entity.User;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangeUserInfoVO {
    private long id;
    private String loginId;
    private String email;
    private String password;
    private String userCode;
    private String name;
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

    public ChangeUserInfoVO(User user){
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userCode = user.getUserCode();
        this.name = user.getName();
        this.gender = user.getGender();
        this.profileImgUrl = user.getProfileImgUrl();
        this.phoneNumber = user.getPhoneNumber();
        this.birth = user.getBirth();
        this.matchesCnt = user.getMatchesCnt();
        this.level = user.getLevel();
        this.signupDate = user.getSignupDate();
        this.profileUpdateDate = user.getProfileUpdateDate();
        this.accountSuspersion = user.isAccountSuspersion();
        this.noShowCnt = user.getNoShowCnt();
        this.preferredCity = user.getPreferredCity();
        this.preferredArea = user.getPreferredArea();
        this.activityClass = user.getActivityClass();
        this.authority = user.getAuthority();
    }
}
