/**
 *
 */
package rel.wob.nah.tan.imagetools.source;

import java.awt.image.BufferedImage;
import java.io.IOException;

import rel.wob.nah.tan.imagetools.exception.SourceException;
import rel.wob.nah.tan.imagetools.transform.Transformer;

/**
 * Applies a {@Transformer} to the images of the decorated ImageSource.
 *
 * @author Nathan
 */
public class TransformedImageSource implements ImageSource {

    private Transformer transformer;

    private ImageSource source;

    /**
     *
     */
    public TransformedImageSource(ImageSource src, Transformer trans) {
        super();

    }

    @Override
    public void close() throws IOException {
        source.close();

    }

    @Override
    public BufferedImage nextImage() throws SourceException {
        BufferedImage next = source.nextImage();
        if (next == null) {
            return null;
        }
        return transformer.transform(next);
    }

    @Override
    public int getImageWidth() {
        return source.getImageWidth();
    }

    @Override
    public int getImageHeight() {
        return source.getImageHeight();
    }

    @Override
    public int getNumImages() {
        return source.getNumImages();
    }

}
