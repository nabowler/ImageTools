/**
 *
 */
package rel.wob.nah.tan.imagetools.app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import rel.wob.nah.tan.imagetools.create.math.MeanImageCreator;
import rel.wob.nah.tan.imagetools.create.motion.MotionagraphieCreator;
import rel.wob.nah.tan.imagetools.exception.CreationException;
import rel.wob.nah.tan.imagetools.exception.SourceException;
import rel.wob.nah.tan.imagetools.monitor.progress.ConsoleProgressBar;
import rel.wob.nah.tan.imagetools.source.fixedsize.FixedSizeImageSource;
import rel.wob.nah.tan.imagetools.source.fixedsize.FixedSizeProgressMonitoredSource;
import rel.wob.nah.tan.imagetools.source.fixedsize.VideoFrameSource;

/**
 * TODO:
 * <ul>
 * <li>Allow for resizing</li>
 * <li>Run progress.</li>
 * <li>Any un-implemented creation modes</li>
 * <li>Flag on input file to specify source type, if non-video sources are
 * implemented.</li>
 * </ul>
 */
public class Main {

    private Main() {
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            printHelp();
        }

        RunModes mode = RunModes.valueOf(args[0].toUpperCase());
        if (mode == null) {
            printHelp();
        }

        File f = new File(args[1]);

        if (!f.isFile()) {
            System.err.println("Cannot find " + args[0]);
            System.exit(1);
        }

        try {
            switch (mode) {
            case MEAN:
                runMean(f, args);
                break;
            case MOTION:
                runMotion(f, args);
                break;
            default:
                System.out.println("This mode is unimplemented.");
                System.exit(2);
            }
        } catch (Throwable e) {
            System.err.println("Error: " + e.getMessage());
            // e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *
     * @param f
     *            input file
     * @param args
     *            motion filename [pct [pct...]]
     * @throws CreationException
     * @throws IOException
     * @throws SourceException
     */
    private static void runMotion(File f, String[] args) throws CreationException, IOException, SourceException {
        double[] pcts;
        if (args.length == 2) {
            pcts = new double[] { .5D };
        } else {
            pcts = new double[args.length - 2];
            for (int i = 2; i < args.length; i++) {
                try {
                    pcts[i - 2] = Double.parseDouble(args[i]);
                } catch (NumberFormatException e) {
                    System.err.println("Cannot parse " + args[i] + " into a double.");
                    System.exit(1);
                }
            }
        }

        try (FixedSizeImageSource source = new FixedSizeProgressMonitoredSource(new VideoFrameSource(f),
                new ConsoleProgressBar())) {
            Map<Integer, BufferedImage> images = MotionagraphieCreator.generate(source, pcts);

            for (Entry<Integer, BufferedImage> entry : images.entrySet()) {
                String outputName = f.getName() + "_" + entry.getKey() + ".png";
                ImageIO.write(entry.getValue(), "png", new File(outputName));
            }
        }
    }

    /**
     *
     * @param f
     *            input file
     * @param args
     * @throws CreationException
     * @throws IOException
     * @throws SourceException
     */
    private static void runMean(File f, String[] args) throws CreationException, IOException, SourceException {
        try (FixedSizeImageSource source = new FixedSizeProgressMonitoredSource(new VideoFrameSource(f),
                new ConsoleProgressBar())) {
            BufferedImage output = MeanImageCreator.generate(source);

            String outputName = f.getName() + "_mean.png";
            ImageIO.write(output, "png", new File(outputName));
        }
    }

    private static void printHelp() {
        System.out.println("arguments: mode InputFile (mode args)");
        System.out.println("  available modes: " + Arrays.deepToString(RunModes.values()));
        System.exit(1);
    }

    private static enum RunModes {
        MOTION, MEAN
    }

}
