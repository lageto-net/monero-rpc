# monero-rpc

A library for Java developers to interface with the Monero P2P daemon and wallet daemon with their RPC protocols.  Created and open sourced by the [Lageto mining pool](https://www.lageto.net).

## Getting monero-rpc

No releases have been tagged yet, but snapshots are frequently published to the Maven Central snapshots repository.
See below for an excerpt from a `pom.xml` file that adds monero-rpc as a dependency.

```xml
<repositories>
    <repository>
        <id>ossrh-snapshots</id>
        <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>net.lageto.monero</groupId>
        <artifactId>rpc</artifactId>
        <version>0.1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

## Example Usage

```java
// Create an instance.
var daemon = DaemonRpcClient.newInstance(URI.create("http://localhost:18081/json_rpc"));

// Perform a blocking call.
System.out.println(daemon.getBlockCount());

// Perform an async call.
daemon.getBlockCountAsync().thenAccept(System.out::println);
```

## License

Released under the terms of version 2 of the Apache License.
See https://www.apache.org/licenses/LICENSE-2.0.txt for more info.
