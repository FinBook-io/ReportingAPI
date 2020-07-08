package io.finbook.command;

import io.finbook.responses.CustomResponse;
import io.finbook.responses.ResponseStructure;
import io.finbook.sparkcontroller.ResponseCreator;

public class ErrorCommand {

    public static ResponseCreator notFound() {
        return CustomResponse.notFound(
                new ResponseStructure(null, "home/errors/404")
        );
    }

    public static ResponseCreator internalServerError() {
        return CustomResponse.notFound(
                new ResponseStructure(null, "home/errors/500")
        );
    }

}
