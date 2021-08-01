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
import net.lageto.monero.rpc.DaemonRpcClient;

/**
 * @see DaemonRpcClient#getBlockHeader(String)
 * @see DaemonRpcClient#getBlockHeader(long)
 */
public record BlockHeader(
        @JsonProperty("block_size") int blockSize,
        long depth,
        long difficulty,
        String hash,
        long height,
        @JsonProperty("major_version") int majorVersion,
        @JsonProperty("minor_version") int minorVersion,
        long nonce,
        @JsonProperty("orphan_status") boolean orphanStatus,
        @JsonProperty("prev_hash") String prevHash,
        long reward,
        long timestamp
) {
}
