package br.com.caiquebrener.todolist.repository.user;

import br.com.caiquebrener.todolist.model.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);
}
