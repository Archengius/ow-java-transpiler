package me.archen.owtranspiler.scriptsdk.constant;

import me.archen.owtranspiler.scriptsdk.impl.Constants;
import me.archen.owtranspiler.scriptsdk.impl.DummyProvider;
import me.archen.owtranspiler.scriptsdk.impl.MethodKey;

public enum Team {
    ALL,
    TEAM_1,
    TEAM_2;

    @MethodKey(Constants.TEAM_GET_OPPOSITE)
    public Team getOpposite() {
        return DummyProvider.createDummy(this);
    }
}
