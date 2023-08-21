package com.goalddae.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@PropertySource("classpath:application-payment.properties")
public class PaymentProperties {
    @Value("${franchise_key}")
    private String franchiseKey;
    @Value("${rest_api_key}")
    private String restApiKey;
    @Value("${rest_api_secret}")
    private String restApiSecret;
}
