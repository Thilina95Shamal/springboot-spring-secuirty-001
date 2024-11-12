package com.example.proj.servcie.user;
import com.example.proj.model.user.User;

public interface UserService {

    User createUser(User user);

    String verify(User user);
}
