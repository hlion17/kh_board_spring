package web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.dao.face.CommentDao;
import web.dto.Comment;
import web.service.face.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public void commentInsert(Comment comment) {
		int result = commentDao.insert(comment);
		
		if (result == 1) {
			logger.info("댓글 등록 성공");
		} else {
			logger.warn("댓글 등록 실패");
		}
	}

	@Override
	public void deleteComment(Comment comment) {
		int result = commentDao.delete(comment);
		
		if (result == 1) {
			logger.info("댓글 삭제 성공");
		} else {
			logger.warn("댓글 삭제 실패");
		}
	}

}
