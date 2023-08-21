package com.goalddae.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RequestFindPasswordDTO {
    private String loginId;
    private String email;
}
