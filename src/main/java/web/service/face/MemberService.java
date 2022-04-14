package web.service.face;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import web.dto.Member;

public interface MemberService {

	void join(Member member, Map<String, String> resJson);

	void login(Member member, Model model, HttpSession session);

	void getDetail(Member member, Model model);

}
