package io.finbook.controller;

import com.google.gson.Gson;
import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.http.StatusResponse;
import io.finbook.spark.ResponseCreator;
import io.finbook.model.Product;
import io.finbook.service.ProductService;

public class ProductController {

    private static ProductService productService = new ProductService();

    public static ResponseCreator create(String body) {
        Product product = new Gson().fromJson(body, Product.class);

        productService.addProduct(product);

        return MyResponse.ok(
                new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS)
                )
        );
    }

    public static ResponseCreator getList() {
        return MyResponse.ok(
                new Gson().toJson(
                        new StandardResponse(
                                StatusResponse.SUCCESS,
                                new Gson().toJsonTree(productService.getAllProduct()))
                )
        );
    }

}
