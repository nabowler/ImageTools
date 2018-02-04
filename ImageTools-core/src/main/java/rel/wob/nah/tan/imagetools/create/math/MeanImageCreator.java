/**
 *
 */
package rel.wob.nah.tan.imagetools.create.math;

import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rel.wob.nah.tan.imagetools.exception.CreationException;
import rel.wob.nah.tan.imagetools.exception.SourceException;
import rel.wob.nah.tan.imagetools.source.FixedSizeImageSource;

/**
 * Create a single image where each pixel is the average of the corresponding
 * pixels of each source image.
 *
 * @author Nathan
 *
 */
public class MeanImageCreator {

    private static Logger logger = LoggerFactory.getLogger(MeanImageCreator.class);

    private MeanImageCreator() {
    }

    public static BufferedImage generate(FixedSizeImageSource source) throws CreationException {
        long start = System.nanoTime();

        int width = source.getImageWidth();
        int height = source.getImageHeight();
        int numImages = source.getNumImages();
        RGBAverage[][] averages = new RGBAverage[width][height];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                averages[w][h] = new RGBAverage();
            }
        }
        for (int i = 0; i < numImages; i++) {
            BufferedImage in;
            try {
                in = source.nextImage();
            } catch (SourceException e) {
                throw new CreationException("Unable to get image " + i, e);
            }
            if (in == null) {
                continue;
            }
            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    averages[w][h].add(in.getRGB(w, h));
                }
            }
        }
        BufferedImage meanImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                meanImage.setRGB(w, h, averages[w][h].get());
            }
        }
        long elapsed = System.nanoTime() - start;
        long elapsedSeconds = (long) (elapsed / 1e9);
        logger.info("Mean generated in " + elapsedSeconds + "seconds.");
        return meanImage;
    }

    /**
     * Maintains cumulative moving averages for the rgb channels of colors.
     *
     * https://en.wikipedia.org/wiki/Moving_average#Cumulative_moving_average
     */
    private static class RGBAverage {
        private double rAvg = 0;
        private double gAvg = 0;
        private double bAvg = 0;
        private int count = 0;

        RGBAverage() {
            super();
        }

        void add(int rgb) {
            count++;
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = (rgb >> 0) & 0xFF;

            rAvg += (r - rAvg) / count;
            gAvg += (g - gAvg) / count;
            bAvg += (b - bAvg) / count;
        }

        int get() {
            int a = 0xFF << 24;
            int r = ((int) rAvg) << 16;
            int g = ((int) gAvg) << 8;
            int b = (int) bAvg;
            return a + r + g + b;
        }
    }
}
