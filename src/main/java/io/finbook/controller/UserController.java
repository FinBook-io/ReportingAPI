package io.finbook.controller;

import com.google.gson.Gson;
import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.http.StatusResponse;
import io.finbook.model.User;
import io.finbook.service.UserService;
import io.finbook.spark.ResponseCreator;

public class UserController {

    private static UserService userService = new UserService();

    public static ResponseCreator create(String body) {
        User user = new Gson().fromJson(body, User.class);

        userService.addUser(user);

        return MyResponse.ok(
                new StandardResponse(StatusResponse.SUCCESS)
        );
    }

    public static ResponseCreator getList() {
        return MyResponse.ok(
                new StandardResponse(
                        StatusResponse.SUCCESS,
                        new Gson().toJsonTree(userService.getAllUsers())
                )
        );
    }

}
