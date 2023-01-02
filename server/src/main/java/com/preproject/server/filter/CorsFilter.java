package com.preproject.server.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String origin = request.getHeader("Host");
            // Origin 검증
            // Todo 검증로직은 추가하지 않음 모두 허용
            // 검증 후 헤더 추가
            response.addHeader("Access-Control-Allow-Origin", origin);
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
            response.addHeader("Access-Control-Allow-Headers", "Authorization, Refresh");
            response.setIntHeader("Access-Control-Max-Age", 3600);

        } catch (Exception e) {
            // 검증 실패 시 공격 Origin 로깅
            logger.info(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
