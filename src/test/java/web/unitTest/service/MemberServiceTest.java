package web.unitTest.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import web.dao.face.MemberDao;
import web.dto.Member;
import web.service.face.MemberService;
import web.service.impl.MemberServiceImpl;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
	@InjectMocks
	private MemberService memberService = new MemberServiceImpl();

	@Mock
	private MemberDao memberDao;
	
	@DisplayName("중복 아이디 회원가입")
	@Test
	void joinWithExistId() {
		// given
		Member paramMember = new Member();
		paramMember.setId("1");
		paramMember.setPw("2");
		paramMember.setNick("3");
		
		Member foundMember = new Member();
		foundMember.setId("1");
		foundMember.setPw("2");
		foundMember.setNick("3");
		
		// mocking
		when(memberDao.findById(paramMember.getId())).thenReturn(foundMember);
		
		// when
		int result = memberService.join(paramMember);
		
		// then
		assertEquals(-1, result);
		verify(memberDao, times(1)).findById(paramMember.getId());
		verify(memberDao, never()).insert(paramMember);
	}
	
	@DisplayName("회원가입 성공")
	@Test
	void joinsuccess() {
		// given
		Member paramMember = new Member();
		paramMember.setId("1");
		paramMember.setPw("2");
		paramMember.setNick("3");
		
		Member emptyMember = null;
		
		// mocking
		when(memberDao.findById(paramMember.getId())).thenReturn(emptyMember);
		when(memberDao.insert(paramMember)).thenReturn(1);
		
		// when
		int result = memberService.join(paramMember);
		
		// then
		assertEquals(1, result);
		verify(memberDao, times(1)).findById(paramMember.getId());
		verify(memberDao, times(1)).insert(paramMember);
	}
	
	@DisplayName("회원가입 실패")
	@Test
	void joinFail() {
		// given
		Member paramMember = new Member();
		paramMember.setId("1");
		paramMember.setPw("2");
		paramMember.setNick("3");
		
		Member emptyMember = null;
		
		// mocking
		when(memberDao.findById(paramMember.getId())).thenReturn(emptyMember);
		when(memberDao.insert(paramMember)).thenReturn(0);
		
		// when
		int result = memberService.join(paramMember);
		
		// then
		assertEquals(-1, result);
		verify(memberDao, times(1)).findById(paramMember.getId());
		verify(memberDao, times(1)).insert(paramMember);
	}
	
	@DisplayName("로그인실패 - 아이디가 존재하지 않음")
	@Test
	void loginNoId() {
		// given
		Member paramMember = new Member();
		paramMember.setId("1");
		paramMember.setPw("2");
		paramMember.setNick("3");
		
		Member emptyMember = null;
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("member", paramMember);
		
		// mocking
		when(memberDao.findById(paramMember.getId())).thenReturn(emptyMember);
		
		// when
		resultMap = memberService.login(resultMap);
		
		// then
		assertEquals(-1, resultMap.get("loginResult"));
		assertNotNull(resultMap.get("msg"));
		verify(memberDao, times(1)).findById(paramMember.getId());
	}
	
	@DisplayName("로그인실패 - 비밀번호가 틀림")
	@Test
	void login_invaildPw() {
		// given
		Member paramMember = new Member();
		paramMember.setId("1");
		paramMember.setPw("2");
		paramMember.setNick("3");
		
		Member foundMember = new Member();
		foundMember.setId("1");
		foundMember.setPw("222");
		foundMember.setNick("3");
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("member", paramMember);
		
		// mocking
		when(memberDao.findById(paramMember.getId())).thenReturn(foundMember);
		
		// when
		resultMap = memberService.login(resultMap);
		
		// then
		assertEquals(-2, resultMap.get("loginResult"));
		assertNotNull(resultMap.get("msg"));
		verify(memberDao, times(1)).findById(paramMember.getId());
	}
	
	@DisplayName("로그인성공")
	@Test
	void login_success() {
		// given
		Member paramMember = new Member();
		paramMember.setId("1");
		paramMember.setPw("2");
		paramMember.setNick("3");
		
		Member foundMember = new Member();
		foundMember.setId("1");
		foundMember.setPw("2");
		foundMember.setNick("3");
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("member", paramMember);
		
		// mocking
		when(memberDao.findById(paramMember.getId())).thenReturn(foundMember);
		
		// when
		resultMap = memberService.login(resultMap);
		
		// then
		assertEquals(1, resultMap.get("loginResult"));
		assertEquals(foundMember, resultMap.get("member"));
		verify(memberDao, times(1)).findById(paramMember.getId());
	}
	
	@DisplayName("회원상세정보 가져오기")
	@Test
	void getDetail() {
		// given
		Member paramMember = new Member();
		paramMember.setId("1");
		
		Member foundMember = new Member();
		foundMember.setId("1");
		foundMember.setPw("2");
		foundMember.setNick("3");
		
		// mocking
		when(memberDao.findById(paramMember.getId())).thenReturn(foundMember);
		
		// when
		Member resultMember = memberService.getDetail(paramMember);
		
		// then
		assertEquals(resultMember.getId(), foundMember.getId());
		assertEquals(resultMember.getPw(), foundMember.getPw());
		assertEquals(resultMember.getNick(), foundMember.getNick());
		verify(memberDao, times(1)).findById(paramMember.getId());
	}

}
