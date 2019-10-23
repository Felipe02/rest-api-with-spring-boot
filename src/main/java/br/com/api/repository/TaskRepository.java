package br.com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	public Task findByTitle(String title);
}