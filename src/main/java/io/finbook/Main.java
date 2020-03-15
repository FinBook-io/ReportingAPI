package io.finbook;

import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {
        get("/", (request, response) -> "Hello World!");
    }
}
