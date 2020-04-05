package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.service.InvoiceService;
import io.finbook.spark.ResponseCreator;
import io.finbook.util.Utilities;

import java.util.*;

public class InvoiceController {

    private static InvoiceService invoiceService = new InvoiceService();

    public static ResponseCreator create(String body) {
        return null;
    }

    public static ResponseCreator list() {
        HashMap<String, List<Invoice>> data = new HashMap<>();

        data.put("invoices", invoiceService.getAllInvoices());

        return MyResponse.created(
                new StandardResponse(data, "home/invoices/list")
        );
    }

}
