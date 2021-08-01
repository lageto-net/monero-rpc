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

package net.lageto.monero.rpc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an un-mined Monero block.
 *
 * @see net.lageto.monero.rpc.DaemonRpcClient#getBlockTemplate(String, int)
 */
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
