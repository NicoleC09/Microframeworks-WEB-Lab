package edu.escuelaing.arep.webframework.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Request class")
class RequestTest {

    private Request request;

    @BeforeEach
    void setUp() {
        request = new Request("GET", "/api/users", "name=John&age=30");
    }

    @Test
    @DisplayName("Should create a request with valid method, path and query parameters")
    void testRequestCreation() {
        assertNotNull(request);
        assertEquals("GET", request.getMethod());
        assertEquals("/api/users", request.getPath());
    }

    @Test
    @DisplayName("Should return the correct HTTP method")
    void testGetMethod() {
        assertEquals("GET", request.getMethod());
    }

    @Test
    @DisplayName("Should return the correct path")
    void testGetPath() {
        assertEquals("/api/users", request.getPath());
    }

    @Test
    @DisplayName("Should retrieve query parameter values by key")
    void testGetValues() {
        assertEquals("John", request.getValues("name"));
        assertEquals("30", request.getValues("age"));
    }

    @Test
    @DisplayName("Should return null for non-existent query parameters")
    void testGetValuesNonExistent() {
        assertNull(request.getValues("email"));
        assertNull(request.getValues("notFound"));
    }

    @Test
    @DisplayName("Should handle null query string")
    void testNullQueryString() {
        Request requestNoQuery = new Request("POST", "/api/login", null);
        assertNull(requestNoQuery.getValues("username"));
        assertEquals("POST", requestNoQuery.getMethod());
        assertEquals("/api/login", requestNoQuery.getPath());
    }

    @Test
    @DisplayName("Should handle empty query string")
    void testEmptyQueryString() {
        Request requestEmptyQuery = new Request("GET", "/api/products", "");
        assertNull(requestEmptyQuery.getValues("id"));
        assertEquals("GET", requestEmptyQuery.getMethod());
    }

    @Test
    @DisplayName("Should handle single query parameter")
    void testSingleQueryParameter() {
        Request singleParam = new Request("GET", "/api/search", "q=java");
        assertEquals("java", singleParam.getValues("q"));
        assertNull(singleParam.getValues("filter"));
    }

    @Test
    @DisplayName("Should handle query parameters with special characters")
    void testQueryParametersWithSpecialChars() {
        Request specialParams = new Request("GET", "/api/search", "q=hello%20world&tag=java-programming");
        assertEquals("hello%20world", specialParams.getValues("q"));
        assertEquals("java-programming", specialParams.getValues("tag"));
    }

    @Test
    @DisplayName("Should handle POST request")
    void testPostRequest() {
        Request postRequest = new Request("POST", "/api/data", "id=123&status=active");
        assertEquals("POST", postRequest.getMethod());
        assertEquals("/api/data", postRequest.getPath());
        assertEquals("123", postRequest.getValues("id"));
        assertEquals("active", postRequest.getValues("status"));
    }

    @Test
    @DisplayName("Should handle multiple query parameters")
    void testMultipleQueryParameters() {
        Request multiRequest = new Request("GET", "/search", "q=test&page=1&limit=10&sort=desc");
        assertEquals("test", multiRequest.getValues("q"));
        assertEquals("1", multiRequest.getValues("page"));
        assertEquals("10", multiRequest.getValues("limit"));
        assertEquals("desc", multiRequest.getValues("sort"));
    }
}
