package fourman.project1.jwt;

import fourman.project1.domain.user.User;
import fourman.project1.domain.user.CustomUserDetails;
import fourman.project1.repository.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 쿠키에서 JWT 토큰 추출
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            token = Arrays.stream(cookies)
                    .filter(cookie -> "auth_token".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        // 토큰이 없으면, 필터 체인 계속 진행
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
            return;
        }

        // 토큰에서 사용자 이름 추출
        String username = jwtUtil.getUsername(token);

        // 해당 사용자 정보 객체를 생성
        User user = userRepository.findByUsername(username);

        // 사용자 정보가 존재하지 않으면 401 반환
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
            return;
        }

        // CustomUserDetails 객체로 감싸기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);


        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }
}
