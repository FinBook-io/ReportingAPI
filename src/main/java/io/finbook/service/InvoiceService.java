package io.finbook.service;

import io.finbook.model.Invoice;

import java.util.List;

public class InvoiceService extends Database {

    public void addInvoice(Invoice invoice) {
        datastore.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return datastore.find(Invoice.class).order("invoiceDate").asList();
    }

    public List<Invoice> findAllInvoicesByIssuerId(String id){
        return datastore.find(Invoice.class).field("issuerIdNumber").equal(id).field("invoiceType").equal("I").asList();
    }

    public List<Invoice> findAllInvoicesByReceiverId(String id){
        return datastore.find(Invoice.class).field("receiverIdNumber").equal(id).field("invoiceType").equal("R").asList();
    }

    public List<Invoice> findAllSalaryInvoicesByIssuerId(String id){
        return datastore.find(Invoice.class).field("issuerIdNumber").equal(id).field("invoiceType").equal("S").asList();
    }

}
