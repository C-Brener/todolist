package br.com.caiquebrener.todolist.user.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.caiquebrener.todolist.user.model.UserModel;
import br.com.caiquebrener.todolist.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserModel user) {
        UserModel getUserByUsername = userRepository.findByUsername(user.getUsername());
        if (getUserByUsername != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Existing user in the database");
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(passwordHashred);
        final UserModel userCreated = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
