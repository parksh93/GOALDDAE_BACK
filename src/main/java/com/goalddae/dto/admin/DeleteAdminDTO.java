package com.goalddae.dto.admin;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAdminDTO {
    List<Long> deleteAdminList;
}
