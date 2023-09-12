package com.goalddae.entity;

import com.goalddae.dto.friend.friendBlock.FindFriendBlockDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FriendBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private Long friendId;

    @Column(nullable = false)
    private LocalDateTime blockDate;

    @Column(nullable = false)
    private Long userId;
}
