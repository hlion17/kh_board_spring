package web.service.face;

import java.util.Map;

import javax.servlet.http.HttpSession;

import web.dto.Member;

public interface MemberService {

	void join(Member member, Map<String, String> resJson);

	void login(Member member, Map<String, String> json, HttpSession session);

}
