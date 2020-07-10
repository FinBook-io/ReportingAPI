package io.finbook.responses;

import io.finbook.sparkcontroller.ResponseCreator;
import io.finbook.sparkcontroller.TemplateEngine;

public class CustomResponse {

    private CustomResponse() {
    }

    public static ResponseCreator ok(ResponseStructure body) {
        return (req, res) -> {
            res.status(200);
            return new TemplateEngine().render(body);
        };
    }

    public static ResponseCreator notFound(ResponseStructure body) {
        return (req, res) -> {
            res.status(404);
            return new TemplateEngine().render(body);
        };
    }

    public static ResponseCreator internalServerError(ResponseStructure body) {
        return (req, res) -> {
            res.status(500);
            return new TemplateEngine().render(body);
        };
    }
}
