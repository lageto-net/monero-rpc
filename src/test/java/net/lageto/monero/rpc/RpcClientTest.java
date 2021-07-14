package net.lageto.monero.rpc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.matchers.MatchType;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.net.URI;
import java.util.Map;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

@ExtendWith(MockServerExtension.class)
@ExtendWith(TestDataParameterResolver.class)
public abstract class RpcClientTest {
    private final MockServerClient http;

    public RpcClientTest(MockServerClient http) {
        this.http = http;
    }

    public static HttpResponse jsonrpcError(int code, String message) {
        return response()
                .withBody(json(Map.of(
                        "jsonrpc", "2.0",
                        "id", 0,
                        "error", Map.of(
                                "code", code,
                                "message", message
                        )
                )));
    }

    public static HttpResponse jsonrpcResponse(Object result) {
        return response()
                .withBody(json(Map.of(
                        "jsonrpc", "2.0",
                        "id", 0,
                        "result", result
                )));
    }

    public static HttpRequest jsonrpcRequest(String method) {
        return request("/json_rpc")
                .withMethod("POST")
                .withBody(json(Map.of(
                        "method", method
                ), MatchType.ONLY_MATCHING_FIELDS));
    }

    public static HttpRequest jsonrpcRequest(String method, Object expectedParams) {
        return request("/json_rpc")
                .withMethod("POST")
                .withBody(json(Map.of(
                        "method", method,
                        "params", expectedParams
                ), MatchType.ONLY_MATCHING_FIELDS));
    }

    @BeforeEach
    public void reset() {
        http.reset();
    }

    protected URI getAddress() {
        return URI.create(String.format("http://localhost:%d/json_rpc", http.getPort()));
    }

    protected MockServerClient getHttp() {
        return this.http;
    }
}
