package org.jonathanyoung.todo.repository;

import org.jonathanyoung.todo.model.Todo;
import org.springframework.data.repository.ListCrudRepository;

public interface TodoRepository extends ListCrudRepository<Todo, String> {

}
