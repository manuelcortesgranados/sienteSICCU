/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Documentoobra;
import co.com.interkont.cobra.to.Historicopolizacontrato;
import co.com.interkont.cobra.to.Modificacioncontrato;
import co.com.interkont.cobra.to.Planificacionpago;
import co.com.interkont.cobra.to.Polizacontrato;
import co.com.interkont.cobra.to.Tipodocumento;
import co.com.interkont.cobra.to.Tipomodificacion;
import cobra.CargadorArchivosWeb;
import cobra.SessionBeanCobra;
import cobra.util.RutasWebArchivos;
import com.interkont.cobra.exception.ArchivoExistenteException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.richfaces.component.UIDataTable;

/**
 *
 * @author desarrollo5
 */
public class ModificarContrato implements Serializable {

    private Modificacioncontrato modificarcontrato = new Modificacioncontrato();
    private int cual = 1;
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private boolean valido = false;
    private CargadorArchivosWeb cargadorPoliza = new CargadorArchivosWeb();
    private String mensaje = "";
    private CargadorArchivosWeb cargadorOtrosi = new CargadorArchivosWeb();
    private Documentoobra documentopoliza = new Documentoobra();
    private Documentoobra documentootrosi = new Documentoobra();
    private List<Documentoobra> listadocuModifContrato = new ArrayList<Documentoobra>();
    private UIDataTable tabladocuModiContrato = new UIDataTable();
    private UIDataTable binditablamodi = new UIDataTable();
    private Date fechaMaxima = new Date();
    private BigDecimal valorModificadocontrato;
    private boolean permitirGuardar = false;
    StringBuilder renderVar = new StringBuilder("strcontrcontrato");
    public boolean habilitarBtnModificarValor = true;
    public boolean habilitarBtnGuardarCancelarValor = false;
    private UIDataTable tablaPolizasbin = new UIDataTable();

    public boolean isHabilitarBtnGuardarCancelarValor() {
        return habilitarBtnGuardarCancelarValor;
    }

    public void setHabilitarBtnGuardarCancelarValor(boolean habilitarBtnGuardarCancelarValor) {
        this.habilitarBtnGuardarCancelarValor = habilitarBtnGuardarCancelarValor;
    }

    public boolean isHabilitarBtnModificarValor() {
        return habilitarBtnModificarValor;
    }

    public void setHabilitarBtnModificarValor(boolean habilitarBtnModificarValor) {
        this.habilitarBtnModificarValor = habilitarBtnModificarValor;
    }

    public BigDecimal getValorModificadocontrato() {
        return valorModificadocontrato;
    }

    public void setValorModificadocontrato(BigDecimal valorModificadocontrato) {
        this.valorModificadocontrato = valorModificadocontrato;
    }

    public Date getFechaMaxima() {
        return fechaMaxima;
    }

    public void setFechaMaxima(Date fechaMaxima) {
        this.fechaMaxima = fechaMaxima;
    }
    /**
     * Verdadero si proviene de modificación de contrato, regresa a modificar el
     * proyecto
     */
    private boolean vieneDeModificarProyecto;

    public boolean isVieneDeModificarProyecto() {
        return vieneDeModificarProyecto;
    }

    public void setVieneDeModificarProyecto(boolean vieneDeModificarProyecto) {
        this.vieneDeModificarProyecto = vieneDeModificarProyecto;
    }

    public UIDataTable getBinditablamodi() {
        return binditablamodi;
    }

    public void setBinditablamodi(UIDataTable binditablamodi) {
        this.binditablamodi = binditablamodi;
    }

    public UIDataTable getTabladocuModiContrato() {
        return tabladocuModiContrato;
    }

    public void setTabladocuModiContrato(UIDataTable tabladocuModiContrato) {
        this.tabladocuModiContrato = tabladocuModiContrato;
    }

    public List<Documentoobra> getListadocuModifContrato() {
        return listadocuModifContrato;
    }

    public void setListadocuModifContrato(List<Documentoobra> listadocuModifContrato) {
        this.listadocuModifContrato = listadocuModifContrato;
    }

    public Documentoobra getDocumentootrosi() {
        return documentootrosi;
    }

    public void setDocumentootrosi(Documentoobra documentootrosi) {
        this.documentootrosi = documentootrosi;
    }

    public Documentoobra getDocumentopoliza() {
        return documentopoliza;
    }

    public void setDocumentopoliza(Documentoobra documentopoliza) {
        this.documentopoliza = documentopoliza;
    }

    public CargadorArchivosWeb getCargadorPoliza() {
        return cargadorPoliza;
    }

