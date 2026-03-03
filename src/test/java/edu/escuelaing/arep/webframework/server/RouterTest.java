package edu.escuelaing.arep.webframework.server;

import edu.escuelaing.arep.webframework.http.Request;
import edu.escuelaing.arep.webframework.http.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Router class")
class RouterTest {

    @Test
    @DisplayName("Should register a GET route")
    void testRegisterGetRoute() {
        BiFunction<Request, Response, String> handler = (req, res) -> "Hello World";
        Router.get("/hello", handler);

        // Verify by handling a request
        Request request = new Request("GET", "/hello", null);
        String result = Router.handle(request);

        assertEquals("Hello World", result);
    }

    @Test
    @DisplayName("Should handle registered route")
    void testHandleRegisteredRoute() {
        BiFunction<Request, Response, String> handler = (req, res) -> "User profile";
        Router.get("/profile", handler);

        Request request = new Request("GET", "/profile", null);
        String result = Router.handle(request);

        assertNotNull(result);
        assertEquals("User profile", result);
    }

    @Test
    @DisplayName("Should return null for unregistered route")
    void testHandleUnregisteredRoute() {
        Request request = new Request("GET", "/unknown", null);
        String result = Router.handle(request);

        assertNull(result);
    }

    @Test
    @DisplayName("Should pass request and response to handler")
    void testHandlerReceivesRequestAndResponse() {
        BiFunction<Request, Response, String> handler = (req, res) -> {
            assertNotNull(req);
            assertNotNull(res);
            return "Path: " + req.getPath();
        };

        Router.get("/api/test", handler);

        Request request = new Request("GET", "/api/test", null);
        String result = Router.handle(request);

        assertEquals("Path: /api/test", result);
    }

    @Test
    @DisplayName("Should handle route with query parameters")
    void testRouteWithQueryParameters() {
        BiFunction<Request, Response, String> handler = (req, res) -> "User: " + req.getValues("username");

        Router.get("/user", handler);

        Request request = new Request("GET", "/user", "username=john&id=123");
        String result = Router.handle(request);

        assertEquals("User: john", result);
    }

    @Test
    @DisplayName("Should register multiple different routes")
    void testMultipleRoutes() {
        Router.get("/home", (req, res) -> "Home Page");
        Router.get("/about", (req, res) -> "About Page");
        Router.get("/contact", (req, res) -> "Contact Page");

        Request homeRequest = new Request("GET", "/home", null);
        Request aboutRequest = new Request("GET", "/about", null);
        Request contactRequest = new Request("GET", "/contact", null);

        assertEquals("Home Page", Router.handle(homeRequest));
        assertEquals("About Page", Router.handle(aboutRequest));
        assertEquals("Contact Page", Router.handle(contactRequest));
    }

    @Test
    @DisplayName("Should override existing route")
    void testOverrideRoute() {
        Router.get("/endpoint", (req, res) -> "First Version");
        String firstResult = Router.handle(new Request("GET", "/endpoint", null));

        Router.get("/endpoint", (req, res) -> "Second Version");
        String secondResult = Router.handle(new Request("GET", "/endpoint", null));

        assertEquals("First Version", firstResult);
        assertEquals("Second Version", secondResult);
    }

    @Test
    @DisplayName("Should execute handler logic correctly")
    void testHandlerLogicExecution() {
        BiFunction<Request, Response, String> handler = (req, res) -> {
            String name = req.getValues("name");
            return name != null ? "Hello " + name : "Hello Guest";
        };

        Router.get("/greet", handler);

        Request withName = new Request("GET", "/greet", "name=Alice");
        Request noName = new Request("GET", "/greet", null);

        assertEquals("Hello Alice", Router.handle(withName));
        assertEquals("Hello Guest", Router.handle(noName));
    }

    @Test
    @DisplayName("Should distinguish between different paths")
    void testPathDistinction() {
        Router.get("/api/v1/users", (req, res) -> "V1 Users");
        Router.get("/api/v2/users", (req, res) -> "V2 Users");

        Request v1 = new Request("GET", "/api/v1/users", null);
        Request v2 = new Request("GET", "/api/v2/users", null);

        assertEquals("V1 Users", Router.handle(v1));
        assertEquals("V2 Users", Router.handle(v2));
    }

    @Test
    @DisplayName("Should handle root path")
    void testRootPathRoute() {
        Router.get("/", (req, res) -> "Root");

        Request rootRequest = new Request("GET", "/", null);
        String result = Router.handle(rootRequest);

        assertEquals("Root", result);
    }
}
