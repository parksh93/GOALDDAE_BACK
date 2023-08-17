package com.goalddae.dto.board;

import com.goalddae.entity.CommunicationReply;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyListDTO {

    private long id;
    private long boardId;
    private long userId; // 작성자 (userId)
    private String writer;  // 작성자 (닉네임)
    private String content;   // 내용
    private LocalDateTime writeDate;    // 작성일
    private LocalDateTime updateDate;   // 수정일
    private int status;
    private List<CommunicationReply> children; // 자식 댓글 리스트

}
