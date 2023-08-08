package com.goalddae.repository;

import com.goalddae.dto.user.GetUserInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<GetUserInfoDTO, Long> {
    GetUserInfoDTO findUserById(long id);
}
