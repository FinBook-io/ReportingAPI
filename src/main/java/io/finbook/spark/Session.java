package io.finbook.spark;

import javax.servlet.http.HttpSession;

public class Session {

    HttpSession session;

    private static boolean logged;
    private static String id;

    public static void addSession(String id) {
        Session.logged = true;
        Session.id = id;
    }

    public static void removeSession() {
        Session.logged = false;
        Session.id = null;
    }

    public static boolean isLogged() {
        return logged;
    }

    public static String getId() {
        return id;
    }
}
