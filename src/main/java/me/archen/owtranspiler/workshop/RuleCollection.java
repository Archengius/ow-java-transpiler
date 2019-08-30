package me.archen.owtranspiler.workshop;

import java.util.List;

public class RuleCollection {

    private final List<Rule> ruleList;

    public RuleCollection(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }

    public void appendRule(Rule rule) {
        this.ruleList.add(rule);
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public void writeToGenerator(CodeGenerator codeGenerator) {
        for(Rule rule : ruleList) {
            rule.writeToGenerator(codeGenerator);
        }
    }
}
