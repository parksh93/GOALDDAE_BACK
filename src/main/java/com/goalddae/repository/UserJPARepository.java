package com.goalddae.repository;

import com.goalddae.dto.user.CheckNicknameDTO;

import com.goalddae.dto.user.GetUserInfoDTO;

import com.goalddae.dto.user.LoginDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface UserJPARepository extends JpaRepository<User, Long> {
    User findByLoginId(String loginId);
    int countByLoginId(String loginId);
    int countByEmail(String email);
    int countByNickname(String nickname);
    @Query(value = "SELECT u.login_id FROM users u WHERE u.email = :email AND name = :name", nativeQuery = true)
    String findLoginIdByEmailAndName(@Param("email") String email, @Param("name") String name);
    int countByLoginIdAndEmail(String loginId, String email);
}
