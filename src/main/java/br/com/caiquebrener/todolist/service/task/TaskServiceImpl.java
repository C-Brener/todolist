
package br.com.caiquebrener.todolist.service.task;

import br.com.caiquebrener.todolist.model.task.TaskModel;
import br.com.caiquebrener.todolist.repository.task.ITaskRepository;
import br.com.caiquebrener.todolist.repository.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private ITaskRepository taskRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public TaskModel createTask(TaskModel task, UUID userId) {
        task.setIdUser(userId);
        if (isInvalidDate(task)) {
            throw new IllegalArgumentException("Invalid date");
        }
        return taskRepository.save(task);
    }

    @Override
    public List<TaskModel> getTasksByUser(UUID userId) {
        return taskRepository.findByIdUser(userId);
    }

    @Override
    public TaskModel updateTask(UUID id, TaskModel task, UUID userId) {
        TaskModel existing = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!existing.getIdUser().equals(userId)) {
            throw new SecurityException("User not authorized");
        }
        return taskRepository.save(existing);
    }

    private boolean isInvalidDate(TaskModel task) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(task.getStartAt()) || now.isAfter(task.getEndAt()) ||
                task.getStartAt().isAfter(task.getEndAt());
    }
}