package io.finbook.sparkcontroller;

import io.finbook.command.*;
import io.finbook.command.ReportingCommand;
import io.finbook.file.XMLCommand;
import io.finbook.mail.Mail;
import io.finbook.file.PDFCommand;
import io.finbook.util.Path;
import spark.Route;

import java.io.IOException;

import static spark.Spark.*;

public class Routes {

    public Routes() {
    }

    private static Route map(Converter c) {
        return (req, res) -> c.convert(req, res).handle(req, res);
    }

    public void init(){

        get(Path.HomeRoutes.INDEX, map((req, res) -> HomeCommand.index(Auth.isLogged(req))));

        get("/pdf", map((req, res) -> {
            try {
                return PDFCommand.reportInPDF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }));

        get("/xml", map((req, res) -> XMLCommand.init()));

        get("/mail", map((req, res) -> {
            Mail mail = new Mail();
            mail.sendMail("juankevin.tr@gmail.com");
            return null;
        }));

        // AUTHENTICATION
        path(Path.AuthRoutes.AUTH, () -> {
            get(Path.AuthRoutes.SIGN_IN, map(Auth::signin));
            get(Path.AuthRoutes.SIGN_CERTIFICATE, map(Auth::sign));
            post(Path.AuthRoutes.SIGN_IN, Auth::initSession);
            get(Path.AuthRoutes.SIGN_OUT, Auth::signout);
        });

        // PRIVATE ROUTES - AUTHENTICATION IS REQUIRED
        path(Path.AdminRoutes.ADMIN, () -> {

            // AUTHENTICATION FILTER
            before(Path.AdminRoutes.ADMIN_FILTER, Auth::authFilter);

            // DASHBOARD
            get(Path.AdminRoutes.DASHBOARD, map((req, res) -> DashboardCommand.index(Auth.getCurrentUserId(req))));

            // INVOICES
            path(Path.AdminRoutes.INVOICES, () -> {
                get(Path.AdminRoutes.INVOICES_EMPTY, map((req, res) -> InvoiceCommand.list(Auth.getCurrentUserId(req))));
            });

            // REPORTS
            path(Path.AdminRoutes.REPORTING, () -> {
                post(Path.AdminRoutes.REPORTING_AJAX_DATEPICKER, (req, res) -> ReportingCommand.getDataForPeriod(Auth.getCurrentUserId(req), req.queryParams("datepicker_value")));
                post(Path.AdminRoutes.REPORTING_AJAX_SEND_REPORT, (req, res) ->
                        ReportingCommand.sendReport(
                                Auth.getCurrentUserId(req),
                                req.queryParams("period"),
                                req.queryParams("email")
                        ));
            });

            get(Path.AdminRoutes.VAT_RETURNS, map((req, res) -> VATReporting.index()));

        });

        // ERROR - NOT FOUND
        // get(Path.HomeRoutes.ERROR_404, map((req, res) -> ErrorCommand.notFound()));

    }

}
