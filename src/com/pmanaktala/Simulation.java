package com.pmanaktala;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulation {

    static AtomicInteger totalTime = new AtomicInteger(0);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter input file name, dispatch Ratio, Demotion Criteria");
        String input = sc.nextLine();
        String[] elements = input.split(" ");
        String fileName = elements[0];
        int dispatchRatio = Integer.parseInt(elements[1]);
        int demotionCriteria = Integer.parseInt(elements[2]);

        List<Result> resultList = new ArrayList<>();
        System.out.println("Running : " + dispatchRatio + " - " + demotionCriteria);
        Result result = simulateMultilevelQueue(fileName, demotionCriteria, dispatchRatio);
        result.setDispatchRatio(dispatchRatio);
        result.setDemotionCriteria(demotionCriteria);
        resultList.add(result);

        List<Result> sortedList =
                resultList.parallelStream().sorted(Comparator.comparingInt(Result::getAverageWaitTime)).collect(Collectors.toList());

        sortedList.forEach(System.out::println);

    }

    /**
     * Read the trace file as a stream of string. Each line in the file, corresponds to a stream element.
     *
     * @param fileName the file name
     * @return the stream
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    private static Stream<String> readTraceFile(String fileName) {
        if (!"Jobs.txt".equals(fileName)) {
            // Checking if the input file is out of the two trace files, if not throw exception
            throw new RuntimeException("Input File name is invalid!");
        }
        Path path = null;
        try {
            path = Paths.get(Objects.requireNonNull(Simulation.class.getClassLoader().getResource("resources/" + fileName)).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
            return Files.lines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Result simulateMultilevelQueue(String fileName, int demotionCriteria, int dispatchRatio) {
        Stream<String> fileContents = readTraceFile(fileName);
        MultiLevelQueue.setDispatchRatio(dispatchRatio);

        List<Process> processList = new ArrayList<>();
        fileContents.forEach(inCompingProcess -> {

            if ("idle".equals(inCompingProcess)) {
                MultiLevelQueue.serveProcess(null);
            } else {
                Process process = new Process(Integer.parseInt(inCompingProcess), demotionCriteria);
                processList.add(process);
                MultiLevelQueue.serveProcess(process);
            }

            totalTime.getAndIncrement();
        });

        Result result = new Result();
        result.setTotalProcess(processList.size());
        result.setIdleTime(MultiLevelQueue.getCpuIdle());
        result.setTotalTime(totalTime.get());

        result.setCompletedProcess((int) processList.stream().filter(Process::getComplete).count());

        int totalWaitTime = processList.stream().map(Process::getWaitTime).mapToInt(Integer::intValue).sum();
        result.setAverageWaitTime(totalWaitTime / processList.size());

        return result;
    }

}
