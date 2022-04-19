package web.dao.face;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import web.common.Pagination;
import web.dto.Board;
import web.dto.BoardFile;

public interface BoardDao {

	//int getTotal();

	int getTotal(@Param("category") String category, @Param("keyword") String keyword);

	List<Board> findPageList(Map<String, Object> paramMap);

	Board findById(Board board);

	int insert(Board board);

	int updateHit(Board board);

	int update(Board board);

	int delete(Board board);

	int isRecommended(Map<String, Object> paramMap);

	int insertRecommend(Map<String, Object> paramMap);

	int deleteRecommend(Map<String, Object> paramMap);

	public void insertFile(BoardFile boardFile);

	public BoardFile selectBoardFileByBoardNo(Board board);

	public BoardFile selectBoardFileByFileNo(BoardFile boardFile);

}
