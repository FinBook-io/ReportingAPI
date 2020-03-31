package io.finbook.spark;

import com.mongodb.client.model.Filters;

import static spark.Spark.before;
import static spark.Spark.staticFiles;

public class App {

    public App() {
    }

    public void init() {
        // Setup Spark-java
        staticFiles.location("/public");
        staticFiles.expireTime(600L);

        // Setup all routes
        Routes routes = new Routes();
        routes.init();
    }

}
