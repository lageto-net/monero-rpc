package net.lageto.monero.rpc.model;

import net.lageto.monero.rpc.WalletRpcClient;

import java.util.List;

/**
 * Represents a destination when transferring Moneroj.
 *
 * @param address recipient's address
 * @param amount  transfer amount in atomic units. 1 moneroj = 1e12 atomic units
 * @see WalletRpcClient#transfer(List)
 * @see WalletRpcClient#transferAsync(List)
 */
public record TransferDestination(
        String address,
        long amount
) {
}
