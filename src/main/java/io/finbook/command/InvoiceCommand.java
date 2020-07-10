package io.finbook.command;

import io.finbook.responses.CustomResponse;
import io.finbook.responses.ResponseStructure;
import io.finbook.service.InvoiceService;
import io.finbook.sparkcontroller.ResponseCreator;
import io.finbook.util.Path;

import java.util.*;

public class InvoiceCommand {

    private static InvoiceService invoiceService = new InvoiceService();

    public static ResponseCreator list(String currentUserId) {
        Map<String, Object> data = new HashMap<>();

        data.put("invoices", invoiceService.getAllInvoicesById(currentUserId));

        return CustomResponse.ok(
                new ResponseStructure(data, Path.Template.ADMIN_INVOICES_LIST)
        );
    }

}
