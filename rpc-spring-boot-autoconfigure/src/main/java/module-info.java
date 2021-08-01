/**
 * This module provides auto-configuration for creating RPC clients in Spring Boot applications.
 */
open module lageto.monero.rpc.spring {
    exports net.lageto.monero.rpc.spring;

    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;

    requires static lageto.monero.rpc;
}
