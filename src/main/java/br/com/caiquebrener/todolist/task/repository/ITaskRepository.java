package br.com.caiquebrener.todolist.task.repository;

import br.com.caiquebrener.todolist.task.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
}
