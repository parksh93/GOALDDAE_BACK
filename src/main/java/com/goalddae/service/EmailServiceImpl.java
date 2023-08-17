package com.goalddae.service;

import com.goalddae.dto.user.RequestFindPasswordDTO;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender emailSender;

    public static String certificationCode;

    private MimeMessage createMessage(String email)throws Exception{
        certificationCode = createKey();
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email);//보내는 대상
        message.setSubject("GOALDDAE 인증번호 발송");//제목

        // 문자열로 html 퍼블리싱하게 되면 메일로 해당 퍼블리싱에 맞게 화면이 출력된다.
        String msg="";
        msg+= "<div style='margin:100px;'>";
        msg+= "<h1> 안녕하세요 GOALDDAE입니다. </h1>";
        msg+= "<br>";
        msg+= "<p>화면으로 돌아가 아래 코드를 입력해주세요<p>";
        msg+= "<br>";
        msg+= "<p>감사합니다!<p>";
        msg+= "<br>";
        msg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msg+= "<h3 style='color:green;'>아래 인증 코드를 입력해 주세요.</h3>";
        msg+= "<div style='font-size:130%'>";
        msg+= "인증번호 : <strong>";
        msg+= certificationCode+"</strong><div><br/> ";
        msg+= "</div>";

        // 위에서 작성한 메일 내용을 Text로 담는다.
        message.setText(msg, "utf-8", "html");//내용
        // 발신자 메일, 수신자가 확인할 이름
        message.setFrom(new InternetAddress("goalddae@naver.com","GOALDDAE"));

        return message;
    }

    // 인증코드 생성부
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }

        return key.toString();
    }

    @Override
    public String sendSimpleMessage(String email) throws Exception {
        MimeMessage message = createMessage(email);
        try{
            emailSender.send(message);
        }catch(MailException e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

        return certificationCode;
    }

    public MimeMessage createGoPasswordChange(RequestFindPasswordDTO findPasswordDTO) throws Exception{
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, findPasswordDTO.getEmail());
        message.setSubject("GOALDDAE 비밀번호 분실에 따른 변경 안내");

        String msg="";
        msg+= "<div style='margin:100px;'>";
        msg+= "<h1> 안녕하세요 GOALDDAE입니다. </h1>";
        msg+= "<br/>";
        msg+= "<p>비밀번호를 분실에 따라 새로운 비밀번호 변경해주셔야합니다.<p>";
        msg+= "<br>";
        msg+= "<p>아래의 <strong>\"비밀번호 변경\"</strong>을 클릭하시면 비밀번호를 변경할 수 있는 페이지로 이동됩니다.</p>";
        msg+= "<p>감사합니다!<p>";
        msg+= "<br/>";
        msg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msg+= "<h3>아래 글자를 눌러주세요.</h3>";
        msg+= "<div style='font-size:130%; width: 100%'>";
        msg+= "<a href='http://localhost:3000/changeLostPassword'";
        msg += "style='color:green;'>";
        msg+= "비밀번호 변경</a>";
        msg += "<div><br/> ";
        msg+= "</div>";

        message.setText(msg, "utf-8", "html");

        message.setFrom(new InternetAddress("goalddae@naver.com","GOALDDAE"));

        return message;
    }

    @Override
    public void sendChangPasswordMessage(RequestFindPasswordDTO findPasswordDTO) throws Exception {
        MimeMessage message = createGoPasswordChange(findPasswordDTO);
        try{
            emailSender.send(message);
        }catch(MailException e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

}