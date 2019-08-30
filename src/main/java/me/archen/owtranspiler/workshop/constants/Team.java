package me.archen.owtranspiler.workshop.constants;

public enum Team {

    ALL("All"),
    TEAM_1("Team1"),
    TEAM_2("Team2");

    private final String name;

    Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
