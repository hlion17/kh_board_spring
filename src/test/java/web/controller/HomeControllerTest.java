package web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import web.service.face.BoardService;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {
	@Mock
	private BoardService boardService;
	
	@InjectMocks
	private HomeController homeController;
	
	private MockMvc MockMvc;
	
	@BeforeEach
	void setup() throws Exception {
		this.MockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
	}

	@Test
	@DisplayName("홈 요청시 200 코드 반환")
	void test() throws Exception {
		this.MockMvc
			.perform(get("/"))
			.andExpect(status().isOk());
	}

}
