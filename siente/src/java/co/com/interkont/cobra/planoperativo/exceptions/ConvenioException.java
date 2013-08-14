/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.exceptions;

/**
 *
 * @author desarrollo2
 */
public class ConvenioException extends RuntimeException{
     public ConvenioException() {
        super();
    }

    public ConvenioException(Throwable cause) {
        super(cause);
    }

    public ConvenioException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvenioException(String message) {
        super(message);       
        
    }
}
