/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Financiera;

import co.com.interkont.cobra.to.Encargofiduciario;
import co.com.interkont.cobra.to.JsfUsuario;
import co.com.interkont.cobra.to.Ordendepago;
import co.com.interkont.cobra.to.SolicitudObrach;
import co.com.interkont.cobra.to.Solicitudmaestro;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipoordendepago;
import cobra.Archivo;
import cobra.CargadorArchivosWeb;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import com.interkont.cobra.exception.ArchivoExistenteException;

import financiera.service.FinancieraServiceAble;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.richfaces.component.UIDataTable;

/**
 *
 * @author desarrollo10
 */
public class FiduFinanciera  {

    private List<Encargofiduciario> listaencargofiducario = new ArrayList<Encargofiduciario>();
    private CargadorArchivosWeb subirDocumento = new CargadorArchivosWeb();
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private UIDataTable tablaTerceroListado = new UIDataTable();
    private UIDataTable tablaEncargoListado = new UIDataTable();
    private UIDataTable tablaOrdenListado = new UIDataTable();
    private boolean aplicafiltro = false;
    private int pagina = 0;
    private int totalpaginas = 0;
    private int totalfilas = 0;
    private int totalfilasencargo = 0;
    private int totalpaginasencargo = 0;
    private boolean veranteriorreales = false;
    private boolean verultimosreales = false;
    private boolean editarorden;
    private SelectItem[] seTipoorden;
    private String filtro = "";
    private int intencargofidu = 0;
    private int filtroint;

    public int getTotalfilasencargo() {
        return totalfilasencargo;
    }

    public void setTotalfilasencargo(int totalfilasencargo) {
        this.totalfilasencargo = totalfilasencargo;
    }

    public int getTotalpaginasencargo() {
        return totalpaginasencargo;
    }

    public void setTotalpaginasencargo(int totalpaginasencargo) {
        this.totalpaginasencargo = totalpaginasencargo;
    }

    public int getIntencargofidu() {
        return intencargofidu;
    }

    public void setIntencargofidu(int intencargofidu) {
        this.intencargofidu = intencargofidu;
    }

    public UIDataTable getTablaOrdenListado() {
        return tablaOrdenListado;
    }

    public void setTablaOrdenListado(UIDataTable tablaOrdenListado) {
        this.tablaOrdenListado = tablaOrdenListado;
    }

    public boolean isEditarorden() {
        return editarorden;
    }

    public void setEditarorden(boolean editarorden) {
        this.editarorden = editarorden;
    }

    public int getFiltroint() {
        return filtroint;
    }

