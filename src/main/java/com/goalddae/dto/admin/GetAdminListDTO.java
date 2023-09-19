package com.goalddae.dto.admin;

import com.goalddae.entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAdminListDTO {
    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String signUpDate;

    public GetAdminListDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }
}
