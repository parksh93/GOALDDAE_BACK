package com.goalddae.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
// 정의 해둔 properties의 경로를 설정해준다
@PropertySource("classpath:application-email.properties")
public class EmailConfig {
    // @Value 애노테이션을 통해 해당 변수에 값을 주입해준다.
    @Value("${mail.id}")
    private String id;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.smtp.port}")
    private int port;
    @Value("${mail.smtp.auth}")
    private boolean auth;
    @Value("${mail.smtp.starttls.enable}")
    private boolean enable;
    @Value("${mail.smtp.starttls.required}")
    private boolean required;
    @Value("${mail.smtp.socketFactory.fallback}")
    private boolean fallback;
    @Value("${mail.smtp.socketFactory.class}")
    private String socketFactoryClass;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host); // smtp 서버 주소
        javaMailSender.setUsername(id); // 설정(발신) 메일 아이디
        javaMailSender.setPassword(password); // 설정(발신) 메일 패스워드
        javaMailSender.setPort(port); //smtp port
        javaMailSender.setJavaMailProperties(getMailProperties()); // 메일 인증서버 정보 가져온다.
        javaMailSender.setDefaultEncoding("UTF-8");  //인코딩
        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.auth",auth);
        properties.put("mail.smtp.starttls.enable", enable);
        properties.put("mail.smtp.starttls.required", required);
        properties.put("mail.smtp.socketFactory.fallback",fallback);
        properties.put("mail.smtp.socketFactory.class", socketFactoryClass);
        return properties;
    }
}