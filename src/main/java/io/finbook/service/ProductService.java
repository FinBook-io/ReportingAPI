package io.finbook.service;

import io.finbook.model.Product;

import java.util.List;

public class ProductService extends Database {

    public void addProduct(Product product) {
        datastore.save(product);
    }

    public List<Product> getAllProduct() {
        return datastore.find(Product.class).order("description").asList();
    }

}
