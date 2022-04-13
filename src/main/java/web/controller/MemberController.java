package web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import web.dto.Member;
import web.service.face.MemberService;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/member/join", method = RequestMethod.GET)
	public String join() {
		logger.info("[/member/join][GET]");
		return "member/join";
	}
	
	@RequestMapping(value = "/member/join", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> joinProcess(Member member) {
		logger.info("[/member/join][POST]");
		logger.info("요청 파라미터 - Member: {}", member);
		Map<String, String> resJson = new HashMap<>();
		
		memberService.join(member, resJson);
		
		return resJson;
	}
	
	@RequestMapping(value = "/member/login", method = RequestMethod.GET)
	public String login() {
		logger.info("[/member/login][GET]");
		return "member/login";
	}
	
	@RequestMapping(value = "/member/login", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> loginProcess(Member member, HttpSession session) {
		logger.info("[/member/login][POST]");
		logger.info("요청 파라미터 - member: {}", member);
		Map<String, String> json = new HashMap<>();
		
		memberService.login(member, json, session);
		logger.info("세션: {}, {}", session.getAttribute("isLogin"), session.getAttribute("loginId"));
		
		return json;
	}
	
	@RequestMapping(value = "/member/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		logger.info("[/member/logout][GET]");
		session.invalidate();
		return "redirect:/";
	}

}
