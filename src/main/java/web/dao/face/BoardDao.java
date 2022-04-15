package web.dao.face;

import java.util.List;

import web.common.Pagination;
import web.dto.Board;

public interface BoardDao {

	int getTotal();

	List<Board> findPageList(Pagination pn);

	Board findById(Board board);

	int insert(Board board);

	int updateHit(Board board);

	int update(Board board);

}
