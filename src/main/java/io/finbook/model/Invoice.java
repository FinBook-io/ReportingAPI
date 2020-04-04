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
    private Date invoiceDate;
    private String invoiceType;
    private String invoiceUse;
    // private User issuer;
    private String issuerFullName;
    private String issuerIdNumber;
    private String issuerEmail;
    private String issuerPhoneNumber;
    // private User receiver;
    private String receiverFullName;
    private String receiverIdNumber;
    private String receiverEmail;
    private String receiverPhoneNumber;
    private List<Concept> concepts;
    private String notes;
    private Double subtotal;
    private Double totalTaxes;
    private Double totalDue;

    public Invoice() {
    }

    public Invoice(String invoiceNumber, Date invoiceDate, String invoiceType, String invoiceUse,
                   String issuerFullName, String issuerIdNumber, String issuerEmail, String issuerPhoneNumber,
                   String receiverFullName, String receiverIdNumber, String receiverEmail, String receiverPhoneNumber,
                   List<Concept> concepts, String notes, Double subtotal, Double totalTaxes, Double totalDue) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.invoiceType = invoiceType;
        this.invoiceUse = invoiceUse;
        this.issuerFullName = issuerFullName;
        this.issuerIdNumber = issuerIdNumber;
        this.issuerEmail = issuerEmail;
        this.issuerPhoneNumber = issuerPhoneNumber;
        this.receiverFullName = receiverFullName;
        this.receiverIdNumber = receiverIdNumber;
        this.receiverEmail = receiverEmail;
        this.receiverPhoneNumber = receiverPhoneNumber;
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

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceUse() {
        return invoiceUse;
    }

    public void setInvoiceUse(String invoiceUse) {
        this.invoiceUse = invoiceUse;
    }

    public String getIssuerFullName() {
        return issuerFullName;
    }

    public void setIssuerFullName(String issuerFullName) {
        this.issuerFullName = issuerFullName;
    }

    public String getIssuerIdNumber() {
        return issuerIdNumber;
    }

    public void setIssuerIdNumber(String issuerIdNumber) {
        this.issuerIdNumber = issuerIdNumber;
    }

    public String getIssuerEmail() {
        return issuerEmail;
    }

    public void setIssuerEmail(String issuerEmail) {
        this.issuerEmail = issuerEmail;
    }

    public String getIssuerPhoneNumber() {
        return issuerPhoneNumber;
    }

    public void setIssuerPhoneNumber(String issuerPhoneNumber) {
        this.issuerPhoneNumber = issuerPhoneNumber;
    }

    public String getReceiverFullName() {
        return receiverFullName;
    }

    public void setReceiverFullName(String receiverFullName) {
        this.receiverFullName = receiverFullName;
    }

    public String getReceiverIdNumber() {
        return receiverIdNumber;
    }

    public void setReceiverIdNumber(String receiverIdNumber) {
        this.receiverIdNumber = receiverIdNumber;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
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

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotalTaxes() {
        return totalTaxes;
    }

    public void setTotalTaxes(Double totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public Double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Double totalDue) {
        this.totalDue = totalDue;
    }
}
