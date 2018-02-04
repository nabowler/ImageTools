/**
 *
 */
package rel.wob.nah.tan.imagetools.monitor.progress;

/**
 * @author Nathan
 *
 */
public interface ProgressMonitor {

    void setTotalWork(int totalWork);

    void worked(int worked);

    default void worked() {
        worked(1);
    }

}
