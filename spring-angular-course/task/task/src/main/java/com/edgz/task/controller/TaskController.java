package com.edgz.task.controller;

import com.edgz.task.entities.Task;
import com.edgz.task.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/task")
@CrossOrigin("*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(value -> ResponseEntity.ok().body(value))
                .orElseThrow(null);
    }

    @PostMapping
    public ResponseEntity<Task> saveTask(@RequestBody Task task){
        Task taskSave = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task){
        Task updateTask = taskService.updateTask(id, task);
        if(updateTask != null){
            return ResponseEntity.ok().body(updateTask);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deletedTask(id);
        return ResponseEntity.noContent().build();
    }
}
