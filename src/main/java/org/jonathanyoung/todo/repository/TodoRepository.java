package org.jonathanyoung.todo.repository;

import org.jonathanyoung.todo.model.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, String> {

}
