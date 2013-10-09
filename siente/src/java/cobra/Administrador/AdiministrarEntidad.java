/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Administrador;

import co.com.interkont.cobra.to.JsfUsuario;
import co.com.interkont.cobra.to.Localidad;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Terceroentidad;
import co.com.interkont.cobra.to.TerceroentidadId;
import co.com.interkont.cobra.to.Tipoidentificacion;
import co.com.interkont.cobra.to.Tipoorigen;
import co.com.interkont.cobra.to.Tiposolicitante;
import co.com.interkont.cobra.to.Tipotercero;
import cobra.SessionBeanCobra;
import cobra.Supervisor.TerceroEntidadLista;
import cobra.Supervisor.FacesUtils;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.model.SelectItem;

/**
 *
 * @author desarrollo3
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author desarrollo3
 */
public class AdiministrarEntidad implements Serializable {

    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private Tercero tercero;
    private JsfUsuario jsfUsuario;
    private String codDepartamentoEntidad = "";
    private SelectItem[] DepartamentoEntidad;
    private SelectItem[] MunicipioEntidad;
    private SelectItem[] TipoEntidad;
    private JsfUsuario usuarioEntidad;
    private Tercero entidadUsuario = new Tercero();
    private SelectItem[] lstEntidad;
    private List<TerceroEntidadLista> temporalEntidadUsuario = new ArrayList<TerceroEntidadLista>();
    private Terceroentidad terceroEntidad = new Terceroentidad(new TerceroentidadId());
    private int codigoEntidad;
    private String login;
    private List<JsfUsuario> lstjsf;
    private boolean entidadvalida=false;

    public boolean isEntidadvalida() {
        return entidadvalida;
    }

    public void setEntidadvalida(boolean entidadvalida) {
        this.entidadvalida = entidadvalida;
    }    
    

    public JsfUsuario getJsfUsuario() {
        return jsfUsuario;
    }

    public void setJsfUsuario(JsfUsuario jsfUsuario) {
        this.jsfUsuario = jsfUsuario;
    }

    public List<JsfUsuario> getLstjsf() {
        return lstjsf;
    }

