package io.finbook.service;

import io.finbook.model.User;

import java.util.List;

public class UserService extends Database {

    public void addUser(User user) {
        datastore.save(user);
    }

    public List<User> getAllUsers() {
        return datastore.find(User.class).asList();
    }

}
