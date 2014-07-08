/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.exception;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

/**
 *
 * @author Manuel Cortes Granados
 * @since 6 Julio 2014 9:11 AM
 */

public class CobraExceptionBean implements Serializable{
    
    boolean ambiente_pruebas=true; // Cambiar a false cuando se pase a produccion
    

    /**
     * @author Manuel Cortes Granados
     * @since 8 Julio 2014 9:25 AM
     * @return 
     */
    
    public boolean isAmbiente_pruebas() {
        return ambiente_pruebas;
    }
    
    /**
     * @author Manuel Cortes Granados
     * @since 8 Julio 2014 9:25 AM
     * @param ambiente_pruebas 
     */

    public void setAmbiente_pruebas(boolean ambiente_pruebas) {
        this.ambiente_pruebas = ambiente_pruebas;
    }
    
    /**
     * @author Manuel Cortes Granados
     * @since 6 Julio 2014 9:11 AM
     * @param exception
     * @return 
     */

    public String printStackTrace(Throwable exception) {
        StackTraceElement[] arreglo;
        arreglo=exception.getStackTrace();
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter, true));
        return stringWriter.toString();
    }
    
    

}
