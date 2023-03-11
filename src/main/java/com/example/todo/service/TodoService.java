package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TodoService implements ITodoService {
    private static List<Todo> todos = new ArrayList<>();

    @Autowired
    TodoRepository todoRepository;

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }
    @Override
    public Todo findById(int id) {
        return todoRepository.findById(id).get();
    }
    @Override
    public int addTodo(Todo todo) {
        Todo todo1 = todoRepository.save(todo);
        return todo1.getId();
    }
    @Override
    public void deleteTodo(int id) {
        todoRepository.deleteById(id);
    }
    @Override
    public void updateTodo(int id, Todo newTodo) {
        // 1- Find Todo to be updated
        // 2- update Todo

        Todo todo = todoRepository.findById(id).get();

        todo.setId(newTodo.getId());
        todo.setTitle(newTodo.getTitle());
        todo.setStatus(newTodo.isStatus());

        todoRepository.save(todo);
    }
}
