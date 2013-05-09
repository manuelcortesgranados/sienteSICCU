/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Financiera;

import co.com.interkont.cobra.to.Encargofiduciario;
import co.com.interkont.cobra.to.Estadomovimiento;
import co.com.interkont.cobra.to.Movimiento;
import co.com.interkont.cobra.to.Ordendepago;
import co.com.interkont.cobra.to.Registrovalidador;
import co.com.interkont.cobra.to.Tipomovimiento;
import cobra.CargadorArchivosWeb;
import cobra.SessionBeanCobra;
import cobra.SubirArchivoBean;
import cobra.Supervisor.FacesUtils;
import com.interkont.cobra.exception.ArchivoExistenteException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import cobra.util.RutasWebArchivos;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.postgresql.util.PSQLException;

/**
 *
 * @author desarrollo5
 */
public class CargarArchivoFinanciera  {

    private final static int TOTAL_COL = 20;
    private List<Movimiento> movimientos = new ArrayList<Movimiento>();
    private List<Ordendepago> ordenPagos = new ArrayList<Ordendepago>();
    private final static int GIRO = 1;
    private final static int DEVOLUCION = 2;
    private final static int ANTICIPO = 3;
    private final static int ESTADO_APLICADO = 1;
    //private SubirArchivoBean subirArchivoMovimientos = new SubirArchivoBean(1, true, false);
    private String colexcel[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S"};
    HashSet ordenes = new HashSet();
    List<Encargos> encargos = new LinkedList<Encargos>();
    List<ExcelFinanciero> registrosExcel = new ArrayList<ExcelFinanciero>();
    Registrovalidador registro = new Registrovalidador();
    private boolean validacion = false;

    public CargarArchivoFinanciera() {
        cargadorArchivoMovimientos = new CargadorArchivosWeb();
    }
    /**
     * Objeto utilizado para subir el archivo del cronograma
     */
    private CargadorArchivosWeb cargadorArchivoMovimientos = new CargadorArchivosWeb();

    public CargadorArchivosWeb getCargadorArchivoMovimientos() {
        return cargadorArchivoMovimientos;
    }

    public void setCargadorArchivoMovimientos(CargadorArchivosWeb cargadorArchivoMovimientos) {
        this.cargadorArchivoMovimientos = cargadorArchivoMovimientos;
    }

    public boolean isValidacion() {
        return validacion;
    }

    public void setValidacion(boolean validacion) {
        this.validacion = validacion;
    }

    public Registrovalidador getRegistro() {
        return registro;
    }

    public void setRegistro(Registrovalidador registro) {
        this.registro = registro;
    }

    public void cargarExcelFinanciera() {

        /*
        try {
        
        InputStream inp = null;
        File docExcel = null;
        
        Iterator arch = getSubirArchivoMovimientos().getArchivosSubidos().iterator();
        
        while (arch.hasNext()) {
        docExcel = (File) arch.next();
        }
        
        inp = new FileInputStream(docExcel);
        
        if (registro.getFechaarchivo() != null) {
        
        limpiarlistas();
        extrarInfoExcelFinanciera(inp);
        } else {
        FacesUtils.addErrorMessage("El campo fecha es obligatorio");
        }
        
        
        } catch (FileNotFoundException ex) {
        FacesUtils.addErrorMessage(ex.getMessage());
        Logger.getLogger(CargarArchivoFinanciera.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        FacesUtils.addErrorMessage(ex.getMessage());
        Logger.getLogger(CargarArchivoFinanciera.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
        FacesUtils.addErrorMessage(ex.getMessage());
        Logger.getLogger(CargarArchivoFinanciera.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RequiredAttributesException ex) {
        FacesUtils.addErrorMessage(ex.getMessage());
        Logger.getLogger(CargarArchivoFinanciera.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FormaterCellException ex) {
        FacesUtils.addErrorMessage(ex.getMessage());
        Logger.getLogger(CargarArchivoFinanciera.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FinancieraException ex) {
        FacesUtils.addErrorMessage(ex.getMessage());
        Logger.getLogger(CargarArchivoFinanciera.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
    }

    public void extrarInfoExcelFinanciera(InputStream inp) throws FileNotFoundException, IOException,
            InvalidFormatException, FormaterCellException, RequiredAttributesException, FinancieraException {

        Workbook workbook = WorkbookFactory.create(inp);
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        int initRow = 3;

        workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();
        workbook.setForceFormulaRecalculation(true);

        Row rowEncabezado = (Row) sheet.getRow(2);
        int lastCol = rowEncabezado.getLastCellNum();


        boolean verif = false;
        int fila = 0;
        //Extraer la informacion del EXCEL
        while (initRow <= lastRowNum) {
            //System.out.println("fila = " + fila);
            Row row = (Row) sheet.getRow(initRow);

            int initCol = 0;
            ExcelFinanciero rowData = new ExcelFinanciero();

            if (verificarCeldasVaciasFila(row)) {
                //System.out.println("entrasss = " );
                verif = true;
                break;
            } else {

                while (initCol < lastCol) {

                    //System.out.println("initCol = " + initCol);

                    Cell cell = (Cell) row.getCell(initCol);


                    if (cell == null) {
                        break;
                    }

                    if (cell.getColumnIndex() == TOTAL_COL) {
                        break;
                    }

                    cell.toString().trim();
                    //  System.out.println("cell.toString()"+cell.toString());
                    int type = cell.getCellType();

                    String valueCell = "";

                    if (type == Cell.CELL_TYPE_FORMULA) {
                        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                        valueCell = evaluator.evaluateInCell(cell).toString().trim();
                    } else {
                        valueCell = cell.toString().trim();
                    }
                    fila = cell.getRowIndex() + 1;

                    switch (initCol) {

                        case 0:
                            try {
                                if (valueCell != null && !valueCell.equals("")) {
                                    rowData.setFecha(valueCell);
                                    //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                                }
                            } catch (Exception ex) {
                                String msg = "formato de fecha incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 1:
                            //System.out.println("cell.getCellType()"+cell.getCellType());
                            try {
                                if (cell.getCellType() == 0) {

                                    if (valueCell != null && !valueCell.equals("")) {
                                        Double ordenDouble = Double.parseDouble(valueCell);
                                        int ordenInt = (int) ordenDouble.doubleValue();
                                        rowData.setOrdenPago(new Integer(ordenInt).toString());
                                        //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + new Integer(ordenInt).toString());
                                    }
                                } else if (cell.getCellType() == 1) {
                                    rowData.setOrdenPago(valueCell);
                                }
                            } catch (Exception ex) {
                                String msg = "formato de orden de pago incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 2:
                            try {
                                if (valueCell != null && !valueCell.equals("") && !valueCell.equals("GD")) {
                                    //    System.out.println("valueCell...."+valueCell);
                                    //   System.out.println("valueCell.trim()...=="+valueCell.trim().replace(" ", ""));

                                    Double encargoDouble = Double.parseDouble(valueCell.replace(" ", ""));
                                    int encargoInt = (int) encargoDouble.doubleValue();
                                    rowData.setNumeroEncargoFidu(new Integer(encargoInt).toString().trim());
                                    //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                                }
                            } catch (NumberFormatException ex) {
                                String msg = "formato de numero de encargo fiduciario incorrecto : contiene caracteres diferente a numeros [" + colexcel[cell.getColumnIndex()] + "," + fila + "] ";
                                throw new FormaterCellException(msg, ex);
                            }

                            break;
                        case 3:
                            try {
                                if (valueCell != null && !valueCell.equals("")) {

                                    rowData.setFechaCreacionEncargoFidu(valueCell);
                                    //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                                }
                                //requerido = true;
                                //}
                            } catch (Exception ex) {
                                String msg = "formato de fecha de encargo fiduciario incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 4:
                            try {
                                if (cell.getCellType() == 0) {
                                    if (valueCell != null && !valueCell.equals("")) {
                                        Double comprobDouble = Double.parseDouble(valueCell);
                                        int comprobInt = (int) comprobDouble.doubleValue();
                                        rowData.setComprobanteFinanciero(new Integer(comprobInt).toString());
                                        //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                                    }
                                } else if (cell.getCellType() == 1) {
                                    rowData.setComprobanteFinanciero(valueCell);
                                }
                            } catch (Exception ex) {
                                String msg = "formato de comprobante incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 5:
                            try {
                                if (valueCell != null && !valueCell.equals("")) {
                                    Double nitDouble = new BigDecimal(valueCell).doubleValue();
                                    long nitInt = (long) nitDouble.doubleValue();
                                    rowData.setNitBeneficiario(new Long(nitInt).toString());
                                    //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                                }
                            } catch (Exception ex) {
                                String msg = "formato de nit beneficiario incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 6:
                            try {


                                if (valueCell != null && !valueCell.equals("")) {
                                    rowData.setTipoBeneficiario(valueCell);
                                    //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                                }

                            } catch (Exception ex) {
                                String msg = "formato de tipo beneficiario incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 7:
                            try {
                                rowData.setDepartamento(valueCell);
                                //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                            } catch (Exception ex) {
                                String msg = "formato de Departamento incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 8:
                            try {
                                rowData.setMunicipio(valueCell);
                                //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                            } catch (Exception ex) {
                                String msg = "formato de municipio incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 9:
                            try {
                                rowData.setCategoria(valueCell);
                                //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                            } catch (Exception ex) {
                                String msg = "formato categoria incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 10:
                            try {
                                rowData.setProyecto(valueCell);
                                //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                            } catch (Exception ex) {
                                String msg = "formato proyecto incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 11:
                            try {
                                if (valueCell != null && !valueCell.equals("")) {
                                    Double nitDouble = new BigDecimal(valueCell).doubleValue();
                                    long nitInt = (long) nitDouble.doubleValue();
                                    rowData.setNitContratista(new Long(nitInt).toString());
                                    //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                                }

                            } catch (Exception ex) {
                                String msg = "formato nit contratista incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }

                            break;
                        case 12:
                            try {
                                rowData.setNombreContratista(valueCell);
                                //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);

                            } catch (Exception ex) {
                                String msg = "formato nombre contratista incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 13:

                            try {

                                if (valueCell != null && !valueCell.equals("")) {

                                    rowData.setFechaInitRecursos(valueCell);
                                    //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                                }
                            } catch (Exception ex) {
                                String msg = "formato fecha inicio de recursos incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 14:

                            try {
                                if (valueCell != null && !valueCell.equals("")) {
                                    rowData.setValorMovimiento(valueCell);
                                    //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                                }
                            } catch (Exception ex) {
                                String msg = "formato valor movimiento incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 15:
                            try {

                                if (valueCell != null && !valueCell.equals("")) {
                                    rowData.setValorEjecutado(valueCell);
                                    //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                                }
                            } catch (Exception ex) {
                                String msg = "formato valor ejecutado incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 16:
                            try {
                                if (valueCell != null && !valueCell.equals("")) {
                                    rowData.setValorLegalizado(valueCell);
                                    // System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                                }
                            } catch (Exception ex) {
                                String msg = "formato valor legalizado incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 17:
                            try {
                                rowData.setFechaLegalizacion(valueCell);
                                //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                            } catch (Exception ex) {
                                String msg = "formato fecha legalizacion incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                        case 18:

                            //System.out.println("valueCell tipo = " + valueCell);
                            try {
                                if (valueCell != null && !valueCell.equals("")) {
                                    rowData.setTipoMovimiento(valueCell);
                                    //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                                }
                            } catch (Exception ex) {
                                String msg = "formato tipo movimiento incorrecto [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;

                        case 19:
                            try {
                                if (valueCell != null && !valueCell.equals("")) {
                                    rowData.setNumvlrreintegro(valueCell);
                                    //System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] " + valueCell);
                                }
                            } catch (Exception ex) {
                                String msg = "formato reintegro [" + colexcel[cell.getColumnIndex()] + "," + fila + "]";
                                throw new FormaterCellException(msg, ex);
                            }
                            break;
                    }


                    rowData.setFila(initRow + 1);

                    initCol += 1;
                }
            }

            //Agregar cada una de las filas del Excel en una lista de tipo ExcelFinanciero
            registrosExcel.add(rowData);

            initRow++;
        }

        try {
            //Crear o actualizar los movimientos de acuerdo a la información
            //extraida del excel
            boolean error = validarEncargos();

            if (!error) {

                for (ExcelFinanciero rowData : registrosExcel) {
                    crearMovimiento(rowData);
                }
            } else {


                if (!ordenes.isEmpty()) {

                    Iterator iter = ordenes.iterator();
                    String msg = "Las siguientes ordenes de pago no ha sido creada : ";

                    while (iter.hasNext()) {
                        msg += iter.next() + ", ";
                    }


                    throw new FinancieraException(msg);
                }

                if (!encargos.isEmpty()) {

                    ListIterator iter1 = encargos.listIterator();
                    String msg1 = "";

                    while (iter1.hasNext()) {

                        Encargos tempen = (Encargos) iter1.next();
                        msg1 += "El encargo fiduciario " + tempen.getEncargos() + " "
                                + " de la orden de pago " + tempen.getOrden() + " no corresponde al número del excel " + tempen.getEncargos();

                    }


                    throw new FinancieraException(msg1);
                }





            }




        } catch (FormaterCellException ex) {
            throw ex;
        }

        if (!verif) {
            //Creacion o actualizacion de los movimientos



            getSessionBeanCobra().getFinancieraService().guardarMovimientos(movimientos);
            //Guardar registro

            registro.setFechacargue(new Date());
            registro.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
            registro.setNumregistro(lastRowNum);
            if (registro.getObservacion() == null) {
                registro.setObservacion("Sin descripcion");
            }
            getSessionBeanCobra().getFinancieraService().guardarRegistrovalidador(registro);
            //Actualización de las ordenes de Pago
            //getSessionBeanCobra().getFinancieraService().guardarOrdenesPago(ordenPagos);
            FacesUtils.addInfoMessage("El archivo ha sido cargado con exito");

        }



    }

    private boolean validarEncargos() throws FormaterCellException, FinancieraException {

        boolean error = false;
        Ordendepago ordenPago = null;
        for (ExcelFinanciero rowData : registrosExcel) {

            ordenPago = getSessionBeanCobra().getFinancieraService().encontrarOrdenPagoPorCodigo(rowData.getOrdenPago());

            if (ordenPago == null) {
                //System.out.println("ordenPago = " + rowData.getOrdenPago());
                ordenes.add(rowData.getOrdenPago());
                ordenPago = null;
                error = true;
            } else {
                if (rowData.getNumeroEncargoFidu() != null) {
                    Integer numeroEncargoFiduciario = Integer.parseInt(rowData.getNumeroEncargoFidu());
                    //System.out.println("ordenPago = " + ordenPago.getStrordenpago());

                    if (ordenPago.getEncargofiduciario() != null) {
                        if (ordenPago.getEncargofiduciario().getIntnumencargofiduciario() != numeroEncargoFiduciario) {
                            Encargos tempen = new Encargos(rowData.getOrdenPago(), rowData.getNumeroEncargoFidu());
                            encargos.add(tempen);
                            error = true;
                        }
                    } else {

                        Encargofiduciario encargo = null;
                        encargo = getSessionBeanCobra().getFinancieraService().encontrarEncargofiduciario(rowData.getNumeroEncargoFidu());

                        if (encargo == null) {
                            Encargos tempen = new Encargos(rowData.getOrdenPago(), rowData.getNumeroEncargoFidu());
                            encargos.add(tempen);
                            error = true;
                        }
                    }

                }

            }
        }

        return error;


    }

    private void crearMovimiento(ExcelFinanciero rowData) throws FormaterCellException, FinancieraException {
        //System.out.println("rowData = " + rowData.getComprobanteFinanciero());
        Movimiento movimiento = getSessionBeanCobra().getFinancieraService().encontrarMovimientoPorComprobante(rowData.getComprobanteFinanciero(), String.valueOf(rowData.getFila()));

        /*  if(movimiento!=null){
        System.out.println("mov"+movimiento.getStrcomprobantefidu());
        }*/
        //System.out.println("moxx");

        BigDecimal valorEjecutado = null;

        try {
            if (rowData.getValorEjecutado() != null) {
                Double valorEjectDouble = Double.valueOf(rowData.getValorEjecutado());
                valorEjecutado = new BigDecimal(valorEjectDouble);
            } else {
                valorEjecutado = BigDecimal.ZERO;
            }

        } catch (Exception ex) {
            String msg = "Error formateando el valor ejecutado [ P," + rowData.getFila() + " ]";
            throw new FormaterCellException(msg, ex);
        }

        BigDecimal valorLegalizado = null;

        try {
            if (rowData.getValorLegalizado() != null) {

                Double valorLegalDouble = Double.valueOf(rowData.getValorLegalizado());
                valorLegalizado = new BigDecimal(valorLegalDouble);
            } else {
                valorLegalizado = BigDecimal.ZERO;
            }
        } catch (Exception ex) {
            String msg = "Error formateando el valor legalizado [Q," + rowData.getFila() + "]";
            throw new FormaterCellException(msg, ex);
        }

        BigDecimal valorreintegro = null;

        try {
            if (rowData.getNumvlrreintegro() != null) {

                Double valorreint = Double.valueOf(rowData.getNumvlrreintegro());
                valorreintegro = new BigDecimal(valorreint);
            } else {
                valorreintegro = BigDecimal.ZERO;
            }
        } catch (Exception ex) {
            String msg = "Error formateando el valor reintegro [T," + rowData.getFila() + "]";
            throw new FormaterCellException(msg, ex);
        }

        Date fechaLegalizacion = null;

        try {
            //  System.out.println(" f l="+rowData.getFechaLegalizacion());
            if (rowData.getFechaLegalizacion() != null && !rowData.getFechaLegalizacion().equals("")) {
//                System.out.println("di null");
                DateFormat dateFormat = new SimpleDateFormat("d-MMM-yyyy");
                fechaLegalizacion = dateFormat.parse(rowData.getFechaLegalizacion());
//                System.out.println("fechaLegalizacion"+fechaLegalizacion);
            }

        } catch (Exception ex) {
            String msg = "Error formateando el campo fecha Legalizacion recursos [R," + rowData.getFila() + "]";
            throw new FormaterCellException(msg, ex);
        }

        //Valida que el movimiento exista para realizar una actualización del valor de ejecución
        //o legalizacion
        if (movimiento != null) {
//          

            //Valida que el valor legalizado del excel sea mayor a cero y mayor al valor legalizado del movimiento
            if (valorLegalizado.compareTo(BigDecimal.ZERO) > 0 && valorLegalizado.compareTo(movimiento.getNumvlrlegalizado()) > 0) {
                movimiento.setNumvlrlegalizado(valorLegalizado);

                if (fechaLegalizacion != null) {
                    movimiento.setDatefechalegalizacion(fechaLegalizacion);
                }
            }

            //Valida que el valor reintegro del excel sea mayor a cero y mayor al valor legalizado del movimiento
            if (valorreintegro.compareTo(BigDecimal.ZERO) > 0 && valorreintegro.compareTo(movimiento.getNumvlrreintegro()) > 0) {

                movimiento.setNumvlrreintegro(valorreintegro);
            }


            movimiento.setJsfUsuarioByIntusumodifica(getSessionBeanCobra().getUsuarioObra());

        } else {

            movimiento = new Movimiento();

            try {
                Date fechaMovimiento = null;
                DateFormat dateFormat = new SimpleDateFormat("d-MMM-yyyy");
                fechaMovimiento = dateFormat.parse(rowData.getFecha());
                movimiento.setDatefechamovimientoentidad(fechaMovimiento);

            } catch (Exception ex) {
                String msg = "Error formateando el campo fecha del movimiento [A," + rowData.getFila() + "]";
                throw new FormaterCellException(msg, ex);
            }

            try {

                if (rowData.getFechaInitRecursos() != null) {
                    Date fechaInitRecursos = null;
                    DateFormat dateFormat = new SimpleDateFormat("d-MMM-yyyy");
                    fechaInitRecursos = dateFormat.parse(rowData.getFechaInitRecursos());
                    movimiento.setDatefechainirecursos(fechaInitRecursos);
                }
            } catch (Exception ex) {
                String msg = "Error formateando el campo fecha inicio recursos [N," + rowData.getFila() + "]";
                throw new FormaterCellException(msg, ex);
            }

            try {
                if (rowData.getFechaLegalizacion() != null) {

                    movimiento.setDatefechalegalizacion(fechaLegalizacion);
                }

            } catch (Exception ex) {
                String msg = "Error formateando el campo fecha Legalizacion recursos [R," + rowData.getFila() + "]";
                throw new FormaterCellException(msg, ex);
            }

            try {

                if (rowData.getValorMovimiento() != null) {
                    Double valorMovimiento = Double.valueOf(rowData.getValorMovimiento());

                    if (valorMovimiento >= 0) {
                        movimiento.setNumvlrmovimiento(new BigDecimal(valorMovimiento));
                    } else {
                        String msg = "El valor del movimiento no puede estar en cero fila: " + rowData.getFila();
                        throw new FinancieraException(msg);
                    }
                }
            } catch (Exception ex) {
                String msg = "Error formateando el valor del movimiento [O," + rowData.getFila() + "]";

                throw new FormaterCellException(msg, ex);
            }

            movimiento.setNumvlrejecutado(valorEjecutado);
            movimiento.setNumvlrlegalizado(valorLegalizado);
            movimiento.setNumvlrreintegro(valorreintegro);

            if (rowData.getTipoMovimiento() != null) {

                movimiento.setTipomovimiento(getTipoMovimiento(rowData.getTipoMovimiento()));
                if (movimiento.getTipomovimiento() != null) {
                    //System.out.println("movimiento tipo = " +  movimiento.getTipomovimiento().getOidtipomovimiento()+"fila movimiento"+rowData.getFila() );
                } else {
                    String msg = "Formato tipo de giro incorrecto : [S," + rowData.getFila() + "]";
                    throw new FinancieraException(msg);
                }

            } else {
                String msg = "Ingresar tipo de giro : [S," + rowData.getFila() + "]";
                throw new FinancieraException(msg);
            }


            Ordendepago ordenPago = getSessionBeanCobra().getFinancieraService().encontrarOrdenPagoPorCodigo(rowData.getOrdenPago());
            if (ordenPago == null) {
                String msg = "La orden " + rowData.getOrdenPago() + " de pago no ha sido creada";
                throw new FinancieraException(msg);

            }


            movimiento.setOrdendepago(ordenPago);


            //if (ordenPago.getEncargofiduciario() != null) {

            Integer numeroEncargoFiduciario = Integer.parseInt(rowData.getNumeroEncargoFidu());

            //Valida que el numero de encargo fiduciario  del excel no sea diferente al encargo fidu
            //de la orden de pago que se encuentra asociad al movimiento que se va a crear
            if (numeroEncargoFiduciario != null && ordenPago.getEncargofiduciario() != null) {
                if (ordenPago.getEncargofiduciario().getIntnumencargofiduciario() != numeroEncargoFiduciario) {
                    String msg = "El encargo fiduciario " + ordenPago.getEncargofiduciario().getIntnumencargofiduciario() + " "
                            + " de la orden de pago " + ordenPago.getStrordenpago() + " no corresponde al número del excel " + rowData.getOrdenPago();
                    throw new FinancieraException(msg);
                }
            }/* else {
            
            //Valida que el valor actual del encargo fiduciario sea igual a 0
            //para asignarle el valor del movimiento
            if (ordenPago.getEncargofiduciario().getNumsaldoactual().compareTo(BigDecimal.ZERO) == 0) {
            ordenPago.getEncargofiduciario().setNumsaldoactual(movimiento.getNumvlrmovimiento());
            
            } else {
            
            
            if (movimiento.getTipomovimiento().getOidtipomovimiento() == GIRO
            && movimiento.getTipomovimiento().getOidtipomovimiento() == ANTICIPO) {
            
            if (ordenPago.getEncargofiduciario().getNumsaldoactual().compareTo(movimiento.getNumvlrmovimiento()) >= 0) {
            BigDecimal nuevoValorEncargo = ordenPago.getEncargofiduciario().getNumsaldoactual().subtract(movimiento.getNumvlrmovimiento());
            ordenPago.getEncargofiduciario().setNumsaldoactual(nuevoValorEncargo);
            ordenPagos.add(ordenPago);
            
            } else {
            String msg = "El valor actual del encargo fiduciario es menor al valor del movimiento";
            throw new FinancieraException(msg);
            }
            
            //Valida que el tipo de movimiento sea un DEVOLUCION
            //para adicionarl al valor actual del encargo fiduciario
            //el valor del movimiento del excel
            } else if (movimiento.getTipomovimiento().getOidtipomovimiento() == DEVOLUCION) {
            
            BigDecimal nuevoValorEncargo = ordenPago.getEncargofiduciario().getNumsaldoactual().add(movimiento.getNumvlrmovimiento());
            ordenPago.getEncargofiduciario().setNumsaldoactual(nuevoValorEncargo);
            ordenPagos.add(ordenPago);
            }
            }
            }
            } else {
            
            Encargofiduciario encargo=null;
            encargo=getSessionBeanCobra().getFinancieraService().encontrarEncargofiduciario(rowData.getNumeroEncargoFidu());
            
            if(encargo!=null){
            
            System.out.println("se asocio encargo fiduciario "+rowData.getNumeroEncargoFidu());
            
            ordenPago.setEncargofiduciario(encargo);
            
            
            //Valida que el valor actual del encargo fiduciario sea igual a 0
            //para asignarle el valor del movimiento
            if (ordenPago.getEncargofiduciario().getNumsaldoactual().compareTo(BigDecimal.ZERO) == 0) {
            ordenPago.getEncargofiduciario().setNumsaldoactual(movimiento.getNumvlrmovimiento());
            
            } else {
            
            //Valida que el tipo de movimiento sea un GIRO o ANTICIPO
            //para restarle al valor actual del encargo fiduciario
            //el valor del movimiento del excel
            try {
            System.out.println("movimiento.getTipomovimiento().getOidtipomovimiento()"+movimiento.getTipomovimiento().getOidtipomovimiento());
            } catch (Exception e) {
            }
            
            
            if (movimiento.getTipomovimiento().getOidtipomovimiento() == GIRO
            && movimiento.getTipomovimiento().getOidtipomovimiento() == ANTICIPO) {
            
            if (ordenPago.getEncargofiduciario().getNumsaldoactual().compareTo(movimiento.getNumvlrmovimiento()) >= 0) {
            BigDecimal nuevoValorEncargo = ordenPago.getEncargofiduciario().getNumsaldoactual().subtract(movimiento.getNumvlrmovimiento());
            ordenPago.getEncargofiduciario().setNumsaldoactual(nuevoValorEncargo);
            ordenPagos.add(ordenPago);
            
            } else {
            String msg = "El valor actual del encargo fiduciario es menor al valor del movimiento";
            throw new FinancieraException(msg);
            }
            
            //Valida que el tipo de movimiento sea un DEVOLUCION
            //para adicionarl al valor actual del encargo fiduciario
            //el valor del movimiento del excel
            } else if (movimiento.getTipomovimiento().getOidtipomovimiento() == DEVOLUCION) {
            
            BigDecimal nuevoValorEncargo = ordenPago.getEncargofiduciario().getNumsaldoactual().add(movimiento.getNumvlrmovimiento());
            ordenPago.getEncargofiduciario().setNumsaldoactual(nuevoValorEncargo);
            ordenPagos.add(ordenPago);
            }
            }
            
            
            }
            else{
            String msg = "El encargo fiduciario no se encuentra creado  ";
            throw new FinancieraException(msg,rowData.getNumeroEncargoFidu());
            }
            
            
            }*/

            movimiento.setStrcomprobantefidu(rowData.getComprobanteFinanciero());

            /*Tercero beneficiario = getSessionBeanCobra().getCobraService().encontrarTerceroPorNit(rowData.getNitBeneficiario());
            
            if (beneficiario == null) {
            String msg = "El beneficiario con nit " + rowData.getNitBeneficiario() + " no se encuentra creado en el sistema";
            throw new FinancieraException(msg);
            }*/

            //   Contratista contratista = getSessionBeanCobra().getCobraService().encontrarContratistaPorNit(rowData.getNitContratista());

//            if (contratista == null) {
//                String msg = "El contratista con nit" + rowData.getNitContratista() + "no se encuentra creado en el sistema";
//                throw new FinancieraException(msg);
//            }

            movimiento.setStrnombrecontratista(rowData.getNombreContratista());
            //     movimiento.setContratista(contratista);
            movimiento.setStrdescripcionmovimiento("Autogenerado");
            movimiento.setJsfUsuarioByIntusucreacion(getSessionBeanCobra().getUsuarioObra());

            Estadomovimiento estadoMovimiento = new Estadomovimiento();
            estadoMovimiento.setIntestadomovimiento(ESTADO_APLICADO);
            movimiento.setEstadomovimiento(estadoMovimiento);

            movimiento.setStrconsecutivo_excel(String.valueOf(rowData.getFila()));

            movimientos.add(movimiento);
        }


    }

    private Tipomovimiento getTipoMovimiento(String nombre) {

        if (nombre.equals("Giro") || nombre.equals("GIRO")) {
            return new Tipomovimiento(GIRO);
        }

        if (nombre.equals("Devolucion") || nombre.equals("DEVOLUCIÓN")) {
            return new Tipomovimiento(DEVOLUCION);
        }

        if (nombre.equals("Anticipo") || nombre.equals("ANTICIPO")) {
            return new Tipomovimiento(ANTICIPO);
        }

        return null;
    }

    private boolean verificarCeldasVaciasFila(Row row) {
        int initCol = 0;
        int cellsEmpty = 0;
        int fila = row.getRowNum() + 1;
        boolean requerido = false;
        String msg = "Tiene los siguientes Campos Vacios";

        while (initCol < TOTAL_COL) {
            Cell cell = (Cell) row.getCell(initCol);

            if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                cellsEmpty = cellsEmpty + 1;

                if (initCol == 0 || initCol == 1 || initCol == 2 || initCol == 4 || initCol == 18) {
                    requerido = true;
                    msg += " " + "[" + colexcel[initCol] + "," + fila + "]";

                }
            }

            initCol++;
        }

        if (requerido) {
            FacesUtils.addErrorMessage(msg);
            return true;
        }
        if (cellsEmpty == TOTAL_COL) {
            FacesUtils.addErrorMessage("Tiene filas sin diligenciar " + fila);
            return true;
        }

        return false;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public List<Ordendepago> getOrdenPagos() {
        return ordenPagos;
    }

    public void setOrdenPagos(List<Ordendepago> ordenPagos) {
        this.ordenPagos = ordenPagos;
    }

//    public SubirArchivoBean getSubirArchivoMovimientos() {
//        return subirArchivoMovimientos;
//    }
//
//    public void setSubirArchivoMovimientos(SubirArchivoBean subirArchivoMovimientos) {
//        this.subirArchivoMovimientos = subirArchivoMovimientos;
//    }
    private void limpiarlistas() {
        movimientos = new ArrayList<Movimiento>();
        encargos = new LinkedList<Encargos>();
        ordenPagos = new ArrayList<Ordendepago>();
        registrosExcel = new ArrayList<ExcelFinanciero>();
    }

    //para guardar los encargos y las ordenes con error
    class Encargos {

        String orden;
        String encargos;

        public Encargos(String orden, String encargos) {
            this.orden = orden;
            this.encargos = encargos;
        }

        public String getEncargos() {
            return encargos;
        }

        public void setEncargos(String encargos) {
            this.encargos = encargos;
        }

        public String getOrden() {
            return orden;
        }

        public void setOrden(String orden) {
            this.orden = orden;
        }
    }

    public void cargarExcelFinancieraPl() {
        try {

            cargadorArchivoMovimientos.guardarArchivosTemporales(RutasWebArchivos.FINANCIERA, true);
            ServletContext cont=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            
            String mensaje=getSessionBeanCobra().getFinancieraService().funcion_importar_movimientos_financieros(
                    
                    cont.getRealPath(RutasWebArchivos.FINANCIERA)+"/"
                    + cargadorArchivoMovimientos.getArchivos().get(0).getNombre(), "2013-03-15");
            
            if(mensaje.compareTo("")!=0)
            {
                FacesUtils.addErrorMessage(mensaje);
            }
            else
            {
                FacesUtils.addInfoMessage("Se ha validado correctamente el archivo.");
            }    
              
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(CargarArchivoFinanciera.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
