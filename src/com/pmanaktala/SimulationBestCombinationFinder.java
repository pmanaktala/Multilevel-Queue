package com.pmanaktala;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Simulation, the best combination finder.
 */
public class SimulationBestCombinationFinder {

    /**
     * Simulate multilevel queue result.
     *
     * @param fileContents     the file contents
     * @param demotionCriteria the demotion criteria
     * @param dispatchRatio    the dispatch ratio
     * @param queue            the queue
     * @param totalTime        the total time
     * @return the result
     */
    private static Result simulateMultilevelQueue(List<String> fileContents, int demotionCriteria, int dispatchRatio,
                                                  MultiLevelQueue queue, int totalTime) {

        System.out.printf("Simulating dispatch ratio of '%s' and demotionCriteria of '%s' %n", dispatchRatio,
                demotionCriteria);
        queue.setDispatchRatio(dispatchRatio);

        List<Process> processList = new ArrayList<>();

        for (String inComingProcess : fileContents) {
            if ("idle".equals(inComingProcess)) {
                queue.serveProcess(null);
            } else {
                Process process = new Process(Integer.parseInt(inComingProcess), demotionCriteria);
                processList.add(process);
                queue.serveProcess(process);
            }

            totalTime++;
        }

        Result result = new Result();
        result.setTotalProcess(processList.size());
        result.setIdleTime(queue.getCpuIdle());
        result.setTotalTime(totalTime);
        result.setDispatchRatio(dispatchRatio);
        result.setDemotionCriteria(demotionCriteria);

        result.setCompletedProcess((int) processList.parallelStream().filter(Process::getComplete).count());

        int totalWaitTime = processList.stream().map(Process::getWaitTime).mapToInt(Integer::intValue).sum();
        result.setAverageWaitTime(totalWaitTime / processList.size());

        return result;
    }

    /**
     * Read trace file list.
     *
     * @param fileName the file name
     * @return the list
     */
    private static List<String> readTraceFile(String fileName) {
        if (!"Jobs.txt".equals(fileName)) {
            // Checking if the input file is out of the two trace files, if not throw exception
            throw new MultiLevelQueueException("Input File name is invalid!");
        }
        Path path = null;
        try {
            path = Paths.get(Objects.requireNonNull(SimulationBestCombinationFinder.class.getClassLoader().getResource("resources/" + fileName)).toURI());
        } catch (URISyntaxException e) {
            throw new MultiLevelQueueException(e);
        }

        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            throw new MultiLevelQueueException(e);
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter input file name");
        String input = sc.nextLine();
        String[] elements = input.split(" ");
        String fileName = elements[0];

        List<String> fileContents = readTraceFile(fileName);

        List<Result> resultList = new ArrayList<>();

        //Creating a Callable List where each task will run in a different thread later.
        List<Callable<Result>> tasks = new ArrayList<>();
        for (int dispatchRatioLoop = 1; dispatchRatioLoop <= 100; dispatchRatioLoop++) {
            for (int demotionCriteriaLoop = 1; demotionCriteriaLoop <= 100; demotionCriteriaLoop++) {
                int finalDemotionCriteriaLoop = demotionCriteriaLoop;
                int finalDispatchRatioLoop = dispatchRatioLoop;
                MultiLevelQueue queue = new MultiLevelQueue();
                int totalTime = 0;
                Callable<Result> callable = () -> simulateMultilevelQueue(fileContents, finalDemotionCriteriaLoop,
                        finalDispatchRatioLoop, queue, totalTime);
                tasks.add(callable);
            }
        }

        //Creating a new executor service.
        ExecutorService exec = Executors.newCachedThreadPool();

        try {
            //Running all the process in parallel.
            List<Future<Result>> results = exec.invokeAll(tasks);
            for (Future<Result> result : results) {

                //Busy wait when invoking result.get()
                resultList.add(result.get());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new MultiLevelQueueException(e);
        }


        //Once all the tasks are finished, we sort the process based on the average wait time.
        List<Result> sortedList =
                resultList.parallelStream().sorted(Comparator.comparingInt(Result::getAverageWaitTime)).collect(Collectors.toList());

        //Creating a json of results.
        String result = sortedList.stream().map(Result::toString).collect(Collectors.joining(","));
        System.out.println("[" + result + "]");


        System.out.println("The best performing task is - ");
        System.out.println(sortedList.get(0));
    }

}
