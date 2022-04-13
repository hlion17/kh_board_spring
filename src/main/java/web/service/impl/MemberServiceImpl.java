package web.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.dao.face.MemberDao;
import web.dto.Member;
import web.service.face.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	private Logger logger = LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	MemberDao memberDao;

	@Override
	public void join(Member member, Map<String, String> resJson) {
		logger.info("MemberService");
		
		int result = memberDao.insert(member);
		
		if (result >= 1) {
			logger.info("회원가입 성공");
			resJson.put("msg", "success");
		} else {
			logger.info("회원가입 실패");
			resJson.put("msg", "fail");
		}
		
	}

	@Override
	public void login(Member member, Map<String, String> json, HttpSession session) {
		String id = member.getId();
		String pw = member.getPw();
		String dest = "";
		if (session.getAttribute("dest") != null) dest = (String) session.getAttribute("dest"); 
		logger.info("로그인 전 URL: {}", dest);
		
		Member foundMember = memberDao.findById(id);
		
		if (foundMember == null) {
			logger.info("아이디가 존재하지 않음");
			json.put("msg", "아이디가 존재하지 않음");
			json.put("result", "false");
		} else if (!pw.equals(foundMember.getPw())) {
			logger.info("비밀번호가 일치하지 않습니다.");
			json.put("msg", "비밀번호가 일치하지 않습니다.");
			json.put("result", "false");
		} else {
			json.put("msg", "로그인에 성공하였습니다.");
			json.put("result", "true");
			if (!"".equals(dest)) {
				json.put("dest", dest);
			}
			session.setAttribute("isLogin", true);
			session.setAttribute("loginId", foundMember.getId());
		}
		
	}

}
