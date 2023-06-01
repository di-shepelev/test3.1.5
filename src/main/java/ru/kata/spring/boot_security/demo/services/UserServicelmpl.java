package ru.kata.spring.boot_security.demo.services;

import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import javax.persistence.EntityManager;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServicelmpl implements UserServise, UserDetailsService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Lazy
    public UserServicelmpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User addUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return user;
    }

    @Override
    public List<User> listUsers() {

        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User getUser = userRepository.getById(user.getId());
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(getUser.getPassword());
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeUser(int id) {

        userRepository.deleteById(id);
    }
    @Override
    public User getUserById(int id) {

        return userRepository.getById(id);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }

}
