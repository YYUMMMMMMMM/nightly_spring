package com.nightly.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      String accessToken = parseAccessToken(request);

      if (accessToken == null) {
        String refreshToken = parseRefeshToken(request);

        if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
          String email = jwtUtil.extractUseremail(refreshToken);

          if (email != null) {
            String newAccessToken = jwtUtil.generateAccessToken(email);

            Cookie newAccessTokenCookie = new Cookie("accessToken", newAccessToken);
            newAccessTokenCookie.setHttpOnly(true);
            newAccessTokenCookie.setSecure(false);
            newAccessTokenCookie.setPath("/");
            newAccessTokenCookie.setMaxAge(60 * 60 * 24 * 7);

            response.addCookie(newAccessTokenCookie);
            response.setHeader("Authorization", "Bearer " + newAccessToken);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return;
          }
        }
      } else if (jwtUtil.validateToken(accessToken)) {
        String email = jwtUtil.extractUseremail(accessToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null,
            userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return;
      }
    } catch (Exception e) {
      System.out.println("doFilterInternal err : " + e.getMessage());
    }

    filterChain.doFilter(request, response);
  }

  // parseAccessToken
  private String parseAccessToken(HttpServletRequest request) {
    String accessToken = null;
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      accessToken = authHeader.substring(7);
    } else {
      Cookie[] cookies = request.getCookies();
      if (cookies != null) {
        for (Cookie cookie : cookies) {
          if ("accessToken".equals(cookie.getName())) {
            accessToken = cookie.getValue();
          }
        }
      }
    }
    return accessToken;
  }

  private String parseRefeshToken(HttpServletRequest request) {
    String refreshToken = null;
    String authHeader = request.getHeader("refreshToken");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      refreshToken = authHeader.substring(7);
    } else {
      Cookie[] cookies = request.getCookies();
      if (cookies != null) {
        for (Cookie cookie : cookies) {
          if ("refreshToken".equals(cookie.getName())) {
            refreshToken = cookie.getValue();
          }
        }
      }
    }
    return refreshToken;
  }
}
