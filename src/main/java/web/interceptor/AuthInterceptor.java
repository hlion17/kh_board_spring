package web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("[로그인 인증 PRE 인터셉터]");
		logger.info(">> 인증 요청 URL: {}/{}", request.getRequestURI(), request.getQueryString());
		HttpSession session = request.getSession();
		
		if (session.getAttribute("isLogin") == null) {
			logger.info(">> 로그인 정보가 없습니다.");

			// 현재 URL 저장
			saveDest(request);
			
			// 로그인 하지 않은 사용자의 경우 로그인 페이지로 이동
			response.sendRedirect("/member/login");
			return false;
		}
		// 로그인 한 사용자의 경우 요청 Controller 호출
		logger.info(">> 로그인 인증 완료");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("[로그인 인증 POST 인터셉터]");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	private void saveDest(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		
		// 그냥 url로 하면 안되나?
		
		if (query == null || query.equals("null")) {
			query = "";
		} else {
			query = "?" + query;
		}
		
		if (request.getMethod().equals("GET")) {
				logger.info(" >> DEST : {}", uri + query);
				request.getSession().setAttribute("dest", uri + query);
		}
	}

}
