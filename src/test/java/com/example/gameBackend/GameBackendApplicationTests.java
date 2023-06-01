package com.example.gameBackend;

import com.example.gameBackend.controller.GameController;
import com.example.gameBackend.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GameBackendApplicationTests {

	@Autowired
	private GameController gameController;

	@Autowired
	private GameService gameService;

	@Test
	void contextLoads() {
		assertThat(gameController).isNotNull();
		assertThat(gameService).isNotNull();
	}

}
