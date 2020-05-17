package io.finbook.command;

import io.finbook.responses.MyResponse;
import io.finbook.responses.StandardResponse;
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
