package web.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import web.dao.face.MemberDao;
import web.dto.Member;
import web.service.face.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	private Logger logger = LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	MemberDao memberDao;

	@Override
	public int join(Member member) {
		logger.info("MemberService");
		
		int result = memberDao.insert(member);
		
		if (result == 1) {
			logger.info("회원가입 성공");
			return 1;
		} else {
			logger.info("회원가입 실패");
			return -1;
		}
		
	}

	@Override
	public void login(Member member, Model model, HttpSession session) {
		String id = member.getId();
		String pw = member.getPw();
		
		// 필요없는 코드 확인 후 삭제
		String dest = "";
		if (session.getAttribute("dest") != null) dest = (String) session.getAttribute("dest"); 
		logger.info("로그인 전 URL: {}", dest);
		// --------------------------
		
		Member foundMember = memberDao.findById(id);
		
		if (foundMember == null) {
			logger.info("아이디가 존재하지 않음");
			model.addAttribute("msg", "아이디가 존재하지 않음");
		} else if (!pw.equals(foundMember.getPw())) {
			logger.info("비밀번호 불일치");
			model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
		} else {
			session.setAttribute("isLogin", true);
			session.setAttribute("loginId", foundMember.getId());
			session.setAttribute("loginNick", foundMember.getNick());
		}
		
	}

	@Override
	public void getDetail(Member member, Model model) {
		String id = member.getId();
		
		Member foundMember = memberDao.findById(id);
		logger.info("조회 된 회원 정보: {}", foundMember);
		
		model.addAttribute("member", foundMember);
	}

}
