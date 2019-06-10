package generator;

public class IterationsComputer {
    private static int ESCAPE_NUMBER = 1 << 16;

    private int maxIterations;

    public IterationsComputer(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public void computeIterations(MandelbrotNumber number) {
        double x = 0;
        double y = 0;
        int iterations = 0;

        while (iterations < this.maxIterations && !hasEscaped(x, y)) {
            double nextX = x * x - y * y + number.getRealPart().doubleValue()/* * Math.pow(Math.E, -x) * Math.cos(-y)*/;
            double nextY = 2 * x * y + number.getImaginaryPart().doubleValue()/* * Math.pow(Math.E, -x) * Math.sin(-y)*/;

            x = nextX;
            y = nextY;

            iterations++;
        }

        number.setIterations(iterations);
    }

    private boolean hasEscaped(double x, double y) {
        return Math.sqrt(x * x + y * y) > ESCAPE_NUMBER;
    }
}
