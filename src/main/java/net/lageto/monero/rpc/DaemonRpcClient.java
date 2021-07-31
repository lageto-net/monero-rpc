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
import net.lageto.monero.rpc.model.Block;
import net.lageto.monero.rpc.model.BlockHeader;
import net.lageto.monero.rpc.model.BlockTemplate;

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
     * Get a block header and the transaction hashes it contains by its height.
     *
     * @param height height of block to look up (0 based)
     * @return block containing header and transaction hashes
     * @see #getBlockAsync(long)
     */
    @RpcMethod(value = "get_block")
    Block getBlock(@RpcParam("height") long height);

    /**
     * Asynchronously get a block header and the transaction hashes it contains by its height.
     *
     * @param height height of block to look up (0 based)
     * @return a future that completes to block containing header and transaction hashes
     * @see #getBlock(long)
     */
    @RpcMethod(value = "get_block")
    CompletableFuture<Block> getBlockAsync(@RpcParam("height") long height);

    /**
     * Get a block header and the transaction hashes it contains by its hash.
     *
     * @param hash hash of block to lookup
     * @return block containing header and transaction hashes
     * @see #getBlock(long)
     * @see #getBlockAsync(String)
     */
    @RpcMethod(value = "get_block")
    Block getBlock(@RpcParam("hash") String hash);

    /**
     * Asynchronously get a block header and the transaction hashes it contains by its hash.
     *
     * @param hash hash of block to lookup
     * @return a future that completes to block containing header and transaction hashes
     * @see #getBlockAsync(long)
     * @see #getBlock(String)
     */
    @RpcMethod(value = "get_block")
    CompletableFuture<Block> getBlockAsync(@RpcParam("hash") String hash);

    /**
     * Look up how many blocks are in the longest chain known to the daemon.
     *
     * @return number of blocks in longest chain seen by the node
     * @see #getBlockCountAsync()
     */
    @RpcMethod(value = "get_block_count", body = "count")
    long getBlockCount();

    /**
     * Asynchronously look up how many blocks are in the longest chain known to the daemon.
     *
     * @return a future that completes to the number of blocks in the longest chain seen by the node
     * @see #getBlockCount()
     */
    @RpcMethod(value = "get_block_count", body = "count")
    CompletableFuture<Long> getBlockCountAsync();

    /**
     * Fetch a block's header by its hash.
     *
     * @param hash hash of the block to lookup, as a hex string
     * @return block header
     * @throws RpcException for malformed or non-existent block hash
     * @see #getBlockHeader(long)
     * @see #getBlockHeaderAsync(String)
     */
    @RpcMethod(value = "get_block_header_by_hash", body = "block_header")
    BlockHeader getBlockHeader(@RpcParam("hash") String hash);

    /**
     * Asynchronously fetch a block's header by its hash.
     *
     * @param hash hash of the block to lookup, as a hex string
     * @return a future that completes to the requested block header
     * @see #getBlockHeader(String)
     */
    @RpcMethod(value = "get_block_header_by_hash", body = "block_header")
    CompletableFuture<BlockHeader> getBlockHeaderAsync(@RpcParam("hash") String hash);

    /**
     * Fetch a block's header by its height.
     *
     * @param height height of the block to lookup
     * @return block header
     * @throws RpcException for non-existent block height
     * @see #getBlockHeader(String)
     * @see #getBlockHeaderAsync(long)
     */
    @RpcMethod(value = "get_block_header_by_height", body = "block_header")
    BlockHeader getBlockHeader(@RpcParam("height") long height);

    /**
     * Asynchronously fetch a block's header by its height.
     *
     * @param height height of the block to lookup
     * @return a future that completes to the requested block header
     * @see #getBlockHeader(long)
     */
    @RpcMethod(value = "get_block_header_by_height", body = "block_header")
    CompletableFuture<BlockHeader> getBlockHeaderAsync(@RpcParam("height") long height);

    /**
     * Create a block template.
     *
     * @param walletAddress wallet address to send mining reward to
     * @param reserveSize   number of bytes to reserve for extra nonce in miner reward tx
     * @return a new block template
     * @see #getBlockTemplateAsync(String, int)
     */
    @RpcMethod(value = "get_block_template")
    BlockTemplate getBlockTemplate(@RpcParam("wallet_address") String walletAddress,
                                   @RpcParam("reserve_size") int reserveSize);

    /**
     * Asynchronously create a block template.
     *
     * @param walletAddress wallet address to send mining reward to
     * @param reserveSize   number of bytes to reserve for extra nonce in miner reward tx
     * @return a future that completes to a new block template
     * @see #getBlockTemplate(String, int)
     */
    @RpcMethod(value = "get_block_template")
    CompletableFuture<BlockTemplate> getBlockTemplateAsync(@RpcParam("wallet_address") String walletAddress,
                                                           @RpcParam("reserve_size") int reserveSize);

    /**
     * Send a mined block for broadcast to the network.
     *
     * @param blob hex encoded block blob
     */
    @RpcMethod(value = "submit_block", array = true)
    void submitBlock(String blob);

    /**
     * Asynchronously send a mined block for broadcast to the network.
     *
     * @param blob hex encoded block blob
     * @return a future that completes when the call is finished
     */
    @RpcMethod(value = "submit_block", array = true)
    CompletableFuture<Void> submitBlockAsync(String blob);
}
