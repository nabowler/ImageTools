/**
 *
 */
package rel.wob.nah.tan.imagetools.transform;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * {@link Transformer} that resizes images.
 *
 * TODO: untested.
 *
 * @author Nathan
 */
public class Resizer implements Transformer {

    public static final Map<RenderingHints.Key, Object> HIGH_QUALITY;

    public static final Map<RenderingHints.Key, Object> SPEED;

    public static final Map<RenderingHints.Key, Object> DEFAULTS;

    static {
        Map<RenderingHints.Key, Object> hints = new HashMap<>();
        hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        HIGH_QUALITY = Collections.unmodifiableMap(hints);

        hints = new HashMap<>();
        hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        SPEED = Collections.unmodifiableMap(hints);

        hints = new HashMap<>();
        hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        DEFAULTS = Collections.unmodifiableMap(hints);
    }

    private int w;

    private int h;

    private Map<RenderingHints.Key, Object> hints;

    public Resizer(int width, int height) {
        this(width, height, DEFAULTS);
    }

    public Resizer(int width, int height, Map<RenderingHints.Key, Object> renderingHints) {
        super();
        w = width;
        h = height;
        hints = renderingHints;
    }

    @Override
    public BufferedImage transform(BufferedImage src) {
        if (src.getWidth() == w && src.getHeight() == h) {
            return src;
        }

        BufferedImage resized = new BufferedImage(w, h,
                src.getType() != 0 ? src.getType() : BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();
        g.setComposite(AlphaComposite.Src);

        if (hints != null) {
            for (Entry<RenderingHints.Key, Object> entry : hints.entrySet()) {
                g.setRenderingHint(entry.getKey(), entry.getValue());
            }
        }

        g.drawImage(src, 0, 0, w, h, null);
        g.dispose();
        return resized;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public Map<RenderingHints.Key, Object> getHints() {
        return Collections.unmodifiableMap(hints);
    }
}
