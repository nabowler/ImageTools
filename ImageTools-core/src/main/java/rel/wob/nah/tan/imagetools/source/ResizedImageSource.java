/**
 *
 */
package rel.wob.nah.tan.imagetools.source;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import rel.wob.nah.tan.imagetools.exception.SourceException;
import rel.wob.nah.tan.imagetools.transform.Resizer;

/**
 * A {@link FixedSizeImageSource} that decorates an {@link ImageSource} and
 * resizes images that are not the desired size.
 *
 * TODO: barely tested
 *
 * @author Nathan
 */
public class ResizedImageSource implements FixedSizeImageSource {

    private ImageSource src;

    private Resizer resizer;

    private ResizedImageSource(ImageSource source, int width, int height, Map<RenderingHints.Key, Object> resizeHints) {
        this(source, new Resizer(width, height, resizeHints));
    }

    private ResizedImageSource(ImageSource source, Resizer res) {
        super();
        src = source;
        resizer = res;
    }

    @Override
    public BufferedImage nextImage() throws SourceException {
        BufferedImage next = src.nextImage();
        if (next == null || (next.getWidth() == resizer.getW() && next.getHeight() == resizer.getH())) {
            return next;
        }

        return resizer.resize(next);
    }

    @Override
    public int getNumImages() {
        return src.getNumImages();
    }

    @Override
    public void close() throws IOException {
        src.close();
    }

    @Override
    public int getImageWidth() {
        return resizer.getW();
    }

    @Override
    public int getImageHeight() {
        return resizer.getH();
    }

    public static ResizedImageSource forQuality(ImageSource source, int width, int height) {
        return new ResizedImageSource(source, width, height, Resizer.HIGH_QUALITY);
    }

    public static ResizedImageSource forSpeed(ImageSource source, int width, int height) {
        return new ResizedImageSource(source, width, height, Resizer.SPEED);
    }

    public static ResizedImageSource forDefaults(ImageSource source, int width, int height) {
        return new ResizedImageSource(source, width, height, Resizer.DEFAULTS);
    }

    public static ResizedImageSource forResizer(ImageSource source, Resizer resizer) {
        return new ResizedImageSource(source, resizer);
    }
}
