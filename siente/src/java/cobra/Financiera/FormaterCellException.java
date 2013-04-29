/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.Financiera;

/**
 *
 * @author desarrollo5
 */
public class FormaterCellException extends RuntimeException{

    public FormaterCellException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormaterCellException(String message) {
        super(message);
    }

    public FormaterCellException(Throwable cause) {
        super(cause);
    }

}
