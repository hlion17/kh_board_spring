package web.service.impl;

import java.util.Map;

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

}
