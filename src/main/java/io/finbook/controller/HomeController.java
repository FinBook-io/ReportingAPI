package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.spark.ResponseCreator;

import java.util.HashMap;

public class IndexController {

    public static ResponseCreator home() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("firstName", "Kevin");
        return MyResponse.ok(
                new StandardResponse(data, "home/index")
        );
    }

    public static ResponseCreator login() {
        HashMap<String, Object> data = new HashMap<>();
        return MyResponse.ok(
                new StandardResponse(data, "home/login/login")
        );
    }

    public static ResponseCreator logout(String b) {
        HashMap<String, Object> data = new HashMap<>();
        return MyResponse.ok(
                new StandardResponse(data, "home/index")
        );
    }

    public static ResponseCreator dashboard() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("firstName", "Kevin");
        return MyResponse.ok(
                new StandardResponse(data, "dashboard/index")
        );
    }

}
