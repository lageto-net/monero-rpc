package net.lageto.monero.rpc;

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
}
