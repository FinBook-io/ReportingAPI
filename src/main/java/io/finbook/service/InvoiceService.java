package io.finbook.service;

import io.finbook.model.Invoice;

import java.util.List;

public class InvoiceService extends Database {

    public void addBill(Invoice bill) {
        datastore.save(bill);
    }

    public List<Invoice> getAllBills() {
        return datastore.find(Invoice.class).order("invoiceDate").asList();
    }

}
