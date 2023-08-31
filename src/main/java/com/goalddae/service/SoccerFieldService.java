package com.goalddae.service;

import com.goalddae.dto.soccerField.SoccerFieldDTO;
import com.goalddae.dto.soccerField.SoccerFieldInfoDTO;
import com.goalddae.entity.SoccerField;
import java.io.IOException;
import java.util.List;

public interface SoccerFieldService {

    List<SoccerField> searchSoccerFields(String searchTerm);
    List<String> searchCityNames(String searchTerm) throws IOException;
    SoccerField save(SoccerField soccerField);
    SoccerField update(SoccerFieldDTO soccerFieldDTO);
    SoccerField findSoccerFieldByName(String fieldName);
    void delete(long id);
    public SoccerFieldInfoDTO findById(long id);
}
