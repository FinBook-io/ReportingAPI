package io.finbook.sparkcontroller;

import io.finbook.command.*;
import io.finbook.command.ReportingCommand;
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

            // PRODUCTS
            path("/products", () -> {
                get("", map((req, res) -> ProductCommand.list()));
                post("", map((req, res) -> ProductCommand.create(req.body())));
            });

            // INVOICES
            path("/invoices", () -> {
                get("", map((req, res) -> InvoiceCommand.list(Auth.getCurrentUserId(req))));
            });

            // REPORTS
            path("/reporting", () -> {
                get("", map((req, res) -> ReportingCommand.index(Auth.getCurrentUserId(req))));
                post("/ajax-datepicker", (req, res) -> ReportingCommand.getDataForPeriod(Auth.getCurrentUserId(req), req.queryParams("datepicker_value")));
            });

        });

        // ERROR - NOT FOUND
        get("*", map((req, res) -> ErrorCommand.notFound()));

    }

}
