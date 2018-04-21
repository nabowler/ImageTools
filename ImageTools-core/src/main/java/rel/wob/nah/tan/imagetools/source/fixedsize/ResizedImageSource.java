/**
 *
 */
package rel.wob.nah.tan.imagetools.source.fixedsize;

import java.awt.RenderingHints;
import java.util.Map;

import rel.wob.nah.tan.imagetools.source.ImageSource;
import rel.wob.nah.tan.imagetools.source.TransformedImageSource;
import rel.wob.nah.tan.imagetools.transform.Resizer;

/**
 * A {@link FixedSizeImageSource} that decorates an {@link ImageSource} and
 * resizes images to the desired size.
 *
 * TODO: barely tested
 *
 * @author Nathan
 */
public class ResizedImageSource extends TransformedImageSource implements FixedSizeImageSource {
    private Resizer resizer;

    private ResizedImageSource(ImageSource source, int width, int height, Map<RenderingHints.Key, Object> resizeHints) {
        this(source, new Resizer(width, height, resizeHints));
    }

    private ResizedImageSource(ImageSource source, Resizer res) {
        super(source, res);
        resizer = res;
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

    @Override
    public int getImageWidth() {
        return resizer.getW();
    }

    @Override
    public int getImageHeight() {
        return resizer.getH();
    }
}
