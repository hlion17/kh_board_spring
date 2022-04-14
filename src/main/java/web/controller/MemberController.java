package web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public void loginProcess(Member member, HttpSession session, Model model) {
		logger.info("[/member/login][POST]");
		logger.info("요청 파라미터 - member: {}", member);
		
		memberService.login(member, model, session);
	}
	
	@RequestMapping(value = "/member/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		logger.info("[/member/logout][GET]");
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/member/detail", method = RequestMethod.GET)
	public String detail(Member member, Model model) {
		logger.info("[/member/detail][GET]");
		logger.info("요청 파라미터 - member: {}", member);
		
		memberService.getDetail(member, model);
		
		return "member/detail";
	}

}
