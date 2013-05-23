/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

//import co.com.interkont.cobra.dao.UsuarioDao;
//import co.com.interkont.cobra.dao.UsuarioDaoInterface;
import co.com.interkont.cobra.to.Eventoscorreo;
import co.com.interkont.cobra.to.Mensaje;
import co.com.interkont.cobra.to.Novedad;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Relacioneventoscorreousuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import cobra.SessionBeanCobra;
import java.util.LinkedHashSet;
import javax.faces.context.FacesContext;
import org.richfaces.component.UIDataTable;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 *
 * @version HomeSupervisor.java
 * @version Created on 13/11/2008, 09:42:21 PM
 * @author carlos
 */
public class HomeSupervisor  {

    private UIDataTable listaPropuestas = new UIDataTable();
    private UIDataTable listaSuspendidas = new UIDataTable();
    private UIDataTable listaVigentes = new UIDataTable();
    private UIDataTable listaHistoricas = new UIDataTable();
    private int selecfiltro = 0;
    private String palabraClave = "";
    private List<Obra> obrasvigentes;
    private List<Obra> obrashistoricas;
    private List<Obra> obrassuspendidas;
    private List<Obra> obrassuspendidasfilt;
    private List<Obra> obraspropuestas;
    private List<Obra> obraspropuestasfilt;
    private List<Obra> obrasvigentesfilt;
    private List<Obra> obrashistoricasfilt;
    private String valorFiltroMensajeRecibido = "";
    private List<Mensaje> listaMensajesRecibidos = new ArrayList<Mensaje>();
    private Mensaje mensajeRecibidoVer = new Mensaje();
    private UIDataTable tablaMensajesRecibidos = new UIDataTable();
//  private UsuarioDaoInterface usuarioDao = new UsuarioDao();
    private String valorFiltroNovedad = "";
    private List<Novedad> listaNovedades = new ArrayList<Novedad>();
    private List<Novedad> listaTotalNovedades = new ArrayList<Novedad>();
    private Novedad novedad = new Novedad();
    private UIDataTable tablaNovedades = new UIDataTable();
    private UIDataTable tablaNovedadesListado = new UIDataTable();
    private int opcionov = 0;
    public boolean esHistorico=false;
    public boolean onNovedades=false;
    private List<Eventoscorreo> liseventosseleccionados;
    private List<Eventoscorreo> listeventos;
    private  List<Eventoscorreo> evendisponibles = new ArrayList<Eventoscorreo>();
    private List<Relacioneventoscorreousuario> lisrela;

    public boolean isOnNovedades() {
        return onNovedades;
    }

    public void setOnNovedades(boolean onNovedades) {
        this.onNovedades = onNovedades;
    }

    public List<Relacioneventoscorreousuario> getLisrela() {
        return lisrela;
    }

    public void setLisrela(List<Relacioneventoscorreousuario> lisrela) {
        this.lisrela = lisrela;
    }


    public List<Eventoscorreo> getEvendisponibles() {
        return evendisponibles;
    }

    public void setEvendisponibles(List<Eventoscorreo> evendisponibles) {
        this.evendisponibles = evendisponibles;
    }


    public List<Eventoscorreo> getLiseventosseleccionados() {
        return liseventosseleccionados;
    }

    public void setLiseventosseleccionados(List<Eventoscorreo> liseventosseleccionados) {
        this.liseventosseleccionados = liseventosseleccionados;
    }



    public List<Eventoscorreo> getListeventos() {
        return listeventos;
    }

    public void setListeventos(List<Eventoscorreo> listeventos) {
        this.listeventos = listeventos;
    }



    public boolean isEsHistorico() {
        return esHistorico;
    }

    public void setEsHistorico(boolean esHistorico) {
        this.esHistorico = esHistorico;
    }

    public List<Novedad> getListaTotalNovedades() {
        return listaTotalNovedades;
    }

    public void setListaTotalNovedades(List<Novedad> listaTotalNovedades) {
        this.listaTotalNovedades = listaTotalNovedades;
    }

    public UIDataTable getListaSuspendidas() {
        return listaSuspendidas;
    }

    public void setListaSuspendidas(UIDataTable listaSuspendidas) {
        this.listaSuspendidas = listaSuspendidas;
    }

    public UIDataTable getListaPropuestas() {
        return listaPropuestas;
    }

