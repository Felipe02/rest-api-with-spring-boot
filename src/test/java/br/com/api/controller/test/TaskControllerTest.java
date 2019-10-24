package br.com.api.controller.test;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.entity.Task;
import br.com.api.repository.TaskRepository;

/**
 * Teste Alelo - Testes unitários, usando banco em memória (h2)
 * 
 * @author FE20079733
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TaskRepository taskRepository;

	/**
	 * Teste do serviço do cadastro de uma nova tarefa (POST)
	 * 
	 * @throws Exception
	 */
	
	@Test
	public void createTaskTest() throws Exception {
		Task task = new Task();
		task.setId(1);
		task.setTitle("Estudar");

		mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON).content(asJsonString(task)))
				.andExpect(status().isOk());

	}

	/**
	 * Teste do serviço de busca de tarefa por {id} (GET)
	 * 
	 * @throws Exception
	 */
	
	@Test
	public void findByTaskTest() throws Exception {
		Task task = new Task();
		task = createNewTask();

		mockMvc.perform(get("/task/{id}", task.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title", is(task.getTitle())));

	}

	/**
	 * Teste do serviço de busca de taredas (GET)
	 * 
	 * @throws Exception
	 */
	
	@Test
	public void findByTasksTest() throws Exception {
		Task task = new Task();
		task = createNewTask();

		mockMvc.perform(get("/tasks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].title", is(task.getTitle())));

	}

	/**
	 * Teste do serviço de alteração do título de uma tarefa (PUT)
	 * 
	 * @throws Exception
	 */
	
	@Test
	public void updateTaskTest() throws Exception {
		Task task = new Task();
		task = createNewTask();
		task.setTitle("Trabalhar");

		mockMvc.perform(
				put("/task/{id}", task.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(task)))
				.andExpect(status().isOk());

	}

	/**
	 * Teste do serviço de exclusão de uma tarefa (DELETE)
	 * 
	 * @throws Exception
	 */
	
	@Test
	public void zremoveTaskTest() throws Exception {
		Task task = new Task();
		task = createNewTask();

		mockMvc.perform(delete("/task/{id}", task.getId())).andExpect(status().isOk());

	}

	/**
	 * Teste “mockando” o retorno de uma API de terceiros
	 * 
	 * @throws Exception
	 */
	
	@Test
	public void consumingRestTest() throws Exception {
		mockMvc.perform(get("/api/rondom")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.type", is("success")));
	}


	public Task createNewTask() {
		Task task = new Task();
		task.setId(1);
		task.setTitle("Estudar");

		taskRepository.save(task);

		return task;
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
