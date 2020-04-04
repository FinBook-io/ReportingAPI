package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.service.InvoiceService;
import io.finbook.spark.ResponseCreator;

import java.util.HashMap;
import java.util.List;

public class ReportController {

    private static InvoiceService invoiceService = new InvoiceService();

    public static ResponseCreator index() {
        String currentUser = "B35290634";
        HashMap<String, Object> data = new HashMap<>();

        // Incomes
        List<Invoice> invoices = invoiceService.findAllInvoicesByIssuerId(currentUser);
        Double incomes = 0.0;
        for(Invoice invoice : invoices){
            incomes += invoice.getTotalDue();
        }
        data.put("incomes", incomes);

        // data.put("refunds", "");
        invoices = invoiceService.findAllInvoicesByReceiverId(currentUser);
        Double refunds = 0.0;
        for(Invoice invoice : invoices){
            refunds += invoice.getTotalDue();
        }
        data.put("refunds", refunds);

        // data.put("salaries", "");
        invoices = invoiceService.findAllSalaryInvoicesByIssuerId(currentUser);
        Double salaries = 0.0;
        for(Invoice invoice : invoices){
            salaries += invoice.getTotalDue();
        }
        data.put("salaries", salaries);

        return MyResponse.ok(
                new StandardResponse(data, "home/reports/index")
        );
    }

}
