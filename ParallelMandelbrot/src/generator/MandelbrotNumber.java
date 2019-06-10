package generator;

import java.math.BigDecimal;

public class MandelbrotNumber {
    private BigDecimal realPart;
    private BigDecimal imaginaryPart;
    private int iterations;

    public MandelbrotNumber(BigDecimal realPart, BigDecimal imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public BigDecimal getRealPart() {
        return realPart;
    }

    public BigDecimal getImaginaryPart() {
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
        String stringValue = String.format("%f%f", this.realPart, this.imaginaryPart);
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
