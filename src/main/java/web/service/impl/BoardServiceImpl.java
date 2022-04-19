package web.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import web.common.Pagination;
import web.dao.face.BoardDao;
import web.dao.face.CommentDao;
import web.dto.Board;
import web.dto.BoardFile;
import web.dto.Comment;
import web.service.face.BoardService;

@Service
public class BoardServiceImpl implements BoardService {
	private static final Logger log = LoggerFactory.getLogger(BoardService.class);
	
	@Autowired
	ServletContext context;
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private CommentDao commentDao;
	
	@Override
	public void list(Integer curPage, String category, String keyword, Model model) {
		
		if (curPage == null || curPage <= 0) {
			curPage = 1;
		}
		
		// 게시글 페이지 조회
		//int total = boardDao.getTotal();
		int total = boardDao.getTotal(category, keyword);
		log.info("전체 게시글 수 조회 결과: {}", total);
		Pagination pn = new Pagination(curPage, 10, total);
		log.info("startIdx: {}, endIdx: {}", pn.getStartIndex(), pn.getEndIndex());
		
		// findPageList 용 파라미터 맵 생성
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("pn", pn);
		paramMap.put("category", category);
		paramMap.put("keyword", keyword);
		
		List<Board> list = boardDao.findPageList(paramMap);
		log.info("게시글 페이지 조회 결과: {}", list.size());
		log.info("페이지네이션: {}", pn);
		
		// View 전달 데이터 저장
		model.addAttribute("list", list);
		model.addAttribute("pn", pn);
		model.addAttribute("keyword", keyword);
		model.addAttribute("category", category);
		
	}

	@Override
	public Map<String, Object> getBoard(Board board) {
		Map<String, Object> resultMap = new HashMap<>();
		
		// 게시글
		Board foundBoard = boardDao.findById(board);
		log.info("조회된 게시글: ", foundBoard.getBoardNo());
		resultMap.put("board", foundBoard);
		
		// 댓글
		List<Comment> commentList = commentDao.findAllByBoardNo(board);
		log.info("조회된 댓글: {}", commentList.size());
		resultMap.put("cList", commentList);
		
		return resultMap;
	}

	@Transactional
	@Override
	public void write(Board board, Model model, RedirectAttributes rttr, MultipartFile file) {
		
		if ("".equals(board.getTitle())) {
			board.setTitle("제목없음");
		}
		
		int result = boardDao.insert(board);
		
		// -------------------------------------------
		// 파일 업로드
		if (file.getSize() <= 0) {
			return;
		}
		
		// 파일이 저장될 경로
		String storedPath = context.getRealPath("upload");
		File storedFolder = new File(storedPath);
		if (!storedFolder.exists()) {
			storedFolder.mkdir();
		}
		
		// 파일이 저장될 이름
		String originName = file.getOriginalFilename();
		String storedName = UUID.randomUUID().toString().split("-")[4] + file.getOriginalFilename();
		
		// 저장될 파일 정보 객체
		File dest = new File(storedFolder, storedName);
		
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// ------------------------------------------
		
		BoardFile boardFile = new BoardFile();
		boardFile.setBoardNo(board.getBoardNo());
		boardFile.setOriginName(originName);
		boardFile.setStoredName(storedName);
		
		boardDao.insertFile(boardFile);
		
		
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
		if (!storedFolder.exists()) {
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
	
	@Override
	public void update(Board board, Model model, RedirectAttributes rttr) {
		int result = boardDao.update(board);
		
		if (result == 1) {
			log.info("게시글 업데이트 성공");
			rttr.addFlashAttribute("msg", "수정 성공");
		} else {
			log.info("게시글 업데이트 실패");
			rttr.addFlashAttribute("msg", "수정 실패");
		}
	}

	@Override
	public void delete(Board board, RedirectAttributes rttr) {
		int result = boardDao.delete(board);
		
		if (result == 1) {
			log.info("글 삭제 성공");
			rttr.addFlashAttribute("msg", "게시글이 삭제되었습니다.");
		} else {
			log.info("글 삭제 실패");
			rttr.addFlashAttribute("msg", "게시글 삭제 실패");
		}
	}

	@Override
	public void isRecommendedBoard(Board board, HttpServletRequest request, Model model) {
		String loginId = (String) request.getSession().getAttribute("loginId");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", loginId);
		paramMap.put("boardNo", board.getBoardNo());
		
		if (loginId != null || "".equals(loginId)) {
			int result = boardDao.isRecommended(paramMap);
			log.warn("추천 조회 결과: {}", result);
			if (result == 1) {
				model.addAttribute("isRecommend", true);
			}
		}
	}

	@Override
	public void recommendBoard(Board board, String loginId, Map<String, String> json) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", loginId);
		paramMap.put("boardNo", board.getBoardNo());
		
		int checkRecommend = boardDao.isRecommended(paramMap);
		
		if (checkRecommend == 1) {
			if (boardDao.deleteRecommend(paramMap) == 1) {
				log.info("추천 취소");
				json.put("msg", "추천이 취소되었습니다.");
				json.put("isRecommend", "false");
			} else {
				log.warn("추천 취소 실패");
				
			}
		} else if (checkRecommend == 0) {
			if (boardDao.insertRecommend(paramMap) == 1) {
				log.info("추천 완료");
				json.put("msg", "해당글을 추천하셨습니다.");				
				json.put("isRecommend", "true");
			} else {
				log.warn("추천 실패");
			}
		}
		
		json.put("recommendCnt", Integer.toString(boardDao.findById(board).getRecommend()));
		
	}

	@Override
	public BoardFile getAttachFile(Board board) {
		return boardDao.selectBoardFileByBoardNo(board);
	}

	@Override
	public BoardFile getFile(BoardFile boardFile) {
		return boardDao.selectBoardFileByFileNo(boardFile);
	}

		
	
	

	
	
	
}
