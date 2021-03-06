/**
 *
 */
package rel.wob.nah.tan.imagetools.monitor.progress;

import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Nathan
 *
 */
public class ConsoleProgressBar implements ProgressMonitor {
    private int totalWorkUnits = -1;

    private int workedUnits = 0;

    private String lastOutput = "";

    private PrintStream printStream;

    /**
     *
     */
    public ConsoleProgressBar() {
        this(System.out);
    }

    /**
     *
     * @param ps
     */
    public ConsoleProgressBar(PrintStream ps) {
        super();
        printStream = ps;
    }

    @Override
    public void setTotalWork(int totalWork) {
        totalWorkUnits = totalWork;
    }

    @Override
    public void worked(int worked) {
        workedUnits += worked;
        if (totalWorkUnits > 0) {
            StringBuilder progress = new StringBuilder(15);
            int pct = (int) ((((double) workedUnits) / totalWorkUnits) * 100);
            for (int i = 10; i <= pct; i += 10) {
                progress.append("=");
            }
            for (int i = progress.length(); i < 10; i++) {
                progress.append(" ");
            }
            progress.append("|");
            if (pct < 10) {
                progress.append("  ");
            } else if (pct < 100) {
                progress.append(" ");
            }
            progress.append(pct).append("%\r");
            if (pct == 0) {
                progress.append("\n");
            }
            if (!progress.toString().equals(lastOutput)) {
                try {
                    lastOutput = progress.toString();
                    printStream.write(lastOutput.getBytes());
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

}
