package io.finbook;

import io.finbook.spark.ResponseCreator;
import io.finbook.spark.Routes;
import spark.Request;
import spark.Response;

import static spark.Spark.staticFiles;

public class App {

    public App() {
    }

    public void init(){
        // Setup Spark-java
        staticFiles.location("/public");

        // Setup database connnection

        // Setup all routes
        Routes routes = new Routes();
        routes.init();
    }

    public interface Converter {
        ResponseCreator convert(Request req, Response res);
    }

}
