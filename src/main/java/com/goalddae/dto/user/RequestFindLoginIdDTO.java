package com.goalddae.dto.user;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestFindLoginIdDTO {
    private String email;
    private String name;
}
