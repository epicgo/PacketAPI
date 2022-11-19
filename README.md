<h1 align="center">PacketAPI</h1>
<p align="center">PacketAPI is a resource for sending, receiving or modifying network packets.</p>

###### What is PacketAPI?
> PacketAPI is an open source project that sending, receiving or modifying network packets. Easy and flexible to use with efficient API Developer.

###### What can do?
###### Here are a couple of things PacketAPI can do:
- Modifies the values for sending packages.
- Modifies the values for receiving packages.

##### What requires?
###### Here are a couple of things that PacketAPI requires:
- Java 8
- Netty 4.1.68
- Spigot API 1.8

##### Compatibility?
###### Here are a couple of things that PacketAPI is compatible with:
- Spigot (1.8.x)

### Usage:
```java
package io.github.epicgo.packet;

public static class PacketExampleListener implements PacketEventListener {
    
    @PacketEventHandler(listenType = PacketWrapperType.PacketPlayInCustomPayload)
    public void onReceive(final PacketReceiveEvent event) {
        final PacketWrapper packetWrapper = event.getPacketWrapper();
        final String packetName = packetWrapper.getName();
        
        System.out.println("Packet -> " + packetName);
        System.out.println("Player Channel -> " + packetWrapper.get("a"));
        System.out.println("Player Packet Data Channel -> " + packetWrapper.get("b"));
    }
}
```
