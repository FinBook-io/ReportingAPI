package io.finbook;

import io.finbook.controller.IndexController;
import io.finbook.controller.ProductController;
import spark.Route;

import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.post;

public class Routes {

    //public static HashMap<String, Controller> routes;

    public Routes() {
        //routes = new HashMap<>();
    }

    private static Route map(App.Converter c) {
        return (req, res) -> c.convert(req, res).handle(req, res);
    }

    public void init(){
        get("/", map((req, res) -> IndexController.index()));
        //post("/", map((req, res) -> IndexController.create()));
        // get("/:id", map((req, res) -> IndexController.getById(req.params(":id"))));


        get("/products", map((req, res) -> ProductController.getList()));
        post("/products", map((req, res) -> ProductController.create(req.body())));
    }


}
