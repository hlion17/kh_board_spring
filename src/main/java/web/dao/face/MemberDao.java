package web.dao.face;

import web.dto.Member;

public interface MemberDao {

	int insert(Member member);

	Member findById(String id);

}
