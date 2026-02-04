package org.jonathanyoung.todo.model;

import java.util.List;

public record PageResponse(
        List<Todo> data,
        int page,
        int limit,
        int total
) {
}
