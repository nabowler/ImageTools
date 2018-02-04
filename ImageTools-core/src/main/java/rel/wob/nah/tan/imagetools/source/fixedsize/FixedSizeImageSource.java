/**
 *
 */
package rel.wob.nah.tan.imagetools.source.fixedsize;

import rel.wob.nah.tan.imagetools.source.ImageSource;

/**
 * A source of images that are guaranteed to be the same size..
 *
 * @author Nathan
 *
 */
public interface FixedSizeImageSource extends ImageSource {

    /**
     * Get the width of the all images.
     *
     * @return the width of the all images.
     */
    @Override
    int getImageWidth();

    /**
     * Get the height of the all images.
     *
     * @return the height of the all images.
     */
    @Override
    int getImageHeight();
}
