package com.orange.mainservice.security;

import com.orange.mainservice.exception.AuthenticationException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import static com.orange.mainservice.util.Constants.EMPTY_STRING;

@RequiredArgsConstructor
class JwtTokenVerifierFilter extends OncePerRequestFilter {

    private final JwtTokenConfig tokenConfig;
    private final JwtTokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(tokenConfig.getAuthenticationHeaders());

        if (Strings.isEmpty(authorizationHeader) || !authorizationHeader.startsWith(tokenConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authorizationHeader.replace(tokenConfig.getTokenPrefix(), EMPTY_STRING);

            String email = tokenUtil.getUsername(token);

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = tokenUtil.getAuthorities(token).stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    simpleGrantedAuthorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e){
            throw new AuthenticationException("Token has not been recognised");
        }
        filterChain.doFilter(request, response);
    }
}
