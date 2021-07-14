package net.lageto.monero.rpc;

import net.lageto.monero.rpc.model.BlockHeader;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DaemonRpcClientTest extends RpcClientTest {
    private final DaemonRpcClient daemon;

    public DaemonRpcClientTest(MockServerClient http) {
        super(http);
        daemon = DaemonRpcClient.newInstance(getAddress());
    }

    @Test
    public void testGetBlockCount() {
        getHttp()
                .when(jsonrpcRequest("get_block_count"))
                .respond(jsonrpcResponse(Map.of(
                        "count", 2404765L,
                        "status", "OK",
                        "untrusted", false
                )));
        assertEquals(2404765L, daemon.getBlockCount());
        daemon.getBlockCountAsync()
                .thenAccept(blockCount -> assertEquals(2404765L, blockCount))
                .join();
    }

    @Test
    public void testGetBlockHeader(BlockHeader blockHeader) {
        Map<String, Object> response = Map.ofEntries(
                Map.entry("block_size", blockHeader.blockSize()),
                Map.entry("block_weight", 128172),
                Map.entry("cumulative_difficulty", 119360895398449138L),
                Map.entry("cumulative_difficulty_top64", 0),
                Map.entry("depth", blockHeader.depth()),
                Map.entry("difficulty", blockHeader.difficulty()),
                Map.entry("difficulty_top64", 0),
                Map.entry("hash", blockHeader.hash()),
                Map.entry("height", blockHeader.height()),
                Map.entry("long_term_weight", 128172),
                Map.entry("major_version", blockHeader.majorVersion()),
                Map.entry("miner_tx_hash", "39bbc397e0d74dad27010342abfabc0cd05ec986cf541b5d9b87422076d55a93"),
                Map.entry("minor_version", blockHeader.minorVersion()),
                Map.entry("nonce", blockHeader.nonce()),
                Map.entry("num_txes", 70),
                Map.entry("orphan_status", blockHeader.orphanStatus()),
                Map.entry("pow_hash", ""),
                Map.entry("prev_hash", blockHeader.prevHash()),
                Map.entry("reward", blockHeader.reward()),
                Map.entry("timestamp", blockHeader.timestamp()),
                Map.entry("wide_cumulative_difficulty", "0x1a80e1a1a4c0ff2"),
                Map.entry("wide_difficulty", "0x4afeedcd38")
        );
        getHttp()
                .when(jsonrpcRequest("get_block_header_by_height", Map.of("height", blockHeader.height())))
                .respond(jsonrpcResponse(Map.of("block_header", response)));
        getHttp()
                .when(jsonrpcRequest("get_block_header_by_hash", Map.of("hash", blockHeader.hash())))
                .respond(jsonrpcResponse(Map.of("block_header", response)));

        assertEquals(blockHeader, daemon.getBlockHeader(blockHeader.height()));
        daemon.getBlockHeaderAsync(blockHeader.height())
                .thenAccept(actual -> assertEquals(blockHeader, actual))
                .join();

        assertEquals(blockHeader, daemon.getBlockHeader(blockHeader.hash()));
        daemon.getBlockHeaderAsync(blockHeader.hash())
                .thenAccept(actual -> assertEquals(blockHeader, actual))
                .join();
    }

    @Test
    public void testGetBlockHeaderInvalid() {
        getHttp()
                .when(jsonrpcRequest("get_block_header_by_height", Map.of("height", "${json-unit.any-number}")))
                .respond(jsonrpcError(-2, "Requested block height: 1 greater than current top block height: 0"));
        getHttp()
                .when(jsonrpcRequest("get_block_header_by_hash", Map.of("hash", "${json-unit.any-string}")))
                .respond(jsonrpcError(-5, "Internal error: can't get block by hash. Hash = 0000000000000000000000000000000000000000000000000000000000000000."));

        try {
            daemon.getBlockHeader(1);
            fail();
        } catch (RpcException e) {
            assertEquals(-2, e.getCode());
        }
        daemon.getBlockHeaderAsync(1)
                .thenAccept(blockHeader -> fail())
                .exceptionally(e -> {
                    assertEquals(-2, ((RpcException) e.getCause()).getCode());
                    return null;
                })
                .join();

        try {
            daemon.getBlockHeader("0000000000000000000000000000000000000000000000000000000000000000");
            fail();
        } catch (RpcException e) {
            assertEquals(-5, e.getCode());
        }
        daemon.getBlockHeaderAsync("0000000000000000000000000000000000000000000000000000000000000000")
                .thenAccept(blockHeader -> fail())
                .exceptionally(e -> {
                    assertEquals(-5, ((RpcException) e.getCause()).getCode());
                    return null;
                })
                .join();
    }
}
