package web.service.face;

import org.springframework.ui.Model;

import web.dto.Board;

public interface BoardService {

	void list(Integer curPage, Model model);

	void getBoard(Board board, Model model);

}
