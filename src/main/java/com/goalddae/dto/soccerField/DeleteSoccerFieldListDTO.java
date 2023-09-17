package com.goalddae.dto.soccerField;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteSoccerFieldListDTO {
    List<Long> soccerFieldList;
}
