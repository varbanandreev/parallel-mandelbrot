package main;

import java.util.Map;

public class Options {
    private static final int MAX_THREADS = 8;

    private int pictureWidth;
    private int pictureHeight;
    private double planeStartX;
    private double planeEndX;
    private double planeStartY;
    private double planeEndY;
    private int threadCount;
    private String fileName;
    private boolean isQuiet;

    public int getPictureWidth() {
        return pictureWidth;
    }

    public int getPictureHeight() {
        return pictureHeight;
    }

    public double getPlaneStartX() {
        return planeStartX;
    }

    public double getPlaneEndX() {
        return planeEndX;
    }

    public double getPlaneStartY() {
        return planeStartY;
    }

    public double getPlaneEndY() {
        return planeEndY;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isQuiet() {
        return isQuiet;
    }

    public void parseOptions(Map<String, String> options) {
        String optionValue = options.getOrDefault("size", "640x480");
        String[] optionValueSplit = optionValue.split("x");
        this.pictureWidth = Integer.parseInt(optionValueSplit[0]);
        this.pictureHeight = Integer.parseInt(optionValueSplit[1]);

        optionValueSplit = options.getOrDefault("rect", "-2.0:2.0:-2.0:2.0").split(":");
        this.planeStartX = Double.parseDouble(optionValueSplit[0]);
        this.planeEndX = Double.parseDouble(optionValueSplit[1]);
        this.planeStartY = Double.parseDouble(optionValueSplit[2]);
        this.planeEndY = Double.parseDouble(optionValueSplit[3]);

        this.threadCount = Integer.parseInt(options.getOrDefault("tasks", "1"));
        if (this.threadCount > MAX_THREADS) {
            this.threadCount = MAX_THREADS;
        }

        this.fileName = options.getOrDefault("output", "zad16.png");
        this.isQuiet = Boolean.parseBoolean(options.getOrDefault("quiet", "false"));
    }
}
