package performancetest;

import java.math.BigDecimal;

public class TestOptions {
    private static final BigDecimal DEFAULT_PRECISION = BigDecimal.valueOf(0.002);
    private static final int DEFAULT_MAX_ITERATIONS = 10000;

    private int numberOfThreads;
    private BigDecimal precision;
    private int maxIterations;

    public TestOptions(int numberOfThreads) {
        this(numberOfThreads, DEFAULT_PRECISION, DEFAULT_MAX_ITERATIONS);
    }

    public TestOptions(int numberOfThreads, BigDecimal precision, int maxIterations) {
        this.numberOfThreads = numberOfThreads;
        this.precision = precision;
        this.maxIterations = maxIterations;
    }

    public BigDecimal getPrecision() {
        return this.precision;
    }

    public int getMaxIterations() {
        return this.maxIterations;
    }

    public int getNumberOfThreads() {
        return this.numberOfThreads;
    }
}
