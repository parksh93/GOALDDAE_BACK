package com.goalddae.service;

import com.goalddae.config.jwt.TokenProvider;
import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.*;

import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.entity.User;
import com.goalddae.exception.NotFoundUserException;
import com.goalddae.repository.CommunicationBoardRepository;
import com.goalddae.repository.UsedTransactionBoardRepository;
import com.goalddae.repository.UserJPARepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService{
    private final UserJPARepository userJPARepository;
    private final CommunicationBoardRepository communicationBoardRepository;
    private final UsedTransactionBoardRepository usedTransactionBoardRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;
    private final FriendService friendService;


    @Autowired
    public UserServiceImpl(UserJPARepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenProvider tokenProvider,
                           CommunicationBoardRepository communicationBoardRepository,
                           UsedTransactionBoardRepository usedTransactionBoardRepository,
                           FriendService friendService){
        this.userJPARepository = userRepository;
        this.bCryptPasswordEncoder =bCryptPasswordEncoder;
        this.tokenProvider = tokenProvider;
        this.communicationBoardRepository = communicationBoardRepository;
        this.usedTransactionBoardRepository = usedTransactionBoardRepository;
        this.friendService = friendService;
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
        friendService.createFriendBlockTable(id);
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
    public String generateTokenFromLogin(LoginDTO loginDTO){
        try {
            User userInfo = getByCredentials(loginDTO.getLoginId());

            if (bCryptPasswordEncoder.matches(loginDTO.getPassword(), userInfo.getPassword())) {
                String token = tokenProvider.generateToken(userInfo, Duration.ofHours(2));

                return token;
            } else {
                throw new NotFoundUserException("login fail");
            }
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public GetUserInfoDTO getUserInfo(String token){
        if(tokenProvider.validToken(token)){
            User user = userJPARepository.findById(tokenProvider.getUserId(token)).get();

            GetUserInfoDTO userInfoDTO = new GetUserInfoDTO(user);

            return userInfoDTO;
        }
        return null;
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
    public String checkLoginIdAndEmail(RequestFindPasswordDTO findPasswordDTO) {
        int userCnt = userJPARepository.countByLoginIdAndEmail(findPasswordDTO.getLoginId(), findPasswordDTO.getEmail());
        if(userCnt == 1){
            return tokenProvider.generateLoinIdToken(findPasswordDTO.getLoginId(), Duration.ofMinutes(5));
        }else{
            return "";
        }
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

            User updateduser = changeUserInfoDTO.toEntity();

            userJPARepository.save(updateduser);
        }
    }

    @Override
    public List<CommunicationBoard> getUserCommunicationBoardPosts(long userId) {
        return communicationBoardRepository.findByUserId(userId);
    }

    @Override
    public List<UsedTransactionBoard> getUserUsedTransactionBoardPosts(long userId) {
        return usedTransactionBoardRepository.findByUserId(userId);
    }


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
}