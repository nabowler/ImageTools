/**
 *
 */
package rel.wob.nah.tan.imagetools.source;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rel.wob.nah.tan.imagetools.exception.SourceException;

/**
 * Generates images by extracting the frames from Videos.
 *
 * @author Nathan
 */
public class VideoFrameSource implements FixedSizeImageSource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private FFmpegFrameGrabber frameGrabber;

    private Java2DFrameConverter converter;

    public VideoFrameSource(URL url) throws SourceException {
        super();

        /*
         * frameGrabber.getLengthInFrames() doesn't seem to work with InputStreams.
         * Write URL info to temp file and try loading from that.
         */
        Path tempPath;
        try {
            tempPath = Files.createTempFile("VideoFrameSource", ".untrusted");
        } catch (IOException e) {
            throw new SourceException("Cannot create temporary file.", e);
        }

        File tempFile = tempPath.toFile();
        tempFile.deleteOnExit();
        try (InputStream is = url.openStream()) {
            Files.copy(is, tempPath);
        } catch (IOException e) {
            throw new SourceException("Cannot populate temporary file.", e);
        }

        frameGrabber = new FFmpegFrameGrabber(tempFile);
        try {
            frameGrabber.start();
        } catch (Exception e) {
            throw new SourceException("Cannot start FrameGrabber.", e);
        }
        converter = new Java2DFrameConverter();
    }

    public VideoFrameSource(File f) throws SourceException {
        super();
        frameGrabber = new FFmpegFrameGrabber(f);
        try {
            frameGrabber.start();
        } catch (Exception e) {
            throw new SourceException("Cannot start FrameGrabber.", e);
        }
        converter = new Java2DFrameConverter();
    }

    @Override
    public BufferedImage nextImage() throws SourceException {
        Frame grabbed;
        try {
            grabbed = frameGrabber.grabImage();
        } catch (Exception e) {
            throw new SourceException(e.getMessage(), e);
        }
        if (grabbed == null) {
            logger.warn("Null video frame.");
            return null;
        }
        BufferedImage frame = converter.getBufferedImage(grabbed);
        if (frame == null) {
            logger.warn("Video frame doesn't contain an image.");
        }

        return frame;
    }

    @Override
    public int getImageWidth() {
        int imageWidth = frameGrabber.getImageWidth();
        return imageWidth;
    }

    @Override
    public int getImageHeight() {
        int imageHeight = frameGrabber.getImageHeight();
        return imageHeight;
    }

    @Override
    public int getNumImages() {
        // long lit = g.getLengthInTime();
        // double fr = ((int) (g.getFrameRate() * 100)) / 100D;
        // int numFrames = (int) ((lit * fr) / 1_000_000L);

        int numFrames = frameGrabber.getLengthInFrames();
        return numFrames;
    }

    @Override
    public void close() throws IOException {
        if (frameGrabber != null) {
            frameGrabber.close();
            frameGrabber = null;
        }
    }
}
