package io.finbook.service;

import com.mongodb.MongoClient;
import io.finbook.model.Product;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.List;

public class ProductService {

    MongoClient client = new MongoClient("localhost", 27017); //connect to mongodb
    Datastore datastore = new Morphia().createDatastore(client, "shopdb"); //select bills collection

    public String addProduct(Product product){
        datastore.save(product);
        return "Product added";
    }

    public List<Product> getAllProduct(){
        return datastore.find(Product.class).asList();
    }

}
