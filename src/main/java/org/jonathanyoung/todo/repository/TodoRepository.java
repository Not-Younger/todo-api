package org.jonathanyoung.todo.repository;

import org.jonathanyoung.todo.model.Todo;
import org.jonathanyoung.todo.model.UserInfo;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface TodoRepository extends ListCrudRepository<Todo, String> {

    List<Todo> findAllByUser(UserInfo user);
}
