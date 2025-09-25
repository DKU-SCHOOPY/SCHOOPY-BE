package com.schoopy.back.global.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.schoopy.back.global.provider.JwtProvider;
import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    // 스킵할 경로(컨텍스트패스 유무 모두 지원)
    private static final String[] SKIP_PREFIXES = new String[] {
        // Swagger/Actuator
        "/swagger-ui", "/v3/api-docs", "/swagger-resources", "/webjars",
        "/actuator/health", "/actuator/info",
        // 로그인/소셜 로그인/토큰 획득 경로
        "/auth", "/auth/", "/oauth", "/oauth/"
    };

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 1) CORS 프리플라이트는 무조건 통과
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true;

        // 2) 컨텍스트패스(/api)가 있든 없든 둘 다 매칭
        String uri = request.getRequestURI();   // 예: /api/auth/login
        String ctx = request.getContextPath();  // 예: /api 또는 ""

        for (String p : SKIP_PREFIXES) {
            if (uri.startsWith(p)) return true;                         // /auth/**, /oauth/**, /v3/api-docs ...
            if (StringUtils.hasText(ctx) && uri.startsWith(ctx + p))    // /api/auth/**, /api/v3/api-docs ...
                return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = parseBearerToken(request);

        // 토큰 없으면 다음 필터로 (익명 접근 허용 경로는 SecurityConfig에서 permitAll)
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String studentNum = jwtProvider.validate(token);
            if (studentNum == null) {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"code\":\"INVALID_TOKEN\",\"message\":\"Invalid or expired token.\"}");
                return;
            }

            UserEntity userEntity = userRepository.findByStudentNum(studentNum);
            if (userEntity == null) {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"code\":\"USER_NOT_FOUND\",\"message\":\"User not found.\"}");
                return;
            }

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole()));

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            AbstractAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(studentNum, null, authorities);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            // 체인 진행
            filterChain.doFilter(request, response);

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            // 토큰 만료
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"code\":\"TOKEN_EXPIRED\",\"message\":\"Token has expired.\"}");
        } catch (Exception e) {
            // 기타 오류
            e.printStackTrace();
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"code\":\"JWT_ERROR\",\"message\":\"Error processing JWT.\"}");
        }
    }

    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) return null;
        return authorization.substring(7);
    }
}
    