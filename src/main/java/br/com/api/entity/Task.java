package br.com.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Task {

	@ApiModelProperty(value = "Código da tarefa")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ApiModelProperty(value = "Nome da tarefa")
	@Column(nullable = false)
	@Size(min = 3, max = 20)
	private String title;

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Task(String title) {
		this.title = title;
	}

	public Task() {
	}
}
