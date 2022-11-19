package io.github.epicgo.packetapi.wrapper.impl;

import io.github.epicgo.packetapi.reflection.Reflection;
import io.github.epicgo.packetapi.wrapper.PacketWrapper;
import lombok.Getter;

@Getter
public class PacketWrapperPlayOutAttachEntity extends PacketWrapper {

    /**
     * This packet is sent when a player has been attached to an entity.
     * @param attachEntityId    entity to attach.
     * @param vechicleEntity    entity to vehicle.
     */
    public PacketWrapperPlayOutAttachEntity(final int attachEntityId, final int vechicleEntity) {
        this(attachEntityId, vechicleEntity, 0);
    }

    /**
     * This packet is sent when a player has been attached to an entity.
     * @param attachEntityId    entity to attach.
     * @param vechicleEntity    entity to vehicle.
     * @param dataId            data id to the vehicle.
     */
    public PacketWrapperPlayOutAttachEntity(final int attachEntityId, final int vechicleEntity, final int dataId) {
        super(Reflection.getConstructor(Reflection.getNMSClass("PacketPlayOutAttachEntity")));

        this.set("a", dataId); // leash

        this.set("d", attachEntityId); // attach entity id
        this.set("c", vechicleEntity); // vehicle entity id (if is null == -1)
    }
}
