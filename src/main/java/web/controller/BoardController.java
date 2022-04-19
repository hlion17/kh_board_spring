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
import web.dto.BoardFile;
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
	public String list(Model model
			, Integer curPage
			, String category
			, String keyword) {
		log.info("[/board/list][GET]");
		log.info("요청 파라미터 - curPage: {}", curPage);
		log.info("요청 파라미터 - category: {}", category);
		log.info("요청 파라미터 - keyword: {}", keyword);
		// 파라미터가 많으면 map 형태로 받는것도 생각해보기
		
		//boardService.list(curPage, model);
		boardService.list(curPage, category, keyword, model);
		
		return "board/list";
	}
	
	@RequestMapping(value = "/board/view", method = RequestMethod.GET)
	public String view(Board board
			, Model model
			, HttpServletRequest request
			, HttpServletResponse response) {
		log.info("[/board/view][GET]");
		log.info("요청 파라미터 - board: ", board);
		
		// 요청 파라미터 유효성 검증
		if (board.getBoardNo() < 1) {
			return "redirect:/board/list";
		}
		
		Map<String, Object> boardResult = boardService.getBoard(board);
		boardService.countingHit(board, request, response);
		boardService.isRecommendedBoard(board, request, model);
		
		// 첨부파일 정보 모델값 전달
		BoardFile boardFile = boardService.getAttachFile(board);
		log.info("게시판 첨부파일: {}", boardFile);
		model.addAttribute("boardFile", boardFile);
		
		// 모델값 전달
		boardResult.forEach( (key, value) -> model.addAttribute(key, value) );
		
		return "board/view";
	}
	
	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
	public String write( ) {
		log.info("[/board/write][GET]");
		return "board/write";
	}
	
	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String writeProcess(
			Board board
			, Model model
			, RedirectAttributes rttr
			, MultipartFile file) {
		log.info("[/board/wirte][POST]");
		log.info("요청 파라미터 - board: {}", board);
		log.info("요청 파라미터 - file: {}", file);
		
		boardService.write(board, model, rttr, file);
		
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
		
		Map<String, Object> boardResult = boardService.getBoard(board);
		boardResult.forEach( (key, value) -> model.addAttribute(key, value) );
		
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
	
	@RequestMapping(value = "/board/recommend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> recommned(Board board, @RequestParam String loginId) {
		log.info("[/board/recommend][POST]");
		log.info("요청 파라미터 - board: {}", board);
		log.info("요청 파라미터 - loginId: {}", loginId);
		
		Map<String, String> json = new HashMap<>();
		
		boardService.recommendBoard(board, loginId, json);
		
		return json;
	}
	
	@RequestMapping("/board/download")
	public String download(BoardFile boardFile, Model model) {
		
		boardFile = boardService.getFile(boardFile);
		model.addAttribute("downFile", boardFile);
		
		return "down";
	}
}
