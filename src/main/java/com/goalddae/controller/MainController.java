package com.goalddae.controller;

import com.goalddae.entity.SoccerFiled;
import com.goalddae.repository.SoccerJPARepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class MainController {

    private final SoccerJPARepository soccerJPARepository;

    public MainController(SoccerJPARepository soccerJPARepository) {
        this.soccerJPARepository = soccerJPARepository;
    }

    @GetMapping("search/soccerFiled")
    public String searchSoccerFiled(String keyword, Model model) {
        List<SoccerFiled> soccerFiledList = soccerJPARepository.findByKeyword(keyword);
        model.addAttribute(soccerFiledList);
        model.addAttribute("keyword", keyword);
        return "search";
    }
}
