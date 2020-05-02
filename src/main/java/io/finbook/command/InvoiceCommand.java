package io.finbook.command;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.service.InvoiceService;
import io.finbook.sparkcontroller.ResponseCreator;

import java.util.*;

public class InvoiceCommand {

    private static InvoiceService invoiceService = new InvoiceService();

    public static ResponseCreator list(String currentUserId) {
        HashMap<String, Object> data = new HashMap<>();

        data.put("invoices", invoiceService.getAllInvoicesById(currentUserId));

        return MyResponse.created(
                new StandardResponse(data, "dashboard/invoices/list")
        );
    }

}
