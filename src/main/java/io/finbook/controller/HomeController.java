package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.model.InvoiceType;
import io.finbook.spark.ResponseCreator;

import java.util.HashMap;

public class HomeController {

    public static ResponseCreator index(boolean isLogged) {
        if (isLogged){
            HashMap<String, Object> data = new HashMap<>();
            data.put("logged", true);
            return MyResponse.ok(
                    new StandardResponse(data, "home/index")
            );
        }else {
            return MyResponse.ok(
                    new StandardResponse(null, "home/index")
            );
        }
    }

}
