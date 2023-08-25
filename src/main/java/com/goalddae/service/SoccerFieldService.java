package com.goalddae.service;

import com.goalddae.dto.soccerField.SoccerFieldInfoDTO;
import com.goalddae.entity.SoccerField;

import java.io.IOException;
import java.util.List;

public interface SoccerFieldService {
    public List<SoccerField> searchSoccerFields(String searchTerm);
    public List<String> searchCityNames(String searchTerm) throws IOException;
    public SoccerFieldInfoDTO findById(long id);
}
