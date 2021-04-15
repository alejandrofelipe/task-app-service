package com.example.task.service;

import com.example.task.exception.ExistingIdObjectException;
import com.example.task.exception.MissingIdObjectException;
import com.example.task.model.Priority;
import com.example.task.repository.PriorityRepo;
import org.springframework.stereotype.Service;

@Service
public class PriorityService extends AbstractService<Priority, Long, PriorityRepo> {
    public PriorityService(PriorityRepo repository) {
        super(repository);
    }

    public Priority add(Priority priority) {
        boolean isNew = true;
        if (priority.getId() != null)
            isNew = !exists(priority.getId());
        if (!isNew)
            throw new ExistingIdObjectException("Already exist a priority with id(" + priority.getId() + ").");
        return save(priority);
    }

    public Priority update(Priority priority) {
        if (priority.getId() == null)
            throw new MissingIdObjectException("ID is required to update a priority.");
        return save(priority);
    }
}
