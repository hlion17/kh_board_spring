package web.service.face;

import web.dto.Comment;

public interface CommentService {

	void commentInsert(Comment comment);

	void deleteComment(Comment comment);

}
