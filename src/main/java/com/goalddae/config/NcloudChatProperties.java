package com.goalddae.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:application-ncloudChat.properties")
public class NcloudChatProperties {
    @Value("${api-key}")
    private String apiKey;

    @Value("${project-id}")
    private String projectId;
}
