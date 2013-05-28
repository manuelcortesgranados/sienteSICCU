package cobra.Supervisor;

import co.com.interkont.cobra.to.*;
import co.com.interkont.cobra.to.excel.ConstructorCronogramaExcel;
import co.com.interkont.cobra.to.excel.ConstructorWorkbook;
import co.com.interkont.cobra.to.excel.CronogramaExcel;
import co.com.interkont.cobra.to.excel.ConstructorWorkbookConCronogramaExcel;
import co.com.interkont.cobra.to.excel.MensajeExcel;
import co.com.interkont.cobra.to.excel.ConstructorCronogramaExcelConObraModificada;
import co.com.interkont.cobra.to.excel.ValidadorCronogramaExcelConObraModificada;
import co.com.interkont.cobra.to.excel.ValidadorCronogramaExcel;
import co.com.interkont.cobra.to.excel.ConstructorCronogramaExcelConWorkbook;
import co.com.interkont.cobra.to.excel.ConstructorObra;
import co.com.interkont.cobra.to.excel.ConstructorObraConCronogramaExcel;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import cobra.ArchivoWeb;
import cobra.CargadorArchivosWeb;
import cobra.Cobra.Download;
import cobra.SessionBeanCobra;
import cobra.service.CobraServiceAble;
import cobra.service.ModificarProyectoServiceAble;
import cobra.util.ArchivoWebUtil;
import cobra.util.RutasWebArchivos;
import com.interkont.cobra.exception.ArchivoExistenteException;
import com.interkont.cobra.exception.ArchivoNoExistenteException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.richfaces.component.UIDataTable;

/**
 * ManagedBean encargado de manejar la presentación correspondiente a la
 * modificación de proyectos
 *
 * @author Jhon Eduard Ortiz S
 */
public class ModificarProyecto  implements Serializable{

    //<editor-fold defaultstate="collapsed" desc="Constantes">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables de Servicios">
    /**
     * Proporciona los servicios relacionados a una obra
     */
    private CobraServiceAble cobraService;
    /**
     * Proporciona los servicios relacionados a la modificación de un proyecto
     */
    private ModificarProyectoServiceAble modificarProyectoService;
    /**
     * Constructor del objeto que representa el cronograma
     */
    private ConstructorCronogramaExcel constructorCronogramaExcel;
    /**
     * Constructor del objeto que representa la hoja de cálculo
     */
    private ConstructorWorkbook constructorWorkbook;
    /**
     * Validador del cronograma de excel
     */
    private ValidadorCronogramaExcel validadorCronogramaExcel;
    /**
     * Carga la información de una obra a partir de otro objeto
     */
    private ConstructorObra constructorObra;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables de visualización">
    /**
     * Si true, se visualizará el paso 1 de modificar proyecto
     */
    private boolean verPaso1 = true;
    /**
     * Si true, se visualizará el paso 2 de modificar proyecto
     */
    private boolean verPaso2;
    /**
     * Si true, se visualizará el paso 3 de modificar proyecto
     */
    private boolean verPaso3;
    /**
     * Si true, se visualizará el paso 4 de modificar proyecto
     */
    private boolean verPaso4;
    /**
     * Si true, se visualizará el paso 5 de modificar proyecto
     */
    private boolean verPaso5;
    /**
     * Si true, se visualizará el botón para seleccionar el contrato al cual se
     * se asociará el valor de la adición
     */
    private boolean verSeleccionarContrato;
    /**
     * Objeto que representa la tabla de contratos
     */
    private UIDataTable tablacontratos = new UIDataTable();
    /**
     * Objeto que representa la tabla de documentos
     */
    private UIDataTable tablaDocumentosModificacion = new UIDataTable();
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables de Apoyo">
    /**
     * Objeto utilizado para subir el archivo del cronograma
     */
    private CargadorArchivosWeb cargadorCronograma = new CargadorArchivosWeb();
    /**
     * Objeto utilizado para subir los archivos relacionados a la modificacion
     */
    private CargadorArchivosWeb cargadorDocumentos = new CargadorArchivosWeb();
    /**
     * Lista en la que se van almacenar los documentos asociados a la
     * modificación.
     */
    private List<Documentoobra> listadocumentosmodi = new ArrayList<Documentoobra>();
    /**
     * Lista en la que se van almacenar los tipos de documentos asociados a la
     * modificación.
     */
    private List<Tipodocumento> listaTipoDocumento;
    /**
     * Objeto en el que se cargan los documentos de la modificacion
     */
    private Documentoobra documentomodificacion = new Documentoobra();
    /**
     * Combo de los tipos de documentos
     */
    private SelectItem[] selectItemTipoDocumentoModi;
    /**
     * Variable para almacenar el tipo de documento elegido
     */

    /*
     * lista el mensaje con el tipo de error al cargar un cronograma en excel
     */
    private List<MensajeExcel> lstmsjexcel;
    /*
     * almacena el valor de la obra sin saber si tiene o no incluido el AIU
     */
    BigDecimal valortotalobraCosto;
    /**
     * Variable que almacena el iva aplicado sobre la utilidad
     */
    BigDecimal ivasobreutilidad;
    /**
     * Tipo de documento selaeccionado cuando se adiciona un documento a la 
     * modificacipon
     */
    private int tipodoc;
    
    /**
     * Verdadero si fecha de finalización de alguno de los contratos es
     * posterior o igual la fecha de prorroga de la modificación
     */
    private boolean fechaContratoValida;
    /**
     * Verdadero si ya se asoció al contrato el valor correspondiente a la
     * adición
     */
    private boolean valorAdicionAsociadoAContrato;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="DTOs">
    /**
     * Contiene la información del proyecto a modificar
     */
    private Obra obra;
    /**
     * Contiene la información de la modificación y luego la información del
     * proyecto para almacenar el histórico correspondiente
     */
    private Historicoobra historicoobra = new Historicoobra();
    /**
     * Variable temporal para el manejo de los documentos de la modificación
     */
    private Documentoobra documentoobra;
    /**
     * Última alimentación del proyecto
     */
    private Alimentacion ultimaAlimentacion;

