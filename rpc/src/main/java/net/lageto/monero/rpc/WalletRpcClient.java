package net.lageto.monero.rpc;

import net.lageto.monero.rpc.annotation.RpcMethod;
import net.lageto.monero.rpc.annotation.RpcParam;
import net.lageto.monero.rpc.model.TransferDestination;

import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.List;
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

    /**
     * Transfer Moneroj from this wallet to one or more recipients.
     * <p>
     * Note that this actually calls {@code transfer_split} which means that it may be split up into many transactions
     * if required.
     * </p>
     *
     * @param destinations list of recipients
     * @return list of transaction hashes
     */
    @RpcMethod(value = "transfer_split", body = "tx_hash_list")
    List<String> transfer(@RpcParam("destinations") List<TransferDestination> destinations);

    /**
     * Asynchronously transfer Moneroj from this wallet to one or more recipients.
     * <p>
     * Note that this actually calls {@code transfer_split} which means that it may be split up into many transactions
     * if required.
     * </p>
     *
     * @param destinations list of recipients
     * @return list of transaction hashes
     */
    @RpcMethod(value = "transfer_split", body = "tx_hash_list")
    CompletableFuture<List<String>> transferAsync(@RpcParam("destinations") List<TransferDestination> destinations);

    /**
     * Get the wallet's balance, including currently locked/un-spendable Moneroj.
     *
     * @return the wallet's balance, in atomic units. 1 moneroj = 1e12 atomic units
     * @see #getBalanceAsync()
     */
    @RpcMethod(value = "get_balance", body = "balance")
    long getBalance();

    /**
     * Asynchronously get the wallet's balance, including currently locked/un-spendable Moneroj.
     *
     * @return a future that completes to the wallet's balance, in atomic units. 1 moneroj = 1e12 atomic units
     */
    @RpcMethod(value = "get_balance", body = "balance")
    CompletableFuture<Long> getBalanceAsync();

    /**
     * Get the wallet's unlocked balance.
     *
     * @return the wallet's balance, in atomic units. 1 moneroj = 1e12 atomic units
     * @see #getUnlockedBalanceAsync()
     * @see #getBalance()
     */
    @RpcMethod(value = "get_balance", body = "unlocked_balance")
    long getUnlockedBalance();

    /**
     * Asynchronously get the wallet's unlocked balance.
     *
     * @return a future that completes to the wallet's balance, in atomic units. 1 moneroj = 1e12 atomic units
     * @see #getUnlockedBalance()
     * @see #getBalanceAsync()
     */
    @RpcMethod(value = "get_balance", body = "unlocked_balance")
    CompletableFuture<Long> getUnlockedBalanceAsync();
}
