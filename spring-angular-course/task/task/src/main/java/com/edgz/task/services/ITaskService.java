package com.edgz.task.services;

import com.edgz.task.entities.Task;

import java.util.*;

public interface ITaskService {

    List<Task> getAllTasks();

    Optional<Task> getTaskById(Long id);

    Task createTask(Task task);

    Task updateTask(Long id, Task task);

    void deletedTask(Long id);

}
