/**
 *
 */
package rel.wob.nah.tan.imagetools.etc;

/**
 * Does nothing.
 *
 * @author Nathan
 */
public class NopProgressMonitor implements ProgressMonitor {

    public NopProgressMonitor() {
        super();
    }

    @Override
    public void setTotalWork(int totalWork) {
    }

    @Override
    public void worked(int worked) {
    }

}
