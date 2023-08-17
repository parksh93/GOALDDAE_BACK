package com.goalddae.service;

import com.goalddae.dto.individualMatch.IndividualMatchDTO;
import com.goalddae.repository.IndividualMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndividualMatchService {

    @Autowired
    private IndividualMatchRepository individualMatchRepository;

    public List<IndividualMatchDTO> findAll() {
        return individualMatchRepository.findAll();
    }

    public IndividualMatchDTO findById(Long id) {
        return individualMatchRepository.findById(id);
    }

    public void save(IndividualMatchDTO individualMatchDTO) {
        individualMatchRepository.save(individualMatchDTO);
    }

    public void update(IndividualMatchDTO individualMatchDTO) {
        individualMatchRepository.update(individualMatchDTO);
    }

    public void deleteById(Long id) {
        individualMatchRepository.deleteById(id);
    }

    public void createIndividualMatchTable(String individualMatch) {
        individualMatchRepository.createIndividualMatchTable(individualMatch);
    }
}
