package me.archen.owtranspiler.workshop.constants;

public enum EventTrigger {

    ONGOING_GLOBAL("Ongoing-Global", true, false),
    ONGOING_EACH_PLAYER("Ongoing-EachPlayer", true, true),
    PLAYER_EARNED_ELIMINATION("PlayerEarnedElimination", false, true),
    PLAYER_DEALT_FINAL_BLOW("PlayerDealtFinalBlow", false, true),
    PLAYER_DEALT_DAMAGE("PlayerDealtDamage", false, true),
    PLAYER_TOOK_DAMAGE("PlayerTookDamage", false, true),
    PLAYER_DIED("PlayerDied", false, true);

    private final String name;
    private boolean shouldWrapWithLoop;
    private boolean hasPlayerContext;

    EventTrigger(String name, boolean shouldWrapWithLoop, boolean hasPlayerContext) {
        this.name = name;
        this.shouldWrapWithLoop = shouldWrapWithLoop;
        this.hasPlayerContext = hasPlayerContext;
    }

    public String getName() {
        return name;
    }

    public boolean isShouldWrapWithLoop() {
        return shouldWrapWithLoop;
    }

    public boolean isHasPlayerContext() {
        return hasPlayerContext;
    }
}
