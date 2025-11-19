package com.mh.crj.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mh.crj.serviceImpl.UserRegisterServiceimpl;
import com.mh.crj.utility.JwtUtilService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 *  AppFilter — Custom JWT Authentication Filter
 * ------------------------------------------------
 * This filter runs once per request and performs:
 * 1️ Extract JWT token from the "Authorization" header.
 * 2️ Validate the token using JwtUtilService.
 * 3️ If valid, set the authentication in SecurityContextHolder.
 */
@Component
public class AppFilter extends OncePerRequestFilter {

    //  Utility for JWT operations (generate, validate, extract username, etc.)
	@Autowired
	private JwtUtilService jwtUtilService;

    //  Loads user details from database for authentication verification
	@Autowired
	private UserRegisterServiceimpl userRegisterServiceimpl;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

        //  Extract "Authorization" header
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;
        
	    //  Check if header contains "Bearer" token
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7); // Remove "Bearer " prefix
            try {
                username = jwtUtilService.extractUsername(token);
            } catch (Exception e) {
//            	e.printStackTrace();
                System.out.println(" Invalid JWT token: " + e.getMessage());
            }
        }
        //  If username exists & not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load user details from DB
            UserDetails userDetails = userRegisterServiceimpl.loadUserByUsername(username);

            //  Validate JWT token
            if (jwtUtilService.validateToken(token, userDetails)) {

                //  Create Spring Security authentication object
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                //  Attach additional request details
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                //  Set the authentication in context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        //  Continue the filter chain
        filterChain.doFilter(request, response);
    }
}