package com.goalddae.service;

import com.goalddae.entity.CommunicationBoard;
import org.springframework.data.domain.Page;

public interface BoardService {
    Page<CommunicationBoard> findAll(Integer page, Integer Size);
}
