package io.finbook.spark;

import spark.Request;
import spark.Response;

public interface Converter {
    ResponseCreator convert(Request req, Response res);
}
