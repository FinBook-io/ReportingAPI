package io.finbook.spark;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import spark.Request;
import spark.Response;

public class Auth {

    public static ResponseCreator login(Request request, Response response) {
        if (isLogged(request)) {
            redirectTo(response, "/admin/dashboard");
        }
        return MyResponse.ok(
                new StandardResponse(null, "home/login/index")
        );
    }

    public static String initSession(Request request, Response response) {
        addSessionAttribute(request, "currentUserId", request.queryParams("dni"));
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
