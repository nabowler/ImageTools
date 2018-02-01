/**
 *
 */
package rel.wob.nah.tan.imagetools.exception;

/**
 * @author Nathan
 */
public class SourceException extends Exception {

    private static final long serialVersionUID = -9024421861407935216L;

    public SourceException() {
        super();
    }

    public SourceException(String message) {
        super(message);
    }

    public SourceException(Throwable cause) {
        super(cause);
    }

    public SourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
