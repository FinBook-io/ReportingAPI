package io.finbook.service;

import io.finbook.model.Invoice;

import java.time.LocalDateTime;
import java.util.*;

public class InvoiceService extends Database {

    private String invoiceDate = "invoiceDate";

    public List<Invoice> getAllInvoices() {
        List<Invoice> invoicesList = datastore.find(Invoice.class).asList();
        invoicesList.sort(Comparator.comparing(Invoice::getInvoiceDate).reversed());

        return invoicesList;
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

    public List<Invoice> getAllInvoicesByIssuerIdPerPeriod(String id, LocalDateTime date1, LocalDateTime date2) {
        return datastore.find(Invoice.class)
                .filter("issuerIdNumber", id)
                .field(invoiceDate).greaterThan(date1)
                .field(invoiceDate).lessThan(date2)
                .asList();
    }

    public List<Invoice> getAllInvoicesByReceiverIdPerPeriod(String id, LocalDateTime date1, LocalDateTime date2) {
        return datastore.find(Invoice.class)
                .filter("receiverIdNumber", id)
                .field(invoiceDate).greaterThan(date1)
                .field(invoiceDate).lessThan(date2)
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
