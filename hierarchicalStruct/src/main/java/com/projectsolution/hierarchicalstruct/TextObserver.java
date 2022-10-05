package com.projectsolution.hierarchicalstruct;

/**
 * observer class
 */
public class TextObserver {
    /**
     * observer
     */
    TextEdit update;

    /**
     * @param updateConnections observer
     */
    public TextObserver(TextEdit updateConnections) {
        update = updateConnections;
    }

    /**
     * update
     */
    public void update()
    {
        update.onEdit();
    }
}
