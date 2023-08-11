package com.goalddae.repository;

import com.goalddae.dto.user.CheckNicknameDTO;
import com.goalddae.dto.user.LoginDTO;
import com.goalddae.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<User, Long> {
    User findByLoginId(String loginId);
    Long countByLoginId(String loginId);
    Long countByEmail(String email);
    Long countByNickname(String nickname);
}