    public void setListaPropuestas(UIDataTable listaPropuestas) {
        this.listaPropuestas = listaPropuestas;
    }

    public UIDataTable getListaHistoricas() {
        return listaHistoricas;
    }

    public void setListaHistoricas(UIDataTable listaHistoricas) {
        this.listaHistoricas = listaHistoricas;
    }

    public UIDataTable getListaVigentes() {
        return listaVigentes;
    }

    public void setListaVigentes(UIDataTable listaVigentes) {
        this.listaVigentes = listaVigentes;
    }


    public List<Obra> getObrashistoricasfilt() {
        return obrashistoricasfilt;
    }

    public void setObrashistoricasfilt(List<Obra> obrashistoricasfilt) {
        this.obrashistoricasfilt = obrashistoricasfilt;
    }

    public List<Obra> getObraspropuestasfilt() {
        return obraspropuestasfilt;
    }

    public void setObraspropuestasfilt(List<Obra> obraspropuestasfilt) {
        this.obraspropuestasfilt = obraspropuestasfilt;
    }

    public List<Obra> getObrasvigentesfilt() {
        return obrasvigentesfilt;
    }

    public void setObrasvigentesfilt(List<Obra> obrasvigentesfilt) {
        this.obrasvigentesfilt = obrasvigentesfilt;
    }

    public int getSelecfiltro() {
        return selecfiltro;
    }

