package br.com.caiquebrener.todolist.user.repository;

import br.com.caiquebrener.todolist.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
}
