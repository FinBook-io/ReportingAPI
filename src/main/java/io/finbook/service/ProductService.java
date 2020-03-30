package io.finbook.service;

import io.finbook.model.Product;

import java.util.List;

public class ProductDatabase extends Database {

    public String addProduct(Product product) {
        datastore.save(product);
        return "Product added";
    }

    public List<Product> getAllProduct() {
        return datastore.find(Product.class).asList();
    }

}
