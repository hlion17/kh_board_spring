package web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("[로그인 요청 PRE 인터셉터]");
		HttpSession session = request.getSession();
		
		saveDest(request);
		
		if (session.getAttribute("isLogin") != null) {
			session.invalidate();
			logger.info(">> 로그인 정보 삭제");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("[로그인 요청 POST 인터셉터]");
		HttpSession session = request.getSession();
		
		if (session.getAttribute("isLogin") != null) {
			logger.info(">> 로그인 성공");
			String dest = (String) session.getAttribute("dest");
			logger.info(">> Dest URL: {}", dest);
			logger.info(">> Dest로 Redirect");
			response.sendRedirect(dest != null ? dest : "/");
		}
	}
	
	// '로그인 페이지' 이전 페이지 저장
	private void saveDest(HttpServletRequest request) {
		// 로그인 get 요청 이면서 이전페이지 정보가 없을 경우 이전페이지 정보 저장
		if (request.getMethod().equals("GET") && request.getSession().getAttribute("dest") == null) {
			String referer = request.getHeader("Referer");
			String host = request.getHeader("Host");
			String scheme = request.getScheme();
			String dest = referer.replaceAll(host, "").replaceAll(scheme + "://", "");
			logger.info(">> login prev page: {}", dest);
			
			request.getSession().setAttribute("dest", dest);
		}
	}
	
}
