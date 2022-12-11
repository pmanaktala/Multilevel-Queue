package com.pmanaktala;

/**
 * The type Process.
 */
public class Process {

    private int waitTime;
    private int executionTime;
    private int demotionCriteria;

    private boolean complete = false;

    /**
     * Instantiates a new Process.
     *
     * @param executionTime    the execution time
     * @param demotionCriteria the demotion criteria
     */
    Process(int executionTime, int demotionCriteria) {
        waitTime = 0;
        this.executionTime = executionTime;
        this.demotionCriteria = demotionCriteria;
    }

    /**
     * Increase wait time.
     */
    public void increaseWaitTime() {
        this.waitTime++;
    }

    /**
     * Decrease execution time.
     */
    public void decreaseExecutionTime() {
        executionTime--;
    }

    /**
     * Gets execution time.
     *
     * @return the execution time
     */
    public int getExecutionTime() {
        return executionTime;
    }

    /**
     * Gets wait time.
     *
     * @return the wait time
     */
    public int getWaitTime() {
        return waitTime;
    }

    /**
     * Decrease demotion criteria.
     */
    public void decreaseDemotionCriteria() {
        if (demotionCriteria == 0) {
            throw new RuntimeException("Process should never be in this Queue");
        }
        demotionCriteria--;
    }

    /**
     * Is demotable boolean.
     *
     * @return the boolean
     */
    public boolean isDemoteable() {
        return demotionCriteria == 0;
    }

    /**
     * Complete.
     */
    public void complete() {
        this.complete = true;
    }

    /**
     * Gets complete.
     *
     * @return the complete
     */
    public boolean getComplete() {
        return complete;
    }
}
