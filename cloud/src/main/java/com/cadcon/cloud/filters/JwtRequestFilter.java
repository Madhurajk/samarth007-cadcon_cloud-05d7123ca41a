package com.cadcon.cloud.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cadcon.cloud.models.Person;
import com.cadcon.cloud.services.UserService;
import com.cadcon.cloud.util.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    private Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    public JwtRequestFilter(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        String authorisationHeader = httpServletRequest.getHeader("Authorization");

        logger.info("Request URL : {}", httpServletRequest.getRequestURI());

        logger.debug("authorisationHeader {}", authorisationHeader);
        String userName = null;
        String jwt = null;

        if (authorisationHeader != null && authorisationHeader.startsWith("Bearer ")) {
            jwt = authorisationHeader.substring(7);
            userName = jwtUtil.extractUsername(jwt);
        }

        logger.debug("jwt -> {}", userName);
        logger.debug("username -> {}", userName);

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Person person = userService.getUserByUsername(userName);
            logger.debug("retrieved person object from db -> {}", person);
            if (jwtUtil.validateToken(jwt, person)) {
                logger.debug("user token is valid!! -> {}", person);
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(person, null, new ArrayList<>());
                logger.debug("principle -> {}", token.getPrincipal());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
