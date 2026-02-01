package org.jonathanyoung.todo.repository;

import org.jonathanyoung.todo.model.UserInfo;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserInfoRepository extends ListCrudRepository<UserInfo, UUID> {

    Optional<UserInfo> findUserInfoByEmail(String email);
}
