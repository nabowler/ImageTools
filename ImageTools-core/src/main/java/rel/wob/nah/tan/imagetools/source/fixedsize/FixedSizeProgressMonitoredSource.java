/**
 *
 */
package rel.wob.nah.tan.imagetools.source.fixedsize;

import rel.wob.nah.tan.imagetools.monitor.progress.ProgressMonitor;
import rel.wob.nah.tan.imagetools.source.ProgressMonitoredSource;

/**
 * @author Nathan
 */
public class FixedSizeProgressMonitoredSource extends ProgressMonitoredSource implements FixedSizeImageSource {

    /**
     * @param src
     * @param mon
     */
    public FixedSizeProgressMonitoredSource(FixedSizeImageSource src, ProgressMonitor mon) {
        super(src, mon);
    }

}
