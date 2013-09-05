/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.resources.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource.ClassName;

/**
 *
 * @author Daniela
 */
public interface CssRecursos extends ClientBundle  {
   
    public CssRecursos INSTANCE = GWT.create(CssRecursos.class);  
    
    @Source("co/com/interkont/cobra/planoperativo/client/resources/images/Gantt.css")
    CssR commonsCss();
   
  
}
