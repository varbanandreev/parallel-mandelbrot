package main;

import visualizer.MandelbrotFractalVisualizer;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Options options = readArgs(args);
        MandelbrotFractalVisualizer visualizer = new MandelbrotFractalVisualizer(options);
        visualizer.drawFractal();
    }

    private static Options readArgs(String[] args) {
        Map<String, String> inputOptions = new HashMap<>();
        Options options = new Options();

        try {
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
                }
            }

            options.parseOptions(inputOptions);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid options format!");
        }

        return options;
    }
}
