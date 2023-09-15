package com.goalddae.service;

import com.goalddae.dto.admin.DeleteAdminDTO;
import com.goalddae.dto.admin.GetAdminListDTO;
import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.*;

import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserJPARepository userJPARepository;

    @Test
    @Transactional
    @DisplayName("로그인 정보 확인 및 토큰발행")
    public void generateTokenFromLoginTest() {
        LoginDTO loginDTO = LoginDTO.builder()
                .loginId("asd")
                .password("qwer1234")
                .build();

//        String token = userService.generateTokenFromLogin(loginDTO);
//
//        assertNotEquals("", token) ;
    }

    @Test
    @Transactional
    @DisplayName("로그인 아이디 중복 체크")
    public void checkLoginIdTest(){
        CheckLoginIdDTO checkLoginIdDTO = CheckLoginIdDTO.builder().loginId("asd").build();

        boolean checkLoginId = userService.checkLoginId(checkLoginIdDTO);

        assertEquals(false, checkLoginId);
    }

    @Test
    @Transactional
    @DisplayName("이메일 중복 체크")
    public void checkEmailTest() {
        SendEmailDTO checkEmailDTO = SendEmailDTO.builder().email("aaa@gamil.com").build();

        boolean checkEmail = userService.checkEmail(checkEmailDTO);

        assertEquals(true, checkEmail);
    }

    @Test
    @Transactional
    @DisplayName("닉네임 중복 체크")
    public void checkNicknameTest() {
        CheckNicknameDTO checkNicknameDTO = CheckNicknameDTO.builder()
                .nickname("새로운 닉네임")
                .build();
        boolean checkNickname = userService.checkNickname(checkNicknameDTO);

        assertEquals(true, checkNickname);
    }

    @Test
    @Transactional
    @DisplayName("유저정보 수정 테스트")
    public void updateTest() {
        // given
        String loginId = "aura0211";
        String nickname = "수정닉네임";
        String email = "aura0211@naver.com";
        String phoneNumber = "01048394849";
        String preferredCity = "부산";
        String preferredArea = "경남";
        int activityClass = 10;

        GetUserInfoDTO getUserInfoDTO = GetUserInfoDTO.builder()
                .loginId(loginId)
                .nickname(nickname)
                .email(email)
                .phoneNumber(phoneNumber)
                .preferredCity(preferredCity)
                .preferredArea(preferredArea)
                .activityClass(activityClass)
                .build();

        // when
        userService.update(getUserInfoDTO);

        // then
        User updatedUser = userJPARepository.findByLoginId(loginId);
        assertEquals("aura0211@naver.com", updatedUser.getEmail());
        assertEquals("부산", updatedUser.getPreferredCity());
        assertEquals("수정닉네임", updatedUser.getNickname());
    }

    @Test
    @Transactional
    @DisplayName("이메일과 닉네임을 통해 로그인 아이디 가져오기")
    public void getLoginIdByEmail(){
        String email = "asd@naver.com";
        String name = "박상현";

        RequestFindLoginIdDTO findLoginIdDTO = RequestFindLoginIdDTO.builder()
                .email(email).name(name).build();

        ResponseFindLoginIdDTO loginId = userService.getLoginIdByEmailAndName(findLoginIdDTO);

        assertNull(loginId);
    }

    @Test
    @Transactional
    @DisplayName("로그인 아이디와 이메일에 해당하는 유저 정보 갯수 가져오기")
    public void countByLoginIdAndEmailTest() {
        String loginId = "asssss";
        String email = "asds@naver.com";

        RequestFindPasswordDTO findPasswordDTO = RequestFindPasswordDTO.builder()
                .loginId(loginId)
                .email(email)
                .build();

//        String loginIdToken = userService.checkLoginIdAndEmail(findPasswordDTO);
//
//        assertNotNull(loginIdToken);
    }

     @Test
    @Transactional
    @DisplayName("비밀번호 변경")
    public void changePasswordTest() {
//        String loginId = "asdas";
        String password = "asd123123";
        String email = "jsap50@naver.com";
        RequestFindPasswordDTO findPasswordDTO = RequestFindPasswordDTO.builder()
//                .loginId(loginId)
                .email(email)
                .build();

        ChangePasswordDTO changePasswordDTO = ChangePasswordDTO.builder()
//                .loginIdToken(userService.checkLoginIdAndEmail(findPasswordDTO))
                .password(password)
                .build();

        userService.changePassword(changePasswordDTO);
    }
    @Test
    @Transactional
    @DisplayName("이메일을 통해 회원정보 가져오기")
    public void findByEmail() {
        String email = "jsap50@naver.com";

        User user = userService.findByEmail(email);

        assertNotNull(user);
    }

    @Test
    @Transactional
    @DisplayName("관리자 조회")
    public void getAdminListTest() {
        String auth = "admin";
        List<GetAdminListDTO> adminList = userService.findByAuthority(auth);

        assertEquals("2023-09-14", adminList.get(0).getSignUpDate());
    }

    @Test
    @Transactional
    @DisplayName("관리자 추가")
    public void saveAdminTest() {
        SaveUserInfoDTO user = SaveUserInfoDTO.builder()
                .loginId("qweqw")
                .password("1234")
                .name("새관리자")
                .email("weq@nave.com")
                .phoneNumber("010-2321-2312")
                .build();

        userService.saveAdmin(user);
    }

    @Test
    @Transactional
    @DisplayName("관리자 삭제")
    public void deleteAdmin() {
        DeleteAdminDTO deleteAdminDTO = DeleteAdminDTO.builder()
                .deleteAdminList(List.of(10L)).build();

        userService.deleteAdmin(deleteAdminDTO);

        String auth = "admin";
        List<GetAdminListDTO> adminList = userService.findByAuthority(auth);

        assertEquals(3, adminList.size());
    }
}