package com.pmanaktala;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Simulation.
 */
public class Simulation {

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

        //Setting the dispatch ratio
        queue.setDispatchRatio(dispatchRatio);

        List<Process> processList = new ArrayList<>();

        for (String inComingProcess : fileContents) {
            if ("idle".equals(inComingProcess)) {
                //If we are getting idle from file, send null to serve the process.
                queue.serveProcess(null);
            } else {
                //If we have a number, consider it a process. We create a process and set the demotion criteria to
                // the one input by the user
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

        //Reading the lines in path
        Path path = null;
        try {
            path = Paths.get(Objects.requireNonNull(Simulation.class.getClassLoader().getResource("resources/" + fileName)).toURI());
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

        // Getting the input from user
        System.out.println("Enter input file name, dispatch Ratio, Demotion Criteria");
        String input = sc.nextLine();
        String[] elements = input.split(" ");
        String fileName = elements[0];
        int dispatchRatio = Integer.parseInt(elements[1]);
        int demotionCriteria = Integer.parseInt(elements[2]);

        // Reading the file contents
        List<String> fileContents = readTraceFile(fileName);

        Result result = simulateMultilevelQueue(fileContents, demotionCriteria, dispatchRatio, new MultiLevelQueue(),
                0);

        //Printing the values
        System.out.printf("End Time : %s%n", result.getTotalTime());
        System.out.printf("Process Completed : %s%n", result.getCompletedProcess());
        System.out.printf("Total Execution Time : %s%n", result.getTotalTime() - result.getIdleTime());
        System.out.printf("Idle Time : %s%n", result.getIdleTime());
        System.out.printf("Wait Time Average : %s%n", result.getAverageWaitTime());

    }

}
