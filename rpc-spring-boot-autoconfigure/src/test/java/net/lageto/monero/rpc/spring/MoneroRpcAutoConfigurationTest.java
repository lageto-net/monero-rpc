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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = MoneroRpcAutoConfiguration.class)
public class MoneroRpcAutoConfigurationTest {
    @Test
    public void testDaemonInContext(@Autowired DaemonRpcClient daemon) {
        assertNotNull(daemon);
    }

    @Test
    public void testWalletInContext(@Autowired WalletRpcClient wallet) {
        assertNotNull(wallet);
    }
}
