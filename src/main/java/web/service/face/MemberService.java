package web.service.face;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import web.dto.Member;

public interface MemberService {

	public int join(Member member);

	public Map<String, Object> login(Map<String, Object> resultMap);

	public Member getDetail(Member member);

}
