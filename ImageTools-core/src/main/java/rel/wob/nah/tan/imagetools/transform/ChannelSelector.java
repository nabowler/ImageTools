/**
 *
 */
package rel.wob.nah.tan.imagetools.transform;

import java.awt.image.BufferedImage;

/**
 * Transforms images by including only the selected channels.
 *
 * @author Nathan
 */
public class ChannelSelector implements Transformer {
    private int mask;

    /**
     *
     * @param outputRed
     *            include the red channel in the transformed image.
     * @param outputGreen
     *            include the green channel in the transformed image
     * @param outputBlue
     *            include the blue channel in the transformed image
     */
    public ChannelSelector(boolean outputRed, boolean outputGreen, boolean outputBlue) {
        super();
        mask = 0xFF000000;

        if (outputRed) {
            mask |= 0x00FF0000;
        }
        if (outputGreen) {
            mask |= 0x0000FF00;
        }
        if (outputBlue) {
            mask |= 0x000000FF;
        }
    }

    @Override
    public BufferedImage transform(BufferedImage src) {
        if (mask == -1) {
            return src;
        }

        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < dest.getWidth(); x++) {
            for (int y = 0; y < dest.getHeight(); y++) {
                dest.setRGB(x, y, src.getRGB(x, y) & mask);
            }
        }

        return dest;
    }

}
