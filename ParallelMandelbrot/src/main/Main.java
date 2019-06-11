package main;

import performancetest.MandelbrotPerformanceTester;
import performancetest.TestOptions;
import visualizer.MandelbrotFractalVisualizer;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final int[] numberOfThreadsSet = {1, 2, 3, 4, 5, 6, 7, 8, 16, 32};

    public static void main(String[] args) {
        Options options = readArgs(args);
        MandelbrotFractalVisualizer visualizer = new MandelbrotFractalVisualizer(options);
        visualizer.drawFractal();

        // MandelbrotPerformanceTester tester = new MandelbrotPerformanceTester();
        // testPerformance(tester);
    }

    private static Options readArgs(String[] args) {
        Map<String, String> inputOptions = new HashMap<>();
        Options options = new Options();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-s":
                case "-size":
                    inputOptions.put("size", args[++i]);
                    break;
                case "-r":
                case "-rect":
                    inputOptions.put("rect", args[++i]);
                    break;
                case "-t":
                case "-tasks":
                    inputOptions.put("tasks", args[++i]);
                    break;
                case "-o":
                case "-output":
                    inputOptions.put("output", args[++i]);
                    break;
                case "-q":
                case "-quiet":
                    inputOptions.put("quiet", args[++i]);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid options format!");
            }
        }

        options.parseOptions(inputOptions);
        return options;
    }

    private static void testPerformance(MandelbrotPerformanceTester tester) {
        for (int numberOfThreads : numberOfThreadsSet) {
            TestOptions current = new TestOptions(numberOfThreads);
            tester.startPerformanceTest(current);
        }
    }
}
