package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.spark.ResponseCreator;

import java.util.HashMap;

public class IndexController {

    public static ResponseCreator index() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("firstName", "Kevin");
        return MyResponse.ok(
                new StandardResponse(data, "index")
        );
    }

}
