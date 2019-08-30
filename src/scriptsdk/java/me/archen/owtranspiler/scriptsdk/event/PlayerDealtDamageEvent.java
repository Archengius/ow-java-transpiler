package me.archen.owtranspiler.scriptsdk.event;

import me.archen.owtranspiler.scriptsdk.impl.Constants;
import me.archen.owtranspiler.scriptsdk.impl.EventTriggerKey;
import me.archen.owtranspiler.scriptsdk.impl.MethodKey;
import me.archen.owtranspiler.scriptsdk.types.Player;

@EventTriggerKey("PLAYER_DEALT_DAMAGE")
public interface PlayerDealtDamageEvent {

    @MethodKey(Constants.EVENT_PLAYER_KEY)
    Player getPlayer();

    @MethodKey(Constants.EVENT_VICTIM_KEY)
    Player getVictim();

    @MethodKey(Constants.EVENT_DAMAGE_KEY)
    double getDamage();

    @MethodKey(Constants.EVENT_WAS_CRITICAL_KEY)
    boolean isCritical();

}
