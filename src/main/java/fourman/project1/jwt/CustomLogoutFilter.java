package fourman.project1.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CustomLogoutFilter extends GenericFilterBean {

    private static final String LOGOUT_URL = "/logout";  // 로그아웃 URL

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getRequestURI().equals(LOGOUT_URL)) {
            deleteAccessTokenCookie(response);
        }

        // 필터 체인 계속 진행
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // 액세스 토큰 삭제 처리
    private void deleteAccessTokenCookie(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie("auth_token", "");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);
        response.addCookie(accessTokenCookie);
    }
}
