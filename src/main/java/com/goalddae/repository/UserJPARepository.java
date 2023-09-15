package com.goalddae.repository;

import com.goalddae.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserJPARepository extends JpaRepository<User, Long> {
    User findByLoginId(String loginId);
    int countByLoginId(String loginId);
    int countByEmail(String email);
    int countByNickname(String nickname);
    @Query(value = "SELECT u.login_id FROM users u WHERE u.email = :email AND u.name = :name", nativeQuery = true)
    String findLoginIdByEmailAndName(@Param("email") String email, @Param("name") String name);
    int countByLoginIdAndEmail(String loginId, String email);
    User findByEmail(String email);
    @Modifying
    @Transactional
    @Query(value = "UPDATE User u SET u.accountSuspersion = true WHERE u.id = :id")
    void updateUserAccountSuspersionById(long id);
    List<User> findByAuthority(String authority);
}
