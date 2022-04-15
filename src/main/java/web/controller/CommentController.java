package web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import web.dto.Comment;
import web.service.face.CommentService;

@Controller
public class CommentController {
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value = "/comment/insert", method = RequestMethod.POST)
	public String insert(Comment comment) {
		logger.info("[/comment/insert]POST]");
		logger.info("요청 파라미터 - comment: {}", comment);
		
		commentService.commentInsert(comment);
		
		return "redirect:/board/view?boardNo=" + comment.getBoardNo();
	}

	@RequestMapping(value = "/comment/delete", method = RequestMethod.POST)
	public String delete(Comment comment, RedirectAttributes rttr) {
		logger.info("[/comment/delete][POST]");
		logger.info("요청 파라미터: {}", comment);
		
		commentService.deleteComment(comment);
		
		return "redirect:/board/view?boardNo=" + comment.getBoardNo();
	}
}
