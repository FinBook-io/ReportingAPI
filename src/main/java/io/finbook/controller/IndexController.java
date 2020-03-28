package io.finbook.controller;

import io.finbook.MyResponse;
import io.finbook.ResponseCreator;

public class IndexController {

    public static ResponseCreator index() {
        return MyResponse.ok("{\"message\": \"INDEX - Welcome page\"}");
    }

    public static ResponseCreator create() {
        return MyResponse.ok("{\"message\": \"CREATE page\"}");
    }

    public static ResponseCreator getList() {
        return MyResponse.ok("{\"message\": \"GET-LIST page\"}");
    }

    public static ResponseCreator getById(String id) {
        return MyResponse.ok("{" +
                "\"id\": id," +
                "\"message\": \"GET-BY-ID page\"}");
    }

}
