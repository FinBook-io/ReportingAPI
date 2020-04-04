package io.finbook.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;
import java.util.List;

@Entity(value = "bills", noClassnameStored = true)
public class Bill {

    @Id
    private ObjectId id;
    private String invoiceNumber;
    // CIF+EJERCICIO+NUMEROFACTURA
    // 202045345662N00.000.010
    private Date invoiceDate;
    private String invoiceType;
    private String invoiceUse;
    private User issuer; // emisor
    private User receiver; // receptor
    private List<Concept> concepts;
    private String notes;
    private Float subtotal;
    private Float totalTaxes;
    private Float totalDue;

    public Bill() {
    }

    public Bill(ObjectId id, String invoiceNumber, Date invoiceDate, User company, User client, List<Concept> concepts,
                String notes, Float subtotal, Float totalTaxes, Float totalDue) {
        super();
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.issuer = company;
        this.receiver = client;
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
