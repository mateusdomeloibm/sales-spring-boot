package com.ibm.mateusmelo.service.impl;

import com.ibm.mateusmelo.domain.entity.User;
import com.ibm.mateusmelo.domain.repository.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    private final Users repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(Users repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional
    public User save(User user) {
        String pwdEncoded = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(pwdEncoded);
        return repository.save(user);
    }

    public boolean authenticate(User user) {
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        return encoder.matches(user.getPassword(), userDetails.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not valid..."));

        String[] roles = user.isAdmin() ?
                new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}