    public void setFiltroint(int filtroint) {
        this.filtroint = filtroint;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public UIDataTable getTablaEncargoListado() {
        return tablaEncargoListado;
    }

    public void setTablaEncargoListado(UIDataTable tablaEncargoListado) {
        this.tablaEncargoListado = tablaEncargoListado;
    }

    public SelectItem[] getSeTipoorden() {
        return seTipoorden;
    }

    public void setSeTipoorden(SelectItem[] seTipoorden) {
        this.seTipoorden = seTipoorden;
    }

    public FinancieraServiceAble getFinancieraService() {
        return getSessionBeanCobra().getFinancieraService();
    }

    public boolean isVeranteriorreales() {
        return veranteriorreales;
    }

    public void setVeranteriorreales(boolean veranteriorreales) {
        this.veranteriorreales = veranteriorreales;
    }

    public boolean isVerultimosreales() {
        return verultimosreales;
    }

    public void setVerultimosreales(boolean verultimosreales) {
        this.verultimosreales = verultimosreales;
    }

    public int getTotalpaginas() {
        return totalpaginas;
    }

    public void setTotalpaginas(int totalpaginas) {
        this.totalpaginas = totalpaginas;
    }

    public boolean isAplicafiltro() {
        return aplicafiltro;
    }

    public void setAplicafiltro(boolean aplicafiltro) {
        this.aplicafiltro = aplicafiltro;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getTotalfilas() {
        return totalfilas;
    }

    public void setTotalfilas(int totalfilas) {
        this.totalfilas = totalfilas;
    }

    public UIDataTable getTablaTerceroListado() {
        return tablaTerceroListado;
    }

    public void setTablaTerceroListado(UIDataTable tablaTerceroListado) {
        this.tablaTerceroListado = tablaTerceroListado;
    }

    public List<Encargofiduciario> getListaencargofiducario() {
        return listaencargofiducario;
    }

    public void setListaencargofiducario(List<Encargofiduciario> listaencargofiducario) {
        this.listaencargofiducario = listaencargofiducario;
    }

    public FiduFinanciera() throws Exception {
        llenarTiposorden();
        getFinancieraService().getOrdendepago().setTipoordendepago(new Tipoordendepago());
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public CargadorArchivosWeb getSubirDocumento() {
        return subirDocumento;
    }

    public void setSubirDocumento(CargadorArchivosWeb subirDocumento) {
        this.subirDocumento = subirDocumento;
    }

    public void subirDocumento() {
        String mensaje = "";
        String realArchivoPath = "";
        String URL = "/resources/Documentos/Financiera/temporal";


        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (subirDocumento.getArchivos().size() > 0) {
            realArchivoPath = theApplicationsServletContext.getRealPath(URL);
            try {

                File carpeta = new File(realArchivoPath);
                if (!carpeta.exists()) {

                    carpeta.mkdirs();
                }
            } catch (Exception ex) {
                mensaje = (bundle.getString("nosepuedesubir"));//"Cannot upload file ");
            }
        }
        try {
            subirDocumento.guardarArchivosTemporales(realArchivoPath, false);
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(FiduFinanciera.class.getName()).log(Level.SEVERE, null, ex);
        }

        Iterator arch = subirDocumento.getArchivos().iterator();
        while (arch.hasNext()) {
            Archivo nombreoriginal = (Archivo) arch.next();
            //  getCoralService().getDocumentoobra().setStrubicaciondocumento(URL + "/" + nombreoriginal.getOnlyName());
        }
        getFinancieraService().setEncargofidu(new Encargofiduciario());
        getFinancieraService().getEncargofidu().setTercero(new Tercero());
        getFinancieraService().setOrdendepago(new Ordendepago());
        getFinancieraService().getOrdendepago().setTercero(new Tercero());




    }

    public boolean guardarArchivoObra(String nomarch, String URLorigen, String URLdestino) {

        String ArchivoPath = "";

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        ArchivoPath = theApplicationsServletContext.getRealPath(URLorigen + nomarch);
        try {

            File carpeta = new File(ArchivoPath);
            if (carpeta.exists()) {


                ArchivoPath = theApplicationsServletContext.getRealPath(URLdestino);
                try {

                    File carpetadest = new File(ArchivoPath);
                    if (!carpetadest.exists()) {

                        carpetadest.mkdirs();
                    }

                } catch (Exception ex) {

                    FacesUtils.addErrorMessage("No pudo crear carpeta ");
                }

                carpeta.renameTo(new File(ArchivoPath + "/" + nomarch));
                return true;
            } else {

                return false;
            }

        } catch (Exception ex) {
            FacesUtils.addErrorMessage(bundle.getString("nosepuedesubir"));
        }

        return false;
    }

    public String bt_agregar_documento_action() {

        subirDocumento();
        return null;
    }

    public String guardarFinanciera() {

        getFinancieraService().validarDatos();
        if (getFinancieraService().isGuardado()) {
            getFinancieraService().getEncargofidu().setDatefechacreacion(new Date());
            getFinancieraService().getEncargofidu().setJsfUsuarioByIntusucreacion(new JsfUsuario(29, "", ""));
            getFinancieraService().getEncargofidu().setJsfUsuarioByIntusumodifica(new JsfUsuario(29, "", ""));
            getFinancieraService().getEncargofidu().getTercero();
            getFinancieraService().getEncargofidu().setLocalidad(getFinancieraService().getEncargofidu().getTercero().getLocalidadByStrcodigolocalidad());
            getFinancieraService().guardarEncargoFiduciario(getFinancieraService().getEncargofidu());
            getFinancieraService().getMensaje();

        } else {
            getFinancieraService().setMensaje(bundle.getString("debedardiligenciarlosdatos"));
        }

        return null;
    }

    public String guardarOrdenPago() {

        getFinancieraService().validarDatosOrden();

        if (getFinancieraService().isGuardado()) {
            getFinancieraService().getOrdendepago().setBoolestadoordenpago(false);
            getFinancieraService().getOrdendepago().setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
            getFinancieraService().getOrdendepago().setTipoordendepago(new Tipoordendepago(1));

            getFinancieraService().guardarOrdenPago(getFinancieraService().getOrdendepago());

        }

        return null;
    }

    public String confirma() {

        guardarFinanciera();
        limpiarEncargo();

        return null;
    }

    public String confirmaOrden() {

        guardarOrdenPago();
        limpiarOrdenPago();

        return null;
    }

    public String seleccionarTercero() {

        Tercero tercero = (Tercero) tablaTerceroListado.getRowData();
        getFinancieraService().getEncargofidu().setTercero(tercero);

        return null;
    }

    public String seleccionarEncargo( int filaSeleccionada ) {
        
        Encargofiduciario encargo = (Encargofiduciario) FacesUtils.getManagedBean("Financiera$FiduFinanciera");
        listaencargofiducario.get(filaSeleccionada);
        Tercero ter = new Tercero();
        ter.setIntcodigo(encargo.getTercero().getIntcodigo());
        getFinancieraService().getOrdendepago().setTercero(ter);

        SolicitudObrach solobra = new SolicitudObrach();
        solobra = getFinancieraService().encontrarsolicitudobraXencargo(encargo.getIntnumencargofiduciario());

        if (solobra != null) {

            getFinancieraService().getOrdendepago().setSolicitud_obra(solobra);
        }
        Solicitudmaestro solmaestro = new Solicitudmaestro();
        solmaestro = getFinancieraService().encontrarsolicitudmaestroXencargo(encargo.getIntnumencargofiduciario());

        if (solmaestro != null) {

            getFinancieraService().getOrdendepago().setSolicitudmaestro(solmaestro);
        }

        return null;
    }

    public String seleccionarTerceroOP(int filaSeleccionada) {
        FiduFinanciera fiduFinanciera = (FiduFinanciera) FacesUtils.getManagedBean("Financiera$FiduFinanciera");
        Tercero tercero = fiduFinanciera.getFinancieraService().getListaTercero().get(filaSeleccionada);
        
        getFinancieraService().getOrdendepago().setTercero(tercero);

        return null;
    }

    public String primerosreales() {

        getFinancieraService().setListaTercero(getFinancieraService().encontrarTodoPorCampo(0, 10));
        totalfilas = getFinancieraService().countTercero();

        if (aplicafiltro) {
            getFinancieraService().setListaTercero(getFinancieraService().filtrarTercero(getFinancieraService().getTercero(), 0, 10));
        }

        pagina = 1;
        if (totalfilas < 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 1) {
                totalpaginas++;
            }
        }
        veranteriorreales = false;
        if (totalpaginas > 1) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }

        return null;
    }

    public String siguientesReales() {
        int num = (pagina) * 10;

        getFinancieraService().setListaTercero(getFinancieraService().encontrarTodoPorCampo(num, num + 10));

        if (aplicafiltro) {
            getFinancieraService().setListaTercero(getFinancieraService().filtrarTercero(getFinancieraService().getTercero(), num, num + 10));
        }
        totalfilas = getFinancieraService().countTercero();

        if (totalfilas < 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 1) {
                totalpaginas++;
            }
        }
        pagina = pagina + 1;
        if (pagina < totalpaginas) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }
        veranteriorreales = true;

