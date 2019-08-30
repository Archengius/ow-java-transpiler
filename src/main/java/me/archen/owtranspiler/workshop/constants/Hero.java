package me.archen.owtranspiler.workshop.constants;

import me.archen.owtranspiler.workshop.EventPlayerSelector;

public enum Hero implements EventPlayerSelector {

    REAPER("Reaper"),
    TRACER("Tracer"),
    MERCY("Mercy"),
    HANZO("Hanzo"),
    TORBJORN("Torbjörn"),
    REINHARDT("Reinhardt"),
    PHARAH("Pharah"),
    WINSTON("Winston"),
    WIDOWMAKER("Widowmaker"),
    BASTION("Bastion"),
    SYMMETRA("Symmetra"),
    ZENYATTA("Zenyatta"),
    GENJI("Genji"),
    ROADHOG("Roadhog"),
    MCCREE("McCree"),
    JUNKRAT("Junkrat"),
    ZARYA("Zarya"),
    SOLDIER_76("Soldier: 76"),
    LUCIO("Lúcio"),
    DVA("D.Va"),
    MEI("Mei"),
    SOMBRA("Sombra"),
    DOOMFIST("Doomfist"),
    ANA("Ana"),
    ORISA("Orisa"),
    BRIGITTE("Brigitte"),
    MOIRA("Moira"),
    WRECKING_BALL("Wrecking Ball"),
    ASHE("Ashe"),
    BAPTISTE("Baptiste");

    private final String name;

    Hero(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
