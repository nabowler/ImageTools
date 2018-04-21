/**
 *
 */
package rel.wob.nah.tan.imagetools.create.motion;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rel.wob.nah.tan.imagetools.exception.CreationException;
import rel.wob.nah.tan.imagetools.exception.SourceException;
import rel.wob.nah.tan.imagetools.source.fixedsize.FixedSizeImageSource;

/**
 * Creates Motionagraphie images from a {@link FixedSizeImageSource}.
 *
 * Motionagraphie is an art style developed by Pat Little, or at least taught to
 * me by him. It uses the same single row of pixels from each image, stacking
 * them into the output image. This image shows how the perspective of a moving
 * subject captured in the source images changes over time.
 *
 * The classical setup is to rotate a subject in small, discrete steps and take
 * a photograph at each step. The smaller the step size, the smoother the curves
 * will be in your creation.
 *
 * An alternative setup is to rotate a subject on a low (< 2) rpm turntable and
 * record the subject with a high framerate. As with before, the lower the rpm
 * and the higher the framerate, the smoother the curves will be in your
 * creation.
 *
 * @author Nathan
 *
 */
public class MotionagraphieCreator {

    private static Logger logger = LoggerFactory.getLogger(MotionagraphieCreator.class);

    private MotionagraphieCreator() {
    }

    public static Map<Integer, BufferedImage> generate(FixedSizeImageSource source, double... pcts)
            throws CreationException {
        long start = System.nanoTime();
        if (pcts == null) {
            throw new NullPointerException();
        }

        if (pcts.length == 0) {
            pcts = new double[] { .5D };
        }

        for (double pct : pcts) {
            if (pct < 0.0D || pct > 1.0D) {
                throw new IllegalArgumentException(pct + " is out of range. Must be in [0,1]");
            }
        }
        Map<Integer, BufferedImage> outputImages = new HashMap<>();

        int imageHeight = source.getNumImages();
        int imageWidth = source.getImageWidth();

        for (double pct : pcts) {
            int lineIdx = (int) ((source.getImageHeight() - 1) * pct);
            BufferedImage outputImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            outputImages.put(lineIdx, outputImage);
        }
        int y = 0;
        try {
            for (; y < imageHeight; y++) {
                BufferedImage frame = source.nextImage();
                if (frame == null) {
                    logger.warn("Image " + y + " is null");
                    continue;
                }
                for (Entry<Integer, BufferedImage> entry : outputImages.entrySet()) {
                    for (int x = 0; x < imageWidth; x++) {
                        entry.getValue().setRGB(x, y, frame.getRGB(x, entry.getKey()));
                    }
                }
            }
        } catch (SourceException e) {
            throw new CreationException("Error retrieving frame " + y, e);
        }
        long elapsed = System.nanoTime() - start;
        long elapsedSeconds = (long) (elapsed / 1e9);
        logger.info("Motionagraphie(s) generated in " + elapsedSeconds + "seconds.");

        return outputImages;
    }

}