        return null;
    }

    public String anterioresReales() {
        pagina = pagina - 1;
        int num = (pagina - 1) * 10;

        getFinancieraService().setListaTercero(getFinancieraService().encontrarTodoPorCampo(num, num + 10));
        if (aplicafiltro) {
            getFinancieraService().setListaTercero(getFinancieraService().filtrarTercero(getFinancieraService().getTercero(), num, num + 10));
        }

        totalfilas = getFinancieraService().countTercero();

        if (totalfilas < 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 1) {
                totalpaginas++;
            }
        }

        if (pagina > 1) {
            veranteriorreales = true;
        } else {
            veranteriorreales = false;
        }
        verultimosreales = true;

        return null;
    }

    public String ultimoReales() {
        totalfilas = getFinancieraService().countTercero();
        getFinancieraService().setListaTercero(getFinancieraService().encontrarTodoPorCampo(totalfilas - 10, totalfilas));
        if (aplicafiltro) {
            getFinancieraService().setListaTercero(getFinancieraService().filtrarTercero(getFinancieraService().getTercero(), totalfilas - 10, totalfilas));
        }

        if (totalfilas < 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 1) {
                totalpaginas++;
            }
        }

        pagina = totalpaginas;
        if (pagina < totalpaginas) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }
        veranteriorreales = true;


        return null;
    }

    public String filtrar() {
        aplicafiltro = true;
        getFinancieraService().setListaTercero(getFinancieraService().filtrarTercero(getFinancieraService().getTercero(), 0, 10));
        pagina = 1;
        totalfilas = getFinancieraService().getNumTerceroFiltro(getFinancieraService().getTercero());
        if (totalfilas < 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 1) {
                totalpaginas++;
            }
        }
        veranteriorreales = false;

        if (totalpaginas > 1) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }

        return null;
    }

    public String filtrarNumeroEncargo() {

        aplicafiltro = true;
        getFinancieraService().setListaencargo(getFinancieraService().filtrarNumeroEncargo(filtroint, 0, 10));
        pagina = 1;
        totalfilas = getFinancieraService().getNumeroEncargoFiltro(filtroint);
        if (totalfilas < 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 1) {
                totalpaginas++;
            }
        }
        veranteriorreales = false;

        if (totalpaginas > 1) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }

        return null;
    }

    public String primerosrealesEncargo() {
        if (aplicafiltro) {
            getFinancieraService().setListaencargo(getSessionBeanCobra().getCobraService().filtroAvanzadoEncargo(intencargofidu, 0, 5));
            totalfilasencargo = getSessionBeanCobra().getCobraService().numFiltrarEncargo(intencargofidu);
        } else {
            getFinancieraService().setListaencargo(getSessionBeanCobra().getCobraService().encontrarEncargo(0, 5));
            totalfilasencargo = getSessionBeanCobra().getCobraService().getNumEncargo();
        }

        pagina = 1;
        if (totalfilasencargo <= 5) {
            totalpaginasencargo = 1;
        } else {
            totalpaginasencargo = totalfilasencargo / 5;
            if (totalfilasencargo % 5 > 0) {
                totalpaginasencargo++;
            }
        }
        veranteriorreales = false;
        if (totalpaginasencargo > 1) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }
        return null;
    }

    public String siguientesRealesEncargo() {
        int num = (pagina) * 5;

        if (aplicafiltro) {
            getFinancieraService().setListaencargo(getSessionBeanCobra().getCobraService().filtroAvanzadoEncargo(intencargofidu, num, num + 5));
            totalfilasencargo =getSessionBeanCobra().getCobraService().numFiltrarEncargo(intencargofidu);
        } else {
            getFinancieraService().setListaencargo(getSessionBeanCobra().getCobraService().encontrarEncargo(num, num + 5));
            totalfilasencargo = getSessionBeanCobra().getCobraService().getNumEncargo();
        }


        if (totalfilasencargo <= 5) {
            totalpaginasencargo = 1;
        } else {
            totalpaginasencargo = totalfilasencargo / 5;
            if (totalfilasencargo % 5 > 0) {
                totalpaginasencargo++;
            }
        }
        pagina = pagina + 1;
        if (pagina < totalpaginasencargo) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }
        veranteriorreales = true;

        return null;

    }

    public String anterioresRealesEncargo() {
        pagina = pagina - 1;
        int num = (pagina - 1) * 5;

        if (aplicafiltro) {
            getFinancieraService().setListaencargo(getSessionBeanCobra().getCobraService().filtroAvanzadoEncargo(intencargofidu, num, num + 5));
            totalfilasencargo = getSessionBeanCobra().getCobraService().numFiltrarEncargo(intencargofidu);
        } else {
            getFinancieraService().setListaencargo(getSessionBeanCobra().getCobraService().encontrarEncargo(num, num + 5));
            totalfilasencargo = getSessionBeanCobra().getCobraService().getNumEncargo();
        }

        if (totalfilasencargo <= 5) {
            totalpaginasencargo = 1;
        } else {
            totalpaginasencargo = totalfilasencargo / 5;
            if (totalfilasencargo % 5 > 0) {
                totalpaginasencargo++;
            }
        }

        if (pagina > 1) {
            veranteriorreales = true;
        } else {
            veranteriorreales = false;
        }
        verultimosreales = true;

        return null;
    }

    public String ultimoRealesEncargo() {
        int num = totalfilasencargo % 5;

        if (aplicafiltro) {
            getFinancieraService().setListaencargo(getSessionBeanCobra().getCobraService().filtroAvanzadoEncargo(intencargofidu, totalfilasencargo - num, totalfilasencargo));
            totalfilasencargo = getSessionBeanCobra().getCobraService().numFiltrarEncargo(intencargofidu);
        } else {
            totalfilasencargo = getSessionBeanCobra().getCobraService().getNumEncargo();
            getFinancieraService().setListaencargo(getSessionBeanCobra().getCobraService().encontrarEncargo(totalfilasencargo - num, totalfilasencargo));

        }

        if (totalfilasencargo <= 5) {
            totalpaginasencargo = 1;
        } else {
            totalpaginasencargo = totalfilasencargo / 5;
            if (totalfilasencargo % 5 > 0) {
                totalpaginasencargo++;
            }
        }

        pagina = totalpaginasencargo;
        if (pagina < totalpaginasencargo) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }
        veranteriorreales = true;

        return null;
    }

    public String filtrarEncargo() {
        aplicafiltro = false;
        if (intencargofidu != 0) {
            getFinancieraService().getListaencargo().clear();
            aplicafiltro = true;
        }
        primerosrealesEncargo();
        return null;
    }

    public String primerosrealesOrden() {

        if (aplicafiltro) {
            getFinancieraService().setListaordenpago(getFinancieraService().filtrarOrden(filtro, 0, 10));
            totalfilas = getFinancieraService().numfiltrarOrden(filtro);

        } else {

            getFinancieraService().setListaordenpago(getFinancieraService().encontrarTodoPorCampoOrden(0, 10));
            totalfilas = getFinancieraService().countOrden();
        }
        pagina = 1;
        if (totalfilas < 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 1) {
                totalpaginas++;
            }
        }
        veranteriorreales = false;
        if (totalpaginas > 1) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }


        return null;
    }

    public String siguientesRealesOrden() {
        int num = (pagina) * 10;

        if (aplicafiltro) {
            getFinancieraService().setListaordenpago(getFinancieraService().filtrarOrden(getFinancieraService().getOrdendepago().getStrordenpago(), num, num + 10));
            totalfilas = getFinancieraService().numfiltrarOrden(getFinancieraService().getOrdendepago().getStrordenpago());
        } else {
            getFinancieraService().setListaordenpago(getFinancieraService().encontrarTodoPorCampoOrden(num, num + 10));
            totalfilas = getFinancieraService().countOrden();
        }


        if (totalfilas < 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 1) {
                totalpaginas++;
            }
        }
        pagina = pagina + 1;
        if (pagina < totalpaginas) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }
        veranteriorreales = true;

        return null;

    }

    public String anterioresRealesOrden() {
        pagina = pagina - 1;
        int num = (pagina - 1) * 10;


        if (aplicafiltro) {
            getFinancieraService().setListaordenpago(getFinancieraService().filtrarOrden(getFinancieraService().getOrdendepago().getStrordenpago(), num, num + 10));
            totalfilas = getFinancieraService().numfiltrarOrden(getFinancieraService().getOrdendepago().getStrordenpago());
        } else {
            getFinancieraService().setListaordenpago(getFinancieraService().encontrarTodoPorCampoOrden(num, num + 10));
            totalfilas = getFinancieraService().countOrden();
        }



        if (totalfilas < 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 1) {
                totalpaginas++;
            }
        }

        if (pagina > 1) {
            veranteriorreales = true;
        } else {
            veranteriorreales = false;
        }
        verultimosreales = true;

        return null;
    }

    public String ultimoRealesOrden() {



        if (aplicafiltro) {
            getFinancieraService().setListaordenpago(getFinancieraService().filtrarOrden(getFinancieraService().getOrdendepago().getStrordenpago(), totalfilas - 10, totalfilas));
            totalfilas = getFinancieraService().numfiltrarOrden(getFinancieraService().getOrdendepago().getStrordenpago());
        } else {
            totalfilas = getFinancieraService().countOrden();
            getFinancieraService().setListaordenpago(getFinancieraService().encontrarTodoPorCampoOrden(totalfilas - 10, totalfilas));
        }

        if (totalfilas < 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 1) {
                totalpaginas++;
            }
        }

        pagina = totalpaginas;
        if (pagina < totalpaginas) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }
        veranteriorreales = true;


        return null;
    }

    public String filtrarOrden() {
        if (filtro.compareTo("") == 0) {
            aplicafiltro = false;
        } else {
            aplicafiltro = true;
        }
        primerosrealesOrden();

        return null;
    }

    public String verTercero() {
        primerosreales();
        return null;
    }

    public String AsociarEncargo() {

        getFinancieraService().setListaordenpago(getFinancieraService().encontrarTodoPorCampoOrden(0, 10));
        primerosrealesOrden();

        return "asociarencargo";
    }

    public String AsociarOrden() {
        getFinancieraService().setListaencargo(getSessionBeanCobra().getCobraService().encontrarEncargo(0, 10));
        primerosrealesEncargo();
        return "asociarorden";
    }

    public String encargo() {

        Ordendepago orden = (Ordendepago) tablaOrdenListado.getRowData();
        getFinancieraService().setOrdendepago(orden);
        setEditarorden(false);

        return "encargo";
    }

    public String orden() {

        Encargofiduciario encargo = (Encargofiduciario) tablaEncargoListado.getRowData();
        getFinancieraService().setEncargofidu(encargo);
        setEditarorden(false);

        return "orden";
    }

    public String verEncargo() {
        intencargofidu = 0;
        primerosrealesEncargo();
        return null;
    }

    public String limpiarEncargo() {
        getFinancieraService().setEncargofidu(new Encargofiduciario());
        return null;
    }

    public String limpiarOrdenPago() {

        getFinancieraService().getOrdendepago().setTipoordendepago(new Tipoordendepago());
        getFinancieraService().getOrdendepago().setEncargofiduciario(new Encargofiduciario());
        getFinancieraService().getOrdendepago().setTercero(new Tercero());
        getFinancieraService().setOrdendepago(new Ordendepago());
        aplicafiltro = false;
        return null;
    }

    public String llenarTiposorden() {
        getFinancieraService().setListatipoordendepago(getFinancieraService().ObtenerTipoOrden());
        seTipoorden = new SelectItem[getFinancieraService().getListatipoordendepago().size()];
        int i = 0;
        for (Tipoordendepago orden : getFinancieraService().getListatipoordendepago()) {
            SelectItem ord = new SelectItem(orden.getOidcodigotipoordenpago(), orden.getStrnombre());
//            if (i == 0) {
//                tipoordenselec = (int) orden.getOidcodigotipoordenpago();
//            }
            seTipoorden[i++] = ord;
        }
        return null;
    }

    public String cargarOrdenesPago() {

        limpiarOrdenPago();
        primerosrealesOrden();
        aplicafiltro = false;

        return "generarorden";
    }
}
