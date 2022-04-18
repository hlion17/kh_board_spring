package web.unitTest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import web.controller.MemberController;
import web.dto.Member;
import web.service.face.MemberService;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberControllerTest {

	private MockMvc mockMvc;
	
	@InjectMocks
	private MemberController memberController;
	
	@Mock
	MemberService memberService;
	
	private static List<Member> members = new ArrayList<>();
	
	@BeforeAll
	public static void setSamples() {
		Member member = new Member();
		member.setId("Alice");
		member.setNick("Apple");
		member.setPw("1234");
		members.add(member);
		
		Member existMember = new Member();
		existMember.setId("1");
		existMember.setPw("2");
		existMember.setNick("3");
		members.add(existMember);
	}
	
	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(memberController)
				.build();
	}
	
	@Test
	void 로그인_페이지_요청_테스트() throws Exception {
		// given
		RequestBuilder request = MockMvcRequestBuilders
				.get("/member/login");
		
		// when
		ResultActions result = this.mockMvc.perform(request);
		
		// then
		result.andDo(print())
			.andExpect(view().name("member/login"))
			.andExpect(status().isOk());
	}
	
	@Test
	void 로그인_인증요청_실패_테스트() throws Exception {
		// given
		//MockHttpSession session = new MockHttpSession();
		RequestBuilder request = post("/member/login")
				//.session(session)
				.param("id", members.get(0).getId())
				.param("pw", members.get(0).getPw());
		// MemberService.login() 실패 세팅
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("loginResult", -1);
		resultMap.put("member", members.get(0));
		when(memberService.login(any())).thenReturn(resultMap);
		
		// when
		ResultActions result = this.mockMvc.perform(request);
		
		// then
		result.andDo(print())
			.andExpectAll(
						view().name("member/login"), 
						status().isOk(),
						request().sessionAttributeDoesNotExist("isLogin", "loginId", "loginNick")
					);
	}
	
	@Test
	void 로그인_인증요청_성공_테스트() throws Exception {
		// given
		//MockHttpSession session = new MockHttpSession();
		RequestBuilder request = post("/member/login")
				//.session(session)
				.param("id", members.get(1).getId())
				.param("pw", members.get(1).getPw());
		// MemberService.login() 실패 세팅
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("loginResult", 1);
		resultMap.put("member", members.get(1));
		when(memberService.login(any())).thenReturn(resultMap);
		
		// when
		ResultActions result = this.mockMvc.perform(request);
		
		// then
		result.andDo(print())
			.andExpectAll(
						// view는 인터셉터에 따라 결과가 달라진다.
						// loginInterceptor는 따로 단위테스트 할 예정 
						//view().name("member/login"), 
						status().isOk(),
						request().sessionAttribute("isLogin", true),
						request().sessionAttribute("loginId", notNullValue()),
						request().sessionAttribute("loginNick", notNullValue())
					);
	}
	

}
