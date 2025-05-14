package br.com.caiquebrener.todolist.task.controller;

import br.com.caiquebrener.todolist.task.model.TaskModel;
import br.com.caiquebrener.todolist.task.repository.ITaskRepository;
import br.com.caiquebrener.todolist.utils.ErrorResponse;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks/")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody TaskModel task, HttpServletRequest request) {
        var idUser = getUserId(request);
        task.setIdUser(idUser);
        if (isInvalidDate(task)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Invalid date"));
        }
        if (task.getStartAt().isAfter(task.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("The initial date cannot be later tha the end date"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.save(task));
    }

    @GetMapping("get-tasks")
    public List<TaskModel> listTask(HttpServletRequest request) {
        var idUser = getUserId(request);
        return taskRepository.findByIdUser(idUser);
    }

    @PutMapping("update/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request) {
        var idUser = getUserId(request);
        taskModel.setIdUser(idUser);
        taskModel.setId(id);
        return taskRepository.save(taskModel);
    }

    private boolean isInvalidDate(TaskModel task) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(task.getStartAt()) || now.isAfter(task.getEndAt()) ||
                task.getStartAt().isAfter(task.getEndAt());
    }

    private UUID getUserId(HttpServletRequest request) {
        return (UUID) request.getAttribute("idUser");
    }

}
