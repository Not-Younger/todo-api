package org.jonathanyoung.todo.model;

public record UserResponse(
        String id,
        String email,
        String roles,
        String token
) {
}
