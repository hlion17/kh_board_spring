package web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import web.dto.Board;
import web.service.face.BoardService;



@Controller
public class BoardController {
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "/board/test", method = RequestMethod.GET)
	public String test() {
		
		return "board/test";
	}
	
	@RequestMapping(value = "/board/list", method = RequestMethod.GET)
	public String list(Model model, Integer curPage) {
		log.info("[/board/list][GET]");
		log.info("요청 파라미터 - curPage: {}", curPage);
		
		boardService.list(curPage, model);
		
		return "board/list";
	}
	
	@RequestMapping(value = "/board/view", method = RequestMethod.GET)
	public String view(Board board, Model model) {
		log.info("[/board/view][GET]");
		log.info("요청 파라미터 - board: ", board);
		
		boardService.getBoard(board, model);
		
		return "board/view";
	}

}
