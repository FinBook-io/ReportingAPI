package io.finbook.controller;

import com.google.gson.Gson;
import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.http.StatusResponse;
import io.finbook.spark.ResponseCreator;

public class IndexController {

    public static ResponseCreator index() {
        return MyResponse.ok(
                new StandardResponse(
                        StatusResponse.SUCCESS,
                        "INDEX Page")
        );
    }

}
