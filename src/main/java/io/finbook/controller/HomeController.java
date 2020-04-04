package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.model.InvoiceType;
import io.finbook.spark.ResponseCreator;

import java.util.HashMap;

public class HomeController {

    public static ResponseCreator home() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("firstName", "Kevin");
        return MyResponse.ok(
                new StandardResponse(data, "home/index")
        );
    }

    public static ResponseCreator dashboard() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("firstName", "Kevin");
        return MyResponse.ok(
                new StandardResponse(data, "dashboard/index")
        );
    }

}
