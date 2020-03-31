package io.finbook.http;

import com.google.gson.Gson;
import io.finbook.spark.ResponseCreator;
import io.finbook.spark.TemplateEngine;

public class MyResponse {

    public static ResponseCreator ok(StandardResponse body) {
        return (req, res) -> {
            res.status(200);
            res.type("application/json");
            return new TemplateEngine().render(body);
        };
    }

    public static ResponseCreator created(StandardResponse body) {
        return (req, res) -> {
            res.status(201);
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

    public static ResponseCreator forbidden(StandardResponse body) {
        return (req, res) -> {
            res.status(403);
            res.type("application/json");
            return new Gson().toJson(body);
        };
    }

    public static ResponseCreator notFound(StandardResponse body) {
        return (req, res) -> {
            res.status(404);
            res.type("application/json");
            return new Gson().toJson(body);
        };
    }

    public static ResponseCreator internalServerError(StandardResponse body) {
        return (req, res) -> {
            res.status(500);
            res.type("application/json");
            return new Gson().toJson(body);
        };
    }

    // more methods creating responses
}
