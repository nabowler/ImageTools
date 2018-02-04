/**
 *
 */
package rel.wob.nah.tan.imagetools.transform;

import java.awt.image.BufferedImage;

/**
 * Interface for classes that transform a image.
 *
 * @author Nathan
 */
public interface Transformer {
    BufferedImage transform(BufferedImage src);
}
