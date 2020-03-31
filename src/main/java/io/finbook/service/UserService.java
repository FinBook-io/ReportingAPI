package io.finbook.service;

import io.finbook.model.User;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class UserService extends Database {

    public void addUser(User user) {
        datastore.save(user);
    }

    public List<User> getAllUsers() {
        return datastore.find(User.class).asList();
    }

    public User findById(String id) {
        Query<User> query = datastore.createQuery(User.class).field("_id").equal(id);
        return query.get();
    }

}
