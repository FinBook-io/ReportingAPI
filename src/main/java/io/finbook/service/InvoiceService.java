package io.finbook.service;

import io.finbook.model.Invoice;

import java.util.*;

public class InvoiceService extends Database {

    public void addInvoice(Invoice invoice) {
        datastore.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return datastore.find(Invoice.class)
                .order("invoiceDate")
                .asList();
    }

    public List<Invoice> getAllInvoicesById(String id) {
        List<Invoice> invoicesList = new ArrayList<>();

        invoicesList.addAll(getAllInvoicesByIssuerId(id));
        invoicesList.addAll(getAllInvoicesByReceiverId(id));

        invoicesList.sort(Comparator.comparing(Invoice::getInvoiceDate).reversed());

        return invoicesList;
    }

    public List<Invoice> getAllInvoicesByIssuerId(String id) {
        return datastore.find(Invoice.class)
                .filter("issuerIdNumber", id)
                .asList();
    }

    public List<Invoice> getAllInvoicesByReceiverId(String id) {
        return datastore.find(Invoice.class)
                .filter("receiverIdNumber", id)
                .asList();
    }

    public List<Invoice> getAllInvoicesByIssuerIdBetweenTwoDates(String id, Date date1, Date date2) {
        return datastore.find(Invoice.class)
                .filter("issuerIdNumber", id)
                .field("invoiceDate").greaterThan(date1)
                .field("invoiceDate").lessThan(date2)
                .asList();
    }

    public List<Invoice> getAllInvoicesByReceiverIdBetweenTwoDates(String id, Date date1, Date date2) {
        return datastore.find(Invoice.class)
                .filter("receiverIdNumber", id)
                .field("invoiceDate").greaterThan(date1)
                .field("invoiceDate").lessThan(date2)
                .asList();
    }

    public Double getSumTotalTaxes(List<Invoice> invoices) {
        Double sum = 0.0;
        for(Invoice invoice : invoices){
            sum += invoice.getTotalTaxes();
        }
        return sum;
    }

}
