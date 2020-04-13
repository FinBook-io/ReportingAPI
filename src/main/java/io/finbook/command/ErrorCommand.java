package io.finbook.command;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.sparkcontroller.ResponseCreator;

public class ErrorCommand {

    public static ResponseCreator notFound() {
        return MyResponse.notFound(
                new StandardResponse(null, "home/errors/404")
        );
    }

    public static ResponseCreator internalServerError() {
        return MyResponse.notFound(
                new StandardResponse(null, "home/errors/500")
        );
    }

}
