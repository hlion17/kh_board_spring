package web.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import web.common.Pagination;
import web.dao.face.BoardDao;
import web.dto.Board;
import web.service.face.BoardService;

@Service
public class BoardServiceImpl implements BoardService {
	private static final Logger log = LoggerFactory.getLogger(BoardService.class);
	
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

}
