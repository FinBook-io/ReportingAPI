package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.service.InvoiceService;
import io.finbook.spark.ResponseCreator;

import java.util.*;

public class InvoiceController {

    private static InvoiceService invoiceService = new InvoiceService();

    public static ResponseCreator create(String body) {
        return null;
    }

    public static ResponseCreator list() {
        HashMap<String, Object> data = new HashMap<>();

        data.put("invoices", invoiceService.getAllInvoices());

        return MyResponse.created(
                new StandardResponse(data, "dashboard/invoices/list")
        );
    }

}
