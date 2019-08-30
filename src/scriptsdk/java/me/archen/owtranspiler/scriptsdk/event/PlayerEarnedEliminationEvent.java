package me.archen.owtranspiler.scriptsdk.event;

import me.archen.owtranspiler.scriptsdk.impl.Constants;
import me.archen.owtranspiler.scriptsdk.impl.EventTriggerKey;
import me.archen.owtranspiler.scriptsdk.impl.MethodKey;
import me.archen.owtranspiler.scriptsdk.types.Player;

@EventTriggerKey("PLAYER_EARNED_ELIMINATION")
public interface PlayerEarnedEliminationEvent {

    @MethodKey(Constants.EVENT_PLAYER_KEY)
    Player getPlayer();

    @MethodKey(Constants.EVENT_ATTACKER_KEY)
    Player getKiller();

    @MethodKey(Constants.EVENT_VICTIM_KEY)
    Player getVictim();
}
