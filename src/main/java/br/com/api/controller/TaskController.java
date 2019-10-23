package br.com.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.api.entity.Task;
import br.com.api.repository.TaskRepository;
import br.com.consumingapirest.ConsumingApiRest;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * Teste Alelo - Criação da API REST
 * @author Felipe Pereira
 *
 */
@RestController
public class TaskController {
	@Autowired
	private TaskRepository taskRepository;

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna a lista de tarefas"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"), })

	@RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = "application/json")
	public List<Task> findByTasks() {
		return taskRepository.findAll();
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma tarefa especifica"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"), })

	@RequestMapping(value = "/task/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Task> findByTask(@PathVariable(value = "id") long id) {
		Optional<Task> taks = taskRepository.findById(id);
		if (taks.isPresent())
			return new ResponseEntity<Task>(taks.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Tarefa cadastrada com sucesso"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"), })

	@RequestMapping(value = "/task", method = RequestMethod.POST, produces = "application/json")
	public Task createNewTask(@Valid @RequestBody Task task) {
		return taskRepository.save(task);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Tarefa atualizada com sucesso"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"), })

	@RequestMapping(value = "/task/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Task> updateTask(@PathVariable(value = "id") long id, @Valid @RequestBody Task newTask) {
		Optional<Task> oldTask = taskRepository.findById(id);

		if (oldTask.isPresent()) {
			Task task = oldTask.get();
			task.setTitle(newTask.getTitle());
			taskRepository.save(task);
			return new ResponseEntity<Task>(task, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Tarefa excluída com sucesso"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"), })

	@RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteTask(@PathVariable(value = "id") long id) {
		Optional<Task> task = taskRepository.findById(id);

		if (task.isPresent()) {
			taskRepository.delete(task.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/api/rondom", method = RequestMethod.GET, produces = "application/json")
	public CommandLineRunner consumingApiRest() {
		ConsumingApiRest consumigApiRest = new ConsumingApiRest();
		RestTemplate restTemplate = null;
		
		try {
			return consumigApiRest.run(restTemplate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

}