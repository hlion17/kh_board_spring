package web.unitTest.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import web.dao.face.MemberDao;
import web.dto.Member;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
@WebAppConfiguration
@Transactional
public class MemberDaoTest {
	@Autowired
	private MemberDao memberDao;
	
	@DisplayName("아이디로 회원 정보 조회")
	@Test
	void findById() {
		// given
		Member paramMember = new Member();
		Member actual;
		
		// when
		paramMember.setId("1");
		actual = memberDao.findById(paramMember.getId());
		// then
		assertEquals(actual.getId(), paramMember.getId());
		
		// when
		paramMember.setId("2");
		actual = memberDao.findById(paramMember.getId());
		// then
		assertNull(actual);
	}
	
	@DisplayName("회원가입 테스트")
	@Test
	void insert() {
		// given
		Member paramMember = new Member();
		paramMember.setId("newMemberId");
		paramMember.setPw("newMemberPw");
		paramMember.setNick("newMemberNick");
		
		Member foundMember;
		
		// when
		memberDao.insert(paramMember);
		
		// then
		foundMember = memberDao.findById(paramMember.getId());
		assertEquals(foundMember.getId(), paramMember.getId());
		assertEquals(foundMember.getPw(), paramMember.getPw());
		assertEquals(foundMember.getNick(), paramMember.getNick());
	}

}
