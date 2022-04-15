package web.service.face;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import web.dto.Board;

public interface BoardService {

	void list(Integer curPage, Model model);

	void getBoard(Board board, Model model);

	void write(Board board, Model model, RedirectAttributes rttr);

	void countingHit(Board board, HttpServletRequest requset, HttpServletResponse response);

	String fileUpload(MultipartFile file);

	void update(Board board, Model model, RedirectAttributes rttr);

	void delete(Board board, RedirectAttributes rttr);

}
