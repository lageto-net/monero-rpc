package net.lageto.monero.rpc.model;

import java.util.List;

/**
 * Represents a destination when transferring Moneroj.
 *
 * @param address recipient's address
 * @param amount  transfer amount in atomic units. 1 moneroj = 1e12 atomic units
 * @see net.lageto.monero.rpc.WalletRpcClient#transfer(List)
 * @see net.lageto.monero.rpc.WalletRpcClient#transferAsync(List)
 */
public record TransferDestination(
        String address,
        long amount
) {
}
