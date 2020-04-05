package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.service.InvoiceService;
import io.finbook.spark.ResponseCreator;
import io.finbook.util.Utilities;

import java.util.HashMap;
import java.util.List;

public class DashboardController {

    private static InvoiceService invoiceService = new InvoiceService();

    public static ResponseCreator index() {
        HashMap<String, Object> data = new HashMap<>();

        // Incomes
        List<Invoice> invoices = invoiceService.findAllInvoicesByIssuerId(Utilities.getCurrentUser());
        Double incomes = 0.0;
        for(Invoice invoice : invoices){
            incomes += invoice.getTotalDue();
        }
        data.put("incomes", incomes);

        // data.put("refunds", "");
        invoices = invoiceService.findAllInvoicesByReceiverId(Utilities.getCurrentUser());
        Double refunds = 0.0;
        for(Invoice invoice : invoices){
            refunds += invoice.getTotalDue();
        }
        data.put("refunds", refunds);

        // data.put("salaries", "");
        invoices = invoiceService.findAllSalaryInvoicesByIssuerId(Utilities.getCurrentUser());
        Double salaries = 0.0;
        for(Invoice invoice : invoices){
            salaries += invoice.getTotalDue();
        }
        data.put("salaries", salaries);

        data.put("invoices", invoiceService.getAllInvoices());

        return MyResponse.ok(
                new StandardResponse(data, "dashboard/index")
        );
    }

}
