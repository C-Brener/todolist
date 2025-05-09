package br.com.caiquebrener.todolist.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserModel {

    private String userName;
    private String name;
    private String password;
}
