package edu.escuelaing.arep.webframework;

import edu.escuelaing.arep.webframework.framework.SparkLite;
import edu.escuelaing.arep.webframework.server.WebServer;

public class Main {

    public static void main(String[] args) throws Exception {

        SparkLite.staticfiles("webroot");

        SparkLite.get("/App/hello", (req, res) -> "Hello " + req.getValues("name"));

        SparkLite.get("/App/pi", (req, res) -> String.valueOf(Math.PI));

        WebServer.start();
    }
}