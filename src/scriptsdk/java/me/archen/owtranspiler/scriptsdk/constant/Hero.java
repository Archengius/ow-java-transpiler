package me.archen.owtranspiler.scriptsdk.constant;

import me.archen.owtranspiler.scriptsdk.impl.Constants;
import me.archen.owtranspiler.scriptsdk.impl.DummyProvider;
import me.archen.owtranspiler.scriptsdk.impl.MethodKey;
import me.archen.owtranspiler.scriptsdk.impl.ValuesOverwrite;

public enum Hero {
    REAPER,
    TRACER,
    MERCY,
    HANZO,
    TORBJORN,
    REINHARDT,
    PHARAH,
    WINSTON,
    WIDOWMAKER,
    BASTION,
    SYMMETRA,
    ZENYATTA,
    GENJI,
    ROADHOG,
    MCCREE,
    JUNKRAT,
    ZARYA,
    SOLDIER_76,
    LUCIO,
    DVA,
    MEI,
    SOMBRA,
    DOOMFIST,
    ANA,
    ORISA,
    BRIGITTE,
    MOIRA,
    WRECKING_BALL,
    ASHE,
    BAPTISTE;

    @MethodKey(Constants.HERO_ICON_STRING)
    public String getIconString() {
        return DummyProvider.createDummy(this);
    }

    @MethodKey(Constants.HERO_ALL_HEROES)
    @ValuesOverwrite
    private static Hero[] valuesOverwrite() {
        return DummyProvider.createDummy();
    }
}
