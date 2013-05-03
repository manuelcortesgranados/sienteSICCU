/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.Supervisor;

import co.com.interkont.cobra.to.Imagenevolucionobra;

import java.util.List;
import cobra.SessionBeanCobra;
import java.util.ArrayList;

/**
 *
 * @author felipe
 */
public class Imagenes  {

    public List<Imagenevolucionobra> imagevo =new ArrayList<Imagenevolucionobra>();
    
    public Imagenes() {        
        
        int idobra=2;
        imagevo= getSessionBeanCobra().getCobraService().obtenerImagenesEvolucionxObra(idobra);

        for (Imagenevolucionobra b : imagevo) {

                      String urlimg=b.getStrubicacion().replace(" ", "%20");
                      b.setStrubicacion(urlimg);

            }

    }

    public List<Imagenevolucionobra> getImagevo() {
        if(imagevo==null){
           imagevo =new ArrayList<Imagenevolucionobra>();
        }
        return imagevo;
    }

    public void setImagevo(List<Imagenevolucionobra> imagevo) {
        this.imagevo = imagevo;
    }


    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

}
