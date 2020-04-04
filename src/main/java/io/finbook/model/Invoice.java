package io.finbook.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;
import java.util.List;

@Entity(value = "invoices", noClassnameStored = true)
public class Invoice {

    @Id
    private ObjectId id;
    private String invoiceNumber;
    // CIF+EJERCICIO+NUMEROFACTURA
    // 202045345662N00.000.010
    private Date invoiceDate;
    private InvoiceType invoiceType;
    private String invoiceUse;
    private User issuer;
    private User receiver;
    private List<Concept> concepts;
    private String notes;
    private Float subtotal;
    private Float totalTaxes;
    private Float totalDue;

    public Invoice() {
    }

    public Invoice(String invoiceNumber, Date invoiceDate, InvoiceType invoiceType, String invoiceUse, User issuer, User receiver, List<Concept> concepts, String notes, Float subtotal, Float totalTaxes, Float totalDue) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.invoiceType = invoiceType;
        this.invoiceUse = invoiceUse;
        this.issuer = issuer;
        this.receiver = receiver;
        this.concepts = concepts;
        this.notes = notes;
        this.subtotal = subtotal;
        this.totalTaxes = totalTaxes;
        this.totalDue = totalDue;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceUse() {
        return invoiceUse;
    }

    public void setInvoiceUse(String invoiceUse) {
        this.invoiceUse = invoiceUse;
    }

    public User getIssuer() {
        return issuer;
    }

    public void setIssuer(User issuer) {
        this.issuer = issuer;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    public Float getTotalTaxes() {
        return totalTaxes;
    }

    public void setTotalTaxes(Float totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public Float getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Float totalDue) {
        this.totalDue = totalDue;
    }
}
