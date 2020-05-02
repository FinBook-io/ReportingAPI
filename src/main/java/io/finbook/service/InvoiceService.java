package io.finbook.service;

import io.finbook.model.Invoice;
import io.finbook.model.InvoiceType;

import java.time.LocalDateTime;
import java.util.*;

public class InvoiceService extends Database {

    private String issuerIdNumberLabel = "issuerIdNumber";
    private String receiverIdNumberLabel = "receiverIdNumber";
    private String invoiceTypeLabel = "invoiceType";
    private String invoiceDateLabel = "invoiceDate";

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
                .filter(issuerIdNumberLabel, id)
                .asList();
    }

    public List<Invoice> getAllInvoicesByReceiverId(String id) {
        return datastore.find(Invoice.class)
                .filter(receiverIdNumberLabel, id)
                .asList();
    }

    public List<Invoice> getAllInvoicesByIssuerIdPerPeriod(String id, LocalDateTime date1, LocalDateTime date2) {
        return datastore.find(Invoice.class)
                .filter(issuerIdNumberLabel, id)
                .field(invoiceDateLabel).greaterThan(date1)
                .field(invoiceDateLabel).lessThan(date2)
                .asList();
    }

    public List<Invoice> getAllInvoicesByReceiverIdPerPeriod(String id, LocalDateTime date1, LocalDateTime date2) {
        return datastore.find(Invoice.class)
                .filter(receiverIdNumberLabel, id)
                .field(invoiceDateLabel).greaterThan(date1)
                .field(invoiceDateLabel).lessThan(date2)
                .asList();
    }

    public List<Invoice> getInvoicesPerPeriodAndType(String id, InvoiceType invoiceType, LocalDateTime date1, LocalDateTime date2){
        List<Invoice> invoicesList = new ArrayList<>();

        String firstFindAs;
        String secondFindAs;

        switch (invoiceType){
            case INCOME:
                // User earn money being issuer and receiver when InvoiceType is I (Income)
                firstFindAs = issuerIdNumberLabel;
                secondFindAs = receiverIdNumberLabel;
                break;
            case REFUND:
                // User loose money being receiver and issuer when InvoiceType is R (Refund)
                firstFindAs = receiverIdNumberLabel;
                secondFindAs = issuerIdNumberLabel;
                break;
            default:
                return invoicesList; // Null - Empty
        }

        invoicesList.addAll(datastore.find(Invoice.class)
                .filter(firstFindAs, id)
                .filter(invoiceTypeLabel, InvoiceType.INCOME.getLabel())
                .field(invoiceDateLabel).greaterThan(date1)
                .field(invoiceDateLabel).lessThan(date2)
                .asList());

        invoicesList.addAll(datastore.find(Invoice.class)
                .filter(secondFindAs, id)
                .filter(invoiceTypeLabel, InvoiceType.REFUND.getLabel())
                .field(invoiceDateLabel).greaterThan(date1)
                .field(invoiceDateLabel).lessThan(date2)
                .asList());

        return invoicesList;
    }

    public Double getSumTotalTaxes(List<Invoice> invoices) {
        Double sum = 0.0;
        for(Invoice invoice : invoices){
            sum += invoice.getTotalTaxes();
        }
        return sum;
    }

}
