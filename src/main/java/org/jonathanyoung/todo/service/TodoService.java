package org.jonathanyoung.todo.service;

import org.jonathanyoung.todo.model.Todo;
import org.jonathanyoung.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Optional<Todo> updateTodo(Todo todo) {
        Optional<Todo> existingTodo = todoRepository.findById(todo.getId());
        return existingTodo.map(updatedTodo -> {
                updatedTodo.setTitle(todo.getTitle());
                updatedTodo.setDescription(todo.getDescription());
                return todoRepository.save(updatedTodo);
        });
    }

    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }
}
