package web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String joinProcess(Member member, RedirectAttributes rttr, Model model) {
		logger.info("[/member/join][POST]");
		logger.info("요청 파라미터 - Member: {}", member);
		
		String viewName = "";
		
		if (memberService.join(member) == 1) {
			viewName = "redirect:/member/login";
			rttr.addFlashAttribute("msg", "회원가입을 축하합니다.");
		} else {
			viewName = "redirect:/member/join";
			rttr.addFlashAttribute("msg", "회원가입에 실패했습니다.");
		}
		
		return viewName;
	}
	
	@RequestMapping(value = "/member/login", method = RequestMethod.GET)
	public String login() {
		logger.info("[/member/login][GET]");
		return "member/login";
	}
	
	@RequestMapping(value = "/member/login", method = RequestMethod.POST)
	public String loginProcess(Member member, HttpSession session, Model model) {
		logger.info("[/member/login][POST]");
		logger.info("요청 파라미터 - member: {}", member);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("member", member);
		
		// 로그인 인증 요청 검증
		resultMap = memberService.login(resultMap);
		resultMap.forEach((key, value) -> model.addAttribute(key, value));
		
		
		if ((Integer) model.getAttribute("loginResult") == -1 || 
				(Integer) model.getAttribute("loginResult") == -2) {
			logger.info("로그인 실패 - 원인: {}", (String) model.getAttribute("msg"));
			return "member/login";
		} else {
			logger.info("로그인 성공");
			session.setAttribute("isLogin", true);
			session.setAttribute("loginId", ((Member) model.getAttribute("member")).getId());
			session.setAttribute("loginNick", ((Member) model.getAttribute("member")).getNick());
			// redirect 는 인터셉터에서 처리
			return null;
		}
	}
	
	@RequestMapping(value = "/member/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		logger.info("[/member/logout][GET]");
		session.invalidate();
		logger.info("로그아웃");
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
