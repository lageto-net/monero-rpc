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

package net.lageto.monero.rpc.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpRequest;

public class JsonBodyPublisher {
    private JsonBodyPublisher() {
        throw new UnsupportedOperationException();
    }

    public static HttpRequest.BodyPublisher ofObject(ObjectMapper objectMapper, Object o) {
        try {
            return HttpRequest.BodyPublishers.ofByteArray(objectMapper.writeValueAsBytes(o));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static HttpRequest.BodyPublisher ofObject(Object o) {
        return ofObject(new ObjectMapper(), o);
    }
}
