/**
 *
 */
package rel.wob.nah.tan.imagetools.source;

import rel.wob.nah.tan.imagetools.monitor.progress.ProgressMonitor;

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
