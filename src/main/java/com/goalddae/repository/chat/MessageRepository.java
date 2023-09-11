package com.goalddae.repository.chat;

import com.goalddae.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChannelIdOrderBySendDateAsc(Long channelId);

//    @Modifying
//    @Query(value = "DELETE FROM message WHERE channel_id = :channelId", nativeQuery = true)
    @Transactional
    void deleteByChannelId(@Param("channelId") Long channelId);
}
