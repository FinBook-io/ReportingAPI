package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.spark.ResponseCreator;

import java.util.HashMap;

public class AuthController {

    public static ResponseCreator login() {
       return MyResponse.ok(
                new StandardResponse(null, "home/login/login")
        );
    }

    public static ResponseCreator initSession() {
        HashMap<String, Object> data = new HashMap<>();
        return MyResponse.ok(
                new StandardResponse(data, "dashboard/index")
        );
    }

    public static ResponseCreator logout(String b) {
        return MyResponse.ok(
                new StandardResponse(null, "home/index")
        );
    }

}
