package com.goalddae.service;

import com.goalddae.config.jwt.TokenProvider;
import com.goalddae.util.S3Uploader;
import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.*;
import com.goalddae.entity.*;
import com.goalddae.exception.NotFoundTokenException;
import com.goalddae.exception.NotFoundUserException;
import com.goalddae.repository.UserJPARepository;
import com.goalddae.repository.*;
import com.goalddae.entity.User;
import com.goalddae.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService{
    private final UserJPARepository userJPARepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(2);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(1);
    public static final String ACCESS_TOKEN_COOKIE_NAME = "token";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    private final FriendService friendService;
    private final S3Uploader s3Uploader;


    @Autowired
    public UserServiceImpl(UserJPARepository userRepository,
                           RefreshTokenRepository refreshTokenRepository,
                           TokenProvider tokenProvider,
                           FriendService friendService,
                           S3Uploader s3Uploader){

        this.userJPARepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.tokenProvider = tokenProvider;
        this.friendService = friendService;
        this.s3Uploader = s3Uploader;
    }

    @Override
    public void save(User user){
        User newUser = User.builder()
                .loginId(user.getLoginId())
                .email(user.getEmail())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .name(user.getName())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .birth(user.getBirth())
                .preferredCity(user.getPreferredCity())
                .preferredArea(user.getPreferredArea())
                .activityClass(user.getActivityClass())
                .authority(user.getAuthority())
                .userCode(createUserCode())
                .build();

        userJPARepository.save(newUser);

        // 로그인 아이디를 가져와 테이블 생성에 사용
        Long id = newUser.getId();

        // 동적 테이블 생성
        friendService.createFriendAcceptTable(id);
        friendService.createFriendAddTable(id);
        friendService.createFriendListTable(id);
    }

    public static String createUserCode() {
        StringBuffer code = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    code.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    code.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    code.append((rnd.nextInt(10)));
                    break;
            }
        }

        return code.toString();
    }
    @Override
    public User getByCredentials(String loginId){
        return userJPARepository.findByLoginId(loginId);
    }

    @Override
    public boolean generateTokenFromLogin(LoginDTO loginDTO, HttpServletResponse response){
        User userInfo = getByCredentials(loginDTO.getLoginId());

        if (userInfo != null && !userInfo.isAccountSuspersion()) {
            if (bCryptPasswordEncoder.matches(loginDTO.getPassword(), userInfo.getPassword())) {
                String refreshToken = tokenProvider.generateToken(userInfo, REFRESH_TOKEN_DURATION);
                saveRefreshToken(userInfo.getId(), refreshToken);

                String token = tokenProvider.generateToken(userInfo,ACCESS_TOKEN_DURATION);

                if(!token.equals("")){
                   CookieUtil.addCookie(response, ACCESS_TOKEN_COOKIE_NAME, token);
                   CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken);
                    return true;
                }
                throw new NotFoundTokenException("fail generateToken");
            }
        }

        throw new NotFoundUserException("login fail");
    }

    private void saveRefreshToken(Long userId, String newRefreshToken){
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId);

        if(refreshToken != null){
            refreshToken.update(newRefreshToken);
        }else{
            refreshToken = new RefreshToken(userId, newRefreshToken);
        }

        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public GetUserInfoDTO getUserInfo(String token){
           User user = userJPARepository.findById(tokenProvider.getUserId(token)).get();
           GetUserInfoDTO userInfoDTO = new GetUserInfoDTO(user);

           return userInfoDTO;
    }

    @Override
    public GetUserInfoDTO getFriendInfo(long id) {
        User user = userJPARepository.findById(id).get();
        GetUserInfoDTO friendInfo = new GetUserInfoDTO(user);
        return friendInfo;
    }

    @Override
    public boolean checkLoginId(CheckLoginIdDTO checkLoginIdDTO){
        int checkLoginIdCnt = userJPARepository.countByLoginId(checkLoginIdDTO.getLoginId());

        if(checkLoginIdCnt == 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean checkEmail(SendEmailDTO checkEmailDTO){
        int checkEmailCnt = userJPARepository.countByEmail(checkEmailDTO.getEmail());

        if(checkEmailCnt == 0){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public boolean checkNickname(CheckNicknameDTO checkNicknameDTO){
        int checkNicknameCnt = userJPARepository.countByNickname(checkNicknameDTO.getNickname());

        if(checkNicknameCnt == 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public ResponseFindLoginIdDTO getLoginIdByEmailAndName(RequestFindLoginIdDTO requestFindLoginIdDTO){
        String loginId = userJPARepository.findLoginIdByEmailAndName(requestFindLoginIdDTO.getEmail(), requestFindLoginIdDTO.getName());
        String star = "**";

        if(loginId != null){
            loginId = loginId.substring(0, loginId.length()-2) + star;
        }

        ResponseFindLoginIdDTO findLoginIdDTO = ResponseFindLoginIdDTO.builder()
                .loginId(loginId).build();

        return findLoginIdDTO;
    }

    @Override
    public boolean checkLoginIdAndEmail(RequestFindPasswordDTO findPasswordDTO, HttpServletResponse response) {
        int userCnt = userJPARepository.countByLoginIdAndEmail(findPasswordDTO.getLoginId(), findPasswordDTO.getEmail());

        if(userCnt == 1){
            String token = tokenProvider.generateLoinIdToken(findPasswordDTO.getLoginId(), Duration.ofMinutes(5));
            if(!token.equals("")) {
                CookieUtil.addCookie(response, "loginIdToken", token);

                return true;
            }
        }

        return false;
    }

    @Override
    public void update(GetUserInfoDTO getUserInfoDTO) {
        User user = userJPARepository.findByLoginId(getUserInfoDTO.getLoginId());

        if (user != null) {
            ChangeUserInfoDTO changeUserInfoDTO = new ChangeUserInfoDTO(user);
            changeUserInfoDTO.setNickname(getUserInfoDTO.getNickname());
            changeUserInfoDTO.setPhoneNumber(getUserInfoDTO.getPhoneNumber());
            changeUserInfoDTO.setPreferredCity(getUserInfoDTO.getPreferredCity());
            changeUserInfoDTO.setPreferredArea(getUserInfoDTO.getPreferredArea());
            changeUserInfoDTO.setActivityClass(getUserInfoDTO.getActivityClass());
            changeUserInfoDTO.setProfileUpdateDate(LocalDateTime.now());

            User updateduser = changeUserInfoDTO.toEntity();

            userJPARepository.save(updateduser);
        }
    }

    @Override
    public void updateProfileImg(GetUserInfoDTO getUserInfoDTO, MultipartFile multipartFile) {
        User user = userJPARepository.findByLoginId(getUserInfoDTO.getLoginId());

        String uploadImageUrl = null;
        try {
            uploadImageUrl = s3Uploader.uploadFiles(multipartFile, "profile");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ChangeUserInfoDTO changeUserInfoDTO = new ChangeUserInfoDTO(user);
        changeUserInfoDTO.setProfileImgUrl(uploadImageUrl);
        changeUserInfoDTO.setProfileUpdateDate(LocalDateTime.now());

        User profileUpdatedUser = changeUserInfoDTO.toEntity();
        userJPARepository.save(profileUpdatedUser);
    }

    public void updateSocialSignup(GetUserInfoDTO getUserInfoDTO){
        User user = userJPARepository.findByEmail(getUserInfoDTO.getEmail());

        if(user != null){
            ChangeUserInfoDTO changeUserInfoDTO = new ChangeUserInfoDTO(user);
            changeUserInfoDTO.setNickname(getUserInfoDTO.getNickname());
            changeUserInfoDTO.setPhoneNumber(getUserInfoDTO.getPhoneNumber());
            changeUserInfoDTO.setBirth(getUserInfoDTO.getBirth());
            changeUserInfoDTO.setUserCode(createUserCode());
            changeUserInfoDTO.setGender(getUserInfoDTO.getGender());
            changeUserInfoDTO.setPreferredCity(getUserInfoDTO.getPreferredCity());
            changeUserInfoDTO.setPreferredArea(getUserInfoDTO.getPreferredArea());
            changeUserInfoDTO.setActivityClass(getUserInfoDTO.getActivityClass());

            user = changeUserInfoDTO.toEntity();

            userJPARepository.save(user);

            friendService.createFriendAcceptTable(user.getId());
            friendService.createFriendAddTable(user.getId());
            friendService.createFriendListTable(user.getId());
        }
    }

    @Override
    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {
        try {
            String loginId = tokenProvider.getLoginId(changePasswordDTO.getLoginIdToken());
            User user = userJPARepository.findByLoginId(loginId);

            ChangeUserInfoDTO userInfoDTO = new ChangeUserInfoDTO(user);
            userInfoDTO.setPassword(bCryptPasswordEncoder.encode(changePasswordDTO.getPassword()));
            userInfoDTO.setProfileUpdateDate(LocalDateTime.now());
            User updateUser = userInfoDTO.toEntity();

            userJPARepository.save(updateUser);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public void changePasswordInMypage(ChangePasswordInMypageDTO changePasswordInMypageDTO) {
        try {
            Optional<User> userOptional = userJPARepository.findById(changePasswordInMypageDTO.getId());

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                if (bCryptPasswordEncoder.matches(changePasswordInMypageDTO.getOldPassword(), user.getPassword())) {
                    String newPassword = bCryptPasswordEncoder.encode(changePasswordInMypageDTO.getNewPassword());

                    ChangeUserInfoDTO userInfoDTO = new ChangeUserInfoDTO(user);
                    userInfoDTO.setPassword(newPassword);
                    userInfoDTO.setProfileUpdateDate(LocalDateTime.now());
                    User updateUser = userInfoDTO.toEntity();

                    userJPARepository.save(updateUser);
                } else {
                    System.out.println("비밀번호가 일치하지 않습니다.");
                }
            } else {
                System.out.println("사용자를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public User findByEmail(String email) {
        return userJPARepository.findByEmail(email);
    }

    @Override
    public void deleteUser(long id) {
        try {
            userJPARepository.updateUserAccountSuspersionById(id);
        } catch (Exception e) {
            System.out.println("예외가 발생했다.");
            e.printStackTrace();
        }
    }
}