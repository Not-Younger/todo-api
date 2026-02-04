package org.jonathanyoung.todo.repository;

import org.jonathanyoung.todo.model.Todo;
import org.jonathanyoung.todo.model.UserInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PageableTodoRepository extends PagingAndSortingRepository<Todo, String> {

    List<Todo> findAllByUser(UserInfo user, Pageable pageable);
}