    public void setSelecfiltro(int selecfiltro) {
        this.selecfiltro = selecfiltro;
    }

    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }

    public String getPalabraClave() {
        return palabraClave;
    }

    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
    }

    public List<Obra> getObrassuspendidas() {
        if (selecfiltro == 0) {
            return obrassuspendidas;
        } else {

            return obrassuspendidasfilt;
        }

    }

    public void setObrassuspendidas(List<Obra> obrassuspendidas) {
        this.obrassuspendidas = obrassuspendidas;
    }

    public List<Obra> getObrashistoricas() {
        if (selecfiltro == 0) {
            return obrashistoricas;
        } else {

            return obrashistoricasfilt;
        }
    }

    public List<Obra> getObrassuspendidasfilt() {
        return obrassuspendidasfilt;
    }

    public void setObrassuspendidasfilt(List<Obra> obrassuspendidasfilt) {
        this.obrassuspendidasfilt = obrassuspendidasfilt;
    }

    public void setObrashistoricas(List<Obra> obrashistoricas) {
        this.obrashistoricas = obrashistoricas;
    }

    public List<Obra> getObrasvigentes() {
        if (selecfiltro == 0) {
            return obrasvigentes;
        } else {

            return obrasvigentesfilt;
        }

    }

    public void setObrasvigentes(List<Obra> obrasvigentes) {
        this.obrasvigentes = obrasvigentes;
    }

    public List<Obra> getObraspropuestas() {
        if (selecfiltro == 0) {
            return obraspropuestas;
        } else {

            return obraspropuestasfilt;
        }
    }

    public void setObraspropuestas(List<Obra> obraspropuestas) {
        this.obraspropuestas = obraspropuestas;
    }

    public int getOpcionov() {
        return opcionov;
    }

    public void setOpcionov(int opcionov) {
        this.opcionov = opcionov;
    }

    public UIDataTable getTablaNovedadesListado() {
        return tablaNovedadesListado;
    }

    public void setTablaNovedadesListado(UIDataTable tablaNovedadesListado) {
        this.tablaNovedadesListado = tablaNovedadesListado;
    }

    public List<Novedad> getListaNovedades() {
        return listaNovedades;
    }

    public void setListaNovedades(List<Novedad> listaNovedades) {
        this.listaNovedades = listaNovedades;
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    public UIDataTable getTablaNovedades() {
        return tablaNovedades;
    }

    public void setTablaNovedades(UIDataTable tablaNovedades) {
        this.tablaNovedades = tablaNovedades;
    }

//    public UsuarioDaoInterface getUsuarioDao() {
//        return usuarioDao;
//    }
//
//    public void setUsuarioDao(UsuarioDaoInterface usuarioDao) {
//        this.usuarioDao = usuarioDao;
//    }

    public String getValorFiltroNovedad() {
        return valorFiltroNovedad;
    }

    public void setValorFiltroNovedad(String valorFiltroNovedad) {
        this.valorFiltroNovedad = valorFiltroNovedad;
    }

    public UIDataTable getTablaMensajesRecibidos() {
        return tablaMensajesRecibidos;
    }

    public void setTablaMensajesRecibidos(UIDataTable tablaMensajesRecibidos) {
        this.tablaMensajesRecibidos = tablaMensajesRecibidos;
    }

    public Mensaje getMensajeRecibidoVer() {
        return mensajeRecibidoVer;
    }

    public void setMensajeRecibidoVer(Mensaje mensajeRecibidoVer) {
        this.mensajeRecibidoVer = mensajeRecibidoVer;
    }

    public List<Mensaje> getListaMensajesRecibidos() {
        return listaMensajesRecibidos;
    }

    public void setListaMensajesRecibidos(List<Mensaje> listaMensajesRecibidos) {
        this.listaMensajesRecibidos = listaMensajesRecibidos;
    }

    public String getValorFiltroMensajeRecibido() {
        return valorFiltroMensajeRecibido;
    }

    public void setValorFiltroMensajeRecibido(String valorFiltroMensajeRecibido) {
        this.valorFiltroMensajeRecibido = valorFiltroMensajeRecibido;
    }
    //novedades

    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public HomeSupervisor() {


        if (getSessionBeanCobra().getUsuarioObra() != null && getSessionBeanCobra().getUsuarioObra().getTercero() != null) {
           
        } else {

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../Inicio.jsp");

                //this.getSessionMap().clear();
            } catch (IOException ex) {
                Logger.getLogger(HomeSupervisor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        llenarTablaNovedades();


    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public String button1_action() {
        // TODO: Replace with your code

        return "supervisor";
    }

    public String button3_action() {
        // TODO: Replace with your code
        try {
            getIngresarNuevaObra().limpiarobra();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return "ingresar";
    }

    public void cargarObrasUsuario() {
        obraspropuestas = new ArrayList<Obra>();
        obrasvigentes = new ArrayList<Obra>();
        obrashistoricas = new ArrayList<Obra>();
        obrassuspendidas = new ArrayList<Obra>();
      

        LinkedHashSet obrasusu = new LinkedHashSet(getSessionBeanCobra().getCobraService().encontrarObrasUsuario(getSessionBeanCobra().getUsuarioObra()));
        for (Iterator j = obrasusu.iterator(); j.hasNext();) {
            Obra obra = (Obra) j.next();
            switch (obra.getTipoestadobra().getIntestadoobra()) {
                ///Obra en Proceso
                case 0:
                    obraspropuestas.add(obra);
                    break;
                ///0- En proceso 1-Vigente 2 - Finalizada 3-En proceso de Modificacion 4- Finalizacion Acta Recibo Final 5- Ultima Acta de Recibo Final 6 - suspendida
                case 2:
                    obrashistoricas.add(obra);
                    break;
                case 1:
                case 3:
                case 4:
                case 5:
                    obrasvigentes.add(obra);
                    break;
                case 6:
                    obrassuspendidas.add(obra);
                    break;

            }


        }

    }

     public void cargarObrasVigentesThumbnailUsuario() {


        if(obrasvigentes!=null){
            
            for (Obra b : obrasvigentes) {
                      String nombcorto=b.getStrnombreobra().substring(0,20)+"...";
                      b.setStrnombrecrot(nombcorto);
                      String urlubica=b.getStrimagenobra().replace(" ", "%20");;
                      b.setStrimagenobra(urlubica);

            }


        }
      
    }



    public String txtPalabra_Clave_processValueChange() {

        if (palabraClave.compareTo("") != 0) {

            llenarTablaObrasFiltradas(palabraClave);
            selecfiltro = 1;
        } else {
            selecfiltro = 0;
        }
        return null;
    }

    public void llenarTablaObrasFiltradas(String valor) {
        obraspropuestasfilt = new ArrayList<Obra>();
        obrasvigentesfilt = new ArrayList<Obra>();
        obrashistoricasfilt = new ArrayList<Obra>();
        obrassuspendidasfilt = new ArrayList<Obra>();
        valor = valor.toUpperCase();
        Iterator obras = obraspropuestas.iterator();
        Obra obraselect;
        while (obras.hasNext()) {

            obraselect = new Obra();
            obraselect = (Obra) obras.next();
            if ((obraselect.getStrnombreobra().toUpperCase().startsWith(valor)) || (obraselect.getStrnombreobra().toUpperCase().endsWith(valor)) || (obraselect.getStrnombreobra().toUpperCase().contains(valor))) {
                obraspropuestasfilt.add(obraselect);
            }
        }

        obras = obrasvigentes.iterator();

        while (obras.hasNext()) {

            obraselect = new Obra();
            obraselect = (Obra) obras.next();
            if ((obraselect.getStrnombreobra().toUpperCase().startsWith(valor)) || (obraselect.getStrnombreobra().toUpperCase().endsWith(valor)) || (obraselect.getStrnombreobra().toUpperCase().contains(valor))) {
                obrasvigentesfilt.add(obraselect);
            }
        }

        obras = obrashistoricas.iterator();

        while (obras.hasNext()) {

            obraselect = new Obra();
            obraselect = (Obra) obras.next();
            if ((obraselect.getStrnombreobra().toUpperCase().startsWith(valor)) || (obraselect.getStrnombreobra().toUpperCase().endsWith(valor)) || (obraselect.getStrnombreobra().toUpperCase().contains(valor))) {
                obrashistoricasfilt.add(obraselect);
            }
        }

        obras = obrassuspendidas.iterator();

        while (obras.hasNext()) {

            obraselect = new Obra();
            obraselect = (Obra) obras.next();
            if ((obraselect.getStrnombreobra().toUpperCase().startsWith(valor)) || (obraselect.getStrnombreobra().toUpperCase().endsWith(valor)) || (obraselect.getStrnombreobra().toUpperCase().contains(valor))) {
                obrassuspendidasfilt.add(obraselect);
            }
        }
    }

    public String vigentes_action() {
        esHistorico=false;
        Obra ob = (Obra) listaVigentes.getRowData();
        //obraalimentar= (Obra) listaVigentes.getRowData();
        //getSessionBeanCobra().setCodigoobraalimentar(ob.getIntcodigoobra());
        //getAdministrarObra().limpiarAlimentar();
        //getAdministrarObra().cargarObra();

        getAdministrarObraNew().cargarObra(ob);
        getAdministrarObraNew().mostrarMenuModificar();
        return "administrar";
    }

    public String suspendidas_action() {
        esHistorico=false;
        Obra ob = (Obra) listaSuspendidas.getRowData();
        //obraalimentar= (Obra) listaVigentes.getRowData();
        //getSessionBeanCobra().setCodigoobraalimentar(ob.getIntcodigoobra());
        getAdministrarObraNew().cargarObra(ob);
        getAdministrarObraNew().mostrarMenuModificar();
        //getAdministrarObra().limpiarAlimentar();
        return "administrar";
    }

    public String finalizadas_action() {
        esHistorico = true;
        Obra ob = (Obra) listaHistoricas.getRowData();

        getAdministrarObraNew().cargarObra(ob);
        return "administrar";
    }

    public String propuestas_action() throws Exception {
        esHistorico=false;
        Obra ob = (Obra) listaPropuestas.getRowData();
        //obraalimentar= (Obra) listaVigentes.getRowData();
        //getSessionBeanCobra().setCodigoobraalimentar(ob.getIntcodigoobra());
        getIngresarNuevaObra().cargarObra(ob);
        //getEditarPropuesta().cargarObra();

        return "ingresar";
    }

//Novedades
    public String llenarTablaMensaejesRecibidos() {
        this.listaMensajesRecibidos.clear();
        Iterator itrtMensaje = getSessionBeanCobra().getUsuarioService().encontrarMensajesRecibidosUsuario(getSessionBeanCobra().getUsuarioObra().getTercero().getIntcodigo()).iterator();
        this.listaMensajesRecibidos = new ArrayList<Mensaje>();
        while (itrtMensaje.hasNext()) {
            this.listaMensajesRecibidos.add((Mensaje) itrtMensaje.next());
        }
        opcionov = 2;
        return null;
    }

    public boolean filtrarMensajesRecibidos(Object actual) {
        Mensaje msj = (Mensaje) actual;
        if (this.valorFiltroMensajeRecibido.length() == 0) {
            return true;
        }
        if (msj.getStrasuntomensaje().toLowerCase().contains(this.valorFiltroMensajeRecibido.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    public String bt_verrecibidos_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        this.mensajeRecibidoVer = (Mensaje) tablaMensajesRecibidos.getRowData();
        return null;
    }

    public String llenarTablaNovedades() {
        opcionov = 1;
        this.listaNovedades.clear();
        listaNovedades = getSessionBeanCobra().getUsuarioService().encontrarUltimasNovedadesUsuario(getSessionBeanCobra().getUsuarioObra().getTercero().getIntcodigo(),5);
        if(listaNovedades.isEmpty()){
            onNovedades=true;
        }
        return null;
    }

    public boolean filtrarNovedades(Object actual) {
        Novedad nov = (Novedad) actual;
        if (this.valorFiltroNovedad.length() == 0) {
            return true;
        }
        if (nov.getTiponovedad().getStrtiponovedad().toLowerCase().contains(this.valorFiltroNovedad.toLowerCase()) || nov.getObra().getStrnombreobra().toLowerCase().contains(this.valorFiltroNovedad.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Carga una obra dada una libra de novedades.
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return null
     */
    public String bt_verObra_action(int filaSeleccionada) {
        HomeSupervisor homeSupervisor = (HomeSupervisor) FacesUtils.getManagedBean("Supervisor$HomeSupervisor");
        novedad = homeSupervisor.getListaTotalNovedades().get(filaSeleccionada);
        
        getAdministrarObraNew().cargarObra(novedad.getObra());
        
        return "administrar";
    }

    public String bt_verObra_action_listado() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        this.novedad = (Novedad) tablaNovedadesListado.getRowData();
        //getSessionBeanCobra().setCodigoobraalimentar(this.novedad.getObra().getIntcodigoobra());
        getAdministrarObraNew().cargarObra(novedad.getObra());
        //getAdministrarObra().limpiarAlimentar();
        return "administrar";
    }

    protected IngresarNuevaObra getIngresarNuevaObra() {
        return (IngresarNuevaObra) FacesUtils.getManagedBean("Supervisor$IngresarNuevaObra");

    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    public String verTodaslasnovedades()
    {
         this.listaTotalNovedades.clear();
        listaTotalNovedades = getSessionBeanCobra().getUsuarioService().encontrarTodasNovedadesUsuario(getSessionBeanCobra().getUsuarioObra().getTercero().getIntcodigo());
        return null;
    }

//    public void correos() {
//        //lista de todos los eventos que existen
//        listeventos =getSessionBeanCobra().getUsuarioService().encontrarEventosCorreo();
//      // UsuarioDao usu = new UsuarioDao();
//        //retomo el usuario de la session
//         liseventosseleccionados = new ArrayList<Eventoscorreo>();
//        lisrela=getSessionBeanCobra().getUsuarioService().encontrarEventosRelacionados(getSessionBeanCobra().getUsuarioObra().getUsuId());
//      //  Iterator<Relacioneventoscorreousuario> itRelaEven=getSessionBeanCobra().getUsuarioObra().getRelacioneventoscorreousuarios().iterator();
//
//        Iterator<Relacioneventoscorreousuario> itRelaEven=lisrela.iterator();
//        while (itRelaEven.hasNext()) {
//            //lista de los eventos que el usuario ya tiene seleccionados Usados
//            liseventosseleccionados.add(itRelaEven.next().getEventoscorreo());
//        }
//        try {
//            int i = 0;
//            while (i < listeventos.size()) {
//                int j = 0;
//                boolean band = false;
//                while (j < liseventosseleccionados.size()) {
//                    //if (listeventos.get(i).getIntidevento() == liseventosseleccionados.get(j).getIntidevento()) {
//                    if (listeventos.get(i).getIntidevento() == liseventosseleccionados.get(j).getIntidevento()) {
//                        band = true;
//                    }
//                    j++;
//                }
//                if (!band) {
//                    //lista de los eventos disponibles
//
//                    evendisponibles.add(listeventos.get(i));
//                    //   liseventosdisporelacion.add(listeventos.get(i));
//                }
//                i++;
//            }
//
//          //  usuarioDao.cerrarSession();
////            try {
////                for (int ix = 0; ix <= evendisponibles.size(); ix++) {
////                }
////            } catch (Exception e) {
////            }
//
//        } catch (Exception e) {
//        }
//    }

}

