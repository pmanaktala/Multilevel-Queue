package com.pmanaktala;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

public class MultiLevelQueue {

    private static final Queue<Process> queueA = new LinkedList<>();
    private static final Queue<Process> queueB = new LinkedList<>();
    private static int dispatchRatio;
    private static int cpuIdle = 0;
    private static int cpuTimeQuantum;
    private static Process processInCpu;
    private static int currentDispatch = 1;

    private MultiLevelQueue() {
        // Do not make an instance
    }

    public static void serveProcess(Process process) {
        boolean newIncomingProcess = Objects.isNull(process);

        if (Objects.isNull(processInCpu)) {
            //CPU Idle
            Optional<Process> readyProcessOptional = getReadyProcess();

            readyProcessOptional.ifPresentOrElse(readyProcess -> {
                //We have a new process to serve
                processInCpu = readyProcess;
                runProcessInCpu();
            }, () -> cpuIdle++);
            incrementWait();
        } else {
            //We already have a process in cpu
            int processRemainingTime = processInCpu.getExecutionTime();
            if (cpuTimeQuantum > 0 && processRemainingTime > 0) {
                // The process can still serve in cpu
                runProcessInCpu();
                incrementWait();
            } else {
                // The process needs to be exited or moved to queue
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

        if (!newIncomingProcess) {
            process.increaseWaitTime();
            queueA.add(process);
        }


    }

    private static void demoteProcess() {
        if (processInCpu.isDemoteable()) {
            // We have to put the process in queue b since it has to be demoted.
            queueB.add(processInCpu);
            processInCpu = null;
            return;
        }

        queueA.add(processInCpu);
        processInCpu = null;
    }

    private static void  runProcessInCpu() {
        processInCpu.decreaseExecutionTime();
        cpuTimeQuantum--;
    }

    private static Optional<Process> getReadyProcess() {

        if (queueA.peek() != null && currentDispatch++ < dispatchRatio) {
            cpuTimeQuantum = 5;
            Process process = queueA.remove();
            process.decreaseDemotionCriteria();
            currentDispatch++;
            return Optional.of(process);
        }

        if (queueB.peek() != null) {
            cpuTimeQuantum = 40;

            currentDispatch = currentDispatch > dispatchRatio ? 1 : currentDispatch;
            return Optional.of(queueB.remove());
        }

        currentDispatch = currentDispatch > dispatchRatio ? 1 : currentDispatch;
        return Optional.empty();
    }

    static void setDispatchRatio(int dispatchRatio) {
        MultiLevelQueue.dispatchRatio = dispatchRatio;
    }

    static int getCpuIdle() {
        return cpuIdle;
    }

    static void incrementWait() {
        queueA.parallelStream().forEach(Process::increaseWaitTime);
        queueB.parallelStream().forEach(Process::increaseWaitTime);
    }

}
