package web.dao.face;

import java.util.List;

import web.dto.Board;
import web.dto.Comment;

public interface CommentDao {

	int insert(Comment comment);

	List<Comment> findAllByBoardNo(Board board);

	int delete(Comment comment);

}
