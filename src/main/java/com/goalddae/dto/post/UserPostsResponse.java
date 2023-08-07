package com.goalddae.dto.post;

import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import lombok.*;

import java.util.List;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class UserPostsResponse {
    private List<CommunicationBoard> communicationBoardPosts;
    private List<UsedTransactionBoard> usedTransactionBoardPosts;
}
