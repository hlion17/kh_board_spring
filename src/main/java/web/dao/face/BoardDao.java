package web.dao.face;

import java.util.List;
import java.util.Map;

import web.common.Pagination;
import web.dto.Board;

public interface BoardDao {

	int getTotal();

	List<Board> findPageList(Pagination pn);

	Board findById(Board board);

	int insert(Board board);

	int updateHit(Board board);

	int update(Board board);

	int delete(Board board);

	int isRecommended(Map<String, Object> paramMap);

	int insertRecommend(Map<String, Object> paramMap);

	int deleteRecommend(Map<String, Object> paramMap);

}
