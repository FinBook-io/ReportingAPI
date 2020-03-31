package io.finbook.http;

import io.finbook.spark.ResponseCreator;
import io.finbook.spark.TemplateEngine;

public class MyResponse {

    public static ResponseCreator ok(StandardResponse body) {
        return (req, res) -> {
            res.status(200);
            return new TemplateEngine().render(body);
        };
    }

    public static ResponseCreator created(StandardResponse body) {
        return (req, res) -> {
            res.status(201);
            return new TemplateEngine().render(body);
        };
    }

    public static ResponseCreator badRequest(StandardResponse body) {
        return (req, res) -> {
            res.status(400);
            return new TemplateEngine().render(body);
        };
    }

    public static ResponseCreator forbidden(StandardResponse body) {
        return (req, res) -> {
            res.status(403);
            return new TemplateEngine().render(body);
        };
    }

    public static ResponseCreator notFound(StandardResponse body) {
        return (req, res) -> {
            res.status(404);
            return new TemplateEngine().render(body);
        };
    }

    public static ResponseCreator internalServerError(StandardResponse body) {
        return (req, res) -> {
            res.status(500);
            return new TemplateEngine().render(body);
        };
    }

    // more methods creating responses
}
