package com.pmanaktala;

public class Process {

    private int waitTime;
    private int executionTime;
    private int demotionCriteria;
    private boolean complete = false;

    Process(int executionTime, int demotionCriteria) {
        waitTime = 0;
        this.executionTime = executionTime;
        this.demotionCriteria = demotionCriteria;
    }

    public void increaseWaitTime() {
        this.waitTime++;
    }

    public void decreaseExecutionTime() {
        executionTime--;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void decreaseDemotionCriteria(){
        if(demotionCriteria == 0){
            throw new RuntimeException("Process should never be in this Queue");
        }
        demotionCriteria--;
    }
    public boolean isDemoteable() {
        return demotionCriteria == 0;
    }

    public void complete(){
        this.complete = true;
    }

    public boolean getComplete(){
        return complete;
    }
}
