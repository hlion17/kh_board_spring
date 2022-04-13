package web.service.face;

import java.util.Map;

import web.dto.Member;

public interface MemberService {

	void join(Member member, Map<String, String> resJson);

}
