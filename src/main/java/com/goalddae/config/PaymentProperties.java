package com.goalddae.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
//@PropertySource("classpath:application-payment.properties")
public class PaymentProperties {
    @Value("${payment.franchise_key}")
    private String franchiseKey;
    @Value("${payment.rest_api_key}")
    private String restApiKey;
    @Value("${payment.rest_api_secret}")
    private String restApiSecret;
}
