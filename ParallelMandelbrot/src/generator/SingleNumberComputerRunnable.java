package generator;

public class SingleNumberComputerRunnable implements Runnable {
    private int maxIterations;
    private MandelbrotNumber number;
    private MandelbrotSetGenerator receiver;

    public SingleNumberComputerRunnable(MandelbrotNumber number, int maxIterations,
                                        MandelbrotSetGenerator receiver) {
        this.maxIterations = maxIterations;
        this.number = number;
        this.receiver = receiver;
    }

    @Override
    public void run() {
        IterationsComputer iterationComputer = new IterationsComputer(this.maxIterations);
        iterationComputer.computeIterations(this.number);

        this.receiver.computationIsCompleted(this.number);
    }
}
