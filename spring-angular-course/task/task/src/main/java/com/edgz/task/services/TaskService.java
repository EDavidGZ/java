package com.edgz.task.services;

import com.edgz.task.entities.Task;
import com.edgz.task.repositories.ITaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private ITaskRepo iTaskRepo;


    @Override
    public List<Task> getAllTasks() {
        return iTaskRepo.findAll();
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return iTaskRepo.findById(id);
    }

    @Override
    public Task createTask(Task task) {
        return iTaskRepo.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        if (iTaskRepo.existsById(id)){
            return iTaskRepo.save( task);
        }
        return null;
    }

    @Override
    public void deletedTask(Long id) {
        iTaskRepo.deleteById(id);
    }
}
