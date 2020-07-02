package io.finbook.sparkcontroller;

import io.finbook.responses.MyResponse;
import io.finbook.responses.StandardResponse;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

public class Auth {

    public static ResponseCreator login(Request request, Response response) {
        if (isLogged(request)) {
            redirectTo(response, "/admin/dashboard");
        }
        return MyResponse.ok(
                new StandardResponse(null, "home/login/index")
        );
    }

    public static ResponseCreator sign(Request request, Response response) {
        if (isLogged(request)) {
            redirectTo(response, "/admin/dashboard");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("textToSign", "testTextToSign");

        return MyResponse.ok(
                new StandardResponse(data, "home/login/sign")
        );
    }

    public static String initSession(Request request, Response response) {
        addSessionAttribute(request, "currentUserId", request.queryParams("signID"));
        addSessionAttribute(request, "logged", true);
        redirectTo(response, "/admin/dashboard");
        return null;
    }

    public static String logout(Request request, Response response) {
        removeSessionAttribute(request, "currentUserId");
        removeSessionAttribute(request, "logged");
        redirectTo(response, "/");
        return null;
    }

    public static void authFilter(Request request, Response response) {
        if (!isLogged(request)) {
            redirectTo(response, "/auth/login");
            halt(401, "Go Away!");
        }
    }

    public static boolean isLogged(Request request) {
        return request.session().attribute("logged") != null;
    }

    public static String getCurrentUserId(Request request) {
        return request.session().attribute("currentUserId");
    }

    private static void addSessionAttribute(Request request, String key, Object value) {
        request.session().attribute(key, value);
    }

    private static void removeSessionAttribute(Request request, String key) {
        request.session().removeAttribute(key);
    }

    private static void redirectTo(Response response, String route) {
        response.redirect(route);
    }

}
