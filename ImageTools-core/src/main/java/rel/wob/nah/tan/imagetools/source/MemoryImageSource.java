/**
 *
 */
package rel.wob.nah.tan.imagetools.source;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rel.wob.nah.tan.imagetools.exception.SourceException;

/**
 * Preloads the images from an {@link ImageSource} into memory. The original
 * ImageSource is not retained, and should be closed
 *
 * TODO: Untested.
 *
 * @author Nathan
 *
 */
public class MemoryImageSource implements ImageSource {

    private List<BufferedImage> images;

    private int curIdx = 0;

    /**
     * @throws SourceException
     */
    public MemoryImageSource(ImageSource src) throws SourceException {
        super();
        images = new ArrayList<>(src.getNumImages());
        for (int i = 0; i < images.size(); i++) {
            images.add(src.nextImage());
        }
    }

    @Override
    public void close() throws IOException {
        images.clear();
        images = null;
    }

    @Override
    public BufferedImage nextImage() throws SourceException {
        if (curIdx >= images.size()) {
            return null;
        }
        return images.get(curIdx++);
    }

    @Override
    public int getImageWidth() {
        if (images != null || curIdx >= images.size() - 1) {
            BufferedImage next = images.get(curIdx + 1);
            if (next == null) {
                return 0;
            }
            return next.getWidth();
        }
        return 0;
    }

    @Override
    public int getImageHeight() {
        if (images != null || curIdx >= images.size() - 1) {
            BufferedImage next = images.get(curIdx + 1);
            if (next == null) {
                return 0;
            }
            return next.getHeight();
        }
        return 0;
    }

    @Override
    public int getNumImages() {
        if (images != null) {
            return images.size();
        }
        return 0;
    }

}
