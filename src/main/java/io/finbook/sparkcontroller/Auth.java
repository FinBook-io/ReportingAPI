package io.finbook.sparkcontroller;

import io.finbook.TextGenerator;
import io.finbook.Verifier;
import io.finbook.responses.MyResponse;
import io.finbook.responses.StandardResponse;
import io.finbook.util.JSONParser;
import io.finbook.util.Path;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

public class Auth {

    public static ResponseCreator signin(Request request, Response response) {
        if (isLogged(request)) {
            redirectTo(response, Path.AdminRoutes.ADMIN + Path.AdminRoutes.DASHBOARD);
        }
        return MyResponse.ok(
                new StandardResponse(null, Path.Template.HOME_LOGIN_INDEX)
        );
    }

    public static ResponseCreator sign(Request request, Response response) {
        /*if (isLogged(request)) {
            redirectTo(response, Path.AdminRoutes.ADMIN + Path.AdminRoutes.DASHBOARD);
        }*/

        Map<String, Object> data = new HashMap<>();
        data.put("textToSign", TextGenerator.generateRandomText());

        return MyResponse.ok(
                new StandardResponse(data, Path.Template.HOME_LOGIN_SIGN)
        );
    }

    public static String initSession(Request request, Response response) {
        // addSessionAttribute(request, "currentUserId", request.queryParams("signID"));
        String id = "11111111H";

        addSessionAttribute(request, "currentUserId", id);
        addSessionAttribute(request, "logged", true);
        redirectTo(response, Path.AdminRoutes.ADMIN + Path.AdminRoutes.DASHBOARD);
        return null;
    }

    public static JSONObject initCertificateSession(Request request){
        HashMap<String, Object> data = new HashMap<>();

        JSONParser jsonParser = new JSONParser(request.queryParams("firmaResponse"));
        byte[] textToValidate = jsonParser.getByteArray("sign");

        Verifier verifier = new Verifier(textToValidate);
        String id = verifier.validateSign();

        if (id != null){
            addSessionAttribute(request, "currentUserId", id);
            addSessionAttribute(request, "logged", true);
            data.put("okay", true);
            data.put("goInside", Path.AdminRoutes.ADMIN + Path.AdminRoutes.DASHBOARD);
        }

        return new JSONObject(data);
    }

    public static String signout(Request request, Response response) {
        removeSessionAttribute(request, "currentUserId");
        removeSessionAttribute(request, "logged");
        redirectTo(response, Path.HomeRoutes.INDEX);
        return null;
    }

    public static void authFilter(Request request, Response response) {
        if (!isLogged(request)) {
            redirectTo(response, Path.AuthRoutes.AUTH + Path.AuthRoutes.SIGN_IN);
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
