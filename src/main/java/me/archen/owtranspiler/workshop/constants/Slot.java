package me.archen.owtranspiler.workshop.constants;

import me.archen.owtranspiler.workshop.EventPlayerSelector;

public final class Slot implements EventPlayerSelector {

    private final int slotNumber;

    public Slot(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public String getName() {
        return String.format("Slot%d", slotNumber);
    }
}
