package me.archen.owtranspiler.workshop;

import me.archen.owtranspiler.workshop.constants.EventTrigger;
import me.archen.owtranspiler.workshop.constants.Team;

public class EventSelector {

    public static final String EVENT_SELECTOR_IDENTIFIER = "event";
    public static final EventPlayerSelector ALL = () -> "All";

    private final EventTrigger trigger;
    private final Team team;
    private final EventPlayerSelector playerSelector;

    public EventSelector(EventTrigger trigger, Team team, EventPlayerSelector playerSelector) {
        this.trigger = trigger;
        this.team = team;
        this.playerSelector = playerSelector;
    }

    public EventTrigger getTrigger() {
        return trigger;
    }

    public Team getTeam() {
        return team;
    }

    public EventPlayerSelector getPlayerSelector() {
        return playerSelector;
    }

    public void writeToCodeGenerator(CodeGenerator codeGenerator) {
        codeGenerator.appendIdentifier(EVENT_SELECTOR_IDENTIFIER);
        codeGenerator.appendBlockStart();
        codeGenerator.appendIdentifier(trigger.getName());
        codeGenerator.appendSemicolon();
        codeGenerator.appendIdentifier(team.getName());
        codeGenerator.appendSemicolon();
        codeGenerator.appendIdentifier(playerSelector.getName());
        codeGenerator.appendSemicolon();
        codeGenerator.appendBlockEnd();
    }
}
