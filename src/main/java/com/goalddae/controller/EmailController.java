package com.goalddae.controller;

import com.goalddae.dto.email.ResponseCertificationCodeDTO;
import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.service.EmailService;
import com.goalddae.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private EmailService emailService;
    private UserService userService;

    @Autowired
    public EmailController(EmailService emailService, UserService userService){
        this.emailService = emailService;
        this.userService = userService;
    }

    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public ResponseEntity<?> sendEmail(@RequestBody SendEmailDTO sendEmailDTO) throws Exception{
        boolean checkEmail = userService.checkEmail(sendEmailDTO);
        if (checkEmail == true){
            ResponseCertificationCodeDTO responseCertificationCodeDTO = ResponseCertificationCodeDTO.builder()
                    .certificationCode(emailService.sendSimpleMessage(sendEmailDTO.getEmail()))
                    .build();

            return ResponseEntity.ok(responseCertificationCodeDTO);
        }else{
            return ResponseEntity.ok(false);
        }
    }
}
