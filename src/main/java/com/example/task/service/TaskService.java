package com.example.task.service;

import com.example.task.exception.ExistingIdObjectException;
import com.example.task.exception.MissingIdObjectException;
import com.example.task.model.Task;
import com.example.task.repository.TaskRepo;
import com.example.task.repository.custom.TaskCustomRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskService extends AbstractService<Task, Long, TaskRepo> {
    private final TaskCustomRepository customRepository;

    public TaskService(TaskRepo repository, TaskCustomRepository customRepository) {
        super(repository);
        this.customRepository = customRepository;
    }

    public Task add(Task task) {
        boolean isNew = true;
        if (task.getId() != null)
            isNew = !exists(task.getId());
        if (!isNew)
            throw new ExistingIdObjectException("Already exist a task with id(" + task.getId() + ").");
        return save(task);
    }

    public Task update(Task task) {
        if (task.getId() == null)
            throw new MissingIdObjectException("ID is required to update a task.");
        task.setUpdated(new Date());
        return save(task);
    }

    public List<Task> search(Task taskSearch) {
        return customRepository.search(taskSearch);
    }
}
