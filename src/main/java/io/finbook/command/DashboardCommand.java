package io.finbook.command;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.service.InvoiceService;
import io.finbook.sparkcontroller.ResponseCreator;
import io.finbook.util.Utils;

import java.util.HashMap;
import java.util.List;

public class DashboardCommand {

    private static InvoiceService invoiceService = new InvoiceService();

    public static ResponseCreator index(String currentUserId) {
        HashMap<String, Object> data = new HashMap<>();

        // Incomes of the current user
        List<Invoice> invoices = invoiceService.getAllInvoicesByIssuerId(currentUserId);
        Double incomes = invoiceService.getSumTotalTaxes(invoices);
        data.put("incomes", Utils.formatDouble(incomes));

        // Refunds of the current user
        invoices = invoiceService.getAllInvoicesByReceiverId(currentUserId);
        Double refunds = invoiceService.getSumTotalTaxes(invoices);
        data.put("refunds", Utils.formatDouble(refunds));

        // Total taxes due
        data.put("totalTaxesDue", Utils.formatDouble(incomes - refunds));

        // List of invoices of the current user
        // data.put("invoices", invoiceService.getAllInvoicesById(currentUserId));

        return MyResponse.ok(
                new StandardResponse(data, "dashboard/index")
        );
    }

}
