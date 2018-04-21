/**
 *
 */
package rel.wob.nah.tan.imagetools.create.math;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rel.wob.nah.tan.imagetools.exception.CreationException;
import rel.wob.nah.tan.imagetools.exception.SourceException;
import rel.wob.nah.tan.imagetools.source.MemoryImageSource;
import rel.wob.nah.tan.imagetools.source.fixedsize.FixedSizeImageSource;
import rel.wob.nah.tan.imagetools.source.fixedsize.ResizedImageSource;

/**
 * Create a series of images where each image is the average of the two
 * consecutive frames.
 *
 * @author Nathan
 *
 */
public class FrameMeanImageCreator {

    private static Logger logger = LoggerFactory.getLogger(FrameMeanImageCreator.class);

    private FrameMeanImageCreator() {
    }

    public static List<BufferedImage> generate(FixedSizeImageSource source) throws CreationException {
        long start = System.nanoTime();

        int numImages = source.getNumImages();
        List<BufferedImage> outImages = new ArrayList<>(source.getNumImages() - 1);
        int i = 0;
        try {
            if (numImages < 2) {
                return Arrays.asList(source.nextImage());
            }
            BufferedImage prev = null;
            for (; i < numImages && prev == null; i++) {
                prev = source.nextImage();
            }
            BufferedImage next;
            for (; i < numImages; i++) {
                next = source.nextImage();
                if (next == null) {
                    continue;
                }
                outImages.add(MeanImageCreator
                        .generate(ResizedImageSource.forDefaults(new MemoryImageSource(Arrays.asList(prev, next)),
                                source.getImageWidth(), source.getImageHeight())));
                prev = next;
            }
        } catch (SourceException e) {
            throw new CreationException("Unable to get image " + i, e);
        }

        long elapsed = System.nanoTime() - start;
        long elapsedSeconds = (long) (elapsed / 1e9);
        logger.info("Mean generated in " + elapsedSeconds + "seconds.");
        return outImages;
    }
}
