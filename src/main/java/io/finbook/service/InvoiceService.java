package io.finbook.service;

import io.finbook.model.Invoice;

import java.util.Date;
import java.util.List;

public class InvoiceService extends Database {

    public void addInvoice(Invoice invoice) {
        datastore.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return datastore.find(Invoice.class).order("invoiceDate").asList();
    }

    public List<Invoice> getAllInvoicesByIssuerId(String id) {
        return datastore.find(Invoice.class).field("issuerIdNumber").equal(id).field("invoiceType").equal("I").asList();
    }

    public List<Invoice> getAllInvoicesByReceiverId(String id) {
        return datastore.find(Invoice.class).field("receiverIdNumber").equal(id).field("invoiceType").equal("R").asList();
    }

    public List<Invoice> getAllSalaryInvoicesByIssuerId(String id) {
        return datastore.find(Invoice.class).field("issuerIdNumber").equal(id).field("invoiceType").equal("S").asList();
    }

    public List<Invoice> getAllInvoiceByIssuerIdBetweenTwoDates(String id, Date date1, Date date2) {
        return datastore.find(Invoice.class)
                .field("issuerIdNumber").equal(id)
                .field("invoiceType").equal("I")
                .field("invoiceDate").greaterThan(date1)
                .field("invoiceDate").lessThan(date2)
                .asList();

    }

}
