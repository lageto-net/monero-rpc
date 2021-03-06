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

/**
 * This module provides clients for interfacing with the Monero crypto-currency P2P and wallet daemons over their HTTP
 * RPC protocols.
 *
 * <p>
 * The {@link net.lageto.monero.rpc} package contains the clients themselves.
 * </p>
 * <p>
 * The {@link net.lageto.monero.rpc.model} package contains objects that RPC methods may return or use as parameters.
 * </p>
 */
module lageto.monero.rpc {
    exports net.lageto.monero.rpc;
    exports net.lageto.monero.rpc.model;

    opens net.lageto.monero.rpc to com.fasterxml.jackson.databind;
    opens net.lageto.monero.rpc.model to com.fasterxml.jackson.databind;

    requires java.net.http;

    requires com.fasterxml.jackson.databind;
}
