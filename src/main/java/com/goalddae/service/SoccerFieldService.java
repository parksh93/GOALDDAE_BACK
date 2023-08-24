package com.goalddae.service;

import com.goalddae.entity.SoccerField;
import java.io.IOException;
import java.util.List;

public interface SoccerFieldService {

    List<SoccerField> searchSoccerFields(String searchTerm);

    List<String> searchCityNames(String searchTerm) throws IOException;

    SoccerField addSoccerField(SoccerField soccerField);

    SoccerField findSoccerFieldByName(String fieldName);

    void deleteSoccerField(long id);
}
