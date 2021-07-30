package fr.eql.projet01.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.eql.projet01.exception.CustomErrorDecoder;

@Configuration
public class FeignExceptionConfig {

    @Bean
    public CustomErrorDecoder mCustomErrorDecoder(){
        return new CustomErrorDecoder();
    }
}