package com.github.rusakovichma.persistance.authenticated.poc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebMvc
@Configuration
@ComponentScan({"com.github.rusakovichma.persistance.antitampering.poc"})
public class WebConfig implements WebMvcConfigurer {

}