    public CargadorArchivosWeb getCargadorDocumentos() {
        return cargadorDocumentos;
    }

    public void setCargadorDocumentos(CargadorArchivosWeb cargadorDocumentos) {
        this.cargadorDocumentos = cargadorDocumentos;
    }

    public Alimentacion getUltimaAlimentacion() {
        return ultimaAlimentacion;
    }

    public void setUltimaAlimentacion(Alimentacion ultimaAlimentacion) {
        this.ultimaAlimentacion = ultimaAlimentacion;
    }

    public List<Documentoobra> getListadocumentosmodi() {
        return listadocumentosmodi;
    }

    public void setListadocumentosmodi(List<Documentoobra> listadocumentosmodi) {
        this.listadocumentosmodi = listadocumentosmodi;
    }
    /**
     * Contiene la información que se presentará en el cronograma de
     * modificación
     */
    private CronogramaExcel cronogramaExcel;

    public boolean isVerPaso4() {
        return verPaso4;
    }

    public void setVerPaso4(boolean verPaso4) {
        this.verPaso4 = verPaso4;
    }

    public ConstructorObra getConstructorObra() {
        return constructorObra;
    }

    public void setConstructorObra(ConstructorObra constructorObra) {
        this.constructorObra = constructorObra;
    }

    public boolean isVerPaso5() {
        return verPaso5;
    }

