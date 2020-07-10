package io.finbook.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.time.LocalDateTime;

@Entity(value = "invoices", noClassnameStored = true)
public class Invoice {

    @Id
    private ObjectId id;

    private String invoiceUUID;
    private LocalDateTime invoiceDate;
    private Integer zipCode;
    private String invoiceType;
    private String usoCFDI;
    private String issuerName;
    private String issuerId;
    private String receiverName;
    private String receiverId;
    private String concept;
    private Double subtotal;
    private Double taxRate;
    private Double totalDue;
    private String currency;
    private String xmlFile;

    public Invoice() {
    }

    public Invoice(String invoiceUUID, LocalDateTime invoiceDate, Integer zipCode,
                   String invoiceType, String usoCFDI, String issuerName,
                   String issuerId, String receiverName, String receiverId,
                   String concept, Double subtotal, Double taxRate, Double totalDue,
                   String currency, String xmlFile) {
        this.invoiceUUID = invoiceUUID;
        this.invoiceDate = invoiceDate;
        this.zipCode = zipCode;
        this.invoiceType = invoiceType;
        this.usoCFDI = usoCFDI;
        this.issuerName = issuerName;
        this.issuerId = issuerId;
        this.receiverName = receiverName;
        this.receiverId = receiverId;
        this.concept = concept;
        this.subtotal = subtotal;
        this.taxRate = taxRate;
        this.totalDue = totalDue;
        this.currency = currency;
        this.xmlFile = xmlFile;
    }

    public Double getTotalTaxes() {
        return subtotal * taxRate;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getInvoiceUUID() {
        return invoiceUUID;
    }

    public void setInvoiceUUID(String invoiceUUID) {
        this.invoiceUUID = invoiceUUID;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getUsoCFDI() {
        return usoCFDI;
    }

    public void setUsoCFDI(String usoCFDI) {
        this.usoCFDI = usoCFDI;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Double totalDue) {
        this.totalDue = totalDue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }
}
