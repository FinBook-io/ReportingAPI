package io.finbook;

import io.finbook.controller.Controller;
import io.finbook.controller.IndexController;

import java.util.HashMap;

import static spark.Spark.get;

public class Routes {

    public static HashMap<String, Controller> routes;

    public Routes() {
        routes = new HashMap<>();
    }

    public void init(){
        get("/", IndexController.index);

    }
}
