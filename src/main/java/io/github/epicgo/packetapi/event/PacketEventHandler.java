package io.github.epicgo.packetapi.event;

import io.github.epicgo.packetapi.wrapper.PacketWrapperType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketEventHandler {

    PacketWrapperType listenType() default PacketWrapperType.ALL;
}
