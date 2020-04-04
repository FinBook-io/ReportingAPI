package io.finbook.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "products", noClassnameStored = true)
public class Product {

    @Id
    private ObjectId id;
    private String code;
    private String description;
    private Float taxRate;
    private Float price;

    public Product() {
    }

    public Product(String code, String description, Float taxRate, Float price) {
        super();
        this.code = code;
        this.description = description;
        this.taxRate = taxRate;
        this.price = price;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Float taxRate) {
        this.taxRate = taxRate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
