package com.jpmorgan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // Created 2 users for  with USER and Admin Roles
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("atul").password("{noop}password").roles("USER")
                .and()
                .withUser("jpmorgan").password("{noop}password").roles("USER", "ADMIN");

    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/customer/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/products/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/product/buy").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/orders/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/h2/**").permitAll().and()
                .csrf().disable()
                .formLogin().disable();
    }


}