    public void setCargadorPoliza(CargadorArchivosWeb cargadorPoliza) {
        this.cargadorPoliza = cargadorPoliza;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public CargadorArchivosWeb getCargadorOtrosi() {
        return cargadorOtrosi;
    }

    public void setCargadorOtrosi(CargadorArchivosWeb cargadorOtrosi) {
        this.cargadorOtrosi = cargadorOtrosi;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public Modificacioncontrato getModificarcontrato() {
        return modificarcontrato;
    }

    public void setModificarcontrato(Modificacioncontrato modificarcontrato) {
        this.modificarcontrato = modificarcontrato;
    }

    public int getCual() {
        return cual;
    }

    public void setCual(int cual) {
        this.cual = cual;
    }

    public ModificarContrato() {
    }

    public String siguiente() {
        if (modificarcontrato.getDatefechamodificacionseleccionada().compareTo(getNuevoContratoBasico().getContrato().getDatefechaini()) >= 0
                && modificarcontrato.getDatefechamodificacionseleccionada().compareTo(getNuevoContratoBasico().getContrato().getDatefechafin()) <= 0) {

            switch (cual) {
                case 1:
                    if (validarProrroga()) {
                        valido = true;
                    } else {
                        return null;
                    }
                case 2:
                    if (calcularValor()) {
                        valido = true;
                    } else {
                        return null;
                    }
                case 3:
                    if (validarPolizas()) {
                        valido = true;
                    } else {
                        return null;
                    }

                case 4:
                    valido = true;
                    break;

            }
            if (valido) {
                cual = cual + 1;
            }
        } else {
            FacesUtils.addErrorMessage("La fecha de modificación debe estar dentro del periodo de ejecución del contrato");
        }

        return null;
    }

    public boolean validarPolizas() {
        if (modificarcontrato.getPolizas() == 1) {
            if (!cargadorPoliza.getArchivos().isEmpty()) {
                return true;
            } else {
                FacesUtils.addErrorMessage("Debe adjuntar el documento que relaciona las pólizas");
                return false;
            }
        } else {
            getNuevoContratoBasico().llenarPolizas();
            getNuevoContratoBasico().instanciarPolizar();
            cargadorPoliza = new CargadorArchivosWeb();
            cargadorPoliza.cargarArchivosweb("poliza");
            return true;
        }

    }

    public void obtenerFechaMaximaContrato() {
        fechaMaxima = (getSessionBeanCobra().getCobraService().encontrarMaximaFecha(getNuevoContratoBasico().getContrato().getIntidcontrato()));
        if (fechaMaxima == null) {
            fechaMaxima = getNuevoContratoBasico().getContrato().getDatefechaini();
        }
    }

    public Boolean validarProrroga() {
        obtenerFechaMaximaContrato();
        if (modificarcontrato.getProrroga() == 0) {
            modificarcontrato.setDatefechafinalizacion(null);
            return true;
        } else {
            if (modificarcontrato.getDatefechafinalizacion() != null) {
                if (getNuevoContratoBasico().getContrato().getDatefechafin().before(modificarcontrato.getDatefechafinalizacion()) && modificarcontrato.getProrroga() == 1) {

                    if (getNuevoContratoBasico().getContrato().getContrato() != null) {
                        //si prorroga y la nueva fecha no supera la fecha del contrato padre
                        if (modificarcontrato.getDatefechafinalizacion().before(getNuevoContratoBasico().getContrato().getContrato().getDatefechafin())
                                || modificarcontrato.getDatefechafinalizacion().equals(getNuevoContratoBasico().getContrato().getContrato().getDatefechafin())) {
                            return true;
                        } else {
                            FacesUtils.addErrorMessage("La fecha de terminación debe ser inferior o igual a la fecha de terminación del contrato padre(" + (getNuevoContratoBasico().getContrato().getContrato().getDatefechafin().getYear() + 1900) + "-"
                                    + (getNuevoContratoBasico().getContrato().getContrato().getDatefechafin().getMonth() + 1) + "-" + getNuevoContratoBasico().getContrato().getContrato().getDatefechafin().getDate() + ")");
                            return false;
                        }
                    }
                    return true;
                    //si es disminucion y la fecha nueva no es inferior a la fecha de los cotratos/proyectos hijos siendo el contrato contratopadre y si es contrato hijo que no sea inferior a la fecha de inicio
                } else if (modificarcontrato.getDatefechafinalizacion().compareTo(fechaMaxima) >= 0 && modificarcontrato.getProrroga() == 2) {
                    return true;
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("fechanovalida") + fechaMaxima);
                    return false;
                }



            } else {
                FacesUtils.addErrorMessage(bundle.getString("fechavacia"));
                return false;
            }
        }
    }

    /**
     * metodo que se encarga de verificar si el valor a modificar del contrato
     * es valido.
     */
    public String modificarRecursosContrato() {
        //System.out.println("entro");

        //se obtiene los datos del contrato sin modificaciones
        Contrato contratoAntiguo = getSessionBeanCobra().getCobraService().encontrarContratoxId(getNuevoContratoBasico().getContrato().getIntidcontrato());

        //se obtiene el contrato que tiene la modificaciones
        Contrato contratoModificado = getNuevoContratoBasico().getContrato();

        BigDecimal valorTotalModificado = BigDecimal.ZERO;

        valorTotalModificado = valorTotalModificado.add(contratoModificado.getNumrecursosch().add(contratoModificado.getNumrecursospropios()).add(contratoModificado.getNumrecursostercero()));
        //se calcula el valor total del contrato teniedo en cuenta los recursos propios, recursos de colombia humanitaria, y recursos de terceros
        // contratoModificado.setNumvlrcontrato(contratoModificado.getNumrecursosch().add(contratoModificado.getNumrecursospropios()).add(contratoModificado.getNumrecursostercero()));
        // System.out.println("valorTotalModificado = " + valorTotalModificado);
        // Se valida que el valor del contrato no sea en 0
        if (getNuevoContratoBasico().getContrato().getNumrecursosch().compareTo(BigDecimal.ZERO) > 0 || getNuevoContratoBasico().getContrato().getNumrecursospropios().compareTo(BigDecimal.ZERO) > 0 || getNuevoContratoBasico().getContrato().getNumrecursostercero().compareTo(BigDecimal.ZERO) > 0) {
            //se verifica si se aumento el valor del contrato
            if (contratoAntiguo.getNumvlrcontrato().compareTo(valorTotalModificado) < 0) {

                //se verifica si el contrato tiene un contrato padre
                if (contratoAntiguo.getContrato() != null) {

                    BigDecimal vldispo = contratoModificado.getContrato().getNumvlrcontrato().subtract(contratoModificado.getContrato().getNumvlrsumahijos().add(contratoModificado.getContrato().getNumvlrsumaproyectos()));

                    valorModificadocontrato = valorTotalModificado.subtract(contratoAntiguo.getNumvlrcontrato());

                    //se verifica si el valor a modificar esta dentro del saldo disponible del contrato padre
                    if (valorModificadocontrato.compareTo(vldispo) <= 0) {

                        contratoModificado.getContrato().setNumvlrsumahijos(contratoModificado.getContrato().getNumvlrsumahijos().add(valorModificadocontrato));

                        setPermitirGuardar(true);
                    } else {
                        FacesUtils.addErrorMessage("El valor del " + getNuevoContratoBasico().getTipoContCon() + " supera el valor disponible del " + getNuevoContratoBasico().getTipoContCon() + " superior");
                    }
                } else {
                    setPermitirGuardar(true);

                }

                //se verifica si se le disminuyo valor al contrato
            } else if (contratoAntiguo.getNumvlrcontrato().compareTo(valorTotalModificado) > 0) {

                BigDecimal vlhijos = contratoModificado.getNumvlrsumahijos().add(contratoModificado.getNumvlrsumaproyectos());

                if (valorTotalModificado.compareTo(vlhijos) >= 0) {

                    valorModificadocontrato = contratoAntiguo.getNumvlrcontrato().subtract(valorTotalModificado);

                    if (contratoModificado.getContrato() != null) {
                        //el valor real que se va a disminuir se le resta al valor de numsumahijos contrato del padre
                        BigDecimal vldispo = contratoModificado.getContrato().getNumvlrsumahijos().subtract(valorModificadocontrato);
                        contratoModificado.getContrato().setNumvlrsumahijos(vldispo);
                        setPermitirGuardar(true);
                    } else {
                        setPermitirGuardar(true);
                    }

                } else {
                    NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("es", "CO", "Traditional_WIN"));
                    FacesUtils.addErrorMessage("El valor del contrato  no puede ser menor que la suma de los proyectos y contratos hijos " + money.format(vlhijos) + "");
                    permitirGuardar = false;
                }
            }

            if (isPermitirGuardar()) {
//            contratoModificado.setNumvlrcontrato(contratoModificado.getNumrecursosch().add(contratoModificado.getNumrecursospropios()).add(contratoModificado.getNumrecursostercero()));
//            getSessionBeanCobra().getCobraService().guardarContrato(contratoModificado);
//            
//            if (getNuevoContratoBasico().getContrato().getContrato() != null) {
//                 getSessionBeanCobra().getCobraService().guardarContrato(contratoModificado.getContrato());
//            }
                habilitarBtnGuardarCancelarValor = false;
                habilitarBtnModificarValor = true;
                FacesUtils.addInfoMessage("Por favor Ingrese Las Formas de Pago");
                permitirGuardar = true;

            }
        } else {
            FacesUtils.addErrorMessage("Ingrese el valor del contrato");
            permitirGuardar = false;
        }

        return null;
    }

