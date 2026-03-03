package edu.escuelaing.arep.webframework.server;

import edu.escuelaing.arep.webframework.http.Request;
import edu.escuelaing.arep.webframework.http.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Router {

    private static Map<String, BiFunction<Request, Response, String>> getRoutes = new HashMap<>();

    public static void get(String path, BiFunction<Request, Response, String> handler) {
        getRoutes.put(path, handler);
    }

    public static String handle(Request request) {

        BiFunction<Request, Response, String> handler = getRoutes.get(request.getPath());

        if (handler != null) {

            Response response = new Response();

            return handler.apply(request, response);
        }

        return null;
    }
}