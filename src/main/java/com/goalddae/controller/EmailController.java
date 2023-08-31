package com.goalddae.controller;

import com.goalddae.dto.email.ResponseCertificationCodeDTO;
import com.goalddae.dto.email.SendEmailDTO;

import com.goalddae.dto.user.RequestFindPasswordDTO;

import com.goalddae.service.EmailService;
import com.goalddae.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@RestController
public class EmailController {
    private EmailService emailService;
    private UserService userService;

    @Autowired
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @RequestMapping(value = "/sendEmailSignup", method = RequestMethod.POST)
    public ResponseEntity<?> sendEmailSingup(@RequestBody SendEmailDTO sendEmailDTO) throws Exception {

        boolean checkEmail = userService.checkEmail(sendEmailDTO);
        if (checkEmail == true) {
            try {
                ResponseCertificationCodeDTO responseCertificationCodeDTO = ResponseCertificationCodeDTO.builder()
                        .certificationCode(emailService.sendSimpleMessage(sendEmailDTO.getEmail()))
                        .build();
                return ResponseEntity.ok(responseCertificationCodeDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            return ResponseEntity.ok(false);
        }
        return null;
    }


    @RequestMapping("/sendEmailFind/{email}")
    public ResponseEntity<?> sendEmailFind(@PathVariable String email) throws Exception{
        String certificationCode = emailService.sendSimpleMessage(email);

        ResponseCertificationCodeDTO certificationCodeDTO = ResponseCertificationCodeDTO.builder()
                .certificationCode(certificationCode).build();
        return ResponseEntity.ok(certificationCodeDTO);
    }

    @RequestMapping("/sendEmailChangePassword")
    public void sendEmailChangePassword(@RequestBody RequestFindPasswordDTO findPasswordDTO) throws Exception {
        emailService.sendChangPasswordMessage(findPasswordDTO);
    }
}
