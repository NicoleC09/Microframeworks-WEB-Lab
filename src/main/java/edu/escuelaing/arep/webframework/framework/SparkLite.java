package edu.escuelaing.arep.webframework.framework;

import edu.escuelaing.arep.webframework.server.Router;
import edu.escuelaing.arep.webframework.http.Request;
import edu.escuelaing.arep.webframework.http.Response;

import java.util.function.BiFunction;

public class SparkLite {

    private static String staticFolder;

    public static void staticfiles(String folder) {

        if (folder == null || folder.isEmpty()) {
            throw new IllegalArgumentException("Static folder cannot be null or empty");
        }

        // Asegurar que siempre empiece con /
        if (!folder.startsWith("/")) {
            folder = "/" + folder;
        }

        staticFolder = folder;
    }

    public static String getStaticFolder() {
        return staticFolder;
    }

    public static void get(String path,
            BiFunction<Request, Response, String> handler) {

        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }

        Router.get(path, handler);
    }
}