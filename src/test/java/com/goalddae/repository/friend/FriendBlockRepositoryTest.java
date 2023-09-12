package com.goalddae.repository.friend;

import com.goalddae.dto.friend.friendBlock.FindFriendBlockDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class FriendBlockRepositoryTest {
    @Autowired
    private FriendBlockRepository friendBlockRepository;

    @Test
    @Transactional
    @DisplayName("친구 차단 목록 조회")
    public void findFriendBlockListTest() {
        long userId = 1;
        List<FindFriendBlockDTO> friendBlockListDTO = friendBlockRepository.findFriendBlockList(userId);

        assertEquals(friendBlockListDTO.get(0).getNickname(), "넵이");
    }
}
