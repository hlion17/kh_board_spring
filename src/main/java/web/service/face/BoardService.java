package web.service.face;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import web.dto.Board;
import web.dto.BoardFile;

public interface BoardService {

	//void list(Integer curPage, Model model);
	void list(Integer curPage, String category, String keyword, Model model);
	
	Map<String, Object> getBoard(Board board);

	void write(Board board, Model model, RedirectAttributes rttr, MultipartFile file);

	void countingHit(Board board, HttpServletRequest requset, HttpServletResponse response);

	String fileUpload(MultipartFile file);

	void update(Board board, Model model, RedirectAttributes rttr);

	void delete(Board board, RedirectAttributes rttr);

	void isRecommendedBoard(Board board, HttpServletRequest request, Model model);

	void recommendBoard(Board board, String loginId, Map<String, String> json);

	public BoardFile getAttachFile(Board board);

	public BoardFile getFile(BoardFile boardFile);

}
