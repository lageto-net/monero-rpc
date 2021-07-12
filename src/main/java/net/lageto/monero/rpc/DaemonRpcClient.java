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
import net.lageto.monero.rpc.annotation.RpcParam;
import net.lageto.monero.rpc.model.BlockHeader;

import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a connection to a Monero P2P daemon.
 * <p>
 * See {@link #newInstance(URI)} to create a new instance.
 * </p>
 */
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
     * @see #getBlockCountAsync()
     */
    @RpcMethod(value = "get_block_count", body = "$.result.count")
    long getBlockCount();

    /**
     * Asynchronously look up how many blocks are in the longest chain known to the daemon.
     *
     * @return a future that completes to the number of blocks in the longest chain seen by the node
     * @see #getBlockCount()
     */
    @RpcMethod(value = "get_block_count", body = "$.result.count")
    CompletableFuture<Long> getBlockCountAsync();

    /**
     * Fetch a block's header by its hash.
     *
     * @param hash hash of the block to lookup, as a hex string
     * @return block header
     * @see #getBlockHeader(long)
     * @see #getBlockHeaderAsync(String)
     */
    @RpcMethod(value = "get_block_header_by_hash", body = "$.result.block_header")
    BlockHeader getBlockHeader(@RpcParam("hash") String hash);

    /**
     * Asynchronously fetch a block's header by its hash.
     *
     * @param hash hash of the block to lookup, as a hex string
     * @return a future that completes to the requested block header
     * @see #getBlockHeader(String)
     */
    @RpcMethod(value = "get_block_header_by_hash", body = "$.result.block_header")
    CompletableFuture<BlockHeader> getBlockHeaderAsync(@RpcParam("hash") String hash);

    /**
     * Fetch a block's header by its height.
     *
     * @param height height of the block to lookup
     * @return block header
     * @see #getBlockHeader(String)
     * @see #getBlockHeaderAsync(long)
     */
    @RpcMethod(value = "get_block_header_by_height", body = "$.result.block_header")
    BlockHeader getBlockHeader(@RpcParam("height") long height);

    /**
     * Asynchronously fetch a block's header by its height.
     *
     * @param height height of the block to lookup
     * @return a future that completes to the requested block header
     * @see #getBlockHeader(long)
     */
    @RpcMethod(value = "get_block_header_by_height", body = "$.result.block_header")
    CompletableFuture<BlockHeader> getBlockHeaderAsync(@RpcParam("height") long height);
}
