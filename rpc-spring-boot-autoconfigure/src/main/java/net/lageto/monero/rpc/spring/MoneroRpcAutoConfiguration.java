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

import net.lageto.monero.rpc.DaemonRpcClient;
import net.lageto.monero.rpc.WalletRpcClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({DaemonRpcClient.class, WalletRpcClient.class})
@EnableConfigurationProperties(MoneroRpcProperties.class)
public class MoneroRpcAutoConfiguration {
    private final MoneroRpcProperties properties;

    public MoneroRpcAutoConfiguration(MoneroRpcProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(DaemonRpcClient.class)
    public DaemonRpcClient daemonRpcClient() {
        return DaemonRpcClient.newInstance(properties.getDaemon());
    }

    @Bean
    @ConditionalOnMissingBean(WalletRpcClient.class)
    public WalletRpcClient walletRpcClient() {
        return WalletRpcClient.newInstance(properties.getWallet());
    }
}
