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

    public static ResponseCreator read(String id) {
        return MyResponse.ok(
                new StandardResponse(null, "home/index")
        );
    }

    public static ResponseCreator update(String id) {
        return MyResponse.ok(
                new StandardResponse(null, "home/index")
        );
    }

    public static ResponseCreator delete(String id) {
        return MyResponse.ok(
                new StandardResponse(null, "home/index")
        );
    }

    public static ResponseCreator list() {
        // new Gson().toJsonTree(userService.getAllUsers())
        HashMap<String, Object> data = new HashMap<>();
        try{
            data.put("users", userService.getAllUsers());
        }catch (Exception ex){
            return MyResponse.internalServerError(
                    new StandardResponse(null, "home/errors/500")
            );
        }
        return MyResponse.ok(
                new StandardResponse(data, "dashboard/users/list")
        );
    }

    public static ResponseCreator getUserByEmail(String email) {
        // new Gson().toJsonTree(userService.findById(email))
        return MyResponse.ok(
                new StandardResponse(null, "home/index")
        );
    }

}
