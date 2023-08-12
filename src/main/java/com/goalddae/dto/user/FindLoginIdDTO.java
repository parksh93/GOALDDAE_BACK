package com.goalddae.dto.user;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindLoginIdDTO {
    private String email;
    private String nickname;
}
