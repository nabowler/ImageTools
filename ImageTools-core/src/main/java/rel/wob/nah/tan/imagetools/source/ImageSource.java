/**
 *
 */
package rel.wob.nah.tan.imagetools.source;

import java.awt.image.BufferedImage;
import java.io.Closeable;

import rel.wob.nah.tan.imagetools.exception.SourceException;

/**
 * A source of images.
 *
 * @author Nathan
 *
 */
public interface ImageSource extends Closeable {

    /**
     * Get the next image.
     *
     * @return The next image. May be null.
     * @throws SourceException
     *             if there is some issue with the backing source. The state of this
     *             ImageSource is undefined, and it may be unsafe to continue using
     *             this ImageSource, except for closing this source.
     */
    BufferedImage nextImage() throws SourceException;

    /**
     * Get the width of the next image.
     *
     * @return the width of the next image.
     */
    int getImageWidth();

    /**
     * Get the height of the next image.
     *
     * @return the height of the next image.
     */
    int getImageHeight();

    /**
     * Get the total number of images in a source. This should not change between
     * calls to next()
     *
     * @return The number of images in the source.
     */
    int getNumImages();

}
