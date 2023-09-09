package com.goalddae.dto.chat;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetChannelListDTO {
    private Long channelId;
    private long friendId;
    private String channelName;
    private String channelImgUrl;
}
