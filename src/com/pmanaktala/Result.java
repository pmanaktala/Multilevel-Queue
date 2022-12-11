package com.pmanaktala;

/**
 * The type Result.
 */
public class Result {

    private Integer dispatchRatio;
    private Integer demotionCriteria;
    private Integer totalTime;
    private Integer totalProcess;
    private Integer completedProcess;
    private Integer idleTime;
    private Integer averageWaitTime;

    /**
     * Gets dispatch ratio.
     *
     * @return the dispatch ratio
     */
    public Integer getDispatchRatio() {
        return dispatchRatio;
    }

    /**
     * Sets dispatch ratio.
     *
     * @param dispatchRatio the dispatch ratio
     */
    public void setDispatchRatio(Integer dispatchRatio) {
        this.dispatchRatio = dispatchRatio;
    }

    /**
     * Gets demotion criteria.
     *
     * @return the demotion criteria
     */
    public Integer getDemotionCriteria() {
        return demotionCriteria;
    }

    /**
     * Sets demotion criteria.
     *
     * @param demotionCriteria the demotion criteria
     */
    public void setDemotionCriteria(Integer demotionCriteria) {
        this.demotionCriteria = demotionCriteria;
    }

    /**
     * Gets total time.
     *
     * @return the total time
     */
    public Integer getTotalTime() {
        return totalTime;
    }

    /**
     * Sets total time.
     *
     * @param totalTime the total time
     */
    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * Gets total process.
     *
     * @return the total process
     */
    public Integer getTotalProcess() {
        return totalProcess;
    }

    /**
     * Sets total process.
     *
     * @param totalProcess the total process
     */
    public void setTotalProcess(Integer totalProcess) {
        this.totalProcess = totalProcess;
    }

    /**
     * Gets completed process.
     *
     * @return the completed process
     */
    public Integer getCompletedProcess() {
        return completedProcess;
    }

    /**
     * Sets completed process.
     *
     * @param completedProcess the completed process
     */
    public void setCompletedProcess(Integer completedProcess) {
        this.completedProcess = completedProcess;
    }

    /**
     * Gets idle time.
     *
     * @return the idle time
     */
    public Integer getIdleTime() {
        return idleTime;
    }

    /**
     * Sets idle time.
     *
     * @param idleTime the idle time
     */
    public void setIdleTime(Integer idleTime) {
        this.idleTime = idleTime;
    }

    /**
     * Gets average wait time.
     *
     * @return the average wait time
     */
    public Integer getAverageWaitTime() {
        return averageWaitTime;
    }

    /**
     * Sets average wait time.
     *
     * @param averageWaitTime the average wait time
     */
    public void setAverageWaitTime(Integer averageWaitTime) {
        this.averageWaitTime = averageWaitTime;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{'dispatchRatio'");
        sb.append(':');
        sb.append(((this.dispatchRatio == null) ? "<null>" : this.dispatchRatio));
        sb.append(',');
        sb.append("'demotionCriteria'");
        sb.append(':');
        sb.append(((this.demotionCriteria == null) ? "<null>" : this.demotionCriteria));
        sb.append(',');
        sb.append("'totalTime'");
        sb.append(':');
        sb.append(((this.totalTime == null) ? "<null>" : this.totalTime));
        sb.append(',');
        sb.append("'totalProcess'");
        sb.append(':');
        sb.append(((this.totalProcess == null) ? "<null>" : this.totalProcess));
        sb.append(',');
        sb.append("'completedProcess'");
        sb.append(':');
        sb.append(((this.completedProcess == null) ? "<null>" : this.completedProcess));
        sb.append(',');
        sb.append("'idleTime'");
        sb.append(':');
        sb.append(((this.idleTime == null) ? "<null>" : this.idleTime));
        sb.append(',');
        sb.append("'averageWaitTime'");
        sb.append(':');
        sb.append(((this.averageWaitTime == null) ? "<null>" : this.averageWaitTime));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), '}');
        } else {
            sb.append('}');
        }
        return sb.toString().replace("'", "\"");
    }

}
