package com.orange.mainservice.security;

import lombok.RequiredArgsConstructor;
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

import static com.orange.mainservice.user.UserRole.ROLE_ADMIN;
import static com.orange.mainservice.user.UserRole.ROLE_USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class JwtWebConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationService userDetailsService;
    private final JwtTokenConfig tokenConfig;
    private final JwtTokenUtil tokenUtils;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    PasswordEncoder passwordEncoder() {
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
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler) // 401 instead of 403 when unauthorized token
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
                .antMatchers("/ingredients/**").permitAll()
                .antMatchers("/comments/**").permitAll()
                .antMatchers("/components/**").permitAll()
                .antMatchers("/componentCategories/**").permitAll()
                .antMatchers("/recipeCategories/**").permitAll()
                .antMatchers("/**").hasAnyAuthority(ROLE_USER.name(), ROLE_ADMIN.name());
    }
}
