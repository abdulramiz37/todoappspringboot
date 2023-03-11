package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.service.ITodoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* Create Todo, Delete Todo, Update Todo, Fetch Todo (CRUD Operation)
 */
@RestController
@RequestMapping("/api/v1/todo-app")
public class TodoController {
    @Autowired
    private ITodoService todoService; // used to inject dependencies

    // http://localhost:8080/api/v1/todo-app/add-todo
    @PostMapping("/add-todo")
    public ResponseEntity<String> addTodo(@RequestBody String todoData) {
        JSONObject object = new JSONObject(todoData);
        JSONObject error = validateTodo(object);
        if(error.isEmpty()) {
            Todo todo = setTodo(object);
            int id = todoService.addTodo(todo);
            return new ResponseEntity<>("todo saved with id " + id, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(error.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    private Todo setTodo(JSONObject object) {
        Todo todo = new Todo();
        todo.setTitle(object.getString("title"));
        todo.setStatus(object.getBoolean("status"));

        return todo;
    }

    private JSONObject validateTodo(JSONObject object) {
        JSONObject error = new JSONObject();
        if(!object.has("title")) {
            error.put("title", "missing parameter");
        }
        if (!object.has("status")) {
            error.put("status", "missing parameter");
        }
        return error;
    }

    //  http://localhost:8080/api/v1/todo-app/find-todo/id/5
    @GetMapping("/find-todo/id/{id}")
    public Todo findTodoById(@PathVariable int id) {
        return todoService.findById(id);
    }

//  http://localhost:8080/api/v1/todo-app/find-all
    @GetMapping("find-all")
    public List<Todo> findAllTodos() {

        return todoService.findAll();
    }

    // http://localhost:8080/api/v1/todo-app/update-todo/id/3
    @PutMapping("/update-todo/id/{id}")
    public void updateTodo(@PathVariable int id, @RequestBody Todo todo) {
        todoService.updateTodo(id, todo);
    }

    // http://localhost:8080/api/v1/todo-app/delete-todo/id/3
    @DeleteMapping("/delete-todo/id/{id}")
    public void deleteTodo(@PathVariable int id) {
        todoService.deleteTodo(id);
    }
}
