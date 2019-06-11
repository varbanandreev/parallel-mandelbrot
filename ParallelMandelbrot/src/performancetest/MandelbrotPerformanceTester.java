package performancetest;

import generator.MandelbrotNumber;
import generator.MandelbrotSetGenerator;
import generator.MandelbrotSetReceiver;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MandelbrotPerformanceTester implements MandelbrotSetReceiver {
    private static final String TEST_RESULT_MESSAGE_FORMAT = "Number of cores %d. Max iterations %d. Precision %f. Elapsed time (nano) %d. Generated numbers %d.";

    private static Logger LOGGER = Logger.getLogger(MandelbrotPerformanceTester.class.getName());

    private boolean isRunning = false;
    private long testStartNanos = -1;

    private MandelbrotSetGenerator generator;

    private TestOptions testConfiguration;

    private Queue<TestOptions> queuedTests = new ArrayDeque<>();

    private void runNextTestTask() {
        isRunning = true;
        TestOptions nextTest = queuedTests.poll();
        startGeneration(nextTest);
    }

    private void startGeneration(TestOptions config) {
        testConfiguration = config;
        generator = new MandelbrotSetGenerator(testConfiguration, this);

        testStartNanos = System.nanoTime();
        generator.generateNumbers(testConfiguration.getPrecision());
    }

    public void startPerformanceTest(TestOptions config) {
        synchronized (queuedTests) {
            queuedTests.offer(config);

            if (!isRunning) {
                this.runNextTestTask();
            }
        }
    }

    @Override
    public void receiveSet(List<MandelbrotNumber> numbers) {
        long testEndNanos = System.nanoTime();
        generator.shutdown();

        String testMessage = String.format(TEST_RESULT_MESSAGE_FORMAT,
                testConfiguration.getNumberOfThreads(),
                testConfiguration.getMaxIterations(),
                testConfiguration.getPrecision(),
                testEndNanos - testStartNanos,
                numbers.size());

        LOGGER.log(Level.INFO, testMessage);

        synchronized (queuedTests) {
            if (!queuedTests.isEmpty()) {
                runNextTestTask();
            } else {
                isRunning = false;
            }
        }
    }
}
