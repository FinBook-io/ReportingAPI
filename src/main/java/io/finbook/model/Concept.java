package io.finbook.model;

public class Concept {

    private Product product;
    private Integer units;
    private Double discountRate;
    private Double taxAmount;
    private Double amount;

    public Concept() {
    }

    public Concept(Product product, Integer units, Double discountRate, Double taxAmount, Double amount) {
        super();
        this.product = product;
        this.units = units;
        this.discountRate = discountRate;
        this.taxAmount = taxAmount;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
