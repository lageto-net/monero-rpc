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

/**
 * An exception representing a failed RPC request.
 */
public class RpcException extends RuntimeException {
    private final int code;

    RpcException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * The error code that caused this exception.
     *
     * @return error code, usually {@code -1}
     */
    public int getCode() {
        return code;
    }
}
