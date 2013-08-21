/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.to.Actividadobra;
import co.com.interkont.cobra.to.Actividadobrahist;
import co.com.interkont.cobra.to.Alimentacion;
import co.com.interkont.cobra.to.Documentoobra;
import co.com.interkont.cobra.to.Historicoobra;
import co.com.interkont.cobra.to.Novedad;
import co.com.interkont.cobra.to.Periodo;
import co.com.interkont.cobra.to.Periodohisto;
import co.com.interkont.cobra.to.Polizacontrato;
import co.com.interkont.cobra.to.Relacionactividadobraperiodo;
import co.com.interkont.cobra.to.Relacionactividadobraperiodohisto;
import co.com.interkont.cobra.to.Tipodocumento;
import co.com.interkont.cobra.to.Tipoestadobra;
import co.com.interkont.cobra.to.Tipomodificacion;
import co.com.interkont.cobra.to.Tiponovedad;
import cobra.Archivo;
import cobra.CargadorArchivosWeb;
import cobra.SessionBeanCobra;
import com.interkont.cobra.exception.ArchivoExistenteException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.richfaces.component.UIDataTable;

/**
 * <p>Fragment bean that corresponds to a similarly named JSP page
 * fragment.  This class contains component definitions (and initialization
 * code) for all components that you have defined on this fragment, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 *
 * @version ModificarObra.java
 * @version Created on 3/11/2010, 12:11:49 AM
 * @author carlos
 */
public class ModificarObra implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    private Historicoobra historicoobra = new Historicoobra();
    private static final String URL_EN_MODIFICACION = "/resources/Documentos/ObrasEnModificacion/";
    private boolean verSoportes = false;
    private boolean verotrosi = false;
    private boolean cmbconvenio = false;
    private BigDecimal valorhistorico = new BigDecimal("0");
    private String mensajeModificacion = "";
    private boolean cambioPrecios = false;
    private boolean adicionActividades = false;
    private CargadorArchivosWeb subirOtroSi = new CargadorArchivosWeb();
    private CargadorArchivosWeb subirActaConvenio = new CargadorArchivosWeb();
    private Documentoobra otroSi = new Documentoobra();
    private Documentoobra actaConvenio = new Documentoobra();
    private String nombreConvenio = "";
    private Date fechaPolizaCumplimiento = null;
    boolean tipificacionValida = false;
    private CargadorArchivosWeb subirCronograma = new CargadorArchivosWeb();
    private String mensajeXLSInvalido = null;
    private boolean hayvideo = false;
    private int contadorguardar = 0;
    private boolean validezalimentacion = false;
    private String mensajeMatrix = "";
    private Date fechaprorrogaini = new Date();
    private Date fechaprorrogafin = new Date();
    private final int NUM_FILA_ENCABEZADO_ACTIVIDAD = 8;
    private final int NUM_FILA_PRIMERA_ACTIVIDAD = 9;
    private final int NUM_COL_Q_PRIMER_PERIODO = 10;
    private final String COL_DIFERENCIA = "G";
    private final String COL_VALOR_UNI = "H";
    private List<Periodo> listaperiodos = new ArrayList<Periodo>();
    private List<Actividadobra> listactividadesmod = new ArrayList<Actividadobra>();
    private boolean cronovalido = false;
    private List<Relacionactividadobraperiodohisto> listarelahisto = new ArrayList<Relacionactividadobraperiodohisto>();
    ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private List<Polizacontrato> listapolizas = new ArrayList<Polizacontrato>();
    private Polizacontrato polizaeditar = new Polizacontrato();
     private UIDataTable tablapolizas = new UIDataTable();

    public UIDataTable getTablapolizas() {
        return tablapolizas;
    }

    public void setTablapolizas(UIDataTable tablapolizas) {
        this.tablapolizas = tablapolizas;
    }
     
     

    public boolean isCmbconvenio() {
        return cmbconvenio;
    }

    public void setCmbconvenio(boolean cmbconvenio) {
        this.cmbconvenio = cmbconvenio;
    }

    public boolean isVerotrosi() {
        return verotrosi;
    }

    public void setVerotrosi(boolean verotrosi) {
        this.verotrosi = verotrosi;
    }

    public Polizacontrato getPolizaeditar() {
        return polizaeditar;
    }

    public void setPolizaeditar(Polizacontrato polizaeditar) {
        this.polizaeditar = polizaeditar;
    }

    public List<Polizacontrato> getListapolizas() {
        return listapolizas;
    }

    public void setListapolizas(List<Polizacontrato> listapolizas) {
        this.listapolizas = listapolizas;
    }

    public List<Relacionactividadobraperiodohisto> getListarelahisto() {
        return listarelahisto;
    }

    public void setListarelahisto(List<Relacionactividadobraperiodohisto> listarelahisto) {
        this.listarelahisto = listarelahisto;
    }

    public boolean isCronovalido() {
        return cronovalido;
    }

    public void setCronovalido(boolean cronovalido) {
        this.cronovalido = cronovalido;
    }

    public List<Actividadobra> getListactividadesmod() {
        return listactividadesmod;
    }

    public void setListactividadesmod(List<Actividadobra> listactividadesmod) {
        this.listactividadesmod = listactividadesmod;
    }

    public List<Periodo> getListaperiodos() {
        return listaperiodos;
    }

    public void setListaperiodos(List<Periodo> listaperiodos) {
        this.listaperiodos = listaperiodos;
    }

    public String getCOL_DIFERENCIA() {
        return COL_DIFERENCIA;
    }

    public String getCOL_VALOR_UNI() {
        return COL_VALOR_UNI;
    }

    public int getNUM_COL_Q_PRIMER_PERIODO() {
        return NUM_COL_Q_PRIMER_PERIODO;
    }

    public int getNUM_FILA_ENCABEZADO_ACTIVIDAD() {
        return NUM_FILA_ENCABEZADO_ACTIVIDAD;
    }

    public int getNUM_FILA_PRIMERA_ACTIVIDAD() {
        return NUM_FILA_PRIMERA_ACTIVIDAD;
    }

    public static String getURL_EN_MODIFICACION() {
        return URL_EN_MODIFICACION;
    }

    public Documentoobra getActaConvenio() {
        return actaConvenio;
    }

    public void setActaConvenio(Documentoobra actaConvenio) {
        this.actaConvenio = actaConvenio;
    }

    public boolean isAdicionActividades() {
        return adicionActividades;
    }

    public void setAdicionActividades(boolean adicionActividades) {
        this.adicionActividades = adicionActividades;
    }

    public boolean isCambioPrecios() {
        return cambioPrecios;
    }

    public void setCambioPrecios(boolean cambioPrecios) {
        this.cambioPrecios = cambioPrecios;
    }

    public int getContadorguardar() {
        return contadorguardar;
    }

    public void setContadorguardar(int contadorguardar) {
        this.contadorguardar = contadorguardar;
    }

    public Date getFechaPolizaCumplimiento() {
        return fechaPolizaCumplimiento;
    }

    public void setFechaPolizaCumplimiento(Date fechaPolizaCumplimiento) {
        this.fechaPolizaCumplimiento = fechaPolizaCumplimiento;
    }

    public Date getFechaprorrogafin() {
        return fechaprorrogafin;
    }

    public void setFechaprorrogafin(Date fechaprorrogafin) {
        this.fechaprorrogafin = fechaprorrogafin;
    }

    public Date getFechaprorrogaini() {
        return fechaprorrogaini;
    }

    public void setFechaprorrogaini(Date fechaprorrogaini) {
        this.fechaprorrogaini = fechaprorrogaini;
    }

    public boolean isHayvideo() {
        return hayvideo;
    }

    public void setHayvideo(boolean hayvideo) {
        this.hayvideo = hayvideo;
    }

    public String getMensajeMatrix() {
        return mensajeMatrix;
    }

    public void setMensajeMatrix(String mensajeMatrix) {
        this.mensajeMatrix = mensajeMatrix;
    }

    public String getMensajeModificacion() {
        return mensajeModificacion;
    }

    public void setMensajeModificacion(String mensajeModificacion) {
        this.mensajeModificacion = mensajeModificacion;
    }

    public String getMensajeXLSInvalido() {
        return mensajeXLSInvalido;
    }

    public void setMensajeXLSInvalido(String mensajeXLSInvalido) {
        this.mensajeXLSInvalido = mensajeXLSInvalido;
    }

    public String getNombreConvenio() {
        return nombreConvenio;
    }

    public void setNombreConvenio(String nombreConvenio) {
        this.nombreConvenio = nombreConvenio;
    }

    public Documentoobra getOtroSi() {
        return otroSi;
    }

    public void setOtroSi(Documentoobra otroSi) {
        this.otroSi = otroSi;
    }

    public CargadorArchivosWeb getSubirActaConvenio() {
        return subirActaConvenio;
    }

    public void setSubirActaConvenio(CargadorArchivosWeb subirActaConvenio) {
        this.subirActaConvenio = subirActaConvenio;
    }

    public CargadorArchivosWeb getSubirCronograma() {
        return subirCronograma;
    }

    public void setSubirCronograma(CargadorArchivosWeb subirCronograma) {
        this.subirCronograma = subirCronograma;
    }

    public CargadorArchivosWeb getSubirOtroSi() {
        return subirOtroSi;
    }

    public void setSubirOtroSi(CargadorArchivosWeb subirOtroSi) {
        this.subirOtroSi = subirOtroSi;
    }

    public boolean isTipificacionValida() {
        return tipificacionValida;
    }

    public void setTipificacionValida(boolean tipificacionValida) {
        this.tipificacionValida = tipificacionValida;
    }

    public boolean isValidezalimentacion() {
        return validezalimentacion;
    }

    public void setValidezalimentacion(boolean validezalimentacion) {
        this.validezalimentacion = validezalimentacion;
    }

    public BigDecimal getValorhistorico() {
        return valorhistorico;
    }

    public void setValorhistorico(BigDecimal valorhistorico) {
        this.valorhistorico = valorhistorico;
    }

    public boolean isVerSoportes() {
        return verSoportes;
    }

    public void setVerSoportes(boolean verSoportes) {
        this.verSoportes = verSoportes;
    }

    public Historicoobra getHistoricoobra() {
        return historicoobra;
    }

    public void setHistoricoobra(Historicoobra historicoobra) {
        this.historicoobra = historicoobra;
    }

    /**
     * <p>Automatically managed component initialization. <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }
    // </editor-fold>

    public ModificarObra() {
    }


    public void iniciarModificar() {
        historicoobra = new Historicoobra();
        getAdministrarObraNew().setOpcion(2);
        if (getAdministrarObraNew().getObra().getTipoestadobra().getIntestadoobra() != 3) {
            getAdministrarObraNew().mostrarMenuModificar();

        }
        cargarModificacion();
    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    public String generarCronograma() {
        try {
            ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realArchivoPath = contexto.getRealPath(URL_EN_MODIFICACION);
            String ArchivoPath = contexto.getRealPath("/resources/Plantilla/" + bundle.getString("plantillamodificacion"));
            generarCronogramaModificacion(ArchivoPath, realArchivoPath);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdministrarObraNew.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdministrarObraNew.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AdministrarObraNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void generarCronogramaModificacion(String ArchivoPath, String realArchivoPath) throws FileNotFoundException, IOException, Exception {
        
        //Date fechaUltimaAlimentacion = getAdministrarObraNew().getObra().getFechaUltimaActaParcial();
        Alimentacion ultalim = getSessionBeanCobra().getCobraService().obtenerUltimaalimentacion(getAdministrarObraNew().getObra().getIntcodigoobra());
        Date fechaUltimaAlimentacion = null;
        if (ultalim == null) {
            fechaUltimaAlimentacion = getAdministrarObraNew().getObra().getDatefeciniobra();
        } else {
            fechaUltimaAlimentacion = ultalim.getDatefecha();
        }

        String formulafinal = "";
        if (fechaUltimaAlimentacion == null) {
            fechaUltimaAlimentacion = getAdministrarObraNew().getObra().getDatefeciniobra();
        }
        int i = 0, periodoini = 0;
        //Iterator itperio = getAdministrarObraNew().getObra().getPeriodos().iterator();
        Iterator itperio = getSessionBeanCobra().getCobraService().obtenerPeriodosObra(getAdministrarObraNew().getObra().getIntcodigoobra()).iterator();
        while (itperio.hasNext()) {
            Periodo pe = (Periodo) itperio.next();

            if (fechaUltimaAlimentacion.compareTo(pe.getDatefeciniperiodo()) >= 0 && fechaUltimaAlimentacion.compareTo(pe.getDatefecfinperiodo()) <= 0) {
                if (fechaUltimaAlimentacion.compareTo(pe.getDatefecfinperiodo()) == 0) {
                    periodoini = i + 1;
                } else {
                    periodoini = i;
                }
            }
            i++;
        }


        int plazoObra = 0;
        if (historicoobra.getDatefecfinhist() != null) {
            plazoObra = (int) ((historicoobra.getDatefecfinhist().getTime() - getAdministrarObraNew().getObra().getDatefeciniobra().getTime()) / (1000 * 60 * 60 * 24) + 1);
        } else {
            plazoObra = getAdministrarObraNew().getObra().getIntplazoobra();
            historicoobra.setDatefecfinhist(getAdministrarObraNew().getObra().getDatefecfinobra());
        }

        File archivo = new File(ArchivoPath);
        if (archivo.exists()) {
            InputStream inp = new FileInputStream(archivo);

            //  Workbook wb = WorkbookFactory.create(inp);
            Workbook wb = new HSSFWorkbook(inp);  //ok para lista
            // XSSFWorkbook wb = new XSSFWorkbook(inp);
            Sheet sheet = wb.getSheetAt(0);
            CellStyle loCellStyle = wb.createCellStyle();
            //   loCellStyle.setAlignment(loCellStyle.ALIGN_RIGHT);
            //  loCellStyle.setLocked(true);
            //CellStyle unlo=wb.createCellStyle();
            //unlo.setLocked(false);
            // sheet.protectSheet("pw");


            Row row = sheet.getRow(2);
            Cell cell = row.getCell(2);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(getAdministrarObraNew().getObra().getStrnombreobra());

            cell = row.getCell(8);
            cell.setCellValue(historicoobra.getNumvalorhist().doubleValue());

            row = sheet.getRow(3);
            cell = row.getCell(2);
            cell.setCellValue(plazoObra);

            row = sheet.getRow(4);
            cell = row.getCell(2);
            cell.setCellValue(getAdministrarObraNew().getObra().getDatefeciniobra());
            //    cell.getCellStyle().setLocked(false);
            row = sheet.getRow(5);
            cell = row.getCell(2);
            cell.setCellValue(historicoobra.getDatefecfinhist());
            row = sheet.getRow(6);
            cell = row.getCell(2);
            //cell.setCellValue("dato prueba bloq");
            //   cell.setAsActiveCell();
            //     cell.getCellStyle().setLocked(true);
            // sheet.protectSheet("password");


//**codigo para crear nueva hoja con listas desplegables
/*
            Sheet sheetxx = wb.createSheet("hoja  Valida");
            CellRangeAddressList addressListxc = new CellRangeAddressList(0, 0, 0, 0);
            String[] excelListValues = new String[]{"obra mayor", "obra menor", "Jason", "agua", "Greg"};
            DVConstraint dvConstraintsxx = DVConstraint.createExplicitListConstraint(excelListValues);//DVConstraint dvConstraints = DVConstraint.createFormulaListConstraint("$B$1:$D$1");
            DataValidation dataValidationxx = new HSSFDataValidation(addressListxc, dvConstraintsxx);
            dataValidationxx.setSuppressDropDownArrow(false);
            sheetxx.addValidationData(dataValidationxx);

            Sheet hoja = wb.createSheet("hoja oija");
            CellRangeAddressList listaDir = new CellRangeAddressList(0, 0, 0, 0);
            String[] excellista = new String[]{"obra mayor", "obra menor", "Jason", "agua", "Greg"};
            DVConstraint crearlista = DVConstraint.createExplicitListConstraint(excellista);//DVConstraint dvConstraints = DVConstraint.createFormulaListConstraint("$B$1:$D$1");
            DataValidation validaciondatos = new HSSFDataValidation(listaDir, crearlista);
            validaciondatos.setSuppressDropDownArrow(false);
            hoja.addValidationData(validaciondatos);
             *
             */