    /**
     * Metodo que se encarga de verificar si las formas de pago son validas en
     * tal caso modifica el contrato con sus nuevas formas de pago.
     *
     * @author daniela garcia
     */
    public String modificarFormasPago() {
        Contrato contratoModificado = getNuevoContratoBasico().getContrato();
        contratoModificado.setNumvlrcontrato(contratoModificado.getNumrecursosch().add(contratoModificado.getNumrecursospropios()).add(contratoModificado.getNumrecursostercero()));
        List<Planificacionpago> lstPlanificacion = getSessionBeanCobra().getCobraService().encontrarPlanificacionpagoxContrato(contratoModificado);
        if (getNuevoContratoBasico().validarDiligenciamientoFormadePago()) {
            getSessionBeanCobra().getCobraService().guardarContrato(contratoModificado,getSessionBeanCobra().getUsuarioObra());
            if (getNuevoContratoBasico().getContrato().getContrato() != null) {
                getSessionBeanCobra().getCobraService().guardarContrato(contratoModificado.getContrato(),getSessionBeanCobra().getUsuarioObra());
            }
            getSessionBeanCobra().getCobraService().borrarPlanificacionPagos(lstPlanificacion);
            getSessionBeanCobra().getCobraService().guardarListaPlanificaciones(getNuevoContratoBasico().getLisplanifiactapar());
            FacesUtils.addInfoMessage("Se modifico correctamente el contrato");
            permitirGuardar = false;
        }
        return null;
    }

