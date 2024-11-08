package fourman.project1.jwt;

import fourman.project1.domain.user.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

        //클라이언트 요청에서 username, password 추출
        String username=obtainUsername(req);
        String password=obtainPassword(req);

        // 시큐리티에서 username과 pasword를 검증하기 위해서 token에 담음
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);

    }

    // 로그인 성공시 실행하는 메소드(JWT 발급)
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,Authentication authentication) throws IOException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        String token = jwtUtil.createJwt(username, 60 * 60 * 10 * 1000L);

        // 토큰을 쿠키에 담기
        Cookie cookie = new Cookie("auth_token", token);
        cookie.setHttpOnly(true);  // 클라이언트에서 자바스크립트로 접근 불가
        cookie.setSecure(false);    // HTTPS 연결에서만 사용
        cookie.setPath("/");       // 쿠키가 유효한 경로 설정
        cookie.setMaxAge(60 * 60 * 10);  // 쿠키 만료 시간 설정 (예: 10시간)

        res.addCookie(cookie);
        res.addHeader("Authorization","Bearer "+ token);
        res.sendRedirect("/home");
    }

    // 로그인 실패시 실행하는  메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed){
        res.setStatus(401);
    }



}
