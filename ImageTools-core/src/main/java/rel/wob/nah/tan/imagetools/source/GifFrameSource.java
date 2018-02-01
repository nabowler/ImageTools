/**
 *
 */
package rel.wob.nah.tan.imagetools.source;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import rel.wob.nah.tan.imagetools.exception.SourceException;

/**
 * Extracts the frames from animated GIFs.
 *
 * TODO: Untested
 *
 * @author Nathan
 */
public class GifFrameSource implements FixedSizeImageSource {

    private ImageReader reader;

    private ImageInputStream stream;

    private int curIdx = -1;

    private int numFrames;

    private int height;

    private int width;

    /**
     *
     */
    public GifFrameSource(URL url) throws SourceException {
        super();
        try (InputStream is = url.openStream()) {
            load(url);
        } catch (IOException e) {
            throw new SourceException("Unable to load from URL.", e);
        }
    }

    public GifFrameSource(File f) throws SourceException, MalformedURLException {
        this(f.toURI().toURL());
    }

    private void load(URL url) {
        Iterator<ImageReader> gifReaders = ImageIO.getImageReadersByFormatName("gif");
        while (gifReaders.hasNext()) {
            ImageReader locReader = gifReaders.next();
            try (InputStream is = url.openStream(); ImageInputStream iis = ImageIO.createImageInputStream(is)) {
                locReader.setInput(iis);

                numFrames = locReader.getNumImages(true);
                height = locReader.getHeight(0);
                width = locReader.getWidth(0);
                locReader.reset();
                stream = ImageIO.createImageInputStream(url.openStream());
                locReader.setInput(stream);
                reader = locReader;
                break;
            } catch (Exception e) {
                continue;
            }
        }
    }

    @Override
    public BufferedImage nextImage() throws SourceException {
        if (curIdx >= numFrames - 1) {
            return null;
        }
        try {
            BufferedImage frame = reader.read(++curIdx);
            return frame;
        } catch (IOException e) {
            throw new SourceException("Error reading frame " + curIdx, e);
        }
    }

    @Override
    public int getNumImages() {
        return numFrames;
    }

    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.reset();
            reader.dispose();
            reader = null;
        }

        if (stream != null) {
            try {
                stream.close();
                stream = null;
            } catch (Exception e) {
                // ignore.
            }
        }
    }

    @Override
    public int getImageWidth() {
        return width;
    }

    @Override
    public int getImageHeight() {
        return height;
    }

}
