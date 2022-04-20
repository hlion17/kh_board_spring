package web.unitTest.interceptor;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import web.controller.MemberController;
import web.interceptor.AuthInterceptor;
import web.interceptor.LoginInterceptor;
import web.service.face.MemberService;

@ExtendWith(MockitoExtension.class)
public class LoginInterceptorTest {

	MockMvc mockMvc;
	
	@InjectMocks
	MemberController memberController;
	
	@Mock
	MemberService memberService;
	
	LoginInterceptor loginInterceptor = new LoginInterceptor();
	
	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(memberController)
				.addInterceptors(loginInterceptor)
				//.apply(SharedHttpSessionConfigurer.sharedHttpSession())
				.build();
	}
	
	@DisplayName("이전 페이지 저장(이전페이지 정보가 없을 경우)")
	@Test
	void loginInterceptorNotWithDest() throws Exception {
		// given
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		RequestBuilder request = get("/member/login")
				.session(session)
				.header("Referer", "https://www.test.com/loginPrevPage") 
				.header("scheme", "https") 
				.header("Host", "https://www.test.com");
		
		loginInterceptor.preHandle(mockRequest, response, null);
		
		// when
		ResultActions result =  this.mockMvc.perform(request);
		
		// then
		result
			//.andDo(print())
			.andExpectAll(
						request().sessionAttributeDoesNotExist("isLogin")
						, request().sessionAttribute("dest", "/loginPrevPage")
						, status().isOk()
					);
		
	}
	
	@DisplayName("이전 페이지 저장(이전페이지 정보가 있을 경우)")
	@Test
	void loginInterceptorWithDest() throws Exception {
		// given
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("dest", "/authPrevPage");
		RequestBuilder request = get("/member/login")
				.session(session)
				.header("Referer", "https://www.test.com/loginPrevPage") 
				.header("scheme", "https") 
				.header("Host", "https://www.test.com");
		
		loginInterceptor.preHandle(mockRequest, response, null);
		
		// when
		ResultActions result =  this.mockMvc.perform(request);
		
		// then
		result
			//.andDo(print())
			.andExpectAll(
						request().sessionAttributeDoesNotExist("isLogin")
						, request().sessionAttribute("dest", "/authPrevPage")
						, status().isOk()
					);
		
	}
	
	@DisplayName("로그인 상태에서 로그인 요청을 보냈을 때")
	@Test
	void loginInterceptorWithLogin() throws Exception {
		// given
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("isLogin", true);
		RequestBuilder request = get("/member/login")
				.session(session)
				.header("Referer", "https://www.test.com/loginPrevPage") 
				.header("scheme", "https") 
				.header("Host", "https://www.test.com");
		
		loginInterceptor.preHandle(mockRequest, response, null);
		
		// when
		ResultActions result =  this.mockMvc.perform(request);
		
		// then
		result
			//.andDo(print())
			.andExpectAll(
						request().sessionAttributeDoesNotExist("isLogin", "dest")
						, forwardedUrl("member/login")
						, status().isOk()
					);
		
	}
	
	@DisplayName("로그인 POST 요청 인터셉트")
	@Test
	void loginProcess() throws Exception {
		// given
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		
		RequestBuilder request = post("/member/login");
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("loginResult", 1);
		
		loginInterceptor.preHandle(mockRequest, response, null);
		
		// mocking
		when(memberService.login(any())).thenReturn(resultMap);
		
		// when
		ResultActions result =  this.mockMvc.perform(request);
		
		// then
		result
			//.andDo(print())
			.andExpectAll(
						request().sessionAttribute("isLogin", true)
						, request().sessionAttribute("dest", nullValue())
						, redirectedUrl("/")
						, status().is3xxRedirection()
						, model().attribute("loginResult", 1)
					);
		
	}
	
}
