package io.finbook.service;

import io.finbook.model.Invoice;

import java.util.List;

public class InvoiceService extends Database {

    public void addBill(Invoice invoice) {
        datastore.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return datastore.find(Invoice.class).order("invoiceDate").asList();
    }

}