    public void setVerPaso5(boolean verPaso5) {
        this.verPaso5 = verPaso5;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters de la clase">
    public boolean isValorAdicionAsociadoAContrato() {
        return valorAdicionAsociadoAContrato;
    }

    public void setValorAdicionAsociadoAContrato(boolean valorAdicionAsociadoAContrato) {
        this.valorAdicionAsociadoAContrato = valorAdicionAsociadoAContrato;
    }
    
    public boolean isVerSeleccionarContrato() {
        return verSeleccionarContrato;
    }

    public void setVerSeleccionarContrato(boolean verSeleccionarContrato) {
        this.verSeleccionarContrato = verSeleccionarContrato;
    }
    
    public boolean isFechaContratoValida() {
        return fechaContratoValida;
    }

    public void setFechaContratoValida(boolean fechaContratoValida) {
        this.fechaContratoValida = fechaContratoValida;
    }

    public UIDataTable getTablacontratos() {
        return tablacontratos;
    }

    public void setTablacontratos(UIDataTable tablacontratos) {
        this.tablacontratos = tablacontratos;
    }

    public boolean isModificarContrato() {
        return fechaContratoValida;
    }

    public void setModificarContrato(boolean modificarContrato) {
        this.fechaContratoValida = modificarContrato;
    }

    public Documentoobra getDocumentoobra() {
        return documentoobra;
    }

    public void setDocumentoobra(Documentoobra documentoobra) {
        this.documentoobra = documentoobra;
    }

    public CronogramaExcel getCronogramaExcel() {
        return cronogramaExcel;
    }

    public void setCronogramaExcel(CronogramaExcel cronogramaExcel) {
        this.cronogramaExcel = cronogramaExcel;
    }

    public ConstructorCronogramaExcel getConstructorCronogramaExcel() {
        return constructorCronogramaExcel;
    }

    public void setConstructorCronogramaExcel(ConstructorCronogramaExcel constructorCronogramaExcel) {
        this.constructorCronogramaExcel = constructorCronogramaExcel;
    }

    public ConstructorWorkbook getConstructorWorkbook() {
        return constructorWorkbook;
    }

    public void setConstructorWorkbook(ConstructorWorkbook constructorWorkbook) {
        this.constructorWorkbook = constructorWorkbook;
    }

    public CobraServiceAble getCobraService() {
        return cobraService;
    }

    public void setCobraService(CobraServiceAble cobraService) {
        this.cobraService = cobraService;
    }

    public ModificarProyectoServiceAble getModificarProyectoService() {
        return modificarProyectoService;
    }

    public void setModificarProyectoService(ModificarProyectoServiceAble modificarProyectoService) {
        this.modificarProyectoService = modificarProyectoService;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public CargadorArchivosWeb getCargadorCronograma() {
        return cargadorCronograma;
    }

    public void setCargadorCronograma(CargadorArchivosWeb cargadorCronograma) {
        this.cargadorCronograma = cargadorCronograma;
    }

    public Historicoobra getHistoricoobra() {
        return historicoobra;
    }

    public void setHistoricoobra(Historicoobra historicoObra) {
        this.historicoobra = historicoObra;
    }

    public boolean isVerPaso1() {
        return verPaso1;
    }

    public void setVerPaso1(boolean verPaso1) {
        this.verPaso1 = verPaso1;
    }

    public boolean isVerPaso2() {
        return verPaso2;
    }

    public void setVerPaso2(boolean verPaso2) {
        this.verPaso2 = verPaso2;
    }

    public boolean isVerPaso3() {
        return verPaso3;
    }

    public void setVerPaso3(boolean verPaso3) {
        this.verPaso3 = verPaso3;
    }

    public ValidadorCronogramaExcel getValidadorCronogramaExcel() {
        return validadorCronogramaExcel;
    }

    public void setValidadorCronogramaExcel(ValidadorCronogramaExcel validadorCronogramaExcel) {
        this.validadorCronogramaExcel = validadorCronogramaExcel;
    }

    public List<MensajeExcel> getLstmsjexcel() {
        return lstmsjexcel;
    }

    public void setLstmsjexcel(List<MensajeExcel> lstmsjexcel) {
        this.lstmsjexcel = lstmsjexcel;
    }

    public UIDataTable getTablaDocumentosModificacion() {
        return tablaDocumentosModificacion;
    }

    public void setTablaDocumentosModificacion(UIDataTable tablaDocumentosModificacion) {
        this.tablaDocumentosModificacion = tablaDocumentosModificacion;
    }

    public BigDecimal getIvasobreutilidad() {
        return ivasobreutilidad;
    }

    public void setIvasobreutilidad(BigDecimal ivasobreutilidad) {
        this.ivasobreutilidad = ivasobreutilidad;
    }

    public BigDecimal getValortotalobraCosto() {
        return valortotalobraCosto;
    }

    public void setValortotalobraCosto(BigDecimal valortotalobraCosto) {
        this.valortotalobraCosto = valortotalobraCosto;
    }

    public int getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(int tipodoc) {
        this.tipodoc = tipodoc;
    }

    public SelectItem[] getSelectItemTipoDocumentoModi() {
        return selectItemTipoDocumentoModi;
    }

    public void setSelectItemTipoDocumentoModi(SelectItem[] selectItemTipoDocumentoModi) {
        this.selectItemTipoDocumentoModi = selectItemTipoDocumentoModi;
    }

    public Documentoobra getDocumentomodificacion() {
        return documentomodificacion;
    }

    public void setDocumentomodificacion(Documentoobra documentomodificacion) {
        this.documentomodificacion = documentomodificacion;
    }

    public List<Tipodocumento> getListaTipoDocumento() {
        return listaTipoDocumento;
    }

    public void setListaTipoDocumento(List<Tipodocumento> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Acciones">
    /**
     * Continúa en el paso 2 de modificar proyecto
     *
     * @return
     */
    public String btSiguientePaso1Action() {
        if (validarPaso1()) {
            this.verPaso1 = false;
            this.verPaso2 = true;
            getSessionBeanCobra().getCobraService().guardarHistorico(historicoobra, getSessionBeanCobra().getUsuarioObra());
            Tipoestadobra tipoestadobra = new Tipoestadobra(Tipoestadobra.EN_MODIFICACION);
            obra.setTipoestadobra(tipoestadobra);
            getSessionBeanCobra().getCobraService().guardarObra(obra, getSessionBeanCobra().getUsuarioObra(), -1);
        }

        return null;
    }

    /**
     * Continúa en el paso 3 de modificar proyecto
     *
     * @return
     */
    public String btSiguientePaso2Action() {
        if (validarPaso2()) {
            if (!historicoobra.isAdicionactivi() && !historicoobra.isAdicionpresu()
                    && !historicoobra.isAdiciontiempo() && !historicoobra.isCambioprecios()) {
                historicoobra.setReprogramacion(true);
            }
            historicoobra.setTipomodificacions(new HashSet());
            //Se establece la tipificación de la modificación
            if (historicoobra.isAdicionactivi()) {
                Tipomodificacion tipomodificacion = new Tipomodificacion(Tipomodificacion.ADICION_ACTIVIDADES, null);
                historicoobra.getTipomodificacions().add(tipomodificacion);
            }
            if (historicoobra.isAdicionpresu()) {
                Tipomodificacion tipomodificacion = new Tipomodificacion(Tipomodificacion.ADICION_PRESUPUESTO, null);
                historicoobra.getTipomodificacions().add(tipomodificacion);
            }
            if (historicoobra.isAdiciontiempo()) {
                Tipomodificacion tipomodificacion = new Tipomodificacion(Tipomodificacion.PRORROGA, null);
                historicoobra.getTipomodificacions().add(tipomodificacion);
            }
            if (historicoobra.isCambioprecios()) {
                Tipomodificacion tipomodificacion = new Tipomodificacion(Tipomodificacion.CAMBIO_PRECIOS, null);
                historicoobra.getTipomodificacions().add(tipomodificacion);
            }
            if (historicoobra.isReprogramacion()) {
                Tipomodificacion tipomodificacion = new Tipomodificacion(Tipomodificacion.REPROGRAMACION, null);
                historicoobra.getTipomodificacions().add(tipomodificacion);
            }
            getSessionBeanCobra().getCobraService().guardarHistorico(historicoobra, getSessionBeanCobra().getUsuarioObra());
            
            this.verPaso2 = false;
            if(obra.isBooleantienehijos()) {
                this.verPaso4 = true;
            } else {
                this.verPaso3 = true;
            }
        }
        return null;
    }

    /**
     * Regresa al paso 1 de modificar proyecto
     *
     * @return Resultado correspondiente en el faces-config
     */
    public String btAnteriorPaso2Action() {
        this.verPaso1 = true;
        this.verPaso2 = false;
        return null;
    }

    /**
     * Regresa al paso 1 de modificar proyecto
     *
     * @return Resultado correspondiente en el faces-config
     */
    public String btAnteriorPaso3Action() {
        this.verPaso2 = true;
        this.verPaso3 = false;
        return null;
    }

    /**
     * Continúa en el paso 3 de modificar proyecto
     *
     * @return
     */
    public String btSiguientePaso3Action() {
        if (validarPaso3()) {
            this.verPaso3 = false;
            this.verPaso4 = true;
            cargarDocumentosModificacion();
        }
        return null;
    }

    /**
     * Regresa al paso 3 de modificar proyecto
     *
     * @return Resultado correspondiente en el faces-config
     */
    public String btAnteriorPaso4Action() {
        if(obra.isBooleantienehijos()) {
            this.verPaso2 = true;
        } else {
            this.verPaso3 = true;
        }
        this.verPaso4 = false;
        return null;
    }

    /**
     * Continúa en el paso 4 de modificar proyecto
     *
     * @return Resultado correspondiente en el faces-config
     */
    public String btSiguientePaso4Action() {
        if (validarPaso4()) {
            //Si es una adición de presupuesto, de tiempo o si el valor de la obra disminuyó en la programación del cronograma
            if (historicoobra.isAdiciontiempo() || historicoobra.isAdicionpresu() || historicoobra.isDisminucionpresu()) {
                obra.setRelacionesContratoObraConsultoria(getSessionBeanCobra().getCobraService().encontrarRelacionesContratoObraConsultoria(obra.getIntcodigoobra()));
                obra.setRelacioncontratoobras(new HashSet(getSessionBeanCobra().getCobraService().obtenerRelacionesContratoxObra(obra.getIntcodigoobra())));
                if (historicoobra.isAdiciontiempo()) {
                    if (obra.getRelacionesContratoObraConsultoria() != null) {
                        if (obra.getRelacionesContratoObraConsultoria().size() > 0) {
                            Date ultimaFechaFinContratosConsultoria = obra.obtenerUltimaFechaFinContratosConsultoria();
                            if (historicoobra.getDatefecfinhist().after(ultimaFechaFinContratosConsultoria)) {
                                FacesContext.getCurrentInstance().addMessage(
                                        null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        Propiedad.getValor("fechafinpostcontratoerror"), ""));
                            }
                        }
                    }
                }
                //Si es una adición de presupuesto o si el valor de la obra disminuyó en la programación del cronograma
                if(historicoobra.isAdicionpresu() || historicoobra.isDisminucionpresu()) {
                    verSeleccionarContrato = true;
                } else {
                    verSeleccionarContrato = false;
                }
                verPaso4 = false;
                verPaso5 = true;
            } else {
                boolean registroExitoso = registrarModificacion();
                if (registroExitoso) {
                    return getAdministrarObraNewBean().iniciardeta();
                }
            }
        }
        return null;
    }

    /**
     * Regresa al paso 4 de la modificación
     *
     * @return
     */
    public String btAnteriorPaso5Action() {
        this.verPaso4 = true;
        this.verPaso5 = false;
        return null;
    }

    /**
     * Ejecuta las acciones del paso 5 de la modificación
     *
     * @return
     */
    public String btSiguientePaso5Action() {
        if (validarPaso5()) {
            boolean registroExitoso = registrarModificacion();
                if (registroExitoso) {
                    return getAdministrarObraNewBean().iniciardeta();
                }
        }
        return null;
    }

    /**
     * Maneja los métodos de apoyo que colaboran con la generación del
     * cronograma de modificación
     *
     * @return
     */
    public String btGenerarCronogramaAction() {
        generarCronograma();
        return null;
    }

    /**
     * Método encargado de invocar la descarga del cronograma mediante la opción
     * de descargar el cronograma
     *
     * @return
     */
    public String btDescargarCronogramaAction() {
        getDownload().onDownload();
        return null;
    }

    /**
     * Método invocado en la opción de validar en la modal de subir cronograma
     *
     * @return
     */
    public String btValidarCronogramaAction() {
        validarCronograma();
        return null;
    }

    /**
     * Acción generada por el botón de agregar documento
     *
     * @return
     */
    public String btAgregarDocumentoAction() {
        agregarDocumento();
        return null;
    }

    /**
     * Acción que se ejecuta al seleccionar un contrato de la lista de contratos
     * @return 
     */
    public String btSeleccionarContratoAction() {
        Relacioncontratoobra contselec = (Relacioncontratoobra) tablacontratos.getRowData();
        BigDecimal vlrDisponibleContrato = contselec.getContrato().getNumvlrcontrato().subtract(contselec.getContrato().getNumvlrsumahijos().add(contselec.getContrato().getNumvlrsumaproyectos()));
        if (vlrDisponibleContrato.compareTo(historicoobra.getNumvalorhist()) >= 0) {
            //Si el valor de la obra disminuyó en la programación del cronograma
            if(historicoobra.isDisminucionpresu()) {
                contselec.setNumvalorrelacion(contselec.getNumvalorrelacion().subtract(cronogramaExcel.getFaltantePorProgramar()));
            } else { //De lo contrario se trata de una adición de presupuesto
                contselec.setNumvalorrelacion(contselec.getNumvalorrelacion().add(historicoobra.getNumvalorhist()));
            }
            Iterator<Relacioncontratoobra> itRelacionesContratoObra = obra.getRelacioncontratoobras().iterator();
            while (itRelacionesContratoObra.hasNext()) {
                Relacioncontratoobra relacioncontratoobra = itRelacionesContratoObra.next();
                if (relacioncontratoobra.getIntidserial() == contselec.getIntidserial()) {
                    relacioncontratoobra.setNumvalorrelacion(contselec.getNumvalorrelacion());
                }
            }
            valorAdicionAsociadoAContrato = true;
            verSeleccionarContrato = false;
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    Propiedad.getValor("contratoasociamodiinfo"), ""));
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    Propiedad.getValor("topevalorcontratoerrormodi"), ""));
        }
        return null;
    }

    /**
     * Acción que se ejecuta para modificar un contrato de la lista de contratos
     * @return
     */
    public String btModificarContratoAction() {
        Relacioncontratoobra contselec = (Relacioncontratoobra) tablacontratos.getRowData();
        return visualizarModificarContrato(contselec.getContrato());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Validaciones">
    /**
     * Valida los datos ingresados en el paso 1 de la modificación de un
     * proyecto
     *
     * @return retorna true si los datos de ingresaron correctamente, de lo
     * contrario retorna falso
     */
    public boolean validarPaso1() {
        boolean valido = true;
        if (historicoobra.getDatefecobrahist() == null) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    Propiedad.getValor("errorfechanullmodificarpaso1"), ""));
            valido = false;
        }

        if (historicoobra.getStrrazonmodif() == null || historicoobra.getStrrazonmodif().equals("")) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    Propiedad.getValor("errorrazonnullmodificarpaso1"), ""));
            valido = false;
        }

        return valido;
    }

    /**
     * Valida los datos ingresados en el paso 2 de la modificación de un
     * proyecto
     *
     * @return retorna true si los datos de ingresaron correctamente, de lo
     * contrario retorna falso
     */
    public boolean validarPaso2() {
        boolean valido = true;
        if (historicoobra.isAdiciontiempo()) {
            if (historicoobra.getDatefecfinhist() == null) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        Propiedad.getValor("errorfechanullmodificarpaso2"), ""));
                valido = false;
            } else if (historicoobra.getDatefecfinhist().before(obra.getDatefecfinobra())
                    || historicoobra.getDatefecfinhist().equals(obra.getDatefecfinobra())) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        Propiedad.getValor("errorfechamodificarpaso2"), ""));
                valido = false;
            }
        }

        if (historicoobra.isAdicionpresu()) {
            if (historicoobra.getNumvalorhist() == null
                    || historicoobra.getNumvalorhist().compareTo(BigDecimal.ZERO) <= 0) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        Propiedad.getValor("errorvalornullmodificarpaso2"), ""));
                valido = false;
            }
        }
        if(!historicoobra.isAdiciontiempo() && !historicoobra.isAdicionpresu() && obra.isBooleantienehijos()) {
            FacesContext.getCurrentInstance().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                Propiedad.getValor("modinotipificadaerror"), ""));
            valido = false;
        }
        return valido;
    }

    /**
     * Realiza las validaciones necesarios para el paso 3 de la modificacion
     * @return
     */
    private boolean validarPaso3() {
        boolean valido = true;
        if (validadorCronogramaExcel == null || !validadorCronogramaExcel.isValido()) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    Propiedad.getValor("cronogramamodificacioninvalido"), ""));
            valido = false;
        }
        return valido;
    }

    /**
     * Realiza las validaciones necesarios para el paso 4 de la modificacion
     * @return
     */
    public boolean validarPaso4() {
        return true;
    }

    /**
     * Realiza la validación del paso 5 de la modificación
     *
     * @return
     */
    public boolean validarPaso5() {
        boolean valido = true;
        if (historicoobra.isAdiciontiempo()) {
            if (obra.getRelacionesContratoObraConsultoria() != null) {
                if (obra.getRelacionesContratoObraConsultoria().size() > 0) {
                    Date ultimaFechaFinContratosConsultoria = obra.obtenerUltimaFechaFinContratosConsultoria();
                    if (historicoobra.getDatefecfinhist().after(ultimaFechaFinContratosConsultoria)) {
                        FacesContext.getCurrentInstance().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                Propiedad.getValor("fechafinpostcontratoerror"), ""));
                        valido = false;
                    }
                }
            }
        }

        if (historicoobra.isAdicionpresu() || historicoobra.isDisminucionpresu()) {
            if (obra.getRelacionesContratoObraConsultoria() != null) {
                if (obra.getRelacionesContratoObraConsultoria().size() > 0) {
                    BigDecimal valorTotalObra = cronogramaExcel.getSumValorPlanificadoActividades().add(BigDecimal.valueOf(cronogramaExcel.getValorEjecutado()));
                    BigDecimal valorContratosObra = obra.obtenerSumValorContratosObra();
                    if (valorTotalObra.compareTo(valorContratosObra) != 0) {
                        FacesContext.getCurrentInstance().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                Propiedad.getValor("valormodmayorcontratoerror"), ""));
                        valido = false;
                    }
                }
            }
        }
        return valido;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Métodos de Apoyo">
    /**
     * Obtiene el bean general de session de la aplicación; mediante este se
     * obtienen los servicios de la aplicación
     *
     * @return
     */
    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    /**
     * Obtiene el bean encargado de controlar los contratos
     * @return 
     */
    protected NuevoContratoBasico getNuevoContratoBasicoBean() {
        return (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
    }

    protected AdministrarObraNew getAdministrarObraNewBean() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    /**
     * Obtiene el bean encargado de Modificar los contratos
     * @return 
     */
    protected ModificarContrato getModificarContratoBean() {
        return (ModificarContrato) FacesUtils.getManagedBean("Supervisor$ModificarContrato");
    }

    /**
     * Obtiene el bean que invoca la descarga del archivo cuya ruta se encuentra
     * establecida en el atributo UrlAbri de SessionBeanCobra
     *
     * @return
     */
    public Download getDownload() {
        return (Download) FacesUtils.getManagedBean("Cobra$Download");
    }

    /**
     * Método que debe ser llamado cada vez que se accede a la página de
     * modificación de proyecto
     */
    public void inicializarPagina() {
        cargadorCronograma.borrarDatosSubidos();
        obra = getSessionBeanCobra().getCobraService().getObra();
        historicoobra = getSessionBeanCobra().getModificarProyectoService().obtenerUltimoHistoricoObra(obra.getIntcodigoobra());
        if (historicoobra == null) {
            historicoobra = new Historicoobra();
        }
        if (historicoobra.getIntestadohist() == null || historicoobra.getIntestadohist() != Historicoobra.EstadoHist.ENPROCESO.getIntestadohist()) {
            historicoobra = new Historicoobra();
            historicoobra.setIntestadohist(Historicoobra.EstadoHist.ENPROCESO.getIntestadohist());
            historicoobra.setObra(obra);
        }
        historicoobra.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
        historicoobra.setDatefechist(new Date());
        if (historicoobra.getTipomodificacions() != null) {
            if (historicoobra.getTipomodificacions().contains(new Tipomodificacion(Tipomodificacion.ADICION_ACTIVIDADES, null))) {
                historicoobra.setAdicionactivi(true);
            }
            if (historicoobra.getTipomodificacions().contains(new Tipomodificacion(Tipomodificacion.ADICION_PRESUPUESTO, null))) {
                historicoobra.setAdicionpresu(true);
            }
            if (historicoobra.getTipomodificacions().contains(new Tipomodificacion(Tipomodificacion.CAMBIO_PRECIOS, null))) {
                historicoobra.setCambioprecios(true);
            }
            if (historicoobra.getTipomodificacions().contains(new Tipomodificacion(Tipomodificacion.PRORROGA, null))) {
                historicoobra.setAdiciontiempo(true);
            }
            if (historicoobra.getTipomodificacions().contains(new Tipomodificacion(Tipomodificacion.REPROGRAMACION, null))) {
                historicoobra.setReprogramacion(true);
            }
        }
        llenarSelectTipoDocumento();
        validarCostosinAiu();
        this.verPaso1 = true;
        this.verPaso2 = false;
        this.verPaso3 = false;
        this.verPaso4 = false;
        this.verPaso5 = false;
    }

    /**
     * Obtiene la plantilla de excel
     *
     * @return Objeto para manipular el contenido del excel
     */
    public InputStream obtenerPlantilla() {
        File archivo = null;
        try {
            archivo = ArchivoWebUtil.obtenerArchivo(Propiedad.getValor("ubicacionplantillamodificacion"));
        } catch (ArchivoNoExistenteException ex) {
            Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
        }
        InputStream archivoInputStream = null;

        if (archivo.exists()) {
            try {
                archivoInputStream = new FileInputStream(archivo);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return archivoInputStream;
    }

    /**
     * Carga la información específica del proyecto en la plantilla de excel
     *
     * @param archivoInputStream
     * @return Objeto que representa una hoja de cálculo
     */
    public Workbook cargarInfoProyectoEnPlantilla(InputStream archivoInputStream) {
        Workbook workbook = null;
        try {
            workbook = new HSSFWorkbook(archivoInputStream);
            constructorCronogramaExcel = new ConstructorCronogramaExcelConObraModificada(obra, historicoobra);
            constructorCronogramaExcel.construirCronograma();
            cronogramaExcel = constructorCronogramaExcel.getCronogramaExcel();
            constructorWorkbook = new ConstructorWorkbookConCronogramaExcel(cronogramaExcel, workbook);
            constructorWorkbook.construirWorkbook();
        } catch (IOException ex) {
            Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return workbook;
    }

    /**
     * Almacena el excel en el fichero
     *
     * @param workbook Objeto que representa una hoja de cálculo
     * @param ubicacionRelativaCarpetaBase Directorio fijo, relativo al proyecto
     * en el que se almacenarán los archivos
     * @param nombreSubCarpeta Subcarpeta variable de relativa al objeto que se
     * almacenará para evitar posible sobreescrituras indeseadas
     * @param nombreArchivo Nombre del archivo
     */
    public void almacenarArchivo(Workbook workbook, String ubicacionRelativaCarpetaBase, String nombreSubCarpeta, String nombreArchivo) {
        String ubicacionAbsolutaArchivo = reservarUbicacionArchivo(ubicacionRelativaCarpetaBase, nombreSubCarpeta, nombreArchivo);
        FileOutputStream CronogramaOutputStream = null;
        try {
            CronogramaOutputStream = new FileOutputStream(ubicacionAbsolutaArchivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            workbook.write(CronogramaOutputStream);
        } catch (IOException ex) {
            Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            CronogramaOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crea la carpeta en donde se almacenará un archivo y elimina el archivo en
     * el caso que este ya exista
     *
     * @param ubicacionRelativaCarpetaBase Directorio fijo, relativo al proyecto
     * en el que se almacenarán los archivos
     * @param nombreSubCarpeta Subcarpeta variable de relativa al objeto que se
     * almacenará para evitar posible sobreescrituras indeseadas
     * @param nombreArchivo Nombre del archivo
     * @return Ubicacion absoluta del archivo
     */
    public String reservarUbicacionArchivo(String ubicacionRelativaCarpetaBase, String nombreSubCarpeta, String nombreArchivo) {
        ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String ubicacionRelativaCarpeta = ubicacionRelativaCarpetaBase;
        if (nombreSubCarpeta != null && !nombreSubCarpeta.equals("")) {
            ubicacionRelativaCarpeta = ubicacionRelativaCarpeta + "/" + nombreSubCarpeta;
        }
        String ubicacionAbsolutaCarpeta = contexto.getRealPath(ubicacionRelativaCarpeta);
        String ubicacionAbsolutaArchivo = ubicacionAbsolutaCarpeta + "/" + nombreArchivo;
        File carpeta = new File(ubicacionAbsolutaCarpeta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        File archivoexcel = new File(ubicacionAbsolutaArchivo);
        if (archivoexcel.exists()) {
            archivoexcel.delete();
        }
        return ubicacionAbsolutaArchivo;
    }

    /**
     * Valida el cronograma de modificación
     */
    private void validarCronograma() {
        cargarObra();
        Workbook workbook = obtenerWorkbook();
        constructorCronogramaExcel = new ConstructorCronogramaExcelConObraModificada(obra, historicoobra);
        constructorCronogramaExcel.construirCronograma();
        cronogramaExcel = constructorCronogramaExcel.getCronogramaExcel();
        constructorCronogramaExcel = new ConstructorCronogramaExcelConWorkbook(workbook, cronogramaExcel);
        constructorCronogramaExcel.construirCronograma();
        cronogramaExcel = constructorCronogramaExcel.getCronogramaExcel();
        validadorCronogramaExcel = new ValidadorCronogramaExcelConObraModificada(obra, historicoobra, cronogramaExcel, constructorCronogramaExcel.getMensajesExcel());
        validadorCronogramaExcel.validar();
        Collections.sort(validadorCronogramaExcel.getMensajesExcel());
        if (!validadorCronogramaExcel.isValido()) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    Propiedad.getValor("errorcronogramamodificacion"), ""));
        }
    }

    /**
     * Realiza el backup del cronograma anterior y registra el nuevo cronograma
     */
    private boolean registrarModificacion() {
        boolean registroExitoso = false;
        try {
            if(!obra.isBooleantienehijos()) {
                if(ultimaAlimentacion != null && ultimaAlimentacion.getDatefecha().compareTo(obra.getDatefecfinobra())==0) {
                    ultimaAlimentacion.setDatefecha(obra.obtenerFechaFinUltimoPeriodoNoProrrogado(cronogramaExcel.getNumDiasProrroga()));
                }
                constructorObra = new ConstructorObraConCronogramaExcel(obra, cronogramaExcel, historicoobra);
                constructorObra.construirObra();
                copiarArchivoCronogramaAnterior();
                copiarArchivoCronogramaModificacion();
            }
            getSessionBeanCobra().getModificarProyectoService().guardarModificacion(obra, historicoobra, ultimaAlimentacion, getSessionBeanCobra().getUsuarioObra());
            registroExitoso = true;
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                Propiedad.getValor("modificacionrealizada"), ""));
        } catch (ArchivoExistenteException ex) {
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                Propiedad.getValor("cronomodiexistenteerror"), ""));
            Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                Propiedad.getValor("guardarmodificacionproyectoerror"), ""));
            Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registroExitoso;
    }

    /**
     * Obtiene el archivo subido para el cronograma
     *
     * @return Archivo correspondiente al cronograma
     */
    public File obtenerCronogramaSubido() {
        File archivoCronograma = null;
        Iterator<ArchivoWeb> itArchivosWeb = cargadorCronograma.getArchivos().iterator();
        if (itArchivosWeb.hasNext()) {
            archivoCronograma = itArchivosWeb.next().getArchivoTmp();
        }
        return archivoCronograma;
    }

    /**
     * Obtiene un objeto Workbook que representa un excel dado el archivo del
     * cronograma
     *
     * @param archivoCronograma Archivo con el cronograma diligenciado
     * @return
     */
    private Workbook obtenerWorkbook() {
        File archivoCronograma = obtenerCronogramaSubido();
        Workbook workbook = null;
        try {
            InputStream inp = new FileInputStream(archivoCronograma);
            workbook = WorkbookFactory.create(inp);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return workbook;
    }

    /**
     * Almacena en el servidor los documentos relacionados a la obra
     * @throws ArchivoExistenteException
     */
    public void subirDocumentoModificacion() throws ArchivoExistenteException {
        String carpetaDoc = MessageFormat.format(RutasWebArchivos.DOCS_OBRA, ""+obra.getIntcodigoobra());
        cargadorDocumentos.guardarArchivosTemporales(carpetaDoc,false);
        String rutaWebDoc = cargadorDocumentos.getArchivos().get(0).getRutaWeb();
        this.documentomodificacion.setStrubicacion(rutaWebDoc);
    }

    /**
     * Carga los datos en selector de tipos de documentos
     */
    public void llenarSelectTipoDocumento() {

        listaTipoDocumento = getSessionBeanCobra().getCobraService().encontrarTiposDocumentos();
        selectItemTipoDocumentoModi = new SelectItem[listaTipoDocumento.size()];
        int i = 0;
        SelectItem selectItem = new SelectItem(0, Propiedad.getValor("seleccioneuntipo"));
        for (Tipodocumento td : listaTipoDocumento) {
            selectItem = new SelectItem(td.getInttipodoc(), td.getStrdesctipodoc());
            selectItemTipoDocumentoModi[i++] = selectItem;
        }
    }

    /**
     * Genera el cronograma para la modificación
     */
    private void generarCronograma() {
        cargarObra();
        String ubicacionRelativaCarpetaBase = Propiedad.getValor("ubicacioncarpetabasemodificacion");
        String nombreSubCarpeta = String.valueOf(obra.getIntcodigoobra());
        String nombreArchivo = Propiedad.getValor("plantillamodificacion");

        InputStream plantilla = obtenerPlantilla();
        Workbook workbook = cargarInfoProyectoEnPlantilla(plantilla);
        almacenarArchivo(workbook, ubicacionRelativaCarpetaBase, nombreSubCarpeta, nombreArchivo);
        this.getSessionBeanCobra().setUrlAbri(ubicacionRelativaCarpetaBase + nombreSubCarpeta + "/" + nombreArchivo);
    }

    /**
     * Metodo que verifica si el proyecto tiene AIU, para luego restarle lo del
     * AIU y mostrar en la modal de modificar cronograma
     */
    public void validarCostosinAiu() {
        if (obra.getBoolincluyeaiu()) {
            BigDecimal valortotalCosto = BigDecimal.valueOf(0);
            BigDecimal operacionadministracion = new BigDecimal(obra.getFloatporadmon());
            BigDecimal imprevistos = new BigDecimal(obra.getFloatporimprevi());
            BigDecimal utilidad = new BigDecimal(obra.getFloatporutilidad());
            ivasobreutilidad = new BigDecimal(obra.getFloatporivasobreutil());
            ivasobreutilidad = ivasobreutilidad.multiply(utilidad);
            ivasobreutilidad = ivasobreutilidad.divide(BigDecimal.valueOf(100));
            BigDecimal operacionvalorotros = new BigDecimal(obra.getFloatporotros());
            valortotalobraCosto = obra.getNumvaltotobra();
            valortotalCosto = valortotalCosto.add(operacionadministracion);
            valortotalCosto = valortotalCosto.add(imprevistos);
            valortotalCosto = valortotalCosto.add(utilidad);
            valortotalCosto = valortotalCosto.add(ivasobreutilidad);
            valortotalCosto = valortotalCosto.add(operacionvalorotros);
            valortotalCosto = valortotalCosto.add(BigDecimal.valueOf(100));
            valortotalobraCosto = valortotalobraCosto.multiply(BigDecimal.valueOf(100)).divide(valortotalCosto, RoundingMode.HALF_UP);
        } else {
            valortotalobraCosto = obra.getNumvaltotobra();
        }
    }

    /**
     * Agrega un nuevo documento relacionado a la modificación
     */
    private void agregarDocumento() {
        boolean valido = true;
        if (documentomodificacion.getStrnombre() == null || documentomodificacion.getStrnombre().equals("")) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    Propiedad.getValor("debeproporcinarelnombredeldocu"), ""));
            valido = false;
        }
        if (cargadorDocumentos.getArchivos().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    Propiedad.getValor("nodocumentoerror"), ""));
            valido = false;
        }
        if (valido) {
            documentomodificacion.setObra(getObra());
            documentomodificacion.setTipodocumento(getSessionBeanCobra().getCobraService().encontrarTipoDocPorId(tipodoc));
            documentomodificacion.setDatefecha(new Date());
            documentomodificacion.setHistoricoobra(historicoobra);
            try {
                subirDocumentoModificacion();
                getSessionBeanCobra().getCobraService().guardarDocumento(documentomodificacion);
                listadocumentosmodi.add(documentomodificacion);
                documentomodificacion = new Documentoobra();
                documentomodificacion.setTipodocumento(new Tipodocumento(1, "", true));
                cargadorDocumentos = new CargadorArchivosWeb();
            } catch (ArchivoExistenteException ex) {
                Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
                FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    Propiedad.getValor("docexistenteerror"), ""));
            }
        }
    }

    /**
     * Carga la información inicial de la obra
     */
    private void cargarObra() {
        obra = getSessionBeanCobra().getCobraService().getObra();
        obra = getSessionBeanCobra().getCobraService().cargarProgramacionAlimentacion(obra);
        ultimaAlimentacion = getSessionBeanCobra().getCobraService().obtenerUltimaalimentacion(obra.getIntcodigoobra());
        if (ultimaAlimentacion != null) {
            obra.setDateFechaFinPeriodoUltimaAlimentacion(ultimaAlimentacion.getDatefecha());
        }
        obra.obtenerNumPeriodosEjecutados();
    }

    /**
     * Realiza una copia del cronograma anterior
     */
    public void copiarArchivoCronogramaAnterior() {
        String ubicacionArchivoDestino = MessageFormat.format(RutasWebArchivos.DOCS_MODI, "" + obra.getIntcodigoobra(),  "" + historicoobra.getOididhistoricoobra());
        String nombreArchivo = obra.getStrurlcronograma().substring(obra.getStrurlcronograma().lastIndexOf("/"));
        ubicacionArchivoDestino = ubicacionArchivoDestino + nombreArchivo;
        try {
            ArchivoWebUtil.copiarArchivo(obra.getStrurlcronograma(), ubicacionArchivoDestino, true, false);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Realiza una copia del cronograma anterior
     * @throws FileNotFoundException Se lanza si el archivo origen no se
     * encuentra
     * @throws ArchivoExistenteException Se lanza si el archivo destino ya
     * existe y sobreescrivir = false
     */
    public void copiarArchivoCronogramaModificacion() throws FileNotFoundException, ArchivoExistenteException {
        String ubicacionCronogramaModificacion = MessageFormat.format(RutasWebArchivos.DOCS_OBRA, ""+obra.getIntcodigoobra());
        cargadorCronograma.guardarArchivosTemporales(ubicacionCronogramaModificacion,false);
        obra.setStrurlcronograma(cargadorCronograma.getArchivos().get(0).getRutaWeb());
        documentomodificacion.setObra(obra);
        Tipodocumento tipodocumento = new Tipodocumento();
        tipodocumento.setInttipodoc(CronogramaExcel.TIPODOC_CRONO_MOD);
        documentomodificacion.setStrubicacion(obra.getStrurlcronograma());
        documentomodificacion.setStrnombre("Cronograma");
        documentomodificacion.setTipodocumento(tipodocumento);
        documentomodificacion.setDatefecha(new Date());
        getSessionBeanCobra().getCobraService().guardarDocumento(documentomodificacion);
        listadocumentosmodi.add(documentomodificacion);
        documentomodificacion = new Documentoobra();
        documentomodificacion.setTipodocumento(new Tipodocumento(1, "", true));
    }

    /**
     * Ejecuta la acción de descargar documento
     *
     * @return
     */
    public String btDescargarDocumentoAction(int filaSeleccionada) {
        ModificarProyecto mp=(ModificarProyecto)FacesUtils.getManagedBean("Supervisor$ModificarProyecto");
         Documentoobra docdowload = mp.getListadocumentosmodi().get(filaSeleccionada);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/"+getSessionBeanCobra().getBundle().getString("versioncobra")+ docdowload.getStrubicacion());
        } catch (Exception e) {
            Logger.getLogger(ModificarProyecto.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    /**
     * Carga los documentos asociado a la modificación en la lista de documentos
     */
    public void cargarDocumentosModificacion() {
        listadocumentosmodi = getSessionBeanCobra().getCobraService().encontrarDocumentosModificacion(historicoobra.getOididhistoricoobra());
    }

    public String btEliminarDocumentoAction(int filaSeleccionada) {
        eliminarDocumento(filaSeleccionada);
        return null;
    }

    /**
     * Permite eliminar el documento de una modificación
     */
    public void eliminarDocumento(int filaSeleccionada) {
        boolean archivoEliminado = false;
         ModificarProyecto mp=(ModificarProyecto)FacesUtils.getManagedBean("Supervisor$ModificarProyecto");
        documentoobra = mp.getListadocumentosmodi().get(filaSeleccionada);
       
        listadocumentosmodi.remove(documentoobra);
        getSessionBeanCobra().getCobraService().borrarDocumento(documentoobra);
        archivoEliminado = ArchivoWebUtil.eliminarArchivo(this.documentoobra.getStrubicacion());
        if (!archivoEliminado) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    Propiedad.getValor("nofueposibleeliminarelarch"), ""));
        }
    }

    /**
     * Visualiza la ventana para modificar un contrato
     * @param contselec Contrato seleccionado
     * @return String que direcciona a la página de contratos
     */
    private String visualizarModificarContrato(Contrato contselec) {
        getNuevoContratoBasicoBean().cargarContrato(contselec);
        getModificarContratoBean().setVieneDeModificarProyecto(true);
        return "consultarContratoModificar";
    }

    /**
     * Cancela la modificación y regresa a detalle de obra
     * @return
     */
    public String btCancelarModificacionAction() {
        obra = getSessionBeanCobra().getCobraService().getObra();
        Tipoestadobra tipoestadobra = new Tipoestadobra(Tipoestadobra.EN_EJECUCION);
        obra.setTipoestadobra(tipoestadobra);
        getSessionBeanCobra().getCobraService().guardarObra(obra, getSessionBeanCobra().getUsuarioObra(), -1);
        getSessionBeanCobra().getCobraService().borrarHistorico(historicoobra);
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                Propiedad.getValor("modificacioncancelada"), ""));
        return getAdministrarObraNewBean().iniciardeta();
    }
    //</editor-fold>
}
