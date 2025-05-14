package br.com.caiquebrener.todolist.controller.task;

import br.com.caiquebrener.todolist.model.task.TaskModel;
import br.com.caiquebrener.todolist.service.task.ITaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks/")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody TaskModel task, HttpServletRequest request) {
        UUID userId = getUserId(request);
        TaskModel createdTask = taskService.createTask(task, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping
    public List<TaskModel> listTask(HttpServletRequest request) {
        UUID userId = getUserId(request);
        return taskService.getTasksByUser(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request) {
        UUID userId = getUserId(request);
        TaskModel updatedTask = taskService.updateTask(id, taskModel, userId);
        return ResponseEntity.ok(updatedTask);
    }

    private UUID getUserId(HttpServletRequest request) {
        return (UUID) request.getAttribute("idUser");
    }
}
