package org.jonathanyoung.todo.service;

import lombok.extern.slf4j.Slf4j;
import org.jonathanyoung.todo.model.Todo;
import org.jonathanyoung.todo.model.UserInfo;
import org.jonathanyoung.todo.repository.TodoRepository;
import org.jonathanyoung.todo.repository.UserInfoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {

    private final UserInfoRepository userInfoRepository;
    private final TodoRepository todoRepository;

    public TodoService(UserInfoRepository userInfoRepository, TodoRepository todoRepository) {
        this.userInfoRepository = userInfoRepository;
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos(Authentication authentication) {
        String email = getEmail(authentication);
        Optional<UserInfo> userInfo = userInfoRepository.findUserInfoByEmail(email);
        return userInfo.map(todoRepository::findAllByUser).orElseGet(List::of);
    }

    public Todo createTodo(Todo todo, Authentication authentication) {
        String email = getEmail(authentication);
        Optional<UserInfo> userInfo = userInfoRepository.findUserInfoByEmail(email);
        return userInfo.map(existingUser -> {
            todo.setUser(existingUser);
            return todoRepository.save(todo);
        }).orElseThrow(() -> new RuntimeException("User not found.")); // should never reach
    }

    public Optional<Todo> updateTodo(Todo todo, Authentication authentication) {
        String email = getEmail(authentication);
        Optional<Todo> existingTodo = todoRepository.findById(todo.getId());

        if (existingTodo.isPresent() && !existingTodo.get().getUser().getEmail().equals(email)) {
            log.info("User tried updating todo that isn't theirs.");
            return Optional.empty();
        }

        return existingTodo.map(updatedTodo -> {
                updatedTodo.setTitle(todo.getTitle());
                updatedTodo.setDescription(todo.getDescription());
                return todoRepository.save(updatedTodo);
        });
    }

    public void deleteTodo(String id, Authentication authentication) {
        String email = getEmail(authentication);
        Optional<Todo> existingTodo = todoRepository.findById(id);

        if (existingTodo.isPresent() && !existingTodo.get().getUser().getEmail().equals(email)) {
            log.info("User tried deleting todo that isn't theirs.");
            return;
        }
        todoRepository.deleteById(id);
    }

    private String getEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
