package io.finbook.controller;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class IndexController extends Controller {

    public IndexController() {
    }

    public static Route index = (Request request, Response response) ->{
        Map<String, Object> model = new HashMap<>();
        System.out.println((String) request.session().attribute("currentUser"));
        System.out.println(getSessionLocale(request));
        return ViewUtil.render(request, model, Path.Template.INDEX);
    };
}
