package io.finbook.sparkcontroller;

import io.finbook.command.*;
import spark.Route;

import static spark.Spark.*;

public class Routes {

    public Routes() {
    }

    private static Route map(Converter c) {
        return (req, res) -> c.convert(req, res).handle(req, res);
    }

    public void init(){
        get("/", map((req, res) -> HomeCommand.index(Auth.isLogged(req))));

        // AUTHENTICATION
        path("/auth", () -> {
            get("/login", map(Auth::login));
            post("/login", Auth::initSession);
            get("/logout", Auth::logout);
        });

        // PRIVATE ROUTES - AUTHENTICATION IS REQUIRED
        path("/admin", () -> {

            // AUTHENTICATION FILTER
            before("/*", Auth::authFilter);

            // DASHBOARD
            get("/dashboard", map((req, res) -> DashboardCommand.index(Auth.getCurrentUserId(req))));

            // USERS
            path("/users", () -> {
                get("", map((req, res) -> UserCommand.list()));
                post("", map((req, res) -> UserCommand.create(req.body())));
                get("/:id", map((req, res) -> UserCommand.read(req.params(":id"))));
                put("/:id", map((req, res) -> UserCommand.update(req.params(":id"))));
                delete("/:id",  map((req, res) -> UserCommand.delete(req.params(":id"))));
                get("/:id", map((req, res) -> UserCommand.getUserByEmail(req.params(":id"))));
            });

            // PRODUCTS
            path("/products", () -> {
                get("", map((req, res) -> ProductCommand.list()));
                post("", map((req, res) -> ProductCommand.create(req.body())));
            });

            // INVOICES
            path("/invoices", () -> {
                get("", map((req, res) -> InvoiceCommand.list()));
                post("", map((req, res) -> InvoiceCommand.create(req.body())));
            });

            // REPORTS
            path("/reporting", () -> {
                get("", map((req, res) -> ReportingCommand.index(Auth.getCurrentUserId(req))));
                get("/current-month", map((req, res) -> ReportingCommand.currentMonth(Auth.getCurrentUserId(req))));
                post("/ajax-datepicker", (req, res) -> ReportingCommand.getDataForPeriod(Auth.getCurrentUserId(req), req.queryParams("datepicker_value")));
            });

        });

        // ERROR - NOT FOUND
        get("*", map((req, res) -> ErrorCommand.notFound()));

    }

}
