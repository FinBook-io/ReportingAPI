package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.service.InvoiceService;
import io.finbook.spark.ResponseCreator;
import io.finbook.util.Utils;
import spark.Session;

import java.util.HashMap;
import java.util.List;

public class DashboardController {

    private static InvoiceService invoiceService = new InvoiceService();

    public static ResponseCreator index(Session session) {
        HashMap<String, Object> data = new HashMap<>();

        // Incomes of the current user
        List<Invoice> invoices = invoiceService.getAllInvoicesByIssuerId(Utils.getCurrentUser());
        Double incomes = invoiceService.getSumTotalTaxes(invoices);
        data.put("incomes", Utils.formatDouble(incomes));

        // Refunds of the current user
        invoices = invoiceService.getAllInvoicesByReceiverId(Utils.getCurrentUser());
        Double refunds = invoiceService.getSumTotalTaxes(invoices);
        data.put("refunds", Utils.formatDouble(refunds));

        // Total taxes due
        data.put("totalTaxesDue", Utils.formatDouble(incomes - refunds));

        // List of invoices of the current user
        data.put("invoices", invoiceService.getAllInvoicesById(Utils.getCurrentUser()));

        return MyResponse.ok(
                new StandardResponse(data, "dashboard/index")
        );
    }

}
