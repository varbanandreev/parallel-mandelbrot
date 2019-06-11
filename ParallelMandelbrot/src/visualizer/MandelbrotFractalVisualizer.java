package visualizer;

import generator.MandelbrotNumber;
import generator.MandelbrotSetGenerator;
import generator.MandelbrotSetReceiver;
import generator.Pixel;
import main.Options;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MandelbrotFractalVisualizer implements MandelbrotSetReceiver {
    private static final int MAX_ITERATIONS = 1000;

    private static final Color[] PALETTE = {Color.YELLOW, Color.WHITE, Color.GREEN, Color.RED,
            Color.BLACK, Color.CYAN};

    private Options options;

    private BufferedImage canvas;

    private MandelbrotSetGenerator generator;

    public MandelbrotFractalVisualizer(Options options) {
        this.options = options;
        this.canvas = new BufferedImage(options.getPictureWidth(),
                options.getPictureHeight(), BufferedImage.TYPE_INT_ARGB);
    }

    public void drawFractal() {
        this.generator = new MandelbrotSetGenerator(options, MAX_ITERATIONS, this);
        this.generator.generatePixels();
    }

    @Override
    public void receiveSet(List<MandelbrotNumber> numbers) {
        this.generator.shutdown();

        Map<MandelbrotNumber, Pixel> pixelMap = this.generator.getPixelMapping();

        for (MandelbrotNumber number : numbers) {
            Color currentColor = getColor(number.getIterations());
            Pixel pixel = pixelMap.get(number);

            this.canvas.setRGB(pixel.getX(), pixel.getY(), currentColor.getRGB());
        }

        File out = new File(options.getFileName());

        try {
            out.createNewFile();
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING,
                    "Output image file creation failed.", e);

        }

        try {
            ImageIO.write(this.canvas, "png", out);
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING,
                    "Image to file dump failed.", e);
        }
    }

    private Color getColor(int iterations) {
        return PALETTE[iterations % PALETTE.length];
    }
}
