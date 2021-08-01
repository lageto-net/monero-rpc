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

package net.lageto.monero.rpc;

import net.lageto.monero.rpc.model.TransferDestination;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletRpcClientTest extends RpcClientTest {
    private final WalletRpcClient wallet;

    public WalletRpcClientTest(MockServerClient http) {
        super(http);
        wallet = WalletRpcClient.newInstance(getAddress());
    }

    @Test
    public void testGetAddress(@WalletAddress String walletAddress) {
        getHttp()
                .when(jsonrpcRequest("get_address"))
                .respond(jsonrpcResponse(Map.of(
                        "address", walletAddress,
                        "addresses", List.of(Map.of(
                                "address", walletAddress,
                                "address_index", 0,
                                "label", "",
                                "used", true
                        ))
                )));

        assertEquals(walletAddress, wallet.getAddress());
        wallet.getAddressAsync()
                .thenAccept(actual -> assertEquals(walletAddress, actual))
                .join();
    }

    @Test
    public void testTransfer(@WalletAddress String walletAddress) {
        var tx = "4adcdc1af3f665770cdf8fb7a380887cd07ac53c2b771bd18df5ca375d5e7540";

        getHttp()
                .when(jsonrpcRequest("transfer_split", Map.of(
                        "destinations", List.of(Map.of(
                                "address", walletAddress,
                                "amount", 350
                        ))
                )))
                .respond(jsonrpcResponse(Map.of(
                        "amount_list", List.of(350),
                        "fee_list", List.of(1),
                        "multisig_txset", "",
                        "tx_hash_list", List.of(tx),
                        "tx_key_list", List.of("5b455c0f97168be652a2c03c5c68a064bb84cdae4ddef01b5c48d73a0bbb27075" +
                                "fb714f2ca19ea6c8ff592417e606addea6deb1d6530e2969f75681ffcbfc4075677b94a8c9197963ae38" +
                                "fa6f543ee68f0a4c4bbda4c453f39538f00b28e980ea08509730b51c004960101ba2f3adbc34cbbdff0d" +
                                "5af9dba061b523090debd06"),
                        "unsigned_txset", ""
                )));

        var destinations = List.of(new TransferDestination(walletAddress, 350));

        assertEquals(List.of(tx), wallet.transfer(destinations));
        wallet.transferAsync(destinations)
                .thenAccept(hashes -> assertEquals(List.of(tx), hashes))
                .join();
    }

    @Test
    public void testGetBalance(@WalletAddress String walletAddress) {
        getHttp()
                .when(jsonrpcRequest("get_balance"))
                .respond(jsonrpcResponse(Map.of(
                        "balance", 1234,
                        "multisig_import_needed", false,
                        "unlocked_balance", 123,
                        "per_subaddress", List.of(Map.of(
                                "address", walletAddress,
                                "address_index", 0,
                                "balance", 1234,
                                "unlocked_balance", 123,
                                "label", "",
                                "num_unspent_outputs", 2
                        ))
                )));

        assertEquals(1234, wallet.getBalance());
        wallet.getBalanceAsync()
                .thenAccept(balance -> assertEquals(1234, balance))
                .join();

        assertEquals(123, wallet.getUnlockedBalance());
        wallet.getUnlockedBalanceAsync()
                .thenAccept(unlockedBalance -> assertEquals(123, unlockedBalance))
                .join();
    }
}
