/**
 *
 */
package rel.wob.nah.tan.imagetools.source;

import java.awt.image.BufferedImage;
import java.io.IOException;

import rel.wob.nah.tan.imagetools.exception.SourceException;
import rel.wob.nah.tan.imagetools.monitor.progress.ProgressMonitor;

/**
 * @author Nathan
 *
 */
public class ProgressMonitoredSource implements ImageSource {

    private ImageSource source;

    private ProgressMonitor monitor;

    /**
     *
     */
    public ProgressMonitoredSource(ImageSource src, ProgressMonitor mon) {
        super();
        source = src;
        monitor = mon;
        monitor.setTotalWork(getNumImages());
    }

    @Override
    public void close() throws IOException {
        source.close();
    }

    @Override
    public BufferedImage nextImage() throws SourceException {
        monitor.worked();
        return source.nextImage();
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
