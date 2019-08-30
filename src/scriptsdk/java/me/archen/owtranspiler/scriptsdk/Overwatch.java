package me.archen.owtranspiler.scriptsdk;

import me.archen.owtranspiler.scriptsdk.constant.Team;
import me.archen.owtranspiler.scriptsdk.impl.Constants;
import me.archen.owtranspiler.scriptsdk.impl.DummyProvider;
import me.archen.owtranspiler.scriptsdk.impl.MethodKey;
import me.archen.owtranspiler.scriptsdk.types.Player;

public class Overwatch {

    @MethodKey(Constants.HERO_ALL_HEROES)
    public static Player[] getAllPlayers(Team team) {
        return DummyProvider.createDummy();
    }

}
