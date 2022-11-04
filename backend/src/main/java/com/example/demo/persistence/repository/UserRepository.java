package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.User;

public interface UserRepository {

    User getById(long id);

    User getUserByLogin(String login);

    User createUser(User user);

    boolean updateUser(User user);

    boolean deleteUserById(long id);
}
