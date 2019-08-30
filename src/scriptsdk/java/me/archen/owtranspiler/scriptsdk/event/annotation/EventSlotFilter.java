package me.archen.owtranspiler.scriptsdk.event.annotation;

import me.archen.owtranspiler.scriptsdk.constant.Team;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventSlotFilter {

    int slotIndex();

}
