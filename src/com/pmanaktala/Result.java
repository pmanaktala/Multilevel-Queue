package com.pmanaktala;

public class Result {

    private Integer dispatchRatio;
    private Integer demotionCriteria;
    private Integer totalTime;
    private Integer totalProcess;
    private Integer completedProcess;
    private Integer idleTime;
    private Integer averageWaitTime;

    public Integer getDispatchRatio() {
        return dispatchRatio;
    }

    public void setDispatchRatio(Integer dispatchRatio) {
        this.dispatchRatio = dispatchRatio;
    }

    public Integer getDemotionCriteria() {
        return demotionCriteria;
    }

    public void setDemotionCriteria(Integer demotionCriteria) {
        this.demotionCriteria = demotionCriteria;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getTotalProcess() {
        return totalProcess;
    }

    public void setTotalProcess(Integer totalProcess) {
        this.totalProcess = totalProcess;
    }

    public Integer getCompletedProcess() {
        return completedProcess;
    }

    public void setCompletedProcess(Integer completedProcess) {
        this.completedProcess = completedProcess;
    }

    public Integer getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(Integer idleTime) {
        this.idleTime = idleTime;
    }

    public Integer getAverageWaitTime() {
        return averageWaitTime;
    }

    public void setAverageWaitTime(Integer averageWaitTime) {
        this.averageWaitTime = averageWaitTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("dispatchRatio");
        sb.append('=');
        sb.append(((this.dispatchRatio == null) ? "<null>" : this.dispatchRatio));
        sb.append(',');
        sb.append("demotionCriteria");
        sb.append('=');
        sb.append(((this.demotionCriteria == null) ? "<null>" : this.demotionCriteria));
        sb.append(',');
        sb.append("totalTime");
        sb.append('=');
        sb.append(((this.totalTime == null) ? "<null>" : this.totalTime));
        sb.append(',');
        sb.append("totalProcess");
        sb.append('=');
        sb.append(((this.totalProcess == null) ? "<null>" : this.totalProcess));
        sb.append(',');
        sb.append("completedProcess");
        sb.append('=');
        sb.append(((this.completedProcess == null) ? "<null>" : this.completedProcess));
        sb.append(',');
        sb.append("idleTime");
        sb.append('=');
        sb.append(((this.idleTime == null) ? "<null>" : this.idleTime));
        sb.append(',');
        sb.append("averageWaitTime");
        sb.append('=');
        sb.append(((this.averageWaitTime == null) ? "<null>" : this.averageWaitTime));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
