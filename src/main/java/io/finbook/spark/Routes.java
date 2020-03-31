package io.finbook.spark;

import io.finbook.controller.IndexController;
import io.finbook.controller.ProductController;
import io.finbook.controller.UserController;
import spark.Route;

import static spark.Spark.get;
import static spark.Spark.post;

public class Routes {

    public Routes() {
    }

    private static Route map(Converter c) {
        return (req, res) -> c.convert(req, res).handle(req, res);
    }

    public void init(){
        get("/", map((req, res) -> IndexController.index()));

        // USERS
        get("/users", map((req, res) -> UserController.getList()));
        post("/users", map((req, res) -> UserController.create(req.body())));
        get("/users/:id", map((req, res) -> UserController.getUserByEmail(req.params(":id"))));

        // PRODUCTS
        get("/products", map((req, res) -> ProductController.getList()));
        //get("/products/:id", map((req, res) -> ProductController.getById(req.params(":id"))));
        post("/products", map((req, res) -> ProductController.create(req.body())));

    }

}
