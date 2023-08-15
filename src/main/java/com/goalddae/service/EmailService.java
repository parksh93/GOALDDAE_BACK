package com.goalddae.service;

public interface EmailService {
    public String sendSimpleMessage(String email) throws Exception;
    public void sendChangPasswordMessage(String email) throws Exception;
}
