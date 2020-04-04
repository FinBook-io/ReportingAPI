package io.finbook.controller;

import com.google.gson.Gson;
import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.User;
import io.finbook.service.UserService;
import io.finbook.spark.ResponseCreator;

import java.util.HashMap;

public class UserController {

    private static UserService userService = new UserService();

    public static ResponseCreator create(String body) {
        User user = new Gson().fromJson(body, User.class);

        userService.addUser(user);

        return MyResponse.ok(
                new StandardResponse(null, "home/index")
        );
    }

    public static ResponseCreator getList() {
        // new Gson().toJsonTree(userService.getAllUsers())
        HashMap<String, Object> data = new HashMap<>();
        data.put("users", userService.getAllUsers());
        return MyResponse.ok(
                new StandardResponse(data, "home/users/list")
        );
    }

    public static ResponseCreator getUserByEmail(String email) {
        // new Gson().toJsonTree(userService.findById(email))
        return MyResponse.ok(
                new StandardResponse(null, "home/index")
        );
    }

}
