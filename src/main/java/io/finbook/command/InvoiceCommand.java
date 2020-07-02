package io.finbook.command;

import io.finbook.responses.MyResponse;
import io.finbook.responses.StandardResponse;
import io.finbook.service.InvoiceService;
import io.finbook.sparkcontroller.ResponseCreator;

import java.util.*;

public class InvoiceCommand {

    private static InvoiceService invoiceService = new InvoiceService();

    public static ResponseCreator list(String currentUserId) {
        Map<String, Object> data = new HashMap<>();

        data.put("invoices", invoiceService.getAllInvoicesById(currentUserId));

        return MyResponse.created(
                new StandardResponse(data, "dashboard/invoices/list")
        );
    }

}
