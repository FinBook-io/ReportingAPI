package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.spark.ResponseCreator;

import java.util.HashMap;

public class IndexController {

    public static ResponseCreator index() {
        HashMap<String, Object> model = new HashMap<>();
        return MyResponse.ok(
                new StandardResponse(null, "index")
        );
    }

}
