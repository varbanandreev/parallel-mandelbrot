package generator;

import main.Options;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MandelbrotSetGenerator {
    private int maxIterations;
    private Options options;
    private MandelbrotSetReceiver receiver;

    private BigDecimal xIntervalLength;
    private BigDecimal yIntervalLength;
    private Map<MandelbrotNumber, Pixel> numberPixelMap;
    private List<MandelbrotNumber> generatedNumbers;
    private int finalNumberCount;

    private ThreadPoolExecutor executor;

    public MandelbrotSetGenerator(Options options, int maxIterations, MandelbrotSetReceiver receiver) {
        this.options = options;
        this.maxIterations = maxIterations;
        this.receiver = receiver;

        this.xIntervalLength = BigDecimal.valueOf(options.getPlaneEndX() - options.getPlaneStartX());
        this.yIntervalLength = BigDecimal.valueOf(options.getPlaneEndY() - options.getPlaneStartX());

        this.executor = new ThreadPoolExecutor(this.options.getThreadCount(), this.options.getThreadCount(),
                0, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<>());
    }

    public Map<MandelbrotNumber, Pixel> getPixelMapping() {
        return this.numberPixelMap;
    }

    private BigDecimal getScaledXPixel(int x, int width) {
        BigDecimal scale = xIntervalLength.divide(BigDecimal.valueOf(width), 8, RoundingMode.FLOOR);
        return (BigDecimal.valueOf(x).multiply(scale)).add(BigDecimal.valueOf(options.getPlaneStartX()));
    }

    private BigDecimal getScaledYPixel(int y, int height) {
        BigDecimal scale = yIntervalLength.divide(BigDecimal.valueOf(height), 8, RoundingMode.FLOOR);
        return (BigDecimal.valueOf(y).multiply(scale)).add(BigDecimal.valueOf(options.getPlaneStartY()));
    }

    public void generatePixels() {
        synchronized (this) {
            this.finalNumberCount = options.getPictureHeight() * options.getPictureWidth();
            this.generatedNumbers = new ArrayList<>();
            this.numberPixelMap = new HashMap<>();

            for (int x = 0; x < options.getPictureWidth(); ++x) {
                for (int y = 0; y < options.getPictureHeight(); ++y) {
                    BigDecimal realPart = getScaledXPixel(x, options.getPictureWidth());
                    BigDecimal imaginaryPart = getScaledYPixel(y, options.getPictureHeight());
                    MandelbrotNumber number = new MandelbrotNumber(realPart, imaginaryPart);
                    this.numberPixelMap.put(number, new Pixel(x, y));

                    computeBoundedIterations(number);
                }
            }
        }
    }

    void computationIsCompleted(MandelbrotNumber number) {
        synchronized (this.generatedNumbers) {
            this.generatedNumbers.add(number);

            if (this.generatedNumbers.size() == this.finalNumberCount) {
                this.receiver.receiveSet(this.generatedNumbers);
            }
        }
    }

    public void shutdown() {
        this.executor.shutdown();
    }


    private void computeBoundedIterations(MandelbrotNumber number) {
        SingleNumberComputerRunnable computationRunnable = new SingleNumberComputerRunnable(
                number, this.maxIterations, this);

        this.executor.execute(computationRunnable);
    }
}
