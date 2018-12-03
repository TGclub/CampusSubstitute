package com.wizzstudio.substitute.config;


import com.wizzstudio.substitute.security.AuthenticationFilter;
import com.wizzstudio.substitute.security.service.CustomAdminDetailsService;
import com.wizzstudio.substitute.security.service.CustomUserDetailsService;
import com.wizzstudio.substitute.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Kikyou on 18-11-12
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private CustomAdminDetailsService adminDetailsService;

    @Autowired
    private RedisUtil redisUtil;

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoderBean());
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoderBean());
        return authenticationProvider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AuthenticationFilter filter = new AuthenticationFilter(userDetailsService, adminDetailsService, redisUtil);

        http
                .csrf()
                .disable()
                .exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests();


        //http.authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests()
                .antMatchers("/login/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/indent/**")
                .hasAuthority("ROLE_USER");
        http
                .logout()
                .logoutUrl("/logout");
    }

}