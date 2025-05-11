package br.com.caiquebrener.todolist.task.controller;

import br.com.caiquebrener.todolist.task.model.TaskModel;
import br.com.caiquebrener.todolist.task.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping
    public TaskModel createTask(@RequestBody TaskModel task) {
        return taskRepository.save(task);
    }
}