    public void setrenderVar(StringBuilder renderVar) {
        this.renderVar = renderVar;
    }

    public StringBuilder getrenderVar() {
        return renderVar;
    }

    public Boolean calcularValor() {
        if (modificarcontrato.getPresupuesto() == 0) {
            modificarcontrato.setNumnuevorecursosch(getNuevoContratoBasico().getContrato().getNumrecursosch());
            modificarcontrato.setNumnuevorecursospropios(getNuevoContratoBasico().getContrato().getNumrecursospropios());
            modificarcontrato.setNumnuevorecursostercero(getNuevoContratoBasico().getContrato().getNumrecursostercero());
            modificarcontrato.setNumvlrnuevo(getNuevoContratoBasico().getContrato().getNumvlrcontrato());

            return true;
        } else {

            if (getNuevoContratoBasico().getContrato().getNumvlrcontrato().compareTo(modificarcontrato.getNumvlrnuevo()) < 0 && modificarcontrato.getPresupuesto() == 1) {
                //Si es adicion y valor positivo
                if (getNuevoContratoBasico().getContrato().getContrato() != null) {
                    BigDecimal vldispo = getNuevoContratoBasico().getContrato().getContrato().getNumvlrcontrato().subtract(getNuevoContratoBasico().getContrato().getContrato().getNumvlrsumahijos());
                    valorModificadocontrato = modificarcontrato.getNumvlrnuevo().subtract(getNuevoContratoBasico().getContrato().getNumvlrcontrato());
                    if (valorModificadocontrato.compareTo(vldispo) <= 0) {//El valor del contrato a crear debe ser menor o igual al valor disponible                        
                        //el valor real que se va a aumentar se le suma al valor de numsumahijos contrato del padre
                        getNuevoContratoBasico().getContrato().getContrato().setNumvlrsumahijos(getNuevoContratoBasico().getContrato().getContrato().getNumvlrsumahijos().add(valorModificadocontrato));
                        return true;
                    } else {
                        FacesUtils.addErrorMessage("El valor del " + getNuevoContratoBasico().getTipoContCon() + " supera el valor disponible del " + getNuevoContratoBasico().getTipoContCon() + " superior");
                        return false;
                    }
                }
                return true;
            } else if (getNuevoContratoBasico().getContrato().getNumvlrcontrato().compareTo(modificarcontrato.getNumvlrnuevo()) > 0
                    && modificarcontrato.getPresupuesto() == 2) {
                //Si es reducción y pusieron un valor positivo
                BigDecimal vldispo3 = getNuevoContratoBasico().getContrato().getNumvlrsumahijos().add(getNuevoContratoBasico().getContrato().getNumvlrsumaproyectos());
                if (modificarcontrato.getNumvlrnuevo().compareTo(vldispo3) >= 0) {
                    valorModificadocontrato = getNuevoContratoBasico().getContrato().getNumvlrcontrato().subtract(modificarcontrato.getNumvlrnuevo());
                    BigDecimal vldispo = BigDecimal.ZERO;
                    if (getNuevoContratoBasico().getContrato().getContrato() != null) {
                        //el valor real que se va a disminuir se le resta al valor de numsumahijos contrato del padre
                        vldispo = getNuevoContratoBasico().getContrato().getContrato().getNumvlrsumahijos().subtract(valorModificadocontrato);
                        getNuevoContratoBasico().getContrato().getContrato().setNumvlrsumahijos(vldispo);
                    }
                    return true;
                } else {
                    NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("es", "CO", "Traditional_WIN"));

                    FacesUtils.addErrorMessage("El valor del contrato  no puede ser menor que la suma de los proyectos y contratos hijos " + money.format(vldispo3) + "");
                    return false;
                }

            } else {

                FacesUtils.addErrorMessage("No se ha realizado una modificación de presupuesto al valor del contrato.");
                return false;
            }
        }
    }

    public void calcularValorNuevoContrato() {
        if (modificarcontrato.getPresupuesto() == 0) {
            modificarcontrato.setNumnuevorecursosch(getNuevoContratoBasico().getContrato().getNumrecursosch());
            modificarcontrato.setNumnuevorecursospropios(getNuevoContratoBasico().getContrato().getNumrecursospropios());
            modificarcontrato.setNumnuevorecursostercero(getNuevoContratoBasico().getContrato().getNumrecursostercero());
            modificarcontrato.setNumvlrnuevo(getNuevoContratoBasico().getContrato().getNumvlrcontrato());

        } else {
            //
            if (modificarcontrato.getPresupuesto() >= 1) {
                modificarcontrato.setNumvlrnuevo(modificarcontrato.getNumnuevorecursosch().add(modificarcontrato.getNumnuevorecursospropios().add(modificarcontrato.getNumnuevorecursostercero())));
            }
        }
    }

