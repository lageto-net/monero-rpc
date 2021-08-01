package net.lageto.monero.rpc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents a block in the blockchain.
 *
 * @see net.lageto.monero.rpc.DaemonRpcClient#getBlock(long)
 * @see net.lageto.monero.rpc.DaemonRpcClient#getBlock(String)
 */
public record Block(
        @JsonProperty("block_header") BlockHeader header,
        @JsonProperty("miner_tx_hash") String coinbaseHash,
        @JsonProperty("tx_hashes") List<String> transactionHashes
) {
}
