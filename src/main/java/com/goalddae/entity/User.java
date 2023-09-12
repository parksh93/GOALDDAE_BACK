package com.goalddae.entity;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    @Column(unique = true)
    private String loginId; // 로그인 아이디
                            // 소셜 로그인을 대비해 null 허용
    
    @Column(nullable = false)
    private String email;   // 이메일

    @Column
    private String password;    // 비밀번호
                                // 소셜 로그인을 대비해 null 허용

    @Column(nullable = true, unique = true)
    private String userCode;    // 유저코드

    @Column(nullable = false)
    private String name;    // 사용자 이름


    @Column(nullable = true)
    private String nickname;    // 닉네임

    @Column(nullable = true)
    private String gender;  // 성별

    @Column(nullable = false)
    private String profileImgUrl;   // 프로필사진 주소

    @Column(nullable = true)
    private String phoneNumber; // 전화번호

    @Column(nullable = true)
    private Date birth; // 생년월일

    @Column
    private Long teamId; // 가입 팀 id

    @Column(nullable = false)
    private int matchesCnt;  // 매치 경기수

    @Column(nullable = false)
    private String level;   // 레벨

    @Column(nullable = false)
    private LocalDateTime signupDate;   // 가입일자

    @Column(nullable = false)
    private LocalDateTime profileUpdateDate;    // 프로필 수정일자

    // 외래키 형성 - 사용자가 생성한 개인 매치 목록
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<IndividualMatch> individualMatches;

    @Column(nullable = false)
    private boolean accountSuspersion;  // 계정 정지 유무
                                        // 정지 : true
                                        // 정상 : false

    @Column(nullable = false)
    private int noShowCnt;  // 노쇼 수

    @Column
    private String preferredCity;   // 선호 도시 - 서울, 경기, 인천 .....

    @Column
    private String preferredArea;   // 선호 지역 - 강남구, 강동구 .......

    @Column
    private int activityClass;   // 활동 반경

    @Column(nullable = true)
    private String authority;  // 권한 등급
                            // 일반 유저 : user / 매니저 : manager / 관리자 : admin

    @Builder
    public User(long id, String loginId, String email, String password, String userCode, String name, String nickname, String gender, String profileImgUrl,
                String phoneNumber, Date birth, Long teamId, int matchesCnt, LocalDateTime signupDate, LocalDateTime profileUpdateDate, String level,
                boolean accountSuspersion, int noShowCnt, String preferredCity, String preferredArea, int activityClass, String authority){

        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userCode = userCode;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.profileImgUrl = profileImgUrl;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.teamId = teamId;
        this.matchesCnt = matchesCnt;
        this.level = level;
        this.signupDate = signupDate;
        this.profileUpdateDate = profileUpdateDate;
        this.accountSuspersion = accountSuspersion;
        this.noShowCnt = noShowCnt;
        this.preferredCity = preferredCity;
        this.preferredArea = preferredArea;
        this.activityClass = activityClass;
        this.authority = authority;
        this.signupDate = signupDate;
        this.profileUpdateDate = profileUpdateDate;
    }

    @PrePersist
    public void setInformation() {
        this.profileImgUrl = "./img/userProfileImg/goalddae_default_profile.Webp";
        this.matchesCnt = 0;
        this.level = "유망주";
        this.signupDate = LocalDateTime.now();
        this.profileUpdateDate = LocalDateTime.now();
        this.accountSuspersion = false;
        this.noShowCnt = 0;
        this.teamId = -1L;
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
