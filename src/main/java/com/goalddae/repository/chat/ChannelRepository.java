package com.goalddae.repository.chat;

import com.goalddae.entity.Channel;
import com.goalddae.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

}
