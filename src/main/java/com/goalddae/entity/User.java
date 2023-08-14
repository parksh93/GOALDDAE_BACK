package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    @Column(unique = true)
    private String loginId; // 로그인 아이디
                            // 소셜 로그인을 대비해 null 허용
    
    @Column(nullable = false, unique = true)
    private String email;   // 이메일

    @Column
    private String password;    // 비밀번호
                                // 소셜 로그인을 대비해 null 허용

    @Column(nullable = false, unique = true)
    private String userCode;    // 유저코드

    @Column(nullable = false)
    private String name;    // 사용자 이름
    
    @Column(unique = true)
    private String nickname;    // 닉네임

    @Column(nullable = false)
    private String gender;  // 성별

    @Column(nullable = false)
    private String profileImgUrl;   // 프로필사진 주소

    @Column(nullable = false)
    private String phoneNumber; // 전화번호

    @Column(nullable = false)
    private Date birth; // 생년월일

    @Column(nullable = false)
    private int matchesCnt;  // 매치 경기수

    @Column(nullable = false)
    private String level;   // 레벨

    @Column(nullable = false)
    private LocalDateTime signupDate;   // 가입일자

    @Column(nullable = false)
    private LocalDateTime profileUpdateDate;    // 프로필 수정일자

    @Column(nullable = false)
    private boolean accountSuspersion;  // 계정 정지 유무
                                        // 정지 : true
                                        // 정상 : false

    @Column(nullable = false)
    private int noShowCnt;  // 노쇼 수

    @Column
    private String preferredCity;   // 선호 도시

    @Column
    private String preferredArea;   // 선호 지역

    @Column
    private int activityClass;   // 활동 반경

    @Column(nullable = false)
    private String authority;  // 권한 등급
                            // 일반 유저 : user / 매니저 : manager / 관리자 : admin

    @Builder
    public User(String loginId, String email, String password, String userCode, String nickname, String gender, String profileImgUrl,
                String phoneNumber, Date birth, int matchesCnt, String level,
                boolean accountSuspersion, int noShowCnt, String preferredCity,
                String preferredArea, int activityClass, String authority){
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.userCode = userCode;
        this.nickname = nickname;
        this.gender = gender;
        this.profileImgUrl = profileImgUrl;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.matchesCnt = matchesCnt;
        this.level = level;
        this.accountSuspersion = accountSuspersion;
        this.noShowCnt = noShowCnt;
        this.preferredCity = preferredCity;
        this.preferredArea = preferredArea;
        this.activityClass = activityClass;
        this.authority = authority;
    }

    @PrePersist
    public void setInformation() {
        this.profileImgUrl = "./userProfileImg/goalddae_default_profile.Webp";
        this.matchesCnt = 0;
        this.level = "유망주";
        this.signupDate = LocalDateTime.now();
        this.profileUpdateDate = LocalDateTime.now();
        this.accountSuspersion = false;
        this.noShowCnt = 0;
    }

    @PreUpdate
    public void setUpdateTime() {
        this.profileUpdateDate = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.authority));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
