package io.finbook;

import spark.Request;
import spark.Response;

public class App {

    public static void main(String[] args) {
        // Set up all routes
        Routes routes = new Routes();
        routes.init();
    }

    public interface Converter {
        ResponseCreator convert(Request req, Response res);
    }

}
