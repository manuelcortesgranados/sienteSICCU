/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.Supervisor;

import co.com.interkont.cobra.to.Videoevolucionobra;
import cobra.SessionBeanCobra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author felipe
 */
public class Video  {

    private List<Videoevolucionobra> listadoVideos = new ArrayList<Videoevolucionobra>();

    public List<Videoevolucionobra> getListadoVideos() {
        return listadoVideos;
    }

    public void setListadoVideos(List<Videoevolucionobra> listadoVideos) {
        this.listadoVideos = listadoVideos;
    }

    public Video() {
        int codobra=186;
        cargarvideoobra(codobra);
    }

    private void cargarvideoobra(int codigobra) {

        Iterator videos = getSessionBeanCobra().getCobraService().obtenerVideosEvolucionxObra(codigobra).iterator();
        //Iterator videos = obramostrar.getVideoevolucionobras().iterator();
        listadoVideos = new ArrayList<Videoevolucionobra>();
        while (videos.hasNext()) {
            Videoevolucionobra video = (Videoevolucionobra) videos.next();
            listadoVideos.add(video);
        }

    }

     protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }


}
