/**
 *
 */
package rel.wob.nah.tan.imagetools.exception;

/**
 * @author Nathan
 *
 */
public class CreationException extends Exception {

    private static final long serialVersionUID = -7216916875240866909L;

    public CreationException() {
        super();
    }

    public CreationException(String message) {
        super(message);
    }

    public CreationException(Throwable cause) {
        super(cause);
    }

    public CreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
