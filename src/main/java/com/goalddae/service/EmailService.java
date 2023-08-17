package com.goalddae.service;

import com.goalddae.dto.user.RequestFindPasswordDTO;

public interface EmailService {
    public String sendSimpleMessage(String email) throws Exception;
    public void sendChangPasswordMessage(RequestFindPasswordDTO findPasswordDTO) throws Exception;
}
