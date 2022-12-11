package com.pmanaktala;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

/**
 * The type Multi level queue.
 */
public class MultiLevelQueue {


    private final Queue<Process> queueA = new LinkedList<>();
    private final Queue<Process> queueB = new LinkedList<>();

    private int dispatchRatio;
    private int currentDispatch = 1;

    private int cpuIdle = 0;
    private int cpuTimeQuantum;

    private Process processInCpu;


    /**
     * Serve process. Simulate one cycle.
     *
     * @param process the process
     */
    public void serveProcess(Process process) {
        //Checking if the incoming process is there or its idle
        boolean newIncomingProcess = Objects.isNull(process);

        if (Objects.isNull(processInCpu)) {
            //CPU is idle, we need to pull out a process to serve from the queue.
            Optional<Process> readyProcessOptional = getReadyProcess();

            readyProcessOptional.ifPresentOrElse(readyProcess -> {
                //We have a new process to serve, we will add it in cpu and run it.
                processInCpu = readyProcess;
                runProcessInCpu();
            }, () -> cpuIdle++);

            //Once the execution is done, we will increment the wait time of the processes in the queue.
            incrementWait();
        } else {

            //We already have a process in cpu, we will check if it can be served or not.

            int processRemainingTime = processInCpu.getExecutionTime();
            if (cpuTimeQuantum > 0 && processRemainingTime > 0) {
                // The process can still serve in cpu since it has remaining time and its quantum has not yet reached
                // zero
                runProcessInCpu();
                incrementWait();
            } else {
                // The process needs to be exited or moved to queue.

                // Assumption : We are not pulling the process from the queue in the same cycle as we are demoting or
                // exiting it.
                if (processRemainingTime == 0) {
                    //Exit the process
                    processInCpu.complete();
                    processInCpu = null;
                    incrementWait();
                } else {
                    //We have to demote the process.
                    incrementWait();
                    demoteProcess();

                }
            }
        }

        //Once the execution is done, increase the wait time of new process and add it to the queue
        if (!newIncomingProcess) {
            process.increaseWaitTime();
            queueA.add(process);
        }


    }

    /**
     * Demote process.
     */
    private void demoteProcess() {
        if (processInCpu.isDemoteable()) {
            // We have to put the process in queue b since it has to be demoted.
            queueB.add(processInCpu);
            processInCpu = null;
            return;
        }

        queueA.add(processInCpu);
        processInCpu = null;
    }

    /**
     * Run process in cpu.
     */
    private void runProcessInCpu() {
        processInCpu.decreaseExecutionTime();
        cpuTimeQuantum--;
    }

    /**
     * Gets ready process.
     *
     * @return the ready process
     */
    private Optional<Process> getReadyProcess() {

        //If queueA has entries and the current dispatch is not more than the dispatch ration, pull from it.
        if (queueA.peek() != null && currentDispatch++ <= dispatchRatio) {
            cpuTimeQuantum = 5;

            Process process = queueA.remove();
            process.decreaseDemotionCriteria();

            currentDispatch++;
            return Optional.of(process);
        }

        //If any of the above condition is not satisfied, then check if we can pull from queueB
        if (queueB.peek() != null) {
            cpuTimeQuantum = 40;

            //If dispatch ratio limit has been reached, reset it.
            currentDispatch = currentDispatch > dispatchRatio ? 1 : currentDispatch;

            //Pull from B and return it.
            return Optional.of(queueB.remove());
        }

        //If dispatch ratio limit has been reached, reset it.
        currentDispatch = currentDispatch > dispatchRatio ? 1 : currentDispatch;

        //We do not have a process to serve. Return empty process.
        return Optional.empty();
    }

    /**
     * Sets dispatch ratio.
     *
     * @param dispatchRatio the dispatch ratio
     */
    public void setDispatchRatio(int dispatchRatio) {
        this.dispatchRatio = dispatchRatio;
    }

    /**
     * Gets cpu idle.
     *
     * @return the cpu idle
     */
    public int getCpuIdle() {
        return cpuIdle;
    }

    /**
     * Increment wait.
     */
    private void incrementWait() {
        //Increment wait time of process in both the queue
        queueA.parallelStream().forEach(Process::increaseWaitTime);
        queueB.parallelStream().forEach(Process::increaseWaitTime);
    }

}
