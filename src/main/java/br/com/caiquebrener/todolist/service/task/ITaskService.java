package br.com.caiquebrener.todolist.service.task;

import br.com.caiquebrener.todolist.model.task.TaskModel;

import java.util.List;
import java.util.UUID;

public interface ITaskService {
    TaskModel createTask(TaskModel task, UUID userId);
    List<TaskModel> getTasksByUser(UUID userId);
    TaskModel updateTask(UUID id, TaskModel task, UUID userId);
}