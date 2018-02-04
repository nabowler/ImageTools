/**
 *
 */
package rel.wob.nah.tan.imagetools.transform;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * {@link Transformer} that converts BufferedImages to Grayscale.
 *
 * @author Nathan
 */
public class Grayscaler implements Transformer {

    /**
     *
     */
    public Grayscaler() {
        super();
    }

    @Override
    public BufferedImage transform(BufferedImage src) {
        return toGrayscale(src);
    }

    public static BufferedImage toGrayscale(BufferedImage src) {
        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = dest.getGraphics();
        g.drawImage(src, 0, 0, null);
        g.dispose();
        return dest;
    }

}
