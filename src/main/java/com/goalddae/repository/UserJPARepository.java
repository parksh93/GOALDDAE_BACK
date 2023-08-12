package com.goalddae.repository;

import com.goalddae.dto.user.CheckNicknameDTO;
import com.goalddae.dto.user.LoginDTO;
import com.goalddae.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

public interface UserJPARepository extends JpaRepository<User, Long> {
    User findByLoginId(String loginId);
    Long countByLoginId(String loginId);
    Long countByEmail(String email);
    Long countByNickname(String nickname);
    @Query(value = "SELECT u.login_id FROM users u WHERE u.email = :email AND nickname = :nickname", nativeQuery = true)
    String findLoginIdByEmailAndNickname(@Param("email") String email, @Param("nickname") String nickname);

}
