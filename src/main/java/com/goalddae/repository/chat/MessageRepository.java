package com.goalddae.repository.chat;

import com.goalddae.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChannelIdOrderBySendDateAsc(Long channelId);
}
