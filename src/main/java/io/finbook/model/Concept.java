package io.finbook.model;

public class Concept {

    private Product product;
    private Integer units;
    private Float discountRate;
    private Float taxAmount;
    private Float amount;

    public Concept() {
    }

    public Concept(Product product, Integer units, Float discountRate, Float taxAmount, Float amount) {
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

    public Float getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Float discountRate) {
        this.discountRate = discountRate;
    }

    public Float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
