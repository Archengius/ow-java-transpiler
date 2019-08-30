package me.archen.owtranspiler.workshop;

public class Rule {

    private static final String TYPE_RULE = "rule";
    private final String name;
    private final EventSelector eventSelector;
    private final ActionList actionList;

    public Rule(String name, EventSelector eventSelector, ActionList actionList) {
        this.name = name;
        this.eventSelector = eventSelector;
        this.actionList = actionList;
    }

    public String getName() {
        return name;
    }

    public EventSelector getEventSelector() {
        return eventSelector;
    }

    public ActionList getActionList() {
        return actionList;
    }

    public void writeToGenerator(CodeGenerator codeGenerator) {
        codeGenerator.appendIdentifier(TYPE_RULE);
        codeGenerator.appendCallStart();
        codeGenerator.appendStringLdc(name);
        codeGenerator.appendCallEnd();
        codeGenerator.appendBlockStart();
        this.eventSelector.writeToCodeGenerator(codeGenerator);
        this.actionList.writeToGenerator(codeGenerator);
        codeGenerator.appendBlockEnd();
    }
}
