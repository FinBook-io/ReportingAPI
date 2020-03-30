package io.finbook.spark;

import io.finbook.controller.IndexController;
import io.finbook.controller.ProductController;
import spark.Route;

import static spark.Spark.get;
import static spark.Spark.post;

public class Routes {

    public Routes() {
    }

    private static Route map(App.Converter c) {
        return (req, res) -> c.convert(req, res).handle(req, res);
    }

    public void init(){
        get("/", map((req, res) -> IndexController.index()));

        // PRODUCTS
        get("/products", map((req, res) -> ProductController.getList()));
        post("/products", map((req, res) -> ProductController.create(req.body())));
    }


}
