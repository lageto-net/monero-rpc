package net.lageto.monero.rpc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BlockTemplate(
        @JsonProperty("blockhashing_blob") String hashingBlob,
        @JsonProperty("blocktemplate_blob") String blob,
        @JsonProperty("difficulty") long difficulty,
        @JsonProperty("expected_reward") long expectedReward,
        @JsonProperty("height") long height,
        @JsonProperty("next_seed_hash") String nextSeedHash,
        @JsonProperty("prev_hash") String prevHash,
        @JsonProperty("reserved_offset") int reservedOffset,
        @JsonProperty("seed_hash") String seedHash,
        @JsonProperty("seed_height") long seedHeight
) {

}
