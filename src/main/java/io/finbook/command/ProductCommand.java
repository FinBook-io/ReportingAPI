package io.finbook.command;

import com.google.gson.Gson;
import io.finbook.responses.MyResponse;
import io.finbook.responses.StandardResponse;
import io.finbook.sparkcontroller.ResponseCreator;
import io.finbook.model.Product;
import io.finbook.service.ProductService;

import java.util.HashMap;

public class ProductCommand {

    private static ProductService productService = new ProductService();

    public static ResponseCreator create(String body) {
        Product product = new Gson().fromJson(body, Product.class);
        productService.addProduct(product);
        return MyResponse.created(
                new StandardResponse(null, "home/products/list")
        );
    }

    public static ResponseCreator list() {
        HashMap<String, Object> data = new HashMap<>();

        try {
            data.put("products", productService.getAllProduct());
        } catch (Exception ex) {
            return MyResponse.internalServerError(
                    new StandardResponse(null, "home/errors/500")
            );
        }

        data.put("products", productService.getAllProduct());
        // new Gson().toJsonTree(productService.getAllProduct())
        return MyResponse.ok(
                new StandardResponse(data, "dashboard/products/list")
        );
    }

}
