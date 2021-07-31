package net.lageto.monero.rpc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Block(
        @JsonProperty("block_header") BlockHeader header,
        @JsonProperty("miner_tx_hash") String coinbaseHash,
        @JsonProperty("tx_hashes") List<String> transactionHashes
) {
}
