package com.orange.mainservice.config;

import com.orange.mainservice.security.AuthenticationService;
import com.orange.mainservice.security.JwtAuthenticationEntryPoint;
import com.orange.mainservice.security.JwtTokenUtil;
import com.orange.mainservice.security.JwtTokenVerifierFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.orange.mainservice.entity.enums.UserRole.ROLE_ADMIN;
import static com.orange.mainservice.entity.enums.UserRole.ROLE_USER;

@Configuration
@EnableWebSecurity
public class JwtWebConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationService userDetailsService;
    private final JwtTokenConfig tokenConfig;
    private final JwtTokenUtil tokenUtils;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    public JwtWebConfig(AuthenticationService userDetailsService, JwtTokenConfig tokenConfig,
                        JwtTokenUtil tokenUtils, JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.tokenConfig = tokenConfig;
        this.tokenUtils = tokenUtils;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                    .userDetailsService(this.userDetailsService)
                    .passwordEncoder(this.passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler) // 401 instead of 403 when unauthorized token
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(new JwtTokenVerifierFilter(tokenConfig, tokenUtils), UsernamePasswordAuthenticationFilter.class)

                .authorizeRequests()
                // ------- general -------
                    .antMatchers("/authentication/**").permitAll()
                    .antMatchers("/",
                            "/**/*.png",
                            "/**/*.gif",
                            "/**/*.svg",
                            "/**/*.jpg",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js").permitAll()
                    .antMatchers("/**").hasAnyAuthority(ROLE_USER.name(),ROLE_ADMIN.name());

    }
}
