package io.finbook.controller;

import com.google.gson.Gson;
import io.finbook.spark.ResponseCreator;

import java.util.Map;

public class MyResponse {

    public static ResponseCreator ok(Map body) {
        return (req, res) -> {
            res.status(200);
            res.type("application/json");
            return convertMapIntoJson(body);
        };
    }

    public static ResponseCreator badRequest(Map body) {
        return (req, res) -> {
            res.status(400);
            res.type("application/json");
            return convertMapIntoJson(body);
        };
    }

    public static ResponseCreator listRequest(String body) {
        return (req, res) -> {
            res.status(400);
            res.type("application/json");
            return body;
        };
    }

    public static ResponseCreator created(String body) {
        return (req, res) -> {
            res.status(400);
            res.type("application/json");
            return body;
        };
    }

    // more methods creating responses
    private static String convertMapIntoJson(Map body){
        Gson gson = new Gson();
        return gson.toJson(body);
    }
}
