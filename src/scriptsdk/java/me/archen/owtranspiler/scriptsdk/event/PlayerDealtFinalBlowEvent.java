package me.archen.owtranspiler.scriptsdk.event;

import me.archen.owtranspiler.scriptsdk.impl.Constants;
import me.archen.owtranspiler.scriptsdk.impl.EventTriggerKey;
import me.archen.owtranspiler.scriptsdk.impl.MethodKey;
import me.archen.owtranspiler.scriptsdk.types.Player;

@EventTriggerKey("PLAYER_DEALT_FINAL_BLOW")
public interface PlayerDealtFinalBlowEvent {

    @MethodKey(Constants.EVENT_PLAYER_KEY)
    Player getPlayer();

    @MethodKey(Constants.EVENT_VICTIM_KEY)
    Player getVictim();

}
