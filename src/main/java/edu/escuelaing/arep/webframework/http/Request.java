package edu.escuelaing.arep.webframework.http;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private String method;
    private String path;
    private Map<String, String> queryParams = new HashMap<>();

    public Request(String method, String path, String queryString) {
        this.method = method;
        this.path = path;

        if (queryString != null) {
            parseQuery(queryString);
        }
    }

    private void parseQuery(String queryString) {
        String[] pairs = queryString.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");

            if (keyValue.length == 2) {
                queryParams.put(keyValue[0], keyValue[1]);
            }
        }
    }

    public String getValues(String key) {
        return queryParams.get(key);
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }
}