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

package net.lageto.monero.rpc.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties("monero.rpc")
public class MoneroRpcProperties {
    /**
     * The address of the daemon's JSON-RPC endpoint.
     */
    private URI daemon = URI.create("http://localhost:18081/json_rpc");

    /**
     * The address of the wallet's JSON-RPC endpoint.
     */
    private URI wallet = URI.create("http://localhost:18083/json_rpc");

    public URI getDaemon() {
        return daemon;
    }

    public void setDaemon(URI daemon) {
        this.daemon = daemon;
    }

    public URI getWallet() {
        return wallet;
    }

    public void setWallet(URI wallet) {
        this.wallet = wallet;
    }
}