//    public Boolean validarVlrContratoPadre() {
//        BigDecimal vldispo = new BigDecimal(BigInteger.ZERO);
//        vldispo = contrpadre.getNumvlrcontrato().subtract(contrpadre.getNumvlrsumahijos());
//        if (contrato.getNumvlrcontrato().compareTo(vldispo) == -1 || contrato.getNumvlrcontrato().compareTo(vldispo) == 0) {//El valor del contrato a crear debe ser menor o igual al valor disponible
//            return true;
//        }
//        return false;
//    }
    protected NuevoContratoBasico getNuevoContratoBasico() {
        return (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    /**
     * Selecciona la poliza a modificar
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return
     */
    public String seleccionareditarPoliza() {
        getNuevoContratoBasico().setPolizacontrato((Polizacontrato) getTablaPolizasbin().getRowData());
        getNuevoContratoBasico().setTipointpoli(getNuevoContratoBasico().getPolizacontrato().getTipopoliza().getInttipopoliza());
        getNuevoContratoBasico().setIndicepolizamodificar(getNuevoContratoBasico().getListaPolizacontratos().indexOf(tablaPolizasbin.getRowIndex()));
        return null;
    }

    /**
     * Elimina la póliza seleccionada
     *
     * @param 
     * @return
     */
    public String eliminarPoliza() {
        Polizacontrato poleli = (Polizacontrato) tablaPolizasbin.getRowData();
        getNuevoContratoBasico().getListaPolizacontratos().remove(poleli);
        return null;
    }

    public String volver() {
        switch (cual) {
            case 1:
                if (validarProrroga()) {
                    valido = true;
                } else {
                    return null;
                }
            case 2:
                if (calcularValor()) {
                    valido = true;
                } else {
                    return null;
                }
            case 3:
                if (validarPolizas()) {
                    valido = true;
                } else {
                    return null;
                }
            case 4:
                valido = true;
                break;
        }
        if (valido) {
//            if (cual == 4) {
//                if (modificacion.getProrroga() == 0 && modificacion.getPresupuesto() == 0) {
//                    cual = cual - 1;
//                }
//            }
            cual = cual - 1;
        }
        return null;
    }

    public String iniciarModicontrato() {
        //contrato=null;
        limpiarModificarContrato();
        cual = 1;
        valido = false;
        return "modificarContrato";

    }

//    public String subirDocPoliza() {
//        if (!getDocumentoobra().getStrnombre().equals("")) {
//          //  getDocumentoobra().setContrato(contrato);
//            subirDocumento();
//            listadocumentos.add(getDocumentoobra());
//            setDocumentoobra(new Documentoobra());
//            getDocumentoobra().setDatefecha(new Date());
//            setSubirDocumento(new SubirArchivoBean(1, true, false));
//            //getSessionBeanCobra().getCobraService().guardarDocumento(documentoobra);
//        } else {
//            FacesUtils.addErrorMessage(bundle.getString("debeproporcinarelnombredeldocu"));
//        }
//        return null;
//    }
    public String subirDocumentoModificacion(CargadorArchivosWeb cargador) {
        String rutaWeb = null;
        if (!cargador.getArchivos().isEmpty()) {
            try {
                cargador.getArchivos().get(0).cambiarNombre(null, true);
                cargador.guardarArchivosTemporales(MessageFormat.format(RutasWebArchivos.DOCS_MODI_CONTRATO, "" + getNuevoContratoBasico().getContrato().getIntidcontrato()), false);
                rutaWeb = cargador.getArchivos().get(0).getRutaWeb();
            } catch (ArchivoExistenteException ex) {
                Logger.getLogger(ModificarContrato.class.getName()).log(Level.SEVERE, null, ex);
                mensaje = (bundle.getString("nosepuedesubir"));
            }
        }
        return rutaWeb;
    }

    public String borrarPoliza() {
        if (!cargadorPoliza.getArchivos().isEmpty()) {
            cargadorPoliza.borrarDatosSubidos();
        }
        return null;
    }

    public String borrarOtrosi() {
        if (!cargadorOtrosi.getArchivos().isEmpty()) {
            cargadorOtrosi.borrarDatosSubidos();
        }
        return null;
    }

    private String limpiarModificarContrato() {

        modificarcontrato = new Modificacioncontrato();
        modificarcontrato.setNumnuevorecursosch(getNuevoContratoBasico().getContrato().getNumrecursosch());
        modificarcontrato.setNumnuevorecursospropios(getNuevoContratoBasico().getContrato().getNumrecursospropios());
        modificarcontrato.setNumnuevorecursostercero(getNuevoContratoBasico().getContrato().getNumrecursostercero());
        modificarcontrato.setNumvlrnuevo(getNuevoContratoBasico().getContrato().getNumvlrcontrato());
        modificarcontrato.setDatefechafinalizacion(getNuevoContratoBasico().getContrato().getDatefechafin());
        modificarcontrato.setTipomodificacions(new LinkedHashSet());
        modificarcontrato.setHistoricopolizacontratos(new LinkedHashSet());
        documentopoliza = new Documentoobra();
        documentootrosi = new Documentoobra();
        documentopoliza.setTipodocumento(new Tipodocumento(1, "", true));
        documentootrosi.setTipodocumento(new Tipodocumento(11, "", true));
        documentopoliza.setModificacioncontrato(modificarcontrato);
        documentootrosi.setModificacioncontrato(modificarcontrato);
        documentopoliza.setStrnombre("Documento polizas modificación contrato " + getNuevoContratoBasico().getContrato().getIntidcontrato());
        documentootrosi.setStrnombre("Documento otrosi modificación contrato " + getNuevoContratoBasico().getContrato().getIntidcontrato());

        cargadorPoliza = new CargadorArchivosWeb();
        cargadorPoliza.cargarArchivosweb("poliza");
        cargadorOtrosi = new CargadorArchivosWeb();
        cargadorOtrosi.cargarArchivosweb("otrosi");
        cual = 1;
        fechaMaxima = new Date();
        getNuevoContratoBasico().getListaPolizacontratos().clear();

        return null;
    }

    public String guardarModificarContrato() {
        if (modificarcontrato.getProrroga() == 0 && modificarcontrato.getPresupuesto() == 0 && modificarcontrato.getPolizas() == 0) {
            FacesUtils.addErrorMessage("No ha seleccionado ningun cambio a efectuar.");
        } else {
            boolean band = false;
            if (modificarcontrato.getProrroga() >= 1 || modificarcontrato.getPresupuesto() >= 1) {

                if (!cargadorOtrosi.getArchivos().isEmpty()) {
                    band = true;
                } else {

                    FacesUtils.addErrorMessage("Debe adjuntar un otrosi que sustente la modificación");
                    band = false;
                    return null;
                }
            } else {
                band = true;
                cargadorOtrosi.borrarDatosSubidos();
                band = true;
            }
            if (modificarcontrato.getPolizas() == 1) {
                band = validarPolizas();
            }



            if (band) {
                modificarcontrato.setContrato(getNuevoContratoBasico().getContrato());
                modificarcontrato.setBoolestado(false);
                modificarcontrato.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
                modificarcontrato.setDatefecharegistro(new Date());
                validartipificacionmodifcacion();
                modificarcontrato.setHistoricopolizacontratos(new LinkedHashSet());
                castearPolizaaHistoricoPoliza();
                modificarcontrato.setDocumentoobras(new LinkedHashSet());
                if (modificarcontrato.getProrroga() >= 1 || modificarcontrato.getPresupuesto() >= 1) {
                    documentootrosi.setStrubicacion(subirDocumentoModificacion(cargadorOtrosi));
                    documentootrosi.setDatefecha(new Date());
                    documentootrosi.setTipodocumento(new Tipodocumento(11, "", true));
                    documentootrosi.setStrnombre("Otro si");
                    documentootrosi.setContrato(getNuevoContratoBasico().getContrato());
                    modificarcontrato.getDocumentoobras().add(documentootrosi);
                }
                if (modificarcontrato.getPolizas() == 1) {
                    documentopoliza.setStrubicacion(subirDocumentoModificacion(cargadorPoliza));
                    documentopoliza.setDatefecha(new Date());
                    documentopoliza.setTipodocumento(new Tipodocumento(20, "", true));
                    documentopoliza.setStrnombre("Poliza");
                    documentopoliza.setContrato(getNuevoContratoBasico().getContrato());
                    modificarcontrato.getDocumentoobras().add(documentopoliza);
                }
                getSessionBeanCobra().getCobraService().guardarModificarContrato(modificarcontrato);
                //Verificar si tiene contrato padre
                if (getNuevoContratoBasico().getContrato().getContrato() != null) {
                    //guardar el contrato padre con la modificacion en valor suma hijos
                    getSessionBeanCobra().getCobraService().guardarContrato(getNuevoContratoBasico().getContrato().getContrato(),getSessionBeanCobra().getUsuarioObra());
                }


                if (getSessionBeanCobra().getCobraService().f_registrar_modificacion_contrato(modificarcontrato)) {
                    FacesUtils.addInfoMessage("La modificación del contrato se ha registrado con éxito.");
                    getNuevoContratoBasico().setContrato(getSessionBeanCobra().getCobraService().encontrarContratoxId(getNuevoContratoBasico().getContrato().getIntidcontrato()));
                    getNuevoContratoBasico().setFinentrega(getNuevoContratoBasico().getContrato().getDatefechafin().toString());
                    limpiarModificarContrato();
                } else {
                    FacesUtils.addErrorMessage("Ocurrió un problema al consolidar la modificación.");

                }



            }
        }

        /**
         * Si la modificación proviene de modificación de contrato, regresa a
         * modificar el proyecto
         */
        if (vieneDeModificarProyecto) {
            getModificarProyecto().btSiguientePaso4Action();
            return "modificarProyecto";
        }
        return null;
    }

    /**
     * Obtiene el bean encargado de Modificar los proyectos
     *
     * @return
     */
    protected ModificarProyecto getModificarProyecto() {
        return (ModificarProyecto) FacesUtils.getManagedBean("Supervisor$ModificarProyecto");
    }

    public void validartipificacionmodifcacion() {

        if (modificarcontrato.getProrroga() >= 1) {
            modificarcontrato.getTipomodificacions().add(new Tipomodificacion(1, "PRORROGA"));
        }
        if (modificarcontrato.getPresupuesto() >= 1) {
            modificarcontrato.getTipomodificacions().add(new Tipomodificacion(2, "ADICION DE PRESUPUESTO"));

        }
        if (modificarcontrato.getPolizas() == 1) {
            modificarcontrato.getTipomodificacions().add(new Tipomodificacion(11, "MODIFICACIÓN PÓLIZA"));

        }
        if (modificarcontrato.getPlanificacion() == 1) {
            modificarcontrato.getTipomodificacions().add(new Tipomodificacion(12, "MODIFICACIÓN PLANIFICACIÓN PAGO"));

        }


    }

    public String llenarPolizas() {
        getNuevoContratoBasico().llenarPolizas();
        getNuevoContratoBasico().instanciarPolizar();
        return null;
    }

    public void castearPolizaaHistoricoPoliza() {
        int i = 0;
        while (i < getNuevoContratoBasico().getListaPolizacontratos().size()) {
            Historicopolizacontrato histo = new Historicopolizacontrato();
            histo.setAseguradora(getNuevoContratoBasico().getListaPolizacontratos().get(i).getAseguradora());
            histo.setDatefechavencimiento(getNuevoContratoBasico().getListaPolizacontratos().get(i).getDatefechavecimiento());
            histo.setModificacioncontrato(modificarcontrato);
            histo.setStrdescripcion(getNuevoContratoBasico().getListaPolizacontratos().get(i).getStrdescripcion());
            histo.setStrdochispolcon(getNombreArchivoPoliza());
            histo.setStrnumhispolcon(getNuevoContratoBasico().getListaPolizacontratos().get(i).getStrnumpoliza());
            histo.setTipopoliza(getNuevoContratoBasico().getListaPolizacontratos().get(i).getTipopoliza());

            modificarcontrato.getHistoricopolizacontratos().add(histo);

            i++;
        }
    }

    public Boolean validarcontratohijo() {
        BigDecimal vldispo = getNuevoContratoBasico().getContrato().getContrato().getNumvlrcontrato().subtract(getNuevoContratoBasico().getContrato().getContrato().getNumvlrsumahijos());
        if (getNuevoContratoBasico().getContrato().getNumvlrcontrato().compareTo(vldispo) == -1 || getNuevoContratoBasico().getContrato().getNumvlrcontrato().compareTo(vldispo) == 0) {//El valor del contrato a crear debe ser menor o igual al valor disponible
            return true;
        }
        FacesUtils.addErrorMessage("El valor del " + getNuevoContratoBasico().getTipoContCon() + " supera el valor del " + getNuevoContratoBasico().getTipoContCon() + " superior");
        return false;
    }

    public String volverContrato() {

        return "consultarContrato";
    }

    public String llenarDocumentosModifContrato() {
       Modificacioncontrato modi = (Modificacioncontrato) binditablamodi.getRowData();
       listadocuModifContrato = getSessionBeanCobra().getCobraService().obtenerDocumentosxModificacionContrato(modi);
        return null;
    }

    /**
     * Seleccionar un registro de documentos de modificación en contrato.
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return null
     */
    public String bt_download_documento_action() {
        Documentoobra doc = (Documentoobra) tabladocuModiContrato.getRowData();

        getSessionBeanCobra().setUrlAbri(doc.getStrubicacion());

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/" + doc.getStrubicacion());
        } catch (IOException ex) {
            Logger.getLogger(Modificacioncontrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Obtiene el nombre del archivo cargado, correspondiente al Otro Si
     *
     * @return
     */
    public String getNombreArchivoOtroSi() {
        String nombreArchivo = "";
        if (!cargadorOtrosi.getArchivos().isEmpty()) {
            nombreArchivo = cargadorOtrosi.getArchivos().get(0).getNombre();
        }
        return nombreArchivo;
    }

    /**
     * Obtiene el nombre del archivo cargado, correspondiente a la Póliza
     *
     * @return
     */
    public String getNombreArchivoPoliza() {
        String nombreArchivo = "";
        if (!cargadorPoliza.getArchivos().isEmpty()) {
            nombreArchivo = cargadorPoliza.getArchivos().get(0).getNombre();
        }
        return nombreArchivo;
    }

    /**
     * @return the permitirGuardar
     */
    public boolean isPermitirGuardar() {
        return permitirGuardar;
    }

    /**
     * @param permitirGuardar the permitirGuardar to set
     */
    public void setPermitirGuardar(boolean permitirGuardar) {
        this.permitirGuardar = permitirGuardar;
    }

    /**
     * metodo que se encarga de verificar si el valor a modificar del contrato
     * es valido.
     */
    public void habilitarBotonModificarValor() {
        habilitarBtnGuardarCancelarValor = true;
        habilitarBtnModificarValor = false;
        permitirGuardar = false;
    }

    public void cancelarModificacionValor() {
        habilitarBtnGuardarCancelarValor = false;
        habilitarBtnModificarValor = true;
    }

    /**
     * @return the tablaPolizasbin
     */
    public UIDataTable getTablaPolizasbin() {
        return tablaPolizasbin;
    }

    /**
     * @param tablaPolizasbin the tablaPolizasbin to set
     */
    public void setTablaPolizasbin(UIDataTable tablaPolizasbin) {
        this.tablaPolizasbin = tablaPolizasbin;
    }
}
