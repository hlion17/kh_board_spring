package web.service.impl;


import java.util.HashMap;
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
		
		// 중복된 ID인지 확인
		if (memberDao.findById(member.getId()) == null) {
			logger.warn("중복된 아이디로 가입요청");
			return -1;
		}
		
		int result = memberDao.insert(member);
		
		if (result == 1) {
			logger.info("회원가입 성공");
			return 1;
		} else {
			logger.warn("회원가입 실패");
			return -1;
		}
		
	}

	@Override
	public Map<String, Object> login(Map<String, Object> resultMap) {
		// 요청 파라미터 분석
		String id = ((Member) resultMap.get("member")).getId();
		String pw = ((Member) resultMap.get("member")).getPw();
		
		// 요청한 id 조회
		Member foundMember = memberDao.findById(id);
		
		// 요청한 회원 정보 검증
		if (foundMember == null) {
			logger.info("아이디가 존재하지 않음");
			resultMap.put("loginResult", -1);
			resultMap.put("msg", "아이디를 확인하세요");
		} else if (!pw.equals(foundMember.getPw())) {
			logger.info("비밀번호 불일치");
			resultMap.put("loginResult", -2);
			resultMap.put("msg", "비밀번호를 확인하세요");
		} else {
			resultMap.put("loginResult", 1);
			resultMap.put("member", foundMember);
			logger.info("로그인 성공");
		}
		
		return resultMap;
		
		/*
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
		*/
		
	}

	@Override
	public Member getDetail(Member member) {
		String id = member.getId();
		
		Member foundMember = memberDao.findById(id);
		logger.info("조회 된 회원 정보: {}", foundMember);
		
		return foundMember;
	}

}
