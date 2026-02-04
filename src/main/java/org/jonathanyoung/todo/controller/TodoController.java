package org.jonathanyoung.todo.controller;

import org.jonathanyoung.todo.model.PageResponse;
import org.jonathanyoung.todo.model.Todo;
import org.jonathanyoung.todo.service.TodoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<PageResponse> getAllTodos(@RequestParam int page, @RequestParam int limit, Authentication authentication) {
        List<Todo> todos = todoService.getAllTodos(page, limit, authentication);
        return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new PageResponse(
                        todos,
                        page,
                        limit,
                        todos.size()
                ));
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo, Authentication authentication) {
        Todo newTodo = todoService.createTodo(todo, authentication);
        return ResponseEntity
                .status(201)
                .contentType(MediaType.APPLICATION_JSON)
                .body(newTodo);
     }

    @PostMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id, @RequestBody Todo todo, Authentication authentication) {
        todo.setId(id);
        return todoService.updateTodo(todo, authentication).map(updateTodo ->
                ResponseEntity
                        .status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(updateTodo)
        ).orElseGet(() -> ResponseEntity.status(404).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id, Authentication authentication) {
        todoService.deleteTodo(id, authentication);
        return ResponseEntity
                .status(204)
                .build();
    }
}
