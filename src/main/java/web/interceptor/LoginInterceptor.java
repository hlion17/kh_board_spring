package web.interceptor;

import static org.hamcrest.CoreMatchers.nullValue;

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
		logger.info("[로그인 PRE 인터셉터]");
		HttpSession session = request.getSession();
		
		if (session.getAttribute("isLogin") != null) {
			session.invalidate();
			logger.info(">> 로그인 정보 삭제");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("[로그인 POST 인터셉터]");
		HttpSession session = request.getSession();
		
		if (session.getAttribute("isLogin") != null) {
			logger.info(">> 로그인 성공");
			String dest = (String) session.getAttribute("dest");
			logger.info(">> Dest URL: ", dest);
			response.sendRedirect(dest != null ? dest : "/");
		}
	}
}
