package me.archen.owtranspiler.scriptsdk.event;

import me.archen.owtranspiler.scriptsdk.impl.Constants;
import me.archen.owtranspiler.scriptsdk.impl.EventTriggerKey;
import me.archen.owtranspiler.scriptsdk.impl.MethodKey;
import me.archen.owtranspiler.scriptsdk.types.Player;

@EventTriggerKey("PLAYER_DIED")
public interface PlayerDiedEvent {

    @MethodKey(Constants.EVENT_PLAYER_KEY)
    Player getPlayer();

}