//HSSFCell cell1Cell=new HSSFCell
// for (int ai=0; ai<wb.getNumberOfSheets();ai++) {
//
//            Sheet sheetx = wb.getSheetAt(ai);
//
//            Row rowx = sheetx.getRow(2);
//            Cell cellx = rowx.getCell(2);
//            cellx.setCellType(Cell.CELL_TYPE_STRING);
//            cellx.setCellValue("xxxo");
// }







            String perionom = "";
            switch (getAdministrarObraNew().getObra().getPeriodomedida().getIntidperiomedida()) {
                case 1:
                    perionom = "SEMANA ";


                    break;
                case 2:
                    perionom = "QUINCENA ";
                    break;
                case 3:
                    perionom = "MES ";

                    break;
            }
            int qActual = NUM_COL_Q_PRIMER_PERIODO;
            i = 0;
            int actividadActual = NUM_FILA_PRIMERA_ACTIVIDAD;

            Iterator itper = getSessionBeanCobra().getCobraService().obtenerPeriodosObra(getAdministrarObraNew().getObra().getIntcodigoobra()).iterator();
            String formula = "";

            List<String> listfaltante = new ArrayList<String>();

            actividadActual = NUM_FILA_PRIMERA_ACTIVIDAD;
            int num = getSessionBeanCobra().getCobraService().obtenerNumeroActividadesObra(getAdministrarObraNew().getObra().getIntcodigoobra());


            while (i < num) {
                listfaltante.add("(");
                actividadActual++;
                i++;
            }
            i = 0;
            while (itper.hasNext()) {
                row = sheet.getRow(8);
                Periodo peri = (Periodo) itper.next();

                if (i >= periodoini) {

                    cell = row.createCell(qActual);
                    cell.setCellValue("Q");
                    cell = row.createCell(qActual + 1);
                    cell.setCellValue("VALOR");

                    row = sheet.getRow(5);

                    cell = row.createCell(qActual + 1);
                    CellReference celdaini = new CellReference(9, qActual + 1);
                    CellReference celdafin = new CellReference(65534, qActual + 1);
                    formula = "SUM(" + celdaini.formatAsString() + ":" + celdafin.formatAsString() + ")";
                    cell.setCellFormula(formula);
                    sheet.setColumnWidth(qActual + 1, 4000);


                    row = sheet.getRow(7);
                    cell = row.createCell(qActual);
                    cell.setCellValue(perionom + " " + (i + 1));
                    sheet.addMergedRegion(new CellRangeAddress(7, 7, qActual, qActual + 1));
                    Iterator itrel = getSessionBeanCobra().getCobraService().obtenerRelacionesactividadobraperiodo(peri.getIntidperiodo()).iterator();

                    boolean band = false;
                    actividadActual = NUM_FILA_PRIMERA_ACTIVIDAD;


                    int j = 0;
                    while (itrel.hasNext()) {

                        Relacionactividadobraperiodo relacion = (Relacionactividadobraperiodo) itrel.next();
                        row = sheet.getRow(actividadActual);


                        if (!band) {
                            cell = row.createCell(0);
                            cell.setCellValue(relacion.getActividadobra().getOidactiviobra());

                            cell = row.getCell(2);
                            cell.setCellValue(relacion.getActividadobra().getStrdescactividad());

                            cell = row.getCell(3);
                            cell.setCellValue(relacion.getActividadobra().getStrtipounidadmed());

                            cell = row.getCell(4);
                            cell.setCellValue(relacion.getActividadobra().getFloatcantplanifao());

                            cell = row.getCell(5);
                            cell.setCellValue(relacion.getActividadobra().getFloatcantidadejecutao().doubleValue());

                            cell = row.getCell(7);
                            cell.setCellValue(relacion.getActividadobra().getNumvalorplanifao().doubleValue());

                        }
                        if (relacion != null) {
                            cell = row.createCell(qActual);
                            cell.setCellValue(relacion.getFloatcantplanif());

                            cell = row.createCell(qActual + 1);
                            CellReference celda = new CellReference(actividadActual, qActual);
                            formula = COL_VALOR_UNI + (actividadActual + 1) + "*" + celda.formatAsString();
                            String form = listfaltante.get(j) + celda.formatAsString() + "+";
                            formulafinal = form;
                            listfaltante.set(j, form);

                            cell.setCellType(Cell.CELL_TYPE_FORMULA);
                            cell.setCellFormula(formula);
                            sheet.setColumnWidth(qActual + 1, 5000);
                        }
                        j++;
                        actividadActual++;


                    }




                    band = true;
                    sheet.autoSizeColumn(qActual);
                    sheet.autoSizeColumn(qActual + 1);
                    qActual = qActual + 2;
                }
                i++;

            }

            if (plazoObra > getAdministrarObraNew().getObra().getIntplazoobra()) {
                getAdministrarObraNew().getObra().setPeriodos(new HashSet(getSessionBeanCobra().getCobraService().obtenerPeriodosObra(getAdministrarObraNew().getObra().getIntcodigoobra())));

                Periodo ultper = getAdministrarObraNew().getObra().getUltimoPeriodo();
                long diferenciaFecha = ultper.getDatefecfinperiodo().getTime() - ultper.getDatefeciniperiodo().getTime();
                diferenciaFecha = diferenciaFecha / (36000 * 24 * 100);
                int dif = Integer.parseInt(String.valueOf(diferenciaFecha));

                if (dif < getAdministrarObraNew().getObra().getPeriodomedida().getIntnrodiasperiomedida()) {
                    int plazoresta = plazoObra - getAdministrarObraNew().getObra().getIntplazoobra();
                    plazoresta = plazoresta - (getAdministrarObraNew().getObra().getPeriodomedida().getIntnrodiasperiomedida() - dif);
                    if (plazoresta > 0) {
                        int division = plazoresta / getAdministrarObraNew().getObra().getPeriodomedida().getIntnrodiasperiomedida();

                        if (plazoresta % getAdministrarObraNew().getObra().getPeriodomedida().getIntnrodiasperiomedida() != 0) {
                            division = division + 1;
                        }

                        int k = 0;
                        int sub = 0;

                        while (k < division) {
                            row = sheet.getRow(5);

                            cell = row.createCell(qActual + 1);
                            CellReference celdaini = new CellReference(9, qActual + 1);
                            CellReference celdafin = new CellReference(65534, qActual + 1);
                            formula = "SUM(" + celdaini.formatAsString() + ":" + celdafin.formatAsString() + ")";
                            cell.setCellFormula(formula);
                            sheet.setColumnWidth(qActual + 1, 4000);


                            row = sheet.getRow(8);
                            cell = row.createCell(qActual);
                            cell.setCellValue("Q");
                            cell = row.createCell(qActual + 1);
                            cell.setCellValue(bundle.getString("valor"));

                            row = sheet.getRow(7);
                            cell = row.createCell(qActual);
                            cell.setCellValue(perionom + " " + (i + 1));
                            sheet.addMergedRegion(new CellRangeAddress(7, 7, qActual, qActual + 1));

                            sub = 0;
                            getAdministrarObraNew().getObra().setActividadobras(new HashSet(getSessionBeanCobra().getCobraService().obtenerActividadesObra(getAdministrarObraNew().getObra().getIntcodigoobra())));

                            while (sub < getAdministrarObraNew().getObra().getActividadobras().size()) {
                                row = sheet.getRow(NUM_FILA_PRIMERA_ACTIVIDAD + sub);
                                cell = row.createCell(qActual);
                                cell.setCellValue(0);

                                cell = row.createCell(qActual + 1);
                                CellReference celda = new CellReference(NUM_FILA_PRIMERA_ACTIVIDAD + sub, qActual);
                                formula = COL_VALOR_UNI + (NUM_FILA_PRIMERA_ACTIVIDAD + sub + 1) + "*" + celda.formatAsString();
                                String form = listfaltante.get(sub) + celda.formatAsString() + "+";

                                formulafinal = form;
                                listfaltante.set(sub, form);
                                cell.setCellType(Cell.CELL_TYPE_FORMULA);
                                cell.setCellFormula(formula);
                                sheet.setColumnWidth(qActual + 1, 5000);
                                sub++;
                            }
                            qActual = qActual + 2;
                            i++;
                            k++;
                        }
                    }
                }

            }

            i = 0;
            row = sheet.getRow(NUM_FILA_ENCABEZADO_ACTIVIDAD);
            cell = row.createCell(qActual);
            cell.setCellValue(bundle.getString("faltante"));

            while (i < (1500 - NUM_FILA_PRIMERA_ACTIVIDAD)) {

                String form = formulafinal.replaceAll(String.valueOf(actividadActual), String.valueOf(NUM_FILA_PRIMERA_ACTIVIDAD + i + 1));

                row = sheet.getRow(NUM_FILA_PRIMERA_ACTIVIDAD + i);
                cell = row.getCell(9);
                cell.setCellType(Cell.CELL_TYPE_FORMULA);
                //cell.setCellFormula(listfaltante.get(i) + "(" + COL_DIFERENCIA + (NUM_FILA_PRIMERA_ACTIVIDAD + i + 1) + "*-1))*-1*" + COL_VALOR_UNI + (NUM_FILA_PRIMERA_ACTIVIDAD + i + 1));


                cell.setCellFormula(form + "(" + COL_DIFERENCIA + (NUM_FILA_PRIMERA_ACTIVIDAD + i + 1) + "*-1))*-1*" + COL_VALOR_UNI + (NUM_FILA_PRIMERA_ACTIVIDAD + i + 1));

                cell = row.createCell(qActual);
                cell.setCellType(Cell.CELL_TYPE_FORMULA);
                //cell.setCellFormula(listfaltante.get(i) + "(" + COL_DIFERENCIA + (NUM_FILA_PRIMERA_ACTIVIDAD + i + 1) + "*-1))*-1");
                cell.setCellFormula(form + "(" + COL_DIFERENCIA + (NUM_FILA_PRIMERA_ACTIVIDAD + i + 1) + "*-1))*-1");

                int j = 11;
                while (j < qActual) {
                    cell = row.createCell(j);
                    CellReference celda = new CellReference(NUM_FILA_PRIMERA_ACTIVIDAD + i, (j - 1));
                    formula = COL_VALOR_UNI + (NUM_FILA_PRIMERA_ACTIVIDAD + i + 1) + "*" + celda.formatAsString();
                    cell.setCellType(Cell.CELL_TYPE_FORMULA);
                    cell.setCellFormula(formula);

                    j = j + 2;
                }


                i++;

            }

            File carpeta = new File(realArchivoPath + "/" + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()));
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            // Write the output to a file
            File archivoexcel = new File(realArchivoPath + "/" + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()) + "/" + bundle.getString("plantillamodificacion"));
            if (archivoexcel.exists()) {
                archivoexcel.delete();
            }

            FileOutputStream fileOut = new FileOutputStream(realArchivoPath + "/" + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()) + "/" + bundle.getString("plantillamodificacion"));
            //FileOutputStream fileOut = new FileOutputStream("workbook.xls");
            wb.write(fileOut);
            fileOut.close();
            this.getSessionBeanCobra().setUrlAbri(URL_EN_MODIFICACION + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()) + "/" + bundle.getString("plantillamodificacion"));
        } else {
        }
    }

    public void subirCronograma() {
        String realArchivoPath = "";
        String URL = "/resources/Documentos/ObrasVigentes/";

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        if (subirCronograma.getArchivos().size() > 0) {
            realArchivoPath = theApplicationsServletContext.getRealPath(URL);
            try {

                File carpeta = new File(realArchivoPath + "/" + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()));


                if (!carpeta.exists()) {
                    carpeta.mkdirs();

                }

            } catch (Exception ex) {
                FacesUtils.addErrorMessage(bundle.getString("nosepuedesubir"));
            }

        }
        try {
            subirCronograma.guardarArchivosTemporales(realArchivoPath + "/" + String.valueOf(this.getAdministrarObraNew().getObra().getIntcodigoobra()), false);
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(ModificarObra.class.getName()).log(Level.SEVERE, null, ex);
        }

        Iterator arch = subirCronograma.getArchivos().iterator();
        while (arch.hasNext()) {
            Archivo nombreoriginal = (Archivo) arch.next();
            historicoobra.setStrurlcronogramahist(URL + String.valueOf(this.getAdministrarObraNew().getObra().getIntcodigoobra()) + "/" + nombreoriginal.getName());
        }


    }

    public void archivarCronogramaHistorico() {
        String realArchivoPathHistoricas = "";
        String URLHistoricas = "/resources/Documentos/ObrasHistoricas/";
        String realArchivoPathVigentes = "";
        String URLVigentes = "/resources/Documentos/ObrasVigentes/";

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        realArchivoPathHistoricas =
                theApplicationsServletContext.getRealPath(URLHistoricas);
        realArchivoPathVigentes =
                theApplicationsServletContext.getRealPath(URLVigentes);
        try {

            File carpeta = new File(realArchivoPathHistoricas + "/" + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()) + "/" + String.valueOf(historicoobra.getOididhistoricoobra()));
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File cronogramaAnterior = new File(realArchivoPathVigentes + "/" + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()) + "/" + bundle.getString("plantillaobra"));
            File cronogramaHistorico = new File(realArchivoPathHistoricas + "/" + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()) + "/" + String.valueOf(historicoobra.getOididhistoricoobra()) + "/" + bundle.getString("plantillaobra"));
            cronogramaAnterior.renameTo(cronogramaHistorico);
        } catch (Exception ex) {
            FacesUtils.addErrorMessage(bundle.getString("nosepudoarchivar"));//"No se pudo archivar el cronograma");
        }

        this.historicoobra.setStrurlcronogramahist(URLHistoricas + String.valueOf(this.getAdministrarObraNew().getObra().getIntcodigoobra()) + String.valueOf(this.historicoobra.getOididhistoricoobra()) + "/" + bundle.getString("plantillaobra"));
    }

    public boolean validarCronograma(File fileCronograma) {
        mensajeModificacion = "";
        Workbook excel;
        Row fila = null;
        historicoobra.calcularEstadoModificaciones();


        Alimentacion ultalim = getSessionBeanCobra().getCobraService().obtenerUltimaalimentacion(getAdministrarObraNew().getObra().getIntcodigoobra());
        Date fechaUltimaAlimentacion = null;
        if (ultalim == null) {
            fechaUltimaAlimentacion = getAdministrarObraNew().getObra().getDatefeciniobra();
        } else {
            fechaUltimaAlimentacion = ultalim.getDatefecha();
        }
        Calendar calendarFechaUltimaAlimentacion = Calendar.getInstance();
        Date fechaInicialNoAlimentada = new Date();



        if (fechaUltimaAlimentacion == null) {
            fechaInicialNoAlimentada = getAdministrarObraNew().getObra().getDatefeciniobra();
        } else {
            calendarFechaUltimaAlimentacion.setTime(fechaUltimaAlimentacion);
            calendarFechaUltimaAlimentacion.add(Calendar.DATE, 1);
            fechaInicialNoAlimentada = calendarFechaUltimaAlimentacion.getTime();
        }

        int plazoObra = 0;
        if (historicoobra.getDatefecfinhist() != null) {
            plazoObra = (int) ((historicoobra.getDatefecfinhist().getTime() - getAdministrarObraNew().getObra().getDatefeciniobra().getTime()) / (1000 * 60 * 60 * 24) + 1);
        } else {
            plazoObra = getAdministrarObraNew().getObra().getIntplazoobra();
        }

        try {
            InputStream inp = new FileInputStream(fileCronograma);
            excel = WorkbookFactory.create(inp);
            Sheet sheet = excel.getSheetAt(0);
            Cell celda = null;
//****recalcular fomrulas
            FormulaEvaluator evaluator = excel.getCreationHelper().createFormulaEvaluator();
            for (int sheetNum = 0; sheetNum < excel.getNumberOfSheets(); sheetNum++) {
                sheet = excel.getSheetAt(sheetNum);
                for (Row r : sheet) {
                    for (Cell c : r) {
                        if (c.getCellType() == Cell.CELL_TYPE_FORMULA) {
                            evaluator.evaluateFormulaCell(c);
                        }
                    }
                }
            }
            int numFilasDeActividadLlenas = 0;
            int numFilasDeActividad = 0;

            for (Iterator rit = sheet.rowIterator(); rit.hasNext();) {
                fila = (Row) rit.next();
                if (fila.getRowNum() >= 9 && fila.getCell(2) != null && !fila.getCell(2).getStringCellValue().equals("")) {
                    numFilasDeActividadLlenas++;
                }
                numFilasDeActividad++;
            }


            if (numFilasDeActividadLlenas > 0) {

                //Verificar Datos de la Obra
                fila = sheet.getRow(2);
                celda = fila.getCell(2);
                if (celda.getStringCellValue().compareTo(getAdministrarObraNew().getObra().getStrnombreobra()) != 0) {
                    mensajeModificacion = bundle.getString("elnombredelaobra");//"El nombre de la obra del archivo no corresponde a esta obra";
                    return false;
                }
                fila = sheet.getRow(3);
                celda = fila.getCell(2);


                if (celda.getNumericCellValue() != plazoObra) {
                    mensajeModificacion = bundle.getString("elplazoendiasdelarchivonocorres");//"El plazo en días del archivo no corresponde a esta obra";
                    return false;
                }
                fila = sheet.getRow(4);
                celda = fila.getCell(2);
                if (celda.getDateCellValue().compareTo(getAdministrarObraNew().getObra().getDatefeciniobra()) != 0) {
                    mensajeModificacion = bundle.getString("lafechadeiniciodelarchivo");//"La fecha de inicio del archivo no corresponde a la consignada en la obra";
                    return false;
                }
                fila = sheet.getRow(5);
                celda = fila.getCell(2);

                if (celda.getDateCellValue().compareTo(historicoobra.getDatefecfinhist()) != 0) {
                    mensajeModificacion = bundle.getString("lafechadefinalizaciondelarchivo");//La fecha de finalización del archivo no corresponde a la consignada en la obra";
                    return false;
                }
                //Verificar Total
                fila = sheet.getRow(2);
                celda = fila.getCell(8);



                try {

                    BigDecimal valordelacelda = new BigDecimal(celda.getNumericCellValue());
                    valordelacelda = valordelacelda.setScale(1, RoundingMode.HALF_UP);
                    if (valordelacelda.compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal valorcompa = historicoobra.getNumvalorhist();
                        valorcompa = valorcompa.setScale(1, RoundingMode.HALF_UP);

                        if (valorcompa.compareTo(valordelacelda) == 0) {
                            fila = sheet.getRow(3);
                            celda = fila.getCell(8);
                            if (celda.getCellType() != 2) {
                                mensajeModificacion = bundle.getString("hasidomodificadadevalorfaltante");//"Ha sido modificada la formula de valor faltante";
                                return false;
                            } else {

                                boolean puedefaltar = false;

                                if (historicoobra.isReprogramacion() || historicoobra.isCambioprecios() || historicoobra.isAdicionactivi()) {
                                    puedefaltar = true;
                                }
                                if (puedefaltar == false && (historicoobra.isAdicionpresu() || historicoobra.isAdiciontiempo())) {
                                    puedefaltar = false;
                                }


                                if (puedefaltar == false) {
                                    BigDecimal val = BigDecimal.valueOf(celda.getNumericCellValue());
                                    val = val.setScale(1, RoundingMode.HALF_EVEN);

                                    if (val.doubleValue() != 0) {
                                        mensajeModificacion = bundle.getString("elvalorfaltanteporprogra");//"El valor faltante por programar debe ser $0";
                                        return false;
                                    }
                                } else {
                                    if (celda.getNumericCellValue() < 0) {
                                        mensajeModificacion = bundle.getString("elvalorfaltantenopuedeser") + celda.getNumericCellValue();
                                        return false;
                                    }
                                }

                                //if (celda.getNumericCellValue() == 0) {
                                //Verificar Actividades diligenciadas

                                int filaActual = NUM_FILA_PRIMERA_ACTIVIDAD;
                                int numTotalFilas = NUM_FILA_PRIMERA_ACTIVIDAD + numFilasDeActividadLlenas;
                                fila = null;
                                int totact = getSessionBeanCobra().getCobraService().obtenerNumeroActividadesObra(getAdministrarObraNew().getObra().getIntcodigoobra());
                                if (numFilasDeActividadLlenas < totact) {
                                    mensajeModificacion = bundle.getString("elnumerototaldeactividades");//"El número total de actividades ingresadas es inferior al número de actividades del cronograma anterior";
                                    return false;
                                } else {
                                    //Validar adicion de actividades
                                    if (numFilasDeActividadLlenas > totact && !historicoobra.isAdicionactivi()) {
                                        mensajeModificacion = bundle.getString("elnuemrototaldeactividadesingresados")
                                                + bundle.getString("ylamodificacionnose");
                                        return false;
                                    }
                                    ///VALIDANDO ACTIVIDADES ANTERIORES
                                    getAdministrarObraNew().getObra().setActividadobras(new LinkedHashSet(getSessionBeanCobra().getCobraService().obtenerActividadesObra(getAdministrarObraNew().getObra().getIntcodigoobra())));

//                           >         FormulaEvaluator evaluator = excel.getCreationHelper().createFormulaEvaluator();

                                    while (filaActual < numTotalFilas) {
                                        fila = sheet.getRow(filaActual);
                                        celda = fila.getCell(2);
                                        Cell celdaooid = fila.getCell(0);
                                        Cell celdauni = fila.getCell(3);
                                        Cell celdacant = fila.getCell(4);
                                        Cell celdaejecutao = fila.getCell(5);
                                        Cell celdavalor = fila.getCell(7);
                                        Cell celdafin = fila.getCell(8);// fila.getCell(8);
//                                        String strCellValue = "";

//                                        if (celdafin.getCellType() == celdafin.CELL_TYPE_FORMULA) {
//                                           evaluator.evaluate(celdafin);
//                                           evaluator.evaluate(celdafin).getNumberValue();
//                                        } else {
//                                            strCellValue = celdafin.toString();
//                                        }






                                        if (!validarActividad(celda.getStringCellValue(), celdauni.getStringCellValue(), celdacant.getNumericCellValue(), celdavalor.getNumericCellValue(), filaActual + 1)) {
                                            //actividadesobra.clear();

                                            return false;
                                        } else {

                                            if (filaActual < (totact + 9)) {
                                                Actividadobra actividadobra = new Actividadobra();
                                                double num = 0;
                                                long num2 = 0;
                                                try {
                                                    num = celdaooid.getNumericCellValue();
                                                    num2 = (long) num;
                                                } catch (NullPointerException e) {
                                                }
                                                actividadobra.setOidactiviobra(Integer.parseInt(String.valueOf(num2)));
                                                actividadobra.setStrdescactividad(celda.getStringCellValue());
                                                actividadobra.setStrtipounidadmed(celdauni.getStringCellValue());
                                                actividadobra.setFloatcantplanifao(celdacant.getNumericCellValue());
                                                actividadobra.setNumvalorplanifao(BigDecimal.valueOf(celdavalor.getNumericCellValue()));
                                                actividadobra.setJsfUsuario(getAdministrarObraNew().getObra().getJsfUsuario());
                                                actividadobra.setFloatcantidadejecutao(celdaejecutao.getNumericCellValue());
                                                actividadobra.setNumvalorejecutao(new BigDecimal(0));
                                                actividadobra.setStrcodcubs("");
                                                if (!validarActividadEnCronogramaAnterior(actividadobra, filaActual)) {
                                                    return false;
                                                }
                                            }



                                            if (celdafin.getCellType() != 2) {
                                                mensajeModificacion = bundle.getString("laformuladevalor") + (filaActual + 1);
                                                return false;
                                            } else {
                                                if (celdavalor.getNumericCellValue() * celdacant.getNumericCellValue() != celdafin.getNumericCellValue()) {//evaluator.evaluate(celdafin).getNumberValue()
                                                    mensajeModificacion = bundle.getString("laformuladevalorfinal") + (filaActual + 1);
                                                    return false;
                                                }
                                            }
                                            celdavalor = fila.getCell(9);
                                            if (celdavalor.getCellType() != 2) {
                                                mensajeModificacion = bundle.getString("lafomruladevalorfaltante") + (filaActual + 1);
                                                return false;
                                            }
                                        }


                                        filaActual++;
                                    }

                                    int numColPeriodos = 0;
                                    fila = sheet.getRow(NUM_FILA_ENCABEZADO_ACTIVIDAD);
                                    for (Iterator cit = fila.cellIterator(); cit.hasNext();) {
                                        cit.next();
                                        numColPeriodos++;
                                    }
                                    int totalcol = (numColPeriodos - 9) / 2;

                                    if (fechaUltimaAlimentacion == null) {
                                        fechaUltimaAlimentacion = getAdministrarObraNew().getObra().getDatefeciniobra();
                                    }
                                    int i = 0, periodoini = 0;
                                    getAdministrarObraNew().getObra().setPeriodos(new LinkedHashSet(getSessionBeanCobra().getCobraService().obtenerPeriodosObra(getAdministrarObraNew().getObra().getIntcodigoobra())));


                                    Iterator itperio = getAdministrarObraNew().getObra().getPeriodos().iterator();


                                    while (itperio.hasNext()) {
                                        Periodo pe = (Periodo) itperio.next();

                                        if (fechaUltimaAlimentacion.compareTo(pe.getDatefeciniperiodo()) >= 0 && fechaUltimaAlimentacion.compareTo(pe.getDatefecfinperiodo()) <= 0) {
                                            if (fechaUltimaAlimentacion.compareTo(pe.getDatefecfinperiodo()) == 0) {
                                                periodoini = i + 1;
                                            } else {
                                                periodoini = i;
                                            }
                                        }
                                        i++;
                                    }

                                    int numperiodvali = getAdministrarObraNew().getObra().getPeriodos().size() - periodoini;

                                    if (plazoObra > getAdministrarObraNew().getObra().getIntplazoobra()) {
                                        Periodo ultper = getAdministrarObraNew().getObra().getUltimoPeriodo();

                                        long diferenciaFecha = ultper.getDatefecfinperiodo().getTime() - ultper.getDatefeciniperiodo().getTime();
                                        diferenciaFecha = diferenciaFecha / (36000 * 24 * 100);
                                        int dif = Integer.parseInt(String.valueOf(diferenciaFecha));

                                        if (dif < getAdministrarObraNew().getObra().getPeriodomedida().getIntnrodiasperiomedida()) {
                                            int plazoresta = plazoObra - getAdministrarObraNew().getObra().getIntplazoobra();
                                            plazoresta = plazoresta - (getAdministrarObraNew().getObra().getPeriodomedida().getIntnrodiasperiomedida() - dif);

                                            if (plazoresta > 0) {
                                                int division = plazoresta / getAdministrarObraNew().getObra().getPeriodomedida().getIntnrodiasperiomedida();

                                                plazoresta = plazoObra - getAdministrarObraNew().getObra().getIntplazoobra();
                                                if (plazoresta % getAdministrarObraNew().getObra().getPeriodomedida().getIntnrodiasperiomedida() != 0) {
                                                    division = division + 1;
                                                }

                                                numperiodvali = numperiodvali + division;
                                            }
                                        }

                                    }
                                    if (totalcol == numperiodvali) {
                                        //verificar la suma de totales
                                        i = NUM_FILA_PRIMERA_ACTIVIDAD;
                                        int colActual = 11;
                                        BigDecimal suma = BigDecimal.valueOf(0);

                                        while (i < numTotalFilas) {
                                            fila = sheet.getRow(i);

                                            colActual = 11;
                                            while (colActual <= numColPeriodos) {


                                                Cell celdaTotalPeriodo = fila.getCell(colActual);

                                                if (celdaTotalPeriodo.getCellType() != 2) {

                                                    Cell filames = sheet.getRow(NUM_FILA_ENCABEZADO_ACTIVIDAD - 1).getCell(colActual - 1);
                                                    mensajeModificacion = bundle.getString("laformuladevaloren") + filames.getStringCellValue() + bundle.getString("enlafila") + (i + 1) + bundle.getString("hasigomodificada");
                                                    return false;
                                                }
                                                
                                                suma = suma.add(new BigDecimal(celdaTotalPeriodo.getNumericCellValue()));
                                                colActual = colActual + 2;
                                            }
                                            i++;
                                        }
                                        if (suma.compareTo(BigDecimal.valueOf(0)) == 0) {
                                            mensajeModificacion = bundle.getString("errorlasumadelos");//"ERROR LA SUMA DE LOS TOTALES DE LOS PERIODOS ES O";
                                            return false;
                                        } else {
                                            if (getAdministrarObraNew().getObra().getNumvalejecobra() != null) {
                                                suma = suma.add(getAdministrarObraNew().getObra().getNumvalejecobra());
                                            }
                                            suma = suma.setScale(1, RoundingMode.HALF_EVEN);
                                            BigDecimal totalper = historicoobra.getNumvalorhist();
                                            totalper = totalper.setScale(1, RoundingMode.HALF_EVEN);
                                            if (puedefaltar == false) {
                                                if (suma.compareTo(totalper) != 0) {
                                                    mensajeModificacion = bundle.getString("lasumadelostotales") + " " + suma + bundle.getString("noconincdeconeltipifi") + " " + historicoobra.getNumvalorhist();
                                                    return false;
                                                }
                                            } else {
                                                if (suma.compareTo(totalper) > 0) {
                                                    mensajeModificacion = bundle.getString("lasumadelostotales") + " " + suma + bundle.getString("nopuedesermayoral") + " " + historicoobra.getNumvalorhist();
                                                    return false;
                                                }
                                            }

                                            ///Lenado de datos

                                            listaperiodos = new ArrayList<Periodo>();
                                            listactividadesmod = new ArrayList<Actividadobra>();

                                            itperio = getAdministrarObraNew().getObra().getPeriodos().iterator();

                                            i = 0;

                                            while (itperio.hasNext()) {
                                                Periodo per = (Periodo) itperio.next();
                                                if (i < periodoini) {
                                                    if (i == periodoini - 1) {
                                                        per.setNumvaltotplanif(getAdministrarObraNew().getObra().getNumvalejecobra());
                                                    } else {
                                                        per.setNumvaltotplanif(BigDecimal.valueOf(0));
                                                    }

                                                    listaperiodos.add(per);
                                                    fechaInicialNoAlimentada = per.getDatefecfinperiodo();
                                                    Calendar fec = Calendar.getInstance();
                                                    fec.setTime(fechaInicialNoAlimentada);
                                                    fec.add(Calendar.DATE, +1);
                                                    fechaInicialNoAlimentada = fec.getTime();
                                                }

                                                i++;
                                            }

                                            if (listaperiodos.size() == 0) {
                                                fechaInicialNoAlimentada = getAdministrarObraNew().getObra().getDatefeciniobra();

                                            }


                                            colActual = 11;
                                            Calendar fecha = Calendar.getInstance();
                                            fecha.setTime(fechaInicialNoAlimentada);
                                            fila = sheet.getRow(5);
                                            while (colActual <= numColPeriodos) {

                                                Cell celdatotal = fila.getCell(colActual);

                                                if (celdatotal != null) {

                                                    Periodo pe = new Periodo();
                                                    pe.setDatefeciniperiodo(fecha.getTime());
                                                    fecha.add(Calendar.DATE, +(getAdministrarObraNew().getObra().getPeriodomedida().getIntnrodiasperiomedida() - 1));
                                                    pe.setDatefecfinperiodo(fecha.getTime());
                                                    pe.setNumvaltotplanif(BigDecimal.valueOf(celdatotal.getNumericCellValue()));
                                                    pe.setObra(getAdministrarObraNew().getObra());
                                                    Periodo perexist = getAdministrarObraNew().getObra().encontrarPeriodo(pe.getDatefeciniperiodo().getYear(), pe.getDatefeciniperiodo().getMonth(), pe.getDatefeciniperiodo().getDate());
                                                    if (perexist != null) {
                                                        pe.setIntidperiodo(perexist.getIntidperiodo());
                                                    }
                                                    if (colActual == numColPeriodos) {
                                                        pe.setDatefecfinperiodo(historicoobra.getDatefecfinhist());
                                                    }


                                                    listaperiodos.add(pe);
                                                    fecha.add(Calendar.DATE, +1);

                                                }
                                                colActual = colActual + 2;
                                            }

                                            filaActual = NUM_FILA_PRIMERA_ACTIVIDAD;



                                            while (filaActual < numTotalFilas) {
                                                i = 0;
                                                Actividadobra actividadobra = new Actividadobra();
                                                fila = sheet.getRow(filaActual);
                                                Cell celdaooid = fila.getCell(0);
                                                Cell celdadesc = fila.getCell(2);
                                                Cell celdauni = fila.getCell(3);
                                                Cell celdacant = fila.getCell(4);
                                                Cell celdacantejecuta = fila.getCell(5);

                                                Cell celdavalor = fila.getCell(7);
                                                double num = 0;
                                                long num2 = 0;
                                                try {
                                                    num = celdaooid.getNumericCellValue();
                                                    num2 = (long) num;
                                                } catch (NullPointerException e) {
                                                }
                                                actividadobra.setOidactiviobra(Integer.parseInt(String.valueOf(num2)));
                                                actividadobra.setStrdescactividad(celdadesc.getStringCellValue());
                                                actividadobra.setStrtipounidadmed(celdauni.getStringCellValue());
                                                actividadobra.setFloatcantplanifao(celdacant.getNumericCellValue());
                                                actividadobra.setNumvalorplanifao(BigDecimal.valueOf(celdavalor.getNumericCellValue()));
                                                actividadobra.setJsfUsuario(getAdministrarObraNew().getObra().getJsfUsuario());
                                                actividadobra.setFloatcantidadejecutao(celdacantejecuta.getNumericCellValue());
                                                actividadobra.setNumvalorejecutao(actividadobra.getNumvalorplanifao().multiply(BigDecimal.valueOf(actividadobra.getFloatcantidadejecutao())));
                                                actividadobra.setStrcodcubs("");
                                                actividadobra.setObra(getAdministrarObraNew().getObra());

                                                Actividadobra act = getAdministrarObraNew().getObra().getActividadobra(actividadobra.getStrdescactividad(), actividadobra.getOidactiviobra());
                                                if (act != null) {
                                                    actividadobra.setOidactiviobra(act.getOidactiviobra());
                                                    actividadobra.setFloatcantidadejecutao(act.getFloatcantidadejecutao());
                                                    actividadobra.setNumvalorejecutao(act.getNumvalorejecutao());

                                                    Set<Relacionactividadobraperiodo> relaciones = new HashSet<Relacionactividadobraperiodo>(0);

                                                    while (i < periodoini) {

                                                        //Iterator itrel = listaperiodos.get(i).getRelacionactividadobraperiodos().iterator();
                                                        Iterator itrel = getSessionBeanCobra().getCobraService().obtenerRelacionesactividadobraperiodo(listaperiodos.get(i).getIntidperiodo()).iterator();
                                                        while (itrel.hasNext()) {
                                                            Relacionactividadobraperiodo rela = (Relacionactividadobraperiodo) itrel.next();
                                                            if (rela.getActividadobra().equals(act)) {
                                                                rela.setNumvalplanif(BigDecimal.valueOf(0));
                                                                relaciones.add(rela);
                                                            }
                                                        }
                                                        i++;
                                                    }

                                                    actividadobra.setRelacionactividadobraperiodos(relaciones);

                                                } else {

                                                    listaperiodos.get(i).setRelacionactividadobraperiodos(new LinkedHashSet());
                                                    while (i < periodoini) {
                                                        Relacionactividadobraperiodo rela = new Relacionactividadobraperiodo();
                                                        rela.setActividadobra(actividadobra);
                                                        rela.setFloatcantplanif(0);
                                                        rela.setNumvalplanif(BigDecimal.valueOf(0));
                                                        rela.setPeriodo(listaperiodos.get(i));
                                                        actividadobra.getRelacionactividadobraperiodos().add(rela);
                                                        listaperiodos.get(i).getRelacionactividadobraperiodos().add(rela);
                                                        i++;
                                                    }
                                                }
                                                colActual = 11;
                                                i = periodoini;

                                                while (colActual <= numColPeriodos) {

                                                    Cell celdatotal = fila.getCell(colActual);
                                                    Cell celdacanti = fila.getCell(colActual - 1);
                                                    Relacionactividadobraperiodo rela = new Relacionactividadobraperiodo();

                                                    rela.setActividadobra(actividadobra);
                                                    if (celdacanti != null) {
                                                        rela.setFloatcantplanif(celdacanti.getNumericCellValue());
                                                    } else {
                                                        rela.setFloatcantplanif(0);
                                                    }
                                                    rela.setNumvalplanif(BigDecimal.valueOf(celdatotal.getNumericCellValue()));
                                                    //Relacionactividadobraperiodo relexist = getAdministrarObraNew().getObra().obtenerRelacionPeriodoActividad(actividadobra.getOidactiviobra(), listaperiodos.get(i).getIntidperiodo());
                                                    Relacionactividadobraperiodo relexist = getSessionBeanCobra().getCobraService().obtenerRelacionactividadobraperiodo(actividadobra.getOidactiviobra(), listaperiodos.get(i).getIntidperiodo());


                                                    if (relexist != null) {
                                                        rela.setOidnmid(relexist.getOidnmid());
                                                    }
                                                    rela.setPeriodo(listaperiodos.get(i));
                                                    actividadobra.getRelacionactividadobraperiodos().add(rela);
                                                    listaperiodos.get(i).getRelacionactividadobraperiodos().add(rela);
                                                    i++;

                                                    colActual = colActual + 2;
                                                }
                                                listactividadesmod.add(actividadobra);
                                                filaActual++;

                                            }
                                        }
                                    } else {
                                        mensajeModificacion = bundle.getString("elarchivodeberiapos") + " " + numperiodvali + " " + bundle.getString("peridos");
                                        return false;
                                    }


                                }

                            }

                        } else {
                            mensajeModificacion = bundle.getString("elvalortotaldelaobraes") + " " + valordelacelda + " " + bundle.getString("noconincdeconeltipifi") + " " + historicoobra.getNumvalorhist();
                            return false;
                        }
                    } else {
                        mensajeModificacion = bundle.getString("elvalortotaldela");
                        return false;
                    }


                } catch (java.lang.IllegalStateException e) {
                    mensajeModificacion = e.getCause().toString();
                    return false;
                }


            } else {
                mensajeModificacion = bundle.getString("elarchivonotieneact");//"EL ARCHIVO NO TIENE ACTIVIDADES";
                return false;

            }

        } catch (Exception ex) {
            mensajeModificacion = bundle.getString("elarchivoesta") + ex;
            return false;
        }
        mensajeModificacion = "";
        return true;


    }

    private boolean validarActividadEnCronogramaAnterior(Actividadobra actividadobra, int fila) {
        boolean valido = true;
        Iterator itrActividades = getAdministrarObraNew().getObra().getActividadobras().iterator();
        boolean descActividadobraExiste = false;
        boolean unidadesIguales = false;
        boolean valoresUnitariosIguales = false;
        boolean valorejecutado = false;
        boolean oid = false;

        while (itrActividades.hasNext()) {
            Actividadobra actividadobraAnterior = (Actividadobra) itrActividades.next();
            //if (actividadobraAnterior.getStrdescactividad().equals(actividadobra.getStrdescactividad())) {
            if (actividadobraAnterior.getOidactiviobra() == actividadobra.getOidactiviobra()) {
                oid = true;
                if (actividadobraAnterior.getStrdescactividad().equals(actividadobra.getStrdescactividad())) {
                    descActividadobraExiste = true;
                    if (actividadobraAnterior.getStrtipounidadmed().equals(actividadobra.getStrtipounidadmed())) {
                        unidadesIguales = true;
                    }

                    if (actividadobraAnterior.getNumvalorplanifao().doubleValue() == actividadobra.getNumvalorplanifao().doubleValue()) {
                        valoresUnitariosIguales = true;
                    } else {
                        valoresUnitariosIguales = false;
                    }

                    if (actividadobraAnterior.getFloatcantidadejecutao().floatValue() == actividadobra.getFloatcantidadejecutao().floatValue()) {

                        valorejecutado = true;
                    } else {

                        valorejecutado = false;
                    }
                }
            }
        }

        if (!oid) {
            valido = false;
            mensajeModificacion = bundle.getString("elidentiicadorocultodelaactivi") + (fila + 1) + bundle.getString("hasidomodifica");
        } else {
            if (!descActividadobraExiste) {
                valido = false;
                mensajeModificacion = bundle.getString("ladescripciondelaactivi") + (fila + 1) + bundle.getString("hasidomodifica");
            } else {
                if (!unidadesIguales) {
                    valido = false;
                    mensajeModificacion = bundle.getString("launidaddelaactivi") + (fila + 1) + bundle.getString("hasidomodifica");
                }
                if (!valoresUnitariosIguales && !historicoobra.isCambioprecios()) {
                    valido = false;

                    mensajeModificacion = bundle.getString("elvalorunitariodelaactividaddelcrono") + (fila + 1) + bundle.getString("hasidomodifica");
                }
                if (!valorejecutado) {
                    valido = false;
                    mensajeModificacion = bundle.getString("lacantidaddeactividadejecutada") + (fila + 1) + bundle.getString("hasidomodifica");
                }
            }
        }

        return valido;
    }

    public boolean validarActividad(String desc, String unidad, Double cantidad, Double valoruni, int fila) {


        if (desc.compareTo("") == 0 || unidad.compareTo("") == 0) {
            mensajeModificacion = bundle.getString("debellenarlosdatosdela") + fila;
            return false;
        }
        if (desc.length() > 255) {
            mensajeModificacion = bundle.getString("lactividadenlafila") + fila + bundle.getString("poseemasde");
            return false;
        }
        if (unidad.length() > 10) {
            mensajeModificacion = bundle.getString("launidaddemedida") + fila + bundle.getString("poseemasdediez");
            return false;
        }
        if (cantidad < 0 || valoruni <= 0) {
            mensajeModificacion = bundle.getString("losvaloresenlafinal") + fila + bundle.getString("nopuedenser");
            return false;
        }
        return true;
    }

    /**
     * Asigna la obra actual a una obra historica
     * Asigna los periodos de la obra modificada en el cronograma a la obra actual
     * Actualiza las actividades de la obra modificada con la obra actual
     * @param gcXLS
     */
    private void mapearCronograma() {

        historicoobra.calcularEstadoModificaciones();
        if (historicoobra.isAdiciontiempo()) {
            //Se intercambian las fechas de obra e historicoobra
            Date fecfinobra = getAdministrarObraNew().getObra().getDatefecfinobra();
            getAdministrarObraNew().getObra().setDatefecfinobra(historicoobra.getDatefecfinhist());
            int plazoObra = 0;
            if (historicoobra.getDatefecfinhist() != null) {
                plazoObra = (int) ((historicoobra.getDatefecfinhist().getTime() - getAdministrarObraNew().getObra().getDatefeciniobra().getTime()) / (1000 * 60 * 60 * 24) + 1);
            } else {
                plazoObra = getAdministrarObraNew().getObra().getIntplazoobra();
            }

            getAdministrarObraNew().getObra().setIntplazoobra(plazoObra);
            historicoobra.setDatefecfinhist(fecfinobra);
        }

        if (historicoobra.isAdicionpresu()) {
            //Se intercambian los valores totales:
            BigDecimal numValorTotal = getAdministrarObraNew().getObra().getNumvaltotobra();
            getAdministrarObraNew().getObra().setNumvaltotobra(historicoobra.getNumvalorhist());
            historicoobra.setNumvalorhist(numValorTotal);
        }

        getAdministrarObraNew().getObra().setTipoestadobra(new Tipoestadobra(1));
        historicoobra.setStrnuevointervhist(getAdministrarObraNew().getObra().getStrinterventor());
        historicoobra.setJsfUsuario(getAdministrarObraNew().getObra().getJsfUsuario());

        backupPlaneacion();



        getAdministrarObraNew().getObra().setActividadobras(new LinkedHashSet(listactividadesmod));
        getAdministrarObraNew().getObra().setPeriodos(new LinkedHashSet(listaperiodos));


        getAdministrarObraNew().getObra().setNovedads(new LinkedHashSet());

        if (historicoobra.isReprogramacion()) {
            Novedad novedad = new Novedad();
            novedad.setDatefechanovedad(new Date());
            novedad.setObra(getAdministrarObraNew().getObra());
            novedad.setTiponovedad(new Tiponovedad(5, ""));
            getAdministrarObraNew().getObra().getNovedads().add(novedad);
        }

        if (historicoobra.isAdiciontiempo()) {
            Novedad novedad = new Novedad();
            novedad.setDatefechanovedad(new Date());
            novedad.setObra(getAdministrarObraNew().getObra());
            novedad.setTiponovedad(new Tiponovedad(6, ""));
            getAdministrarObraNew().getObra().getNovedads().add(novedad);
            //getAdministrarObraNew().getObra().getContrato().setPolizacontratos(new HashSet(listapolizas));
            //getSessionBeanCobra().getCobraService().guardarContrato(getAdministrarObraNew().getObra().getContrato());
        }

        if (historicoobra.isAdicionpresu()) {
            Novedad novedad = new Novedad();
            novedad.setDatefechanovedad(new Date());
            novedad.setObra(getAdministrarObraNew().getObra());
            novedad.setTiponovedad(new Tiponovedad(7, ""));
            getAdministrarObraNew().getObra().getNovedads().add(novedad);
            //getAdministrarObraNew().getObra().getContrato().setPolizacontratos(new HashSet(listapolizas));
            //getSessionBeanCobra().getCobraService().guardarContrato(getAdministrarObraNew().getObra().getContrato());
        }

        if (historicoobra.isCambioprecios()) {
            Novedad novedad = new Novedad();
            novedad.setDatefechanovedad(new Date());
            novedad.setObra(getAdministrarObraNew().getObra());
            novedad.setTiponovedad(new Tiponovedad(8, ""));
            getAdministrarObraNew().getObra().getNovedads().add(novedad);
        }

        if (historicoobra.isAdicionactivi()) {
            Novedad novedad = new Novedad();
            novedad.setDatefechanovedad(new Date());
            novedad.setObra(getAdministrarObraNew().getObra());
            novedad.setTiponovedad(new Tiponovedad(9, ""));
            getAdministrarObraNew().getObra().getNovedads().add(novedad);
        }



        getSessionBeanCobra().getCobraService().guardarObra(getAdministrarObraNew().getObra(), getSessionBeanCobra().getUsuarioObra(),-1);

        int i = 0;

        while (i < listarelahisto.size()) {
            getSessionBeanCobra().getCobraService().guardarRelacionActihistPeriodo(listarelahisto.get(i));
            i++;
        }


    }

    public String backupPlaneacion() {
        ///Backup Actividades

        Iterator itperiodo = getAdministrarObraNew().getObra().getPeriodos().iterator();
        historicoobra.setPeriodohistos(new LinkedHashSet<Periodohisto>());
        historicoobra.setActividadobrahists(new LinkedHashSet<Actividadobrahist>());
        while (itperiodo.hasNext()) {
            Periodo per = (Periodo) itperiodo.next();
            Periodohisto periohisto = new Periodohisto();
            //periohisto.setIntidperiodo(per.getIntidperiodo());
            periohisto.setDatefecfinperiodo(per.getDatefecfinperiodo());
            periohisto.setDatefeciniperiodo(per.getDatefeciniperiodo());
            periohisto.setHistoricoobra(historicoobra);
            periohisto.setNumvaltotplanif(per.getNumvaltotplanif());

            //Iterator itrelper = per.getRelacionactividadobraperiodos().iterator();
            Iterator itrelper = getSessionBeanCobra().getCobraService().obtenerRelacionesactividadobraperiodo(per.getIntidperiodo()).iterator();
            while (itrelper.hasNext()) {
                Relacionactividadobraperiodo rela = (Relacionactividadobraperiodo) itrelper.next();
                Relacionactividadobraperiodohisto relahist = new Relacionactividadobraperiodohisto();
                Actividadobrahist acthist = new Actividadobrahist();
                //acthist.setOidactiviobra(rela.getActividadobra().getOidactiviobra());
                acthist.setFloatcantidadejecutao(rela.getActividadobra().getFloatcantidadejecutao());
                acthist.setFloatcantplanifao(rela.getActividadobra().getFloatcantplanifao());
                acthist.setHistoricoobra(historicoobra);
                acthist.setNumvalorejecutao(rela.getActividadobra().getNumvalorejecutao());
                acthist.setNumvalorplanifao(rela.getActividadobra().getNumvalorplanifao());
                acthist.setStrcodcubs(rela.getActividadobra().getStrcodcubs());
                acthist.setStrdescactividad(rela.getActividadobra().getStrdescactividad());
                acthist.setStrtipounidadmed(rela.getActividadobra().getStrtipounidadmed());
                acthist.setJsfUsuario(rela.getActividadobra().getJsfUsuario());
                relahist.setActividadobrahist(acthist);
                relahist.setFloatcantplanif(rela.getFloatcantplanif());
                relahist.setNumvalplanif(rela.getNumvalplanif());
                relahist.setPeriodohisto(periohisto);
                periohisto.getRelacionactividadobraperiodohistos().add(relahist);
            }
            historicoobra.getPeriodohistos().add(periohisto);

        }
        //Iterator itActi = getAdministrarObraNew().getObra().getActividadobras().iterator();
        Iterator itActi = getSessionBeanCobra().getCobraService().obtenerActividadesObra(getAdministrarObraNew().getObra().getIntcodigoobra()).iterator();
        List<Periodohisto> listaperhistos = new ArrayList<Periodohisto>(historicoobra.getPeriodohistos());
        //List<Periodohisto> listaperhistos = getSessionBeanCobra().getCobraService().obtenerPeriodosporHistorico(historicoobra.getOididhistoricoobra());
        while (itActi.hasNext()) {
            Actividadobrahist acthist = new Actividadobrahist();
            Actividadobra actobra = (Actividadobra) itActi.next();
            acthist.setFloatcantidadejecutao(actobra.getFloatcantidadejecutao());
            acthist.setFloatcantplanifao(actobra.getFloatcantplanifao());

            acthist.setHistoricoobra(historicoobra);
            acthist.setNumvalorejecutao(actobra.getNumvalorejecutao());
            acthist.setNumvalorplanifao(actobra.getNumvalorplanifao());
            acthist.setStrcodcubs(actobra.getStrcodcubs());
            acthist.setStrdescactividad(actobra.getStrdescactividad());
            acthist.setStrtipounidadmed(actobra.getStrtipounidadmed());
            acthist.setJsfUsuario(actobra.getJsfUsuario());
            //periodos

            listarelahisto = new ArrayList<Relacionactividadobraperiodohisto>();

            int i = 0;

            while (i < listaperhistos.size()) {

                Iterator itrelaper = listaperhistos.get(i).getRelacionactividadobraperiodohistos().iterator();
                while (itrelaper.hasNext()) {

                    Relacionactividadobraperiodohisto rela = (Relacionactividadobraperiodohisto) itrelaper.next();
                    //if (rela.getActividadobrahist().equals(acthist)) {
                    if (rela.getActividadobrahist().getStrdescactividad().compareTo(acthist.getStrdescactividad()) == 0
                            && rela.getActividadobrahist().getFloatcantidadejecutao().equals(acthist.getFloatcantidadejecutao())
                            && rela.getActividadobrahist().getFloatcantplanifao() == acthist.getFloatcantplanifao()
                            && rela.getActividadobrahist().getNumvalorejecutao().equals(acthist.getNumvalorejecutao())
                            && rela.getActividadobrahist().getNumvalorplanifao().equals(acthist.getNumvalorplanifao())
                            && rela.getActividadobrahist().getStrtipounidadmed().compareTo(acthist.getStrtipounidadmed()) == 0) {
                        rela.setActividadobrahist(acthist);

                        listarelahisto.add(rela);

                    }
                }
                i++;
            }

            acthist.setRelacionactividadobraperiodohistos(new LinkedHashSet(listarelahisto));
            historicoobra.getActividadobrahists().add(acthist);

        }


        getSessionBeanCobra().getCobraService().guardarHistorico(historicoobra, getSessionBeanCobra().getUsuarioObra());
        return null;
    }

    public String validarCrono() {
        cronovalido = false;
        if (getSubirCronograma().getArchivos().size() > 0) {
            Iterator arch = getSubirCronograma().getArchivos().iterator();
            mensajeXLSInvalido =
                    null;
            File fileCronograma = null;
            if (historicoobra.getDatefecfinhist() == null) {
                historicoobra.setDatefecfinhist(getAdministrarObraNew().getObra().getDatefecfinobra());
            }

            //GestorCronogramaXLS gcXLS = new GestorCronogramaXLS(obra, historicoobra);
            while (arch.hasNext()) {
                fileCronograma = (File) arch.next();
            }
            cronovalido = validarCronograma(fileCronograma);

        } else {
            mensajeModificacion = bundle.getString("debeadjuntarelcrono");

        }
        return null;

    }

    public String registrarModificacion() {
        
        mensajeModificacion = "";

        if (cronovalido) {
//                archivarCronogramaHistorico();
            subirCronograma();
            mapearCronograma();
            //persistirModificacion();

            otroSi = new Documentoobra();
            actaConvenio = new Documentoobra();
            verSoportes = false;
            getAdministrarObraNew().mostrarMenuModificar();
            //mensajeModificacion="Se ha cancelado la modificación";
            //getSessionBeanCobra().getOb().cerrarSession();
            tipificacionValida = false;

            historicoobra = new Historicoobra();

            //getAdministrarObraNew().cargarObra(getAdministrarObraNew().getObra());

            mensajeModificacion = bundle.getString("lamodificacionseha");
        } else {
            mensajeModificacion = bundle.getString("debeadjuntarelcrono");
        }
        return null;
    }

    public String borrarCronogramaSubido() {
        cronovalido = false;
        subirCronograma.borrarDatosSubidos();
        return null;
    }

    public String borrarUltimaTipificacion() {
        mensajeModificacion = "";
        if (historicoobra != null) {
            String ArchivoPath = "";
            ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            if (otroSi != null && otroSi.getStrubicacion() != null && otroSi.getStrubicacion().compareTo("") != 0) {
                ArchivoPath = theApplicationsServletContext.getRealPath(otroSi.getStrubicacion());
                File archivo =
                        new File(ArchivoPath);
                if (archivo.exists()) {
                    archivo.delete();
                }
                getSessionBeanCobra().getCobraService().borrarDocumento(otroSi);
                
                subirOtroSi.borrarDatosSubidos();
            }
            if (actaConvenio != null && actaConvenio.getStrubicacion() != null && actaConvenio.getStrubicacion().compareTo("") != 0) {
                ArchivoPath = theApplicationsServletContext.getRealPath(actaConvenio.getStrubicacion());
                File archivo =
                        new File(ArchivoPath);
                if (archivo.exists()) {
                    if (archivo.exists()) {
                        archivo.delete();
                    }
                }

                getSessionBeanCobra().getCobraService().borrarDocumento(actaConvenio);
                //getAdministrarObraNew().getObra().getDocumentoobras().remove(actaConvenio);
                subirActaConvenio.borrarDatosSubidos();

            }
            subirCronograma.borrarDatosSubidos();
            getAdministrarObraNew().getObra().setTipoestadobra(new Tipoestadobra(1));
            getSessionBeanCobra().getCobraService().borrarHistorico(historicoobra);
            getSessionBeanCobra().getCobraService().guardarObra(getAdministrarObraNew().getObra(), getSessionBeanCobra().getUsuarioObra(),-1);

//            if (otroSi != null && otroSi.getStrubicacion() != null && otroSi.getStrubicacion().compareTo("") != 0) {
////                getSessionBeanCobra().getCobraService().borrarDocumento(otroSi);
//            }
//            if (actaConvenio != null && actaConvenio.getStrubicacion() != null && actaConvenio.getStrubicacion().compareTo("") != 0) {
////                getSessionBeanCobra().getCobraService().borrarDocumento(actaConvenio);
//            }
            historicoobra = new Historicoobra();

            valorhistorico = BigDecimal.valueOf(0);
            verSoportes = false;
            otroSi = new Documentoobra();
            actaConvenio = new Documentoobra();

            getAdministrarObraNew().mostrarMenuModificar();
            mensajeModificacion = bundle.getString("sehacancelado");
            

            tipificacionValida = false;
        }


        return null;
    }

    public String cambioFechaTerminacion() {
        validarTipificacion();
        return null;
    }

    /**
     * Permite ver los soportes de modificación si se ha seleccionado una fecha
     * de adición de tiempo o si se ha ingresado un valor de adición de
     * presupuesto
     */
    public void evaluarVerSoportes() {
        listapolizas = new ArrayList<Polizacontrato>();
        if (historicoobra != null && valorhistorico != null) {
            if (valorhistorico.compareTo(new BigDecimal(0)) > 0 || (historicoobra.getDatefecfinhist() != null && !historicoobra.getDatefecfinhist().equals(getAdministrarObraNew().getObra().getDatefecfinobra()))) {
                setVerSoportes(true);
                //listapolizas= getSessionBeanCobra().getCobraService().encontrarPolizasxContrato(getAdministrarObraNew().getObra().getContrato().getIntidcontrato());


            } else {
                setVerSoportes(false);
            }
        } else {
            setVerSoportes(false);
        }
    }

    /**
     * Valida los datos de la tipificación
     * @return
     */
    public boolean validarTipificacion() {
        mensajeModificacion = "";
        tipificacionValida = false;

        if (historicoobra.getStrrazonmodif() == null || historicoobra.getStrrazonmodif().compareTo("") == 0) {
            mensajeModificacion = bundle.getString("debeingresarunajustificacion");//"Debe ingresar una justificaión para la modificación";
            return false;
        }
        if (historicoobra.getDatefecobrahist() == null) {
            mensajeModificacion = bundle.getString("debeingresarunafecha");//"Debe ingresar una fecha de modificación";
            return false;
        }
        Alimentacion ultalim = getSessionBeanCobra().getCobraService().obtenerUltimaalimentacion(getAdministrarObraNew().getObra().getIntcodigoobra());
        Date fechaUltimaAlimentacion = null;
        if (ultalim == null) {
            fechaUltimaAlimentacion = getAdministrarObraNew().getObra().getDatefeciniobra();
        } else {
            fechaUltimaAlimentacion = ultalim.getDatefecha();
        }

        if (fechaUltimaAlimentacion != null) {
            if (fechaUltimaAlimentacion.after(historicoobra.getDatefecobrahist()) || fechaUltimaAlimentacion.equals(historicoobra.getDatefecobrahist())) {
                mensajeModificacion = bundle.getString("lafechademodificacionnopued");//"La fecha de modificación no puede ser inferior o igual a la fecha de última alimentación de la obra";
                return false;
            }

        }
        if (fechaUltimaAlimentacion == null) {
            if (getAdministrarObraNew().getObra().getDatefeciniobra().after(historicoobra.getDatefecobrahist())) {
                mensajeModificacion = bundle.getString("lafechademoficacionnopuedeserinferior");//"La fecha de modificación no puede ser inferior o igual a la fecha de inicio de la obra";
                return false;
            }

        }

        ///Preguntar si esta validación es cierta

        if (historicoobra.getDatefecfinhist() != null && getAdministrarObraNew().getObra().getDatefecfinobra().after(historicoobra.getDatefecfinhist())) {
            mensajeModificacion = bundle.getString("lanuevafechadefinalizaciondeobra");//"La nueva fecha de finalización de obra no puede ser inferior a la fecha actual de finalización";
            return false;
        }
        if (actaConvenio != null && actaConvenio.getStrubicacion() != null && !actaConvenio.getStrubicacion().equals("")) {
            if (!cambioPrecios && !adicionActividades) {
                mensajeModificacion = bundle.getString("debeactivarlacasillacambio");//"Debe activar la casilla: Cambio de precios o la casilla: Adición de actividades antes de seleccionar el acta de convenio";
                return false;
            }

        }
        historicoobra.calcularEstadoModificaciones();

        if (historicoobra.isCambioprecios() || historicoobra.isAdicionactivi()) {
            //if (actaConvenio == null || actaConvenio.getStrubicacion() == null || actaConvenio.getStrubicacion().equals("")) {
            if (subirActaConvenio.getArchivos().isEmpty()) {
                mensajeModificacion = bundle.getString("debeingresarelactadeconve");//"Debe ingresar el acta de convenio";
                return false;
            }

        }
        if (historicoobra.isAdiciontiempo() || historicoobra.isAdicionpresu()) {
            //if (otroSi == null || otroSi.getStrubicacion() == null || otroSi.getStrubicacion().equals("")) {
            if (subirOtroSi.getArchivos().isEmpty()) {
                mensajeModificacion = bundle.getString("debeingresarelotrosi");//"Debe ingresar el Otro Si";
                return false;
            }



//            if (historicoobra.getStrresaprobahist() == null || historicoobra.getStrresaprobahist().equals("")) {
//                mensajeModificacion = bundle.getString("debeingresarlaresoluciondeapr");//"Debe ingresar la resolución de aprobación";
//                return false;
//            }
//
//            if (historicoobra.getDatefecresaprobhist() == null) {
//                mensajeModificacion = bundle.getString("debeingresarlafechaderesolu");//"Debe ingresar la fecha de resolución de aprobación";
//                return false;
//            }
        }
        tipificacionValida = true;
        return true;
    }

    public void subirOtroSi() {
        String realArchivoPath = "";
        String URL = "/resources/Documentos/ObrasVigentes/";

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        if (subirOtroSi.getArchivos().size() > 0) {
            realArchivoPath = theApplicationsServletContext.getRealPath(URL);
            try {

                File carpeta = new File(realArchivoPath + "/" + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()));
                if (!carpeta.exists()) {
                    carpeta.mkdirs();

                }

            } catch (Exception ex) {
                FacesUtils.addErrorMessage(bundle.getString("nosepuedesubir"));
            }

        }
        try {
            subirOtroSi.guardarArchivosTemporales(realArchivoPath + "/" + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()), false);
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(ModificarObra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Iterator arch = subirOtroSi.getArchivos().iterator();
        while (arch.hasNext()) {
            Archivo nombreoriginal = (Archivo) arch.next();
            this.otroSi.setStrubicacion(URL + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()) + "/" + nombreoriginal.getOnlyName());

        }

    }

    public void subirActaConvenio() {
        String realArchivoPath = "";
        String URL = "/resources/Documentos/ObrasVigentes/";
        nombreConvenio = "";
        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        if (subirActaConvenio.getArchivos().size() > 0) {
            realArchivoPath = theApplicationsServletContext.getRealPath(URL);
            try {

                File carpeta = new File(realArchivoPath + "/" + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()));
                if (!carpeta.exists()) {
                    carpeta.mkdirs();

                }

            } catch (Exception ex) {
                FacesUtils.addErrorMessage(bundle.getString("nosepuedesubir"));
            }

        }
        try {
            subirActaConvenio.guardarArchivosTemporales(realArchivoPath + "/" + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()), false);
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(ModificarObra.class.getName()).log(Level.SEVERE, null, ex);
        }

        Iterator arch = subirActaConvenio.getArchivos().iterator();
        while (arch.hasNext()) {
            Archivo nombreoriginal = (Archivo) arch.next();
            this.actaConvenio.setStrubicacion(URL + String.valueOf(getAdministrarObraNew().getObra().getIntcodigoobra()) + "/" + nombreoriginal.getOnlyName());
            nombreConvenio = nombreoriginal.getOnlyName();
        }

    }

    public String bt_agregar_otroSi_action() {
        // TODO: Replace with your code
        Tipodocumento td = new Tipodocumento();
        td.setInttipodoc(11);
        otroSi = new Documentoobra();
        otroSi.setDatefecha(new Date());
        otroSi.setObra(getAdministrarObraNew().getObra());
        otroSi.setStrnombre("Otro Si");
        otroSi.setTipodocumento(td);

        subirOtroSi();
        getSessionBeanCobra().getCobraService().guardarDocumento(otroSi);
        //getAdministrarObraNew().getObra().getDocumentoobras().add(otroSi);
        //this.subirOtroSi = new SubirArchivoBean(1, true, false);
        return null;
    }

    public String bt_agregar_actaConvenio_action() {
        // TODO: Replace with your code
        Tipodocumento td = new Tipodocumento();
        td.setInttipodoc(17);
        actaConvenio = new Documentoobra();
        actaConvenio.setDatefecha(new Date());
        actaConvenio.setObra(getAdministrarObraNew().getObra());
        actaConvenio.setStrnombre("Acta de Convenio");
        actaConvenio.setTipodocumento(td);

        subirActaConvenio();

        //getAdministrarObraNew().getObra().getDocumentoobras().add(actaConvenio);
        //this.subirActaConvenio = new SubirArchivoBean(1, true, false);
        return null;
    }

    public void cargarModificacion() {

        valorhistorico = BigDecimal.valueOf(0);
        mensajeModificacion = "";
        tipificacionValida = false;
        subirCronograma = null;
        subirCronograma = new CargadorArchivosWeb();
        subirCronograma.borrarDatosSubidos();

        subirActaConvenio = null;
        subirActaConvenio = new CargadorArchivosWeb();
        subirActaConvenio.borrarDatosSubidos();

        subirOtroSi = null;
        subirOtroSi = new CargadorArchivosWeb();
        subirOtroSi.borrarDatosSubidos();

        this.cambioPrecios = false;
        this.adicionActividades = false;

        listaperiodos = new ArrayList<Periodo>();
        listactividadesmod = new ArrayList<Actividadobra>();



        if (getAdministrarObraNew().getObra().getTipoestadobra().getIntestadoobra() == 3) {
            //historicoobra = getAdministrarObraNew().getObra().getUltimoHistorico();
            historicoobra = getSessionBeanCobra().getCobraService().obtenerUltimoHistorico(getAdministrarObraNew().getObra().getIntcodigoobra());
//            if (historicoobra.getNumvalorhist().compareTo(obra.getNumvaltotobra()) == 0) {
//                valorhistorico = new BigDecimal(0);
//            } else {
            valorhistorico = historicoobra.getNumvalorhist().subtract(getAdministrarObraNew().getObra().getNumvaltotobra());
//            }

            //historicoobra.setNumvalorhist(historicoobra.getNumvalorhist().remainder(obra.getNumvaltotobra()));
            historicoobra.calcularEstadoModificaciones();
            this.cambioPrecios = historicoobra.isCambioprecios();
            this.adicionActividades = historicoobra.isAdicionactivi();
            if (historicoobra.isCambioprecios() || historicoobra.isAdiciontiempo() || historicoobra.isAdicionpresu() || historicoobra.isAdicionactivi()) {
                ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String path = "";
                //otroSi = getAdministrarObraNew().getObra().getUltimoOtroSi();
                otroSi = getSessionBeanCobra().getCobraService().getUltimoOtroSi(getAdministrarObraNew().getObra().getIntcodigoobra());
                if (otroSi != null) {
                    if (otroSi.getStrubicacion() != null && otroSi.getDatefecha().compareTo(historicoobra.getDatefechist()) == 0) {

                        path = otroSi.getStrubicacion();

                        path = theApplicationsServletContext.getRealPath(path);
                        String nombre = "";

                        if (path.contains("%20")) {

                            int i = 0;
                            while (i < path.length()) {
                                if (String.valueOf(path.charAt(i)).compareTo("%") == 0) {
                                    nombre = nombre + " ";
                                    i = i + 2;
                                } else {
                                    nombre = nombre + String.valueOf(path.charAt(i));
                                }
                                i++;
                            }

                        } else {
                            nombre = path;
                        }

                        try {
                            File imagen = new File(nombre);
                            if (imagen.getTotalSpace() == 0) {
                            } else {
                                subirOtroSi.cargarArchivo(nombre);
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(AdministrarObraNew.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }


                //actaConvenio = getAdministrarObraNew().getObra().getUltimaActaConvenio();
                actaConvenio = getSessionBeanCobra().getCobraService().getUltimaActaConvenio(getAdministrarObraNew().getObra().getIntcodigoobra());
                if (actaConvenio != null) {
                    if (actaConvenio.getStrubicacion() != null && actaConvenio.getDatefecha().compareTo(historicoobra.getDatefechist()) == 0) {

                        path = actaConvenio.getStrubicacion();
                        path = theApplicationsServletContext.getRealPath(path);
                        String nombre = "";

                        if (path.contains("%20")) {

                            int i = 0;
                            while (i < path.length()) {
                                if (String.valueOf(path.charAt(i)).compareTo("%") == 0) {
                                    nombre = nombre + " ";
                                    i = i + 2;
                                } else {
                                    nombre = nombre + String.valueOf(path.charAt(i));
                                }
                                i++;
                            }

                        } else {
                            nombre = path;
                        }

                        try {
                            File imagen = new File(nombre);
                            if (imagen.getTotalSpace() == 0) {
                                nombreConvenio = "";

                            } else {
                                subirActaConvenio.cargarArchivo(nombre);
                                nombreConvenio = imagen.getName();

                            }
                        } catch (Exception ex) {
                            Logger.getLogger(AdministrarObraNew.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        nombreConvenio = "";
                    }
                }
            }

            evaluarVerSoportes();
            validarTipificacion();


            getAdministrarObraNew().mostrarMenuModificar();
        } else {
            if (getAdministrarObraNew().getObra().getTipoestadobra().getIntestadoobra() == 6) {
                //historicoobra = getAdministrarObraNew().getObra().getUltimoHistorico();
                historicoobra = getSessionBeanCobra().getCobraService().obtenerUltimoHistorico(getAdministrarObraNew().getObra().getIntcodigoobra());
            } else {
                historicoobra = new Historicoobra();
            }
            evaluarVerSoportes();
        }

    }

    public void establecerTipificacion() {
        boolean soloReprogramacion = true;
        //Tipificamos la modificación


        historicoobra.getTipomodificacions().clear();

        if (historicoobra.getDatefecfinhist() != null && historicoobra.getDatefecfinhist().after(getAdministrarObraNew().getObra().getDatefecfinobra())) {
            historicoobra.getTipomodificacions().add(new Tipomodificacion(1, ""));
            soloReprogramacion = false;
        }

        if (historicoobra.getNumvalorhist().compareTo(getAdministrarObraNew().getObra().getNumvaltotobra()) != 0) {
            historicoobra.getTipomodificacions().add(new Tipomodificacion(2, ""));
            soloReprogramacion = false;
        }

        if (cambioPrecios) {
            historicoobra.getTipomodificacions().add(new Tipomodificacion(7, ""));
            soloReprogramacion = false;
        }

        if (adicionActividades) {
            historicoobra.getTipomodificacions().add(new Tipomodificacion(3, ""));
            soloReprogramacion = false;
        }

        if (soloReprogramacion) {
            historicoobra.getTipomodificacions().add(new Tipomodificacion(8, ""));
            soloReprogramacion = false;
        }

    }

    public String registrarTipificacion() throws FileNotFoundException, IOException, Exception {

        //GestorCronogramaXLS gcXLS = new GestorCronogramaXLS(obra, historicoobra);
        //String ArchivoPath = "";
        ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        if (valorhistorico != null) {

            historicoobra.setNumvalorhist(getAdministrarObraNew().getObra().getNumvaltotobra().add(valorhistorico));
        } else {
            historicoobra.setNumvalorhist(getAdministrarObraNew().getObra().getNumvaltotobra());
        }
        if (historicoobra.getDatefecfinhist() == null) {

            historicoobra.setDatefecfinhist(getAdministrarObraNew().getObra().getDatefecfinobra());
        }
        establecerTipificacion();

        historicoobra.setDatefechist(new Date());
        historicoobra.setJsfUsuario(getAdministrarObraNew().getObra().getJsfUsuario());
        historicoobra.setObra(getAdministrarObraNew().getObra());
        historicoobra.setStrnuevointervhist(getAdministrarObraNew().getObra().getStrinterventor());

        //Validamos los datos ingresados para la tipificación

        if (validarTipificacion()) {

            //Se genera el cronograma
            //gcXLS.generarCronogramaModificacion(ArchivoPath, realArchivoPath);

            getAdministrarObraNew().getObra().setTipoestadobra(new Tipoestadobra(3));
            ///Guardando fisicamente el otro si
            getSessionBeanCobra().getCobraService().guardarHistorico(historicoobra, getSessionBeanCobra().getUsuarioObra());
            getSessionBeanCobra().getCobraService().guardarObra(getAdministrarObraNew().getObra(), getSessionBeanCobra().getUsuarioObra(),-1);

            if (verSoportes) {
                //if (otroSi == null) {
                if (subirOtroSi.getArchivos().size() > 0) {
                    bt_agregar_otroSi_action();
                }

                if (otroSi != null && otroSi.getStrubicacion() != null && !otroSi.getStrubicacion().equals("")) {
                    persistirDocumento(otroSi);
                    ////Volviendo a cargar el otro si en el file upload
                    String path = otroSi.getStrubicacion();

                    path = contexto.getRealPath(path);
                    String nombre = "";

                    if (path.contains("%20")) {

                        int i = 0;
                        while (i < path.length()) {
                            if (String.valueOf(path.charAt(i)).compareTo("%") == 0) {
                                nombre = nombre + " ";
                                i = i + 2;
                            } else {
                                nombre = nombre + String.valueOf(path.charAt(i));
                            }
                            i++;
                        }

                    } else {
                        nombre = path;
                    }

                    try {
                        File imagen = new File(nombre);
                        if (imagen.getTotalSpace() == 0) {
                        } else {
                            subirOtroSi.cargarArchivo(nombre);

                        }
                    } catch (Exception ex) {
                        Logger.getLogger(AdministrarObraNew.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //}
            }

            //Guardando fisicamente el Acta de Convenio
            if ((adicionActividades || cambioPrecios)) {
                //if (actaConvenio == null) {
                if (subirActaConvenio.getArchivos().size() > 0) {
                    bt_agregar_actaConvenio_action();
                }

                if (actaConvenio != null && actaConvenio.getStrubicacion() != null && !actaConvenio.getStrubicacion().equals("")) {
                    persistirDocumento(actaConvenio);
                    ////Volviendo a cargar el actaconvenio en el file upload
                    String path = actaConvenio.getStrubicacion();

                    path = contexto.getRealPath(path);
                    String nombre = "";

                    if (path.contains("%20")) {

                        int i = 0;
                        while (i < path.length()) {
                            if (String.valueOf(path.charAt(i)).compareTo("%") == 0) {
                                nombre = nombre + " ";
                                i = i + 2;
                            } else {
                                nombre = nombre + String.valueOf(path.charAt(i));
                            }
                            i++;
                        }

                    } else {
                        nombre = path;
                    }

                    try {
                        File imagen = new File(nombre);
                        if (imagen.getTotalSpace() == 0) {
                        } else {
                            subirActaConvenio.cargarArchivo(nombre);

                        }
                    } catch (Exception ex) {
                        Logger.getLogger(AdministrarObraNew.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //}
            }
            cargarModificacion();
            mensajeModificacion = bundle.getString("latipificacionseharegistrado");//"La tipificación se ha registrado y la obra se ha pasado al estado: En Modificación";   
            return null;
        } else {

            return null;

        }
    }

    private void persistirDocumento(Documentoobra doc) {
        getSessionBeanCobra().getCobraService().guardarDocumento(doc);
    }

    /**
     * Selecciona la póliza a editar
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return
     */
    public String elegirPolizaEditar() {
         polizaeditar = (Polizacontrato) tablapolizas.getRowData();
//        polizaeditar = listapolizas.get(filaSeleccionada);
        return null;
    }

    public String modificarPoliza() {
        int i = 0;

        while (i < listapolizas.size()) {
            if (listapolizas.get(i).getIntidpolizacontrato() == polizaeditar.getIntidpolizacontrato()) {


                listapolizas.get(i).setDatefechavecimiento(polizaeditar.getDatefechavecimiento());

            }
            i++;
        }

        return null;
    }

    /**
     * Si true, se visualizará el paso 1 de modificar proyecto
     */
    private boolean verPaso1 = true;

    public boolean isVerPaso1() {
        return verPaso1;
    }

    public void setVerPaso1(boolean verPaso1) {
        this.verPaso1 = verPaso1;
    }

    /**
     * Si true, se visualizará el paso 2 de modificar proyecto
     */
    private boolean verPaso2;

    public boolean isVerPaso2() {
        return verPaso2;
    }

    public void setVerPaso2(boolean verPaso2) {
        this.verPaso2 = verPaso2;
    }

    /**
     * Si true, se visualizará el paso 3 de modificar proyecto
     */
    private boolean verPaso3;

    public boolean isVerPaso3() {
        return verPaso3;
    }

    public void setVerPaso3(boolean verPaso3) {
        this.verPaso3 = verPaso3;
    }

    /**
     * Determina si se realizará prórroga en la modificación
     */
    private boolean realizarProrroga;

    public boolean isRealizarProrroga() {
        return realizarProrroga;
    }

    public void setRealizarProrroga(boolean realizarProrroga) {
        this.realizarProrroga = realizarProrroga;
    }

    /**
     * Determina si se realizará adición de presupuesto en la modificación
     */
    private boolean adicionarPresupuesto;

    public boolean isAdicionarPresupuesto() {
        return adicionarPresupuesto;
    }

    public void setAdicionarPresupuesto(boolean adicionarPresupuesto) {
        this.adicionarPresupuesto = adicionarPresupuesto;
    }

    /**
     * Determina si se realizará adición de actividades en la modificación
     */
    private boolean adicionarActividades;

    public boolean isAdicionarActividades() {
        return adicionarActividades;
    }

    public void setAdicionarActividades(boolean adicionarActividades) {
        this.adicionarActividades = adicionarActividades;
    }

    /**
     * Determina si se realizará cambio de precios en la modificación
     */
    private boolean cambiarPrecios;

    public boolean isCambiarPrecios() {
        return cambiarPrecios;
    }

    public void setCambiarPrecios(boolean cambiarPrecios) {
        this.cambiarPrecios = cambiarPrecios;
    }

    /**
     * Continúa en el paso 2 de modificar proyecto
     * @return
     */
    public String btSiguientePaso1Action() {
        this.verPaso1 = false;
        this.verPaso2 = true;
        return null;
    }

    /**
     * Regresa al paso 1 de modificar proyecto
     * @return Resultado correspondiente en el faces-config
     */
    public String btAnteriorPaso2Action() {
        this.verPaso1 = true;
        this.verPaso2 = false;
        return null;
    }

    /**
     * Continúa en el paso 3 de modificar proyecto
     * @return
     */
    public String btSiguientePaso2Action() {
        this.verPaso2 = false;
        this.verPaso3 = true;
        return null;
    }

    /**
     * Regresa al paso 1 de modificar proyecto
     * @return Resultado correspondiente en el faces-config
     */
    public String btAnteriorPaso3Action() {
        this.verPaso2 = true;
        this.verPaso3 = false;
        return null;
    }

    /**
     * Continúa en el paso 3 de modificar proyecto
     * @return
     */
    public String btSiguientePaso3Action() {
        return null;
    }

}
