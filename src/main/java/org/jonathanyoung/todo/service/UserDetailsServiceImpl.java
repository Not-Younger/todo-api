package org.jonathanyoung.todo.service;

import lombok.extern.slf4j.Slf4j;
import org.jonathanyoung.todo.model.UserDetailsModel;
import org.jonathanyoung.todo.model.UserInfo;
import org.jonathanyoung.todo.repository.UserInfoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    public UserDetailsServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findUserInfoByEmail(username);
        return userInfo.map(UserDetailsModel::new).orElseThrow(() -> {
            log.info("User name not found: {}", username);
            return new UsernameNotFoundException("Invalid username");
        });
    }
}
