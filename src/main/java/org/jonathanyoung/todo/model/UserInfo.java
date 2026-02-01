package org.jonathanyoung.todo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserInfo {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String roles;
}
