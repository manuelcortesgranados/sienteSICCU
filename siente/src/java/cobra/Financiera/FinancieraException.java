/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.Financiera;

/**
 *
 * @author desarrollo5
 */
public class FinancieraException extends Exception{

    public FinancieraException(String message, Throwable cause) {
        super(message, cause);
    }

    public FinancieraException(String message) {
        super(message);
    }

    public FinancieraException(Throwable cause) {
        super(cause);
    }
     public FinancieraException(String message,String id) {
        super(message+" "+id);
     }

}
