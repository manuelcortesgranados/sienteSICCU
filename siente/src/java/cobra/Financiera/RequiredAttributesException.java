/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.Financiera;

/**
 *
 * @author desarrollo5
 */
public class RequiredAttributesException extends RuntimeException {

    public RequiredAttributesException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequiredAttributesException(String message) {
        super(message);
    }

    public RequiredAttributesException(Throwable cause) {
        super(cause);
    }
}
