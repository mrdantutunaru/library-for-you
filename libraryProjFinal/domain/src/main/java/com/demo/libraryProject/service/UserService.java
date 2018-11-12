package com.demo.libraryProject.service;

import com.demo.libraryProject.domain.Role;
import com.demo.libraryProject.domain.User;
import com.demo.libraryProject.repository.RoleRepository;
import com.demo.libraryProject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        //Role userRole = roleRepository.findByRole("ADMIN");
        //user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        Role userRole = roleRepository.findByRole("ROLE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        //log.info("All books are retrieved");
        return userRepository.findAll();
    }

    public User findOne(int id){
        return userRepository.findOne(id);
    }

    @Transactional
    public void deleteUserById(int id){
      //  log.info("An user was deleted");
        userRepository.delete(id);
    }

}