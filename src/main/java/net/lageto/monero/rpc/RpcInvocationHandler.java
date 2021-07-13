/*
 * Copyright (c) 2021 Lageto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.lageto.monero.rpc;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPathException;
import net.lageto.monero.rpc.annotation.RpcMethod;
import net.lageto.monero.rpc.annotation.RpcParam;
import net.lageto.monero.rpc.http.JsonBodyHandler;
import net.lageto.monero.rpc.http.JsonBodyPublisher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

class RpcInvocationHandler implements InvocationHandler {
    private final URI uri;
    private final HttpClient httpClient;

    RpcInvocationHandler(URI uri) {
        this.uri = uri;
        httpClient = HttpClient.newHttpClient();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // If a method is not annotated with `@RpcMethod', just pass the call through.
        if (!method.isAnnotationPresent(RpcMethod.class)) {
            return method.invoke(proxy, args);
        }

        RpcMethod rpcMethod = method.getAnnotation(RpcMethod.class);

        final HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(uri)
                        .POST(JsonBodyPublisher.ofObject(createBody(rpcMethod, method, args)))
                        .build();

        if (method.getReturnType().equals(CompletableFuture.class)) {
            var returnType = (Class<?>) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
            return httpClient.sendAsync(request, new JsonBodyHandler())
                    .thenApply(HttpResponse::body)
                    .thenApply(this::checkResponseBody)
                    .thenApply(body -> body.read(rpcMethod.body(), returnType));
        } else {
            final HttpResponse<DocumentContext> response = httpClient.send(request, new JsonBodyHandler());
            return checkResponseBody(response.body()).read(rpcMethod.body(), method.getReturnType());
        }
    }

    private DocumentContext checkResponseBody(DocumentContext body) {
        // JsonPath does not allow you to check if a key exists, so just try to read the error - if it throws an
        // exception, the error is not present.
        try {
            body.read("$.error");
            throw new RpcException(
                    body.read("$.error.code"),
                    body.read("$.error.message")
            );
        } catch (JsonPathException e) {
            return body;
        }
    }

    private RpcRequest<?> createBody(RpcMethod rpcMethod, Method method, Object[] args) {
        if (method.getParameterCount() == 0) {
            return new RpcRequest<>(rpcMethod.value(), null);
        }

        var params = new HashMap<String, Object>();
        for (int i = 0; i < args.length; i++) {
            String paramName = Arrays.stream(method.getParameterAnnotations()[i])
                    .filter(a -> a.annotationType().equals(RpcParam.class))
                    .findFirst()
                    .map(a -> ((RpcParam) a).value())
                    .orElseThrow(RuntimeException::new);

            params.put(paramName, args[i]);
        }

        return new RpcRequest<>(rpcMethod.value(), params);
    }
}
