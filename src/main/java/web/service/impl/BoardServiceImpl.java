package web.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import web.common.Pagination;
import web.dao.face.BoardDao;
import web.dto.Board;
import web.service.face.BoardService;

@Service
public class BoardServiceImpl implements BoardService {
	private static final Logger log = LoggerFactory.getLogger(BoardService.class);
	
	@Autowired
	ServletContext context;
	
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public void list(Integer curPage, Model model) {
		
		if (curPage == null || curPage < 0) {
			curPage = 1;
		}
		
		// 게시글 페이지 조회
		int total = boardDao.getTotal();
		log.info("전체 게시글 수 조회 결과: {}", total);
		Pagination pn = new Pagination(curPage, 10, total);
		log.info("startIdx: {}, endIdx: {}", pn.getStartIndex(), pn.getEndIndex());
		
		List<Board> list = boardDao.findPageList(pn);
		log.info("게시글 페이지 조회 결과: {}", list);
		log.info("페이지네이션: {}", pn);
		
		// View 전달 데이터 저장
		model.addAttribute("list", list);
		model.addAttribute("pn", pn);
		
	}

	@Override
	public void getBoard(Board board, Model model) {
		Board foundBoard = boardDao.findById(board);
		log.info("조회된 게시글: ", foundBoard);
		
		model.addAttribute("board", foundBoard);
		
	}

	@Override
	public void write(Board board, Model model, RedirectAttributes rttr) {
		int result = boardDao.insert(board);
		
		if (result == 1) {
			rttr.addFlashAttribute("msg", "게시글이 성공적으로 등록되었습니다.");
			log.info("게시글 등록 성공");
		} else {
			rttr.addFlashAttribute("msg", "게시글 등록에 실패했습니다.");
			log.warn("게시글 등록 실패");
		}
	}

	
	@Override
	public void countingHit(Board board
			, HttpServletRequest requset
			, HttpServletResponse response) {
		
		int result = -1;
		
		Cookie oldCookie = null;
		Cookie[] cookies = requset.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("postView")) {
					oldCookie = cookie;
				}
			}
		}
		
		// 이 정도면 로거레벨 debug가 어울리지 않을까?
		if (oldCookie != null) {
			// 본적 없는 게시물인 경우 조회수 증가
			if (!oldCookie.getValue().contains("[" + board.getBoardNo() + "]")) {
				result = boardDao.updateHit(board);
				oldCookie.setValue(oldCookie.getValue() + "_[" + board.getBoardNo() + "]");
				oldCookie.setPath("/");
				oldCookie.setMaxAge(60 * 60 * 24);
				response.addCookie(oldCookie);
				log.info("처음 본 게시물 조회수 증가");
			}
			log.info("이미 본 게시물 조회수 증가시키지 않음");
		} else {
			// 게시글 조회 쿠키가 없는 경우 조회수 증가
			// 조회 쿠키 생성
			result = boardDao.updateHit(board);
			Cookie newCookie = new Cookie("postView", "[" + board.getBoardNo() + "]");
			newCookie.setPath("/");
			newCookie.setMaxAge(60 * 60 * 24);
			response.addCookie(newCookie);
			log.info("조회 쿠기 없음, 조회수 증가시키고 조회쿠키 생성");
		}
		
		if (result == 1) {
			log.info("조회수 증가");
		} else {
			log.warn("조회수 증가 실패");
		}
	}

	
	@Override
	public String fileUpload(MultipartFile file) {
		// 파일 저장 경로 생성
		String storedPath = context.getRealPath("img");
		File storedFolder = new File(storedPath);
		if (storedFolder.exists()) {
			storedFolder.mkdir();			
		}
		
		// 저장 파일 이름 생성
		String fileName = UUID.randomUUID().toString().split("-")[4] + file.getOriginalFilename();
		
		// 업로드 파일 객체 생성
		File dest = new File(storedFolder, fileName);
		
		// 파일 업로드
		try {
			file.transferTo(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return fileName;
	}

}
