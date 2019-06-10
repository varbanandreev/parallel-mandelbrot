package generator;

import java.util.List;

public interface MandelbrotSetReceiver {
    void receiveSet(List<MandelbrotNumber> numbers);
}
