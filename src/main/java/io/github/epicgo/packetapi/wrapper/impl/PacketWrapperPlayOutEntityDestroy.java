package io.github.epicgo.packetapi.wrapper.impl;

import io.github.epicgo.packetapi.reflection.Reflection;
import io.github.epicgo.packetapi.wrapper.PacketWrapper;
import lombok.Getter;

@Getter
public class PacketWrapperPlayOutEntityDestroy extends PacketWrapper {

    /**
     * Sent by the server when a list of entities is to be destroyed on the client.
     *
     * @param entityIds the entities to destroy.
     */
    public PacketWrapperPlayOutEntityDestroy(final int... entityIds) {
        super(Reflection.getConstructor(Reflection.getNMSClass("PacketPlayOutEntityDestroy")));

        this.set("a", entityIds); // entities to destroy
    }
}
