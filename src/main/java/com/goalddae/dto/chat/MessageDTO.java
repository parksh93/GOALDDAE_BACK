package com.goalddae.dto.chat;

import com.goalddae.entity.Channel;
import com.goalddae.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String content;
    private Long channelId;
    private long userId;
    private String senderName;
    private LocalDateTime sendDate;
}
