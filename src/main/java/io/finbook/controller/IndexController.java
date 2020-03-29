package io.finbook.controller;

import io.finbook.MyResponse;
import io.finbook.ResponseCreator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexController {

    public static ResponseCreator index() {
        Map<Object, Object> body = new HashMap<>();
        body.put("message", "INDEX - Welcome page");

        // return MyResponse.ok("{\"message\": \"INDEX - Welcome page\"}");
        return MyResponse.ok(body);
    }

    public static ResponseCreator create() {
        Map<Object, Object> body = new HashMap<>();
        body.put("message", "CREATE page");

        // return MyResponse.ok("{\"message\": \"CREATE page\"}");
        return MyResponse.ok(body);
    }

    public static ResponseCreator getList() {
        Map<Object, Object> body = new HashMap<>();
        body.put("message", "GET-LIST page");

        // return MyResponse.ok("{\"message\": \"GET-LIST page\"}");
        return MyResponse.ok(body);
    }

    public static ResponseCreator getById(String id) {
        Map<Object, Object> body = new HashMap<>();
        body.put("id", id);
        body.put("message", "GET-BY-ID page");

        // return MyResponse.ok("{" +"\"id\": " + id + "," + "\"message\": \"GET-BY-ID page\"}");
        return MyResponse.ok(body);
    }

}
