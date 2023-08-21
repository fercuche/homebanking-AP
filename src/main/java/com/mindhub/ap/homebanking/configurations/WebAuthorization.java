package com.mindhub.ap.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/clients","/api/login", "/api/logout").permitAll()
                .antMatchers("/admin/").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/clients").hasAnyAuthority("CLIENT")
                .antMatchers(HttpMethod.GET,"/api/accounts").hasAnyAuthority("CLIENT");


        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> response
                        .sendError(HttpServletResponse.SC_UNAUTHORIZED)));
        http.formLogin().successHandler((request, response, authentication) -> clearAuthenticationAttributes(request));
        http.formLogin().failureHandler((request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }
}