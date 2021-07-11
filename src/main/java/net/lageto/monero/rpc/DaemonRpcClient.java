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

import net.lageto.monero.rpc.annotation.RpcMethod;

import java.lang.reflect.Proxy;
import java.net.URI;

public interface DaemonRpcClient {
    /**
     * Create a new instance of a {@code DaemonRpcClient}
     *
     * @param address address of JSON-RPC endpoint, e.g. {@code URI.create("http://localhost:18081/json_rpc")}
     * @return new instance of {@code DaemonRpcClient}
     */
    static DaemonRpcClient newInstance(URI address) {
        return (DaemonRpcClient) Proxy.newProxyInstance(
                DaemonRpcClient.class.getClassLoader(),
                new Class<?>[]{DaemonRpcClient.class},
                new RpcInvocationHandler(address)
        );
    }

    /**
     * Look up how many blocks are in the longest chain known to the daemon.
     *
     * @return number of blocks in longest chain seen by the node
     */
    @RpcMethod(value = "get_block_count", body = "$.result.count")
    long getBlockCount();
}
