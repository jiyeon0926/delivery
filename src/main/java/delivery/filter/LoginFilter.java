package delivery.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/","/users/signup","/login"};

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (!isWhiteList(requestURI)) {
            // 세션을 가져옴. 세션이 존재하지 않으면 null 반환
            HttpSession session = httpServletRequest.getSession(false);

            // 세션이 없거나 로그인 정보가 없는 경우
            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 설정
                httpServletResponse.getWriter().write("로그인 해주세요"); // 클라이언트에 메시지 전송
                return;
            }

            // 세션에 저장된 객체가 LoginResponseDto인지 확인
            Long loginUser = (Long) session.getAttribute("LOGIN_USER");
            if (loginUser == null) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401 상태 설정
                httpServletResponse.getWriter().write("로그인 해주세요"); // 클라이언트에 메시지 전송
                return;
            }
        }
        // 필터 체인에 현재 요청과 응답 전달
        filterChain.doFilter(servletRequest, servletResponse);

    }

    private boolean isWhiteList(String requestURL) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURL);
    }
}
