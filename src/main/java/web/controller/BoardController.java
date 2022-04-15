package web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
	public String view(Board board
			, Model model
			, HttpServletRequest request
			, HttpServletResponse response) {
		log.info("[/board/view][GET]");
		log.info("요청 파라미터 - board: ", board);
		
		boardService.getBoard(board, model);
		boardService.countingHit(board, request, response);
		
		return "board/view";
	}
	
	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
	public String write( ) {
		log.info("[/board/write][GET]");
		return "board/write";
	}
	
	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String writeProcess(Board board, Model model, RedirectAttributes rttr) {
		log.info("[/board/wirte][POST]");
		log.info("요청 파라미터 - title, content, writerId: {}", board);
		
		boardService.write(board, model, rttr);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/ck/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ckUpload(
			HttpServletRequest req
			, HttpServletResponse resp
			, @RequestParam("upload") MultipartFile file) {
		
		//resp.setCharacterEncoding("UTF-8");
		//resp.setContentType("text/html; charset=UTF-8");
		
		String fileName = boardService.fileUpload(file);
		String fileUrl = "/ckImg/" + fileName;
		
		Map<String, Object> json = new HashMap<>();
		json.put("uploaded", 1);
		json.put("fileName", fileName);
		json.put("url", fileUrl);
		
		log.info("json: {}", json);
		
		return json;
	}

	@RequestMapping(value = "/board/update", method = RequestMethod.GET)
	public String update(Board board, Model model, HttpSession session, RedirectAttributes rttr) {
		log.info("[/board/update][GET]");
		log.info("요청 파라미터 - board: {}", board);
		
		if (!board.getWriterId().equals((String)session.getAttribute("loginId"))) {
			rttr.addFlashAttribute("msg", "글 작성자만 수정할 수 있습니다.");
			return "redirect:/board/view?boardNo=" + board.getBoardNo();
		}
		
		boardService.getBoard(board, model);
		return "board/update";
	}
	
	@RequestMapping(value = "/board/update", method = RequestMethod.POST)
	public String updateProcess(Board board, Model model, RedirectAttributes rttr) {
		log.info("[/board/update][POST]");
		log.info("요청 파라미터 - board: {}", board);
		
		boardService.update(board, model, rttr);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/board/delete", method = RequestMethod.POST)
	public String delete(Board board, HttpSession session, RedirectAttributes rttr) {
		log.info("[/board/delete][POST]");
		log.info("요청 파라미터 - board: {}", board);
		
		if (!board.getWriterId().equals((String)session.getAttribute("loginId"))) {
			rttr.addFlashAttribute("msg", "글 작성자만 삭제 할 수 있습니다.");
			return "redirect:/board/view?boardNo=" + board.getBoardNo();
		}
		
		boardService.delete(board, rttr);
		
		return "redirect:/board/list";
	}
}
