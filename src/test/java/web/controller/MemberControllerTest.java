package web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import web.dto.Member;
import web.service.face.MemberService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
@WebAppConfiguration
class MemberControllerTest {
	
	@Autowired
	WebApplicationContext context;
	
	@Autowired
	MemberService memberService;
	
	private MockMvc mockMvc;
	
	private static Member member;
	private static Member existMember;

	@BeforeAll
	public static void sample() {
		// 테스트 데이터
		member = new Member();
		member.setId("Alice");
		member.setNick("Apple");
		member.setPw("1234");
		
		existMember = new Member();
		existMember.setId("1");
		existMember.setPw("2");
		existMember.setNick("3");
	}
	
	@BeforeEach
	void setup() throws Exception {
//		this.mockMvc = MockMvcBuilders
//							.standaloneSetup(memberController)
//							.addInterceptors(loginInterceptor)
//							.addInterceptors(authInterceptor)
//							.build();
//		when(loginInterceptor.preHandle(any(), any(), any())).thenReturn(true);
//		when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);
		
		this.mockMvc = MockMvcBuilders
							.webAppContextSetup(this.context)
							.build();
		
	}
	
	@DisplayName("회원가입 페이지 이동")
	@Test
	void join() throws Exception {
		// given
		RequestBuilder request = MockMvcRequestBuilders
									.get("/member/join")
									.param("id", member.getId())
									.param("pw", member.getPw())
									.param("nick", member.getNick())
									.sessionAttr("isLogin", true);
		// when
		this.mockMvc.perform(request)
		// then
					.andExpect(view().name("member/join"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(forwardedUrl("/WEB-INF/views/member/join.jsp"));
	}
	
	@DisplayName("회원가입 - 가입성공")
	@Transactional
	@Test
	void joinProccessSuccess() throws Exception {
		// given
		RequestBuilder request = MockMvcRequestBuilders
									.post("/member/join")
									.param("id", member.getId())
									.param("pw", member.getPw())
									.param("nick", member.getNick());
		
		// when
		this.mockMvc.perform(request)
		// then
						//.andDo(print())
						.andExpect(redirectedUrl("/member/login"));
		
	}
	
	// 에러 발생을 위해 mock memberService 객체 주입하는 법 모르겠다.
	@DisplayName("회원가입 - 가입실패")
	@Transactional
	@Test
	@Disabled
	void joinProccessFail(@Mock MemberService memberService) throws Exception {
		this.memberService = memberService;
		
		// given
		when(memberService.join(any())).thenReturn(0);
		RequestBuilder request = MockMvcRequestBuilders
									.post("/member/join")
									.param("id", member.getId())
									.param("pw", member.getPw())
									.param("nick", member.getNick());
		
		// when
		this.mockMvc.perform(request)
		// then
						//.andDo(print())
						.andExpect(redirectedUrl("/member/join"));
		
	}
	
	
	@DisplayName("로그인 - 로그인 상태")
	@Test
	void login() throws Exception {
		// given
		RequestBuilder request = MockMvcRequestBuilders
				.post("/member/login")
				.param("id", existMember.getId())
				.param("pw", existMember.getPw())
				.param("nick", existMember.getNick());
				//.sessionAttr("isLogin", true);
		
		// when
		MvcResult result = this.mockMvc.perform(request)
		// then
					.andExpect(redirectedUrl("/"))
					//.andDo(print())
					.andReturn();
		
		HttpSession session = result.getRequest().getSession();
		
		assertTrue((Boolean) session.getAttribute("isLogin"));;
			
	}
	
	
//	  @Disabled
//	  @DisplayName("회원가입 - 가입성공")
//	  @Test void joinProccessSuccess() throws Exception { // given //
//	  when(memberService.join(any())).thenReturn(1); // assertEquals(1,
//	  memberService.join(member));
//	  
//	  RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/member/join")
//	  .param("id", member.getId()) .param("pw", member.getPw()) .param("nick",
//	  member.getNick());
//	  
//	  this.mockMvc // when .perform(requestBuilder) // then //.andDo(print())
//	  .andExpect(redirectedUrl("/member/login")); }
//	  
//	  @Disabled
//	  
//	  @DisplayName("회원가입 - 가입실패")
//	  
//	  @Test void joinProccess() throws Exception { // given //
//	  when(memberService.join(any())).thenReturn(0); // assertEquals(0,
//	  memberService.join(member));
//	  
//	  RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/member/join")
//	  .param("id", member.getId()) .param("pw", member.getPw()) .param("nick",
//	  member.getNick());
//	  
//	  this.mockMvc // when .perform(requestBuilder) // then
//	  .andExpect(redirectedUrl("/member/join")); }
//	  
//	  @Disabled
//	  
//	  @DisplayName("로그인 페이지 요청")
//	  
//	  @Test void login() throws Exception { this.mockMvc
//	  .perform(get("/member/login")) .andExpect(status().isOk())
//	  .andExpect(view().name("member/login")); }
//	  
//	  @Disabled
//	  
//	  @DisplayName("회원 로그인")
//	  
//	  @Test void loginProccess() throws Exception {
//	  
//	  }
	 

}
