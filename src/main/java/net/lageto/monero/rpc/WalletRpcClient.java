package net.lageto.monero.rpc;

import net.lageto.monero.rpc.annotation.RpcMethod;

import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a connection to a Monero wallet.
 * <p>
 * See {@link #newInstance(URI)} to create a new instance.
 * </p>
 */
public interface WalletRpcClient {
    /**
     * Create a new instance of a {@code WalletRpcClient}
     *
     * @param address address of JSON-RPC endpoint, e.g. {@code URI.create("http://localhost:18083/json_rpc")}
     * @return new instance of {@code WalletRpcClient}
     */
    static WalletRpcClient newInstance(URI address) {
        return (WalletRpcClient) Proxy.newProxyInstance(
                WalletRpcClient.class.getClassLoader(),
                new Class<?>[]{WalletRpcClient.class},
                new RpcInvocationHandler(address)
        );
    }

    /**
     * Return the wallet's first address.
     *
     * @return address of wallet as base-58 encoded string
     * @see #getAddressAsync()
     */
    @RpcMethod(value = "get_address", body = "address")
    String getAddress();

    /**
     * Asynchronously get a wallet's first address.
     *
     * @return a future that completes to the wallet's first address
     * @see #getAddress()
     */
    @RpcMethod(value = "get_address", body = "address")
    CompletableFuture<String> getAddressAsync();
}
