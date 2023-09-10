package com.goalddae.repository.chat;

import com.goalddae.entity.Channel;
import com.goalddae.entity.ChannelUser;
import com.goalddae.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChannelUserRepository extends JpaRepository<ChannelUser, Long> {
    List<ChannelUser> findByUserId(Long userId);
    List<ChannelUser> findByChannelId(Long channelId);
//    @Modifying
//    @Query(value = "DELETE FROM channel_user WHERE channel_id = :channelId", nativeQuery = true)
    @Transactional
    void deleteByChannelId(@Param("channelId") Long channelId);
}
