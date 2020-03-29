package io.finbook.controller;

import com.google.gson.Gson;
import io.finbook.MyResponse;
import io.finbook.ResponseCreator;
import io.finbook.model.Product;
import io.finbook.service.ProductService;

import java.util.Map;

public class ProductController {

    static Gson gson = new Gson();
    private static ProductService productService = new ProductService();

    public static ResponseCreator create(String body) {
        Product product = gson.fromJson(body, Product.class);
        return MyResponse.created(productService.addProduct(product));
    }

    public static ResponseCreator getList() {
        return MyResponse.listRequest(gson.toJson(productService.getAllProduct()));
    }

}
