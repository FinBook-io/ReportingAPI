package io.finbook.spark;

import io.finbook.controller.*;
import spark.Route;

import static spark.Spark.*;

public class Routes {

    public Routes() {
    }

    private static Route map(Converter c) {
        return (req, res) -> c.convert(req, res).handle(req, res);
    }

    public void init(){
        get("/", map((req, res) -> HomeController.index()));
        get("/dashboard", map((req, res) -> DashboardController.index(req.session())));

        // AUTHENTICATION
        path("/auth", () -> {
            get("/login", map((req, res) -> AuthController.login()));
            post("/login", map((req, res) -> AuthController.initSession()));
            get("/logout", map((req, res) -> AuthController.logout(req.body())));
        });

        // USERS
        path("/users", () -> {
            get("", map((req, res) -> UserController.list()));
            post("", map((req, res) -> UserController.create(req.body())));
            get("/:id", map((req, res) -> UserController.read(req.params(":id"))));
            put("/:id", map((req, res) -> UserController.update(req.params(":id"))));
            delete("/:id",  map((req, res) -> UserController.delete(req.params(":id"))));
            get("/:id", map((req, res) -> UserController.getUserByEmail(req.params(":id"))));
        });

        // PRODUCTS
        path("/products", () -> {
            get("", map((req, res) -> ProductController.list()));
            post("", map((req, res) -> ProductController.create(req.body())));

            //get("/:id", map((req, res) -> ProductController.getById(req.params(":id"))));
        });

        // INVOICES
        path("/invoices", () -> {
            get("", map((req, res) -> InvoiceController.list()));
            post("", map((req, res) -> InvoiceController.create(req.body())));

            //get("/:id", map((req, res) -> ProductController.getById(req.params(":id"))));
        });

        // REPORTS
        path("/reporting", () -> {
            get("", map((req, res) -> ReportingController.index()));
            get("/current-month", map((req, res) -> ReportingController.currentMonth()));
            // post("", map((req, res) -> InvoiceController.create(req.body())));
            //get("/:id", map((req, res) -> ProductController.getById(req.params(":id"))));
        });

        // ERROR - NOT FOUND
        get("*", map((req, res) -> ErrorController.notFound()));
    }

}
