package io.finbook.http;

import com.google.gson.Gson;
import io.finbook.spark.ResponseCreator;

public class MyResponse {

    public static ResponseCreator ok(StandardResponse body) {
        return (req, res) -> {
            res.status(200);
            res.type("application/json");
            return new Gson().toJson(body);
        };
    }

    public static ResponseCreator badRequest(StandardResponse body) {
        return (req, res) -> {
            res.status(400);
            res.type("application/json");
            return new Gson().toJson(body);
        };
    }

    // more methods creating responses
}
