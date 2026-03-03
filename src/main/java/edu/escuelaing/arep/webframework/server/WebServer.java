package edu.escuelaing.arep.webframework.server;

import edu.escuelaing.arep.webframework.http.Request;
import edu.escuelaing.arep.webframework.framework.SparkLite;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

public class WebServer {

    private static final int PORT = 35000;

    public static void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server running on http://localhost:" + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleClient(clientSocket);
        }
    }

    private static void handleClient(Socket clientSocket) {

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out = clientSocket.getOutputStream()) {

            String requestLine = in.readLine();

            if (requestLine == null || requestLine.isEmpty()) {
                return;
            }

            System.out.println("Request Line: " + requestLine);

            String[] parts = requestLine.split(" ");
            String method = parts[0];
            String fullPath = parts[1];

            String path;
            String queryString = null;

            if (fullPath.contains("?")) {
                path = fullPath.substring(0, fullPath.indexOf("?"));
                queryString = fullPath.substring(fullPath.indexOf("?") + 1);
            } else {
                path = fullPath;
            }

            Request request = new Request(method, path, queryString);

            byte[] responseBodyBytes;
            String contentType = "text/plain";
            int statusCode = 200;

            String restResponse = Router.handle(request);

            if (restResponse != null) {

                responseBodyBytes = restResponse.getBytes();
                contentType = "text/plain";

            } else {

                FileResponse fileResponse = serveStaticFile(path);

                if (fileResponse != null) {
                    responseBodyBytes = fileResponse.content;
                    contentType = fileResponse.contentType;
                } else {
                    String notFound = "404 Not Found";
                    responseBodyBytes = notFound.getBytes();
                    statusCode = 404;
                }
            }

            String statusText = (statusCode == 200) ? "OK" : "Not Found";

            String headers = "HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
                    "Content-Type: " + contentType + "\r\n" +
                    "Content-Length: " + responseBodyBytes.length + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n";

            out.write(headers.getBytes());
            out.write(responseBodyBytes);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class FileResponse {
        byte[] content;
        String contentType;

        FileResponse(byte[] content, String contentType) {
            this.content = content;
            this.contentType = contentType;
        }
    }

    private static FileResponse serveStaticFile(String path) {

        try {

            if (path.equals("/")) {
                path = "/index.html";
            }

            String staticFolder = SparkLite.getStaticFolder();

            if (staticFolder == null) {
                return null;
            }

            String filePath = "target/classes" + staticFolder + path;
            File file = new File(filePath);

            if (!file.exists()) {
                return null;
            }

            byte[] fileBytes = Files.readAllBytes(file.toPath());

            String contentType = getContentType(path);

            return new FileResponse(fileBytes, contentType);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getContentType(String path) {

        if (path.endsWith(".html"))
            return "text/html";

        if (path.endsWith(".css"))
            return "text/css";

        if (path.endsWith(".js"))
            return "application/javascript";

        if (path.endsWith(".json"))
            return "application/json";

        if (path.endsWith(".png"))
            return "image/png";

        if (path.endsWith(".jpg") || path.endsWith(".jpeg"))
            return "image/jpeg";

        if (path.endsWith(".gif"))
            return "image/gif";

        if (path.endsWith(".svg"))
            return "image/svg+xml";

        return "text/plain";
    }
}