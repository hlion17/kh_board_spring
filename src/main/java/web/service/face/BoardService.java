package web.service.face;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import web.dto.Board;

public interface BoardService {

	void list(Integer curPage, Model model);

	void getBoard(Board board, Model model);

	void write(Board board, Model model, RedirectAttributes rttr);

}
