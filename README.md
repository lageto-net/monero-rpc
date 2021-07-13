# monero-rpc

A library for Java developers to interface with the Monero P2P daemon and wallet daemon with their RPC protocols.  Created and open sourced by the [Lageto mining pool](https://www.lageto.net).

## Getting monero-rpc

monero-rpc is distributed via Maven Central.  Regular snapshot releases are also frequently published to the Maven Central snapshots repository.

See below for various build tool snippets.

##### Maven

```xml
<dependency>
    <groupId>net.lageto.monero</groupId>
    <artifactId>rpc</artifactId>
    <version>0.1.0</version>
</dependency>
```

##### Gradle

```groovy
implementation 'net.lageto.monero:rpc:0.1.0'
```

## Example Usage

```java
// Create an instance.
var daemon = DaemonRpcClient.newInstance(URI.create("http://localhost:18081/json_rpc"));

// Perform blocking calls.
long height = daemon.getBlockCount() - 1;
System.out.println(daemon.getBlockHeaderAsync(height));

// Perform async calls.
daemon.getBlockCountAsync()
    .thenApply(count -> count - 1)
    .thenCompose(daemon::getBlockHeaderAsync)
    .thenAccept(System.out::println);
```

## License

Released under the terms of version 2 of the Apache License.
See https://www.apache.org/licenses/LICENSE-2.0.txt for more info.
