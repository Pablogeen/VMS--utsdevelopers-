package com.utsdevelopers.vms.notification;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendgridConfig {

    @Bean
    public SendGrid sendGrid(@Value("${sendgrid.api.key}") String apiKey) {
        return new SendGrid(apiKey);
    }
}
