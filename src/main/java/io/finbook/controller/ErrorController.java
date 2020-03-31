package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.spark.ResponseCreator;

public class ErrorController {

    public static ResponseCreator notFound() {
        return MyResponse.notFound(
                new StandardResponse(null, "errors/404")
        );
    }

}
