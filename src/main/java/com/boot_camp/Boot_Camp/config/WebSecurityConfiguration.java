package com.boot_camp.Boot_Camp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity

public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().antMatcher("/api/**")
                .csrf().disable();
    }
}

//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(
//            ServerHttpSecurity http) {
//        http.csrf().disable()
//                .authorizeExchange()
//
//                .pathMatchers("/api/**").permitAll()
//                .and()
//                .httpBasic();
//        return http.build();
//    }


