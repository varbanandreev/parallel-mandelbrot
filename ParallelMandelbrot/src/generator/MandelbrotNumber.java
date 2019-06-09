package generator;

public class MandelbrotNumber {
    private double realPart;
    private double imaginaryPart;
    private int iterations;

    public MandelbrotNumber(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public double getRealPart() {
        return realPart;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    @Override
    public int hashCode() {
        String stringValue = String.format("%f%f%d", this.realPart, this.imaginaryPart, this.iterations);
        return stringValue.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MandelbrotNumber)) {
            return false;
        }

        return this.hashCode() == obj.hashCode();
    }
}
