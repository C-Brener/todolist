package br.com.caiquebrener.todolist.service.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.caiquebrener.todolist.model.user.UserModel;
import br.com.caiquebrener.todolist.repository.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserModel createUser(UserModel user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Existing user in the database");
        }
        String passwordHashed = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(passwordHashed);
        return userRepository.save(user);
    }
}