    public void setLstjsf(List<JsfUsuario> lstjsf) {
        this.lstjsf = lstjsf;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(int codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public AdiministrarEntidad(String codDepartamentoEntidad, JsfUsuario usuarioEntidad, Tercero entidadUsuario, SelectItem[] lstEntidad, Terceroentidad terceroEntidad) {
         
        this.codDepartamentoEntidad = codDepartamentoEntidad;
        this.usuarioEntidad = usuarioEntidad;
        this.entidadUsuario = entidadUsuario;
        this.lstEntidad = lstEntidad;
        this.terceroEntidad = terceroEntidad;
        //limpiarEntidad();

    }

    public SelectItem[] getTipoEntidad() {
        return TipoEntidad;
    }

    public void setTipoEntidad(SelectItem[] TipoEntidad) {
        this.TipoEntidad = TipoEntidad;
    }

    public Tercero getEntidadUsuario() {
        return entidadUsuario;
    }

    public void setEntidadUsuario(Tercero entidadUsuario) {
        this.entidadUsuario = entidadUsuario;
    }

    public SelectItem[] getLstEntidad() {
        return lstEntidad;
    }

    public void setLstEntidad(SelectItem[] lstEntidad) {
        this.lstEntidad = lstEntidad;
    }

    public List<TerceroEntidadLista> getTemporalEntidadUsuario() {
        return temporalEntidadUsuario;
    }

    public void setTemporalEntidadUsuario(List<TerceroEntidadLista> temporalEntidadUsuario) {
        this.temporalEntidadUsuario = temporalEntidadUsuario;
    }

    public Terceroentidad getTerceroEntidad() {
        return terceroEntidad;
    }

    public void setTerceroEntidad(Terceroentidad terceroEntidad) {
        this.terceroEntidad = terceroEntidad;
    }

    public JsfUsuario getUsuarioEntidad() {
        return usuarioEntidad;
    }

    public void setUsuarioEntidad(JsfUsuario usuarioEntidad) {
        this.usuarioEntidad = usuarioEntidad;
    }
    private SelectItem[] TipoOrigenEntidad;

    public AdiministrarEntidad() {
       // limpiartercero();
    }

    public SelectItem[] getTipoOrigenEntidad() {
        return TipoOrigenEntidad;
    }

    public void setTipoOrigenEntidad(SelectItem[] TipoOrigenEntidad) {
        this.TipoOrigenEntidad = TipoOrigenEntidad;
    }

    public SelectItem[] getDepartamentoEntidad() {
        return DepartamentoEntidad;
    }

    public void setDepartamentoEntidad(SelectItem[] DepartamentoEntidad) {
        this.DepartamentoEntidad = DepartamentoEntidad;
    }

    public SelectItem[] getMunicipioEntidad() {
        return MunicipioEntidad;
    }

    public void setMunicipioEntidad(SelectItem[] MunicipioEntidad) {
        this.MunicipioEntidad = MunicipioEntidad;
    }

    public String getCodDepartamentoEntidad() {
        return codDepartamentoEntidad;
    }

    public void setCodDepartamentoEntidad(String codDepartamentoEntidad) {
        this.codDepartamentoEntidad = codDepartamentoEntidad;
    }

    public Tercero getTercero() {
        return tercero;
    }

    public void setTercero(Tercero tercero) {
        this.tercero = tercero;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String ingresarEntidad() {
        iniciarCombos();
        return "ingresarEntidad";
    }
    
    public String ingresarUsuario() {
        return "ingresarUsuario";
    }
    public String saliringresarUsuario() {
        return "gestion";
    }

    public String asociarEntidad() {
        iniciarCombosAsociarEntidad();
        limpiartercero();
        return "asociarEntidad";
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public String enviarDatos() {
        guardarEntidad();
        return null;
    }

    public String salirCrearEntidad() {
        limpiarEntidad();
        return "gestion";
    }

    /**
     * guardarEntidad metodo que guarda una nueva entidad
     */
   private void guardarEntidad() {
        if (getSessionBeanCobra().getUtil().isEmail(tercero.getStremail())) {
            tercero.setStrnombrecompleto(tercero.getStrnombre());
            tercero.setTipoidentificacion(new Tipoidentificacion(6, "NIT"));
            tercero.setTipotercero(new Tipotercero(2));
            tercero.setBoolestado(true);
            tercero.setDateusuariocreacion(new Date());
            tercero.setStrdireccionprincipal("faltante");
            tercero.setTiposolicitante(new Tiposolicitante(2));
            if (tercero.getTipoOrigen().getIntidtipoorigen() == 4) {
                tercero.setTipoOrigen(new Tipoorigen(4, "Nacional"));
                tercero.setBoolobraslocalidad(false);
                tercero.setBoolentidadnacional(true);
                tercero.setTiposolicitante(new Tiposolicitante(2));
                tercero.setLocalidadByStrcodigolocalidad(new Localidad("169"));
            }
            if (tercero.getTipoOrigen().getIntidtipoorigen() == 1) {
                tercero.setBoolobraslocalidad(true);
                tercero.setBoolentidadnacional(false);
            }
            if (tercero.getTipoOrigen().getIntidtipoorigen() == 2) {
                tercero.setBoolobraslocalidad(true);
                tercero.setBoolentidadnacional(false);
                tercero.setLocalidadByStrcodigolocalidad(new Localidad(codDepartamentoEntidad));
            }
            getSessionBeanCobra().getCiudadanoservice().guardarTercero(tercero);
            FacesUtils.addInfoMessage(bundle.getString("mensajeguardarentidad"));
            limpiartercero();
        } else {
        FacesUtils.addErrorMessage(bundle.getString("correoinvalido"));
        }

    }
    /**
     * iniciarCombos metodo que inicializa las variables de las listas para
     * crear una nueva entidad
     */
    private void iniciarCombos() {
        llenarDepartamentoEntidad();
        llenarMunicipioEntidadModi();
        llenarTipoOrigen();
        limpiartercero();
    }

    private void iniciarCombosAsociarEntidad() {
        llenarEntidadesUsuario();
    }

    /**
     * llenarDepartamentoEntidad metodo que llena la lista de departamentos
     *
     * @return
     */
    public String llenarDepartamentoEntidad() {
        getSessionBeanCobra().getCiudadanoservice().setListaDeptos(getSessionBeanCobra().getCobraService().encontrarDepartamentos());
        DepartamentoEntidad = new SelectItem[getSessionBeanCobra().getCiudadanoservice().getListaDeptos().size()];
        int i = 0;
        for (Localidad depto : getSessionBeanCobra().getCiudadanoservice().getListaDeptos()) {
            SelectItem dep = new SelectItem(depto.getStrcodigolocalidad(), depto.getStrdepartamento());
            if (i == 0) {
                codDepartamentoEntidad = depto.getStrcodigolocalidad();
            }
            DepartamentoEntidad[i++] = dep;
        }
        return null;
        
    }

    /**
     * llenarMunicipioEntidad metodo que se ejecuta cuando se cambia un el
     * departamento
     *
     * @return
     */
    public String llenarMunicipioEntidad() {
        getSessionBeanCobra().getCiudadanoservice().setListaMunicipios(getSessionBeanCobra().getCobraService().encontrarMunicipios(codDepartamentoEntidad));
        MunicipioEntidad = new SelectItem[getSessionBeanCobra().getCiudadanoservice().getListaMunicipios().size()];
        int i = 0;
        for (Localidad muni : getSessionBeanCobra().getCiudadanoservice().getListaMunicipios()) {
            SelectItem mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            MunicipioEntidad[i++] = mun;
        }
        return null;
    }

    /**
     * llenarMunicipioEntidad metodo que se ejecuta cuando se cambia un el
     * departamento
     *
     * @return
     */
    public String llenarMunicipioEntidadModi() {

        getSessionBeanCobra().getCiudadanoservice().setListaMunicipios(getSessionBeanCobra().getCobraService().encontrarMunicipios(codDepartamentoEntidad));
        MunicipioEntidad = new SelectItem[getSessionBeanCobra().getCiudadanoservice().getListaMunicipios().size()];
        int i = 0;
        for (Localidad muni : getSessionBeanCobra().getCiudadanoservice().getListaMunicipios()) {
            SelectItem mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            if (i == 0) {
                mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            }
            MunicipioEntidad[i++] = mun;
        }
        return null;
    }

        public String llenarTipoOrigen() {
        getSessionBeanCobra().getCobraService().setListaTipoOrigen(getSessionBeanCobra().getCobraService().listaTipoOrigen());
        TipoOrigenEntidad = new SelectItem[getSessionBeanCobra().getCobraService().getListaTipoOrigen().size() - 1];
        int i = 0;
        for (Tipoorigen tipoorigen : getSessionBeanCobra().getCobraService().getListaTipoOrigen()) {
            SelectItem tp = new SelectItem(tipoorigen.getIntidtipoorigen(), tipoorigen.getStrnombre());
            if (tipoorigen.getIntidtipoorigen() != 3) {
                TipoOrigenEntidad[i++] = tp;
            }
        }
        return null;
    }


    public void llenarEntidadesUsuario() {
        try {
            lstEntidad = new SelectItem[getSessionBeanCobra().getUsuarioService().encontrarTotalEntidades().size()];
            int i = 0;
            for (Tercero ter : getSessionBeanCobra().getUsuarioService().encontrarTotalEntidades()) {
                SelectItem itemEntidad = new SelectItem(ter.getStrnombrecompleto());
                TerceroEntidadLista terce = new TerceroEntidadLista(ter.getIntcodigo(), itemEntidad.getValue().toString());
                temporalEntidadUsuario.add(terce);
                lstEntidad[i++] = itemEntidad;

            }
        } catch (Exception e) {
            System.out.println("Exception llenado Entidades Contratante Nuevo Contrato " + e.getCause());
        }


    }

    public boolean verificarUsuario() {

        lstjsf = getSessionBeanCobra().getUsuarioService().buscarUsuario(login);
        if (!lstjsf.isEmpty() ) {
            jsfUsuario = lstjsf.get(0);
            return true;
        }
        return false;

    }

    public void asociarEntidadUsuario() {

       
        if(entidadvalida)
            {
        
        if (verificarUsuario()) {
          //getTerceroEntidad().getId().setIntcodigoentidad(codigoEntidad);
            getTerceroEntidad().getId().setIntcodigotercero(jsfUsuario.getTercero().getIntcodigo());

            Terceroentidad entidadUsuarioasociado = getSessionBeanCobra().getCobraService().encontrarEntidadUsuario(getEntidadUsuario().getIntcodigo(), jsfUsuario.getTercero().getIntcodigo());

            if (entidadUsuarioasociado == null ) {
                getSessionBeanCobra().getCobraService().guardarEntidadUsuario(new Terceroentidad(new TerceroentidadId(getEntidadUsuario().getIntcodigo(), jsfUsuario.getTercero().getIntcodigo())));
                FacesUtils.addInfoMessage(bundle.getString("asociarentidadvalida"));
                limpiarEntidad();

            } else {
                FacesUtils.addErrorMessage(bundle.getString("usuarioasociado"));
                //limpiarEntidad();
            }
        } else {
            FacesUtils.addErrorMessage(bundle.getString("usuarionoexiste"));
            //limpiarEntidad();
        }
        
        } else {
            FacesUtils.addErrorMessage(bundle.getString("entidadvalida"));
            //limpiarEntidad();
        }

    }

    public void limpiartercero() {
        tercero = new Tercero();
        tercero.setTipoOrigen(new Tipoorigen(4, ""));
        tercero.setLocalidadByStrcodigolocalidad(new Localidad());
    }
    
    
    public void limpiarEntidad() {
       
        login="";
        entidadUsuario = new Tercero();
        entidadUsuario.setIntcodigo(codigoEntidad);

        
    }
////    public void limpiarAsociacionEntidadUsuario() {
////        terceroEntidad = new Terceroentidad(new TerceroentidadId().setIntcodigoentidad()); 
////        
////        
////        
////    }

    /**
     * Dependiendo si es contrato o convenio se seta la variable y se inicializa
     * el paginador para el llenado de los contratos por entidades
     */
    public void comboEntidades() {

        entidadvalida=comboEntidadesGuardar();
        System.out.println("entidadvalida = " + entidadvalida);

        if (comboEntidadesGuardar()) {
             //primeroDetcontrato();
//            primeroDetcontratoContratista();
        } else {
              limpiartercero();
//            getContrato().getTercero().setIntcodigo(0);
//            listacontratos = new ArrayList<Contrato>();
//            listacontratoscontratista = new ArrayList<Contrato>();
        }
    }

    /**
     * Permite guardar el id de la entidad y no el nombre
     *
     * @return id entidad si la tiene
     */
    public boolean comboEntidadesGuardar() {
        if (getEntidadUsuario().getStrnombrecompleto() != null) {
           
            Iterator ite = temporalEntidadUsuario.iterator();
            int idtercero = 0;
            while (ite.hasNext()) {
                TerceroEntidadLista tempter = (TerceroEntidadLista) ite.next();

                if (getEntidadUsuario().getStrnombrecompleto().equals(tempter.getStrnombre())) {                   
                    idtercero = tempter.getCodigo();
                  
                }
            }
            if (idtercero != 0) {
                getEntidadUsuario().setIntcodigo(idtercero);
                return true;
            }
        }
        return false;
    }
    
}
