/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.to.Historicoobra;
import co.com.interkont.cobra.to.Obra;
import java.io.Serializable;

/**
 *
 * @author jhon
 */
public class GestorCronogramaXLS implements Serializable{
    private Obra obraAnterior;
    private Historicoobra historicoobra;
    private Obra obraModificada;
    private String mensajeError;
    private final int NUM_FILA_VALOR_TOTAL_PERIODO = 5;
    private final int NUM_FILA_ENCABEZADO_ACTIVIDAD = 8;
    private final int NUM_FILA_PRIMERA_ACTIVIDAD = 9;
    private final int NUM_ULTIMA_FILA = 1500;
    private final int NUM_COL_ACTIVIDAD = 2;
    private final int NUM_COL_UNIDAD = 3;
    private final int NUM_COL_CANTIDAD = 4;
    private final int NUM_COL_EJECUCION = 5;
    private final int NUM_COL_DIFERENCIA = 6;
    private final int NUM_COL_VALOR_UNI = 7;
    private final int NUM_COL_VALOR_FINAL = 8;
    private final int NUM_COL_FALTANTE = 9;
    private final int NUM_COL_Q_PRIMER_PERIODO = 10;
    private final int NUM_MAX_PERIODOS = 100;
    private final String COL_ACTIVIDAD = "C";
    private final String COL_UNIDDAS = "D";
    private final String COL_CANTIDAD = "E";
    private final String COL_EJECUCION = "F";
    private final String COL_DIFERENCIA = "G";
    private final String COL_VALOR_UNI = "H";
    private final String COL_VALOR_FINAL = "I";
    private final String COL_FALTANTE = "J";
    private boolean cambioPrecio = false;

    public Obra getObraModificada() {
        return obraModificada;
    }

    public void setObraModificada(Obra obraModificada) {
        this.obraModificada = obraModificada;
    }

    public boolean isCambioPrecio() {
        return cambioPrecio;
    }

    public void setCambioPrecio(boolean cambioPrecio) {
        this.cambioPrecio = cambioPrecio;
    }

    public GestorCronogramaXLS(Obra obra, Historicoobra historicoobra) {
        this.obraAnterior = obra;
        this.obraModificada = new Obra();
        this.historicoobra = historicoobra;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public Historicoobra getHistoricoobra() {
        return historicoobra;
    }

    public void setHistoricoobra(Historicoobra historicoobra) {
        this.historicoobra = historicoobra;
    }

    public Obra getObraAnterior() {
        return obraAnterior;
    }

    public void setObraAnterior(Obra obraAnterior) {
        this.obraAnterior = obraAnterior;
    }

    public boolean validarActividad(String desc, String unidad, Double cantidad, Double valoruni, int fila) {
        if (desc.compareTo("") == 0 || unidad.compareTo("") == 0) {
            mensajeError = "Debe llenar los datos de la actividad en la fila " + fila;
            return false;
        }
        if (desc.length() > 255) {
            mensajeError = "La actividad en la fila " + fila + " posee más de 255 caracteres";
            return false;
        }
        if (unidad.length() > 10) {
            mensajeError = "La unidad de medida en la fila " + fila + " posee más de 10 caracteres";
            return false;
        }
        if (cantidad <= 0 || valoruni <= 0) {
            mensajeError = "Los valores en la fila " + fila + " no pueden ser menores o iguales a cero";
            return false;
        }
        return true;
    }

    public boolean validarActividadModificacion(String desc, String unidad, Double cantidad, Double valoruni, int fila) {
        if (desc.compareTo("") == 0 || unidad.compareTo("") == 0) {
            mensajeError = "Debe llenar los datos de la actividad en la fila " + fila;
            return false;
        }
        if (desc.length() > 255) {
            mensajeError = "La actividad en la fila " + fila + " posee más de 255 caracteres";
            return false;
        }
        if (unidad.length() > 10) {
            mensajeError = "La unidad de medida en la fila " + fila + " posee más de 10 caracteres";
            return false;
        }
        if (cantidad < 0 || valoruni < 0) {
            mensajeError = "Los valores en la fila " + fila + " no pueden ser menores que cero";
            return false;
        }
        return true;
    }
/*

    private boolean validarActividadEnCronogramaAnterior(Actividadobra actividadobra, int fila) {
        boolean valido = true;
        Iterator itrActividades = obraAnterior.getActividadobras().iterator();
        boolean descActividadobraExiste = false;
        boolean unidadesIguales = false;
        boolean cantidadesIguales = false;
        boolean valoresUnitariosIguales = false;
        while (itrActividades.hasNext()){
            Actividadobra actividadobraAnterior = (Actividadobra) itrActividades.next();
            if (actividadobraAnterior.getStrdescactividad().equals(actividadobra.getStrdescactividad())){
                descActividadobraExiste = true;
                if (actividadobraAnterior.getStrtipounidadmed().equals(actividadobra.getStrtipounidadmed())){
                    unidadesIguales = true;
                }

                if (actividadobraAnterior.getNumvalorplanifao().doubleValue() == actividadobra.getNumvalorplanifao().doubleValue()){
                    valoresUnitariosIguales = true;
                } else {
                    cambioPrecio = true;
                }

            }
        }
        if(!descActividadobraExiste){
            valido = false;
            mensajeError = "La descripción de la atividad del cronograma anterior en la fila "+ (fila + 1) + "ha sido modificada";
        }
        if(!unidadesIguales){
            valido = false;
            mensajeError = "La unidad de la actividad del cronograma anterior en la fila "+ (fila + 1) + " ha sido modificada";
        }
        if(!valoresUnitariosIguales && !historicoobra.cambioPrecio()){
            valido = false;
            mensajeError = "El valor unitario de la actividad del cronograma anterior en la fila "+ (fila + 1) + " ha sido modificada";
        }
        return valido;
    }

    public void generarCronogramaModificacion(String ArchivoPath, String realArchivoPath) throws FileNotFoundException, IOException, Exception {
        Date fechaInicialNoAlimentada = null;
        Date fechaUltimaAlimentacion = obraAnterior.getFechaUltimaActaParcial();
        Calendar calendarFechaUltimaAlimentacion = Calendar.getInstance();

        


        if(fechaUltimaAlimentacion == null){
            fechaInicialNoAlimentada = obraAnterior.getDatefeciniobra();
        }
        else{
            calendarFechaUltimaAlimentacion.setTime(fechaUltimaAlimentacion);
            calendarFechaUltimaAlimentacion.add(Calendar.DATE, 1);
            fechaInicialNoAlimentada = calendarFechaUltimaAlimentacion.getTime();
        }

        int plazoObra = (int) ((historicoobra.getDatefecfinhist().getTime() - obraAnterior.getDatefeciniobra().getTime()) / (1000 * 60 * 60 * 24) + 1);
        int plazoRestanteObra = (int) ((historicoobra.getDatefecfinhist().getTime() - fechaInicialNoAlimentada.getTime()) / (1000 * 60 * 60 * 24) + 1);

        File archivo = new File(ArchivoPath);
        if (archivo.exists()) {
            InputStream inp = new FileInputStream(archivo);

            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            Row row = sheet.getRow(2);
            Cell cell = row.getCell(2);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(obraAnterior.getStrnombreobra());

            cell = row.getCell(8);
            cell.setCellValue(historicoobra.getNumvalorhist().doubleValue());

            row = sheet.getRow(3);
            cell = row.getCell(2);
            cell.setCellValue(plazoObra);

            row = sheet.getRow(4);
            cell = row.getCell(2);
            cell.setCellValue(obraAnterior.getDatefeciniobra());

            row = sheet.getRow(5);
            cell = row.getCell(2);
            cell.setCellValue(historicoobra.getDatefecfinhist());

            row = sheet.getRow(NUM_FILA_ENCABEZADO_ACTIVIDAD);
            //Generando Periodos de Medida
            int indiceperiodo = obraAnterior.getPeriodomedida().getIntidperiomedida();
            int numPeriodosRestantes = 0;
            int numPeriodos = 0;
            String perio = "";
            switch (indiceperiodo) {
                case 1:
                    perio = "SEMANA ";
                    numPeriodosRestantes = plazoRestanteObra / 7;
                    numPeriodos = plazoObra / 7;
                    if (plazoRestanteObra % 7 != 0) {
                        numPeriodosRestantes = numPeriodosRestantes + 1;
                        numPeriodos = numPeriodos + 1;
                    }
                    break;
                case 2:
                    perio = "QUINCENA ";
                    numPeriodosRestantes = plazoRestanteObra / 14;
                    numPeriodos = plazoObra / 14;
                    if (plazoRestanteObra % 14 != 0) {
                        numPeriodosRestantes = numPeriodosRestantes + 1;
                        numPeriodos = numPeriodos + 1;
                    }
                    break;
                case 3:
                    perio = "MES ";
                    numPeriodosRestantes = plazoRestanteObra / 30;
                    numPeriodos = plazoObra / 30;
                    if (plazoRestanteObra % 30 != 0) {
                        numPeriodosRestantes = numPeriodosRestantes + 1;
                        numPeriodos = numPeriodos + 1;
                    }
                    break;
            }
            int periodoActual = 1;
            int qActual = NUM_COL_Q_PRIMER_PERIODO;
            while (periodoActual <= numPeriodosRestantes) {
                cell = row.createCell(qActual);
                cell.setCellValue("Q");
                cell = row.createCell(qActual + 1);
                cell.setCellValue("VALOR");
                periodoActual++;
                qActual = qActual + 2;
                sheet.autoSizeColumn(qActual);
                sheet.autoSizeColumn(qActual + 1);
            }
            cell = row.createCell(qActual);
            cell.setCellValue("VALOR FALTANTE");
            sheet.autoSizeColumn(qActual);
            qActual = NUM_COL_Q_PRIMER_PERIODO;
            periodoActual = 1;
            row = sheet.getRow(7);
            cell = row.getCell(9);
            if (cell == (null)) {
                cell = row.createCell(9);
            }
            cell.setCellValue("FALTANTE");
            while (periodoActual <= numPeriodosRestantes) {

                cell = row.createCell(qActual);
                cell.setCellValue(perio + " " + (periodoActual + numPeriodos - numPeriodosRestantes));
                sheet.addMergedRegion(new CellRangeAddress(7, 7, qActual, qActual + 1));
                periodoActual++;
                qActual = qActual + 2;
            }
            row = sheet.getRow(5);
            int numUltimaFilaReal = 65535;
            qActual = NUM_COL_Q_PRIMER_PERIODO;
            periodoActual = 1;
            String formula = "";
            while (periodoActual <= numPeriodosRestantes) {

                cell = row.createCell(qActual + 1);
                CellReference celdaini = new CellReference(NUM_FILA_PRIMERA_ACTIVIDAD, qActual + 1);
                CellReference celdafin = new CellReference(numUltimaFilaReal - 1, qActual + 1);
                formula = "SUM(" + celdaini.formatAsString() + ":" + celdafin.formatAsString() + ")";
                cell.setCellType(Cell.CELL_TYPE_FORMULA);
                cell.setCellFormula(formula);
                sheet.setColumnWidth(qActual + 1, 4000);
                periodoActual++;
                qActual = qActual + 2;

            }
            int actividadActual = NUM_FILA_PRIMERA_ACTIVIDAD;
            Iterator itrActividadesObra = obraAnterior.getActividadobras().iterator();
            while (itrActividadesObra.hasNext()) {

                Actividadobra actividadobra = (Actividadobra) itrActividadesObra.next();
                row = sheet.getRow(actividadActual);

                cell = row.getCell(2);
                cell.setCellValue(actividadobra.getStrdescactividad());

                cell = row.getCell(3);
                cell.setCellValue(actividadobra.getStrtipounidadmed());

                cell = row.getCell(4);
                cell.setCellValue(actividadobra.getFloatcantplanifao());

                cell = row.getCell(5);
                cell.setCellValue(actividadobra.getFloatcantidadejecutao().doubleValue());

                cell = row.getCell(7);
                cell.setCellValue(actividadobra.getNumvalorplanifao().doubleValue());

                qActual = NUM_COL_Q_PRIMER_PERIODO;
                periodoActual = 1;
                formula = "";
                String formulafalt = "(";
                Calendar fechaIniPeriodo = Calendar.getInstance();
                fechaIniPeriodo.setTime(fechaInicialNoAlimentada);
                while (periodoActual <= numPeriodosRestantes) {
                    Relacionactividadobraperiodo relActividadPeriodo = actividadobra.obtenerRelacionactividadobraperiodo(fechaIniPeriodo.getTime());
                    if (relActividadPeriodo != null){
                        cell = row.createCell(qActual);
                        cell.setCellValue(relActividadPeriodo.getFloatcantplanif());
                    }
                    fechaIniPeriodo.add(Calendar.DATE, +(obraAnterior.getPeriodomedida().getIntnrodiasperiomedida()));
                    cell = row.createCell(qActual + 1);
                    CellReference celda = new CellReference(actividadActual, qActual);
                    formula = COL_VALOR_UNI + (actividadActual + 1) + "*" + celda.formatAsString();
                    formulafalt = formulafalt + celda.formatAsString() + "+";
                    cell.setCellType(Cell.CELL_TYPE_FORMULA);
                    cell.setCellFormula(formula);
                    sheet.setColumnWidth(qActual + 1, 5000);
                    periodoActual++;
                    qActual = qActual + 2;
                }
                cell = row.createCell(9);
                cell.setCellType(Cell.CELL_TYPE_FORMULA);
                cell.setCellFormula(formulafalt + "(" + COL_DIFERENCIA + (actividadActual + 1) + "*-1))*-1");

                CellReference celdafalt = new CellReference(actividadActual, 9);
                cell = row.createCell(qActual);
                cell.setCellType(Cell.CELL_TYPE_FORMULA);
                cell.setCellFormula(COL_VALOR_UNI + (actividadActual + 1) + "*" + celdafalt.formatAsString());
                actividadActual++;
            }
            row = sheet.getRow(3);
            cell = row.getCell(8);
            CellReference celdaValFaltIni = new CellReference(NUM_FILA_PRIMERA_ACTIVIDAD, qActual);
            CellReference celdaValFaltFin = new CellReference(NUM_ULTIMA_FILA, qActual);
            CellReference celdaValFinalIni = new CellReference(NUM_FILA_PRIMERA_ACTIVIDAD, NUM_COL_VALOR_FINAL);
            CellReference celdaValFinalFin = new CellReference(NUM_ULTIMA_FILA, NUM_COL_VALOR_FINAL);
            cell.setCellType(Cell.CELL_TYPE_FORMULA);
            cell.setCellFormula("I3-SUM("+celdaValFinalIni.formatAsString()+":"+celdaValFinalFin.formatAsString()+")");
            int ultimaFila = actividadActual;
            if (historicoobra.adicionoActividades()) {
                ultimaFila = ultimaFila + 1482;
            }
            while (actividadActual < ultimaFila) {
                row = sheet.getRow(actividadActual);
                qActual = NUM_COL_Q_PRIMER_PERIODO;
                periodoActual = 1;
                formula = "";
                String formulafalt = "(";
                while (periodoActual <= numPeriodosRestantes) {

                    cell = row.createCell(qActual + 1);
                    CellReference celda = new CellReference(actividadActual, qActual);
                    formula = COL_VALOR_UNI + (actividadActual + 1) + "*" + celda.formatAsString();
                    formulafalt = formulafalt + celda.formatAsString() + "+";
                    cell.setCellType(Cell.CELL_TYPE_FORMULA);
                    cell.setCellFormula(formula);
                    sheet.setColumnWidth(qActual + 1, 5000);
                    periodoActual++;
                    qActual = qActual + 2;
                }
                cell = row.createCell(NUM_COL_FALTANTE);
                cell.setCellType(Cell.CELL_TYPE_FORMULA);
                cell.setCellFormula(formulafalt + "(" + COL_DIFERENCIA + (actividadActual + 1) + "*-1))*-1");

                CellReference celdafalt = new CellReference(actividadActual, NUM_COL_FALTANTE);
                cell = row.createCell(qActual);
                cell.setCellType(Cell.CELL_TYPE_FORMULA);
                cell.setCellFormula(COL_VALOR_UNI + (actividadActual + 1) + "*" + celdafalt.formatAsString());
                actividadActual++;
            }

            row = sheet.getRow(NUM_FILA_PRIMERA_ACTIVIDAD);
            cell = row.getCell(NUM_COL_Q_PRIMER_PERIODO - 1);
            cell.setAsActiveCell();

            File carpeta = new File(realArchivoPath + "/" + String.valueOf(obraAnterior.getIntcodigoobra()));
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream(realArchivoPath + "/" + String.valueOf(obraAnterior.getIntcodigoobra()) + "/formato-cobra.xls");
            //FileOutputStream fileOut = new FileOutputStream("workbook.xls");
            wb.write(fileOut);
            fileOut.close();
        } else {
        }
    }

    public boolean validarXLSModificacion(File fileCronograma) {
        Periodo[] listadoperiodos = new Periodo[100];
        Workbook excel;
        Row fila = null;
        Date fechaInicialNoAlimentada = null;
        if(obraAnterior.getFechaUltimaActaParcial() == null)
            fechaInicialNoAlimentada = obraAnterior.getDatefeciniobra();
        else
            fechaInicialNoAlimentada = obraAnterior.getFechaUltimaActaParcial();
        int plazoObra = (int) ((historicoobra.getDatefecfinhist().getTime() - obraAnterior.getDatefeciniobra().getTime()) / (1000 * 60 * 60 * 24) + 1);
        int plazoRestanteObra = (int) ((historicoobra.getDatefecfinhist().getTime() - fechaInicialNoAlimentada.getTime()) / (1000 * 60 * 60 * 24) + 1);
        try {
            InputStream inp = new FileInputStream(fileCronograma);
            excel = WorkbookFactory.create(inp);
            Sheet sheet = excel.getSheetAt(0);
            Cell celda = null;

            ///Se cuentan las actividades del cronograma anterior
            ///Se cuentan las filas de la hoja para saber el numero de actividades

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
                if (celda.getStringCellValue().compareTo(obraAnterior.getStrnombreobra()) != 0) {
                    mensajeError = "El nombre de la obra del archivo no corresponde a esta obra";
                    return false;
                }
                fila = sheet.getRow(3);
                celda = fila.getCell(2);


                if (celda.getNumericCellValue() != plazoObra) {
                    mensajeError = "El plazo en días del archivo no corresponde a esta obra";
                    return false;
                }
                fila = sheet.getRow(4);
                celda = fila.getCell(2);
                if (celda.getDateCellValue().compareTo(obraAnterior.getDatefeciniobra()) != 0) {
                    mensajeError = "La fecha de inicio del archivo no corresponde a la consignada en la obra";
                    return false;
                }
                fila = sheet.getRow(5);
                celda = fila.getCell(2);

                if (celda.getDateCellValue().compareTo(historicoobra.getDatefecfinhist()) != 0) {
                    mensajeError = "La fecha de finalización del archivo no corresponde a la consignada en la obra";
                    return false;
                }
                //Verificar Total
                fila = sheet.getRow(2);
                celda = fila.getCell(8);



                    try {
                        
                        BigDecimal valordelacelda = new BigDecimal(celda.getNumericCellValue());

                        
                        if (historicoobra.getNumvalorhist().compareTo(BigDecimal.ZERO) >= 0 &&  historicoobra.getNumvalorhist().compareTo(valordelacelda.setScale(6, RoundingMode.HALF_EVEN))  != 0){
                            mensajeError = "El valor total de la obra fue modificado";
                            return false;
                        }



                        

                        if (historicoobra.getNumvalorhist().compareTo(BigDecimal.ZERO) == 0 &&  obraAnterior.getNumvaltotobra().compareTo(valordelacelda.setScale(6, RoundingMode.HALF_EVEN)) == 0) {
                            mensajeError = "El valor total de la obra fue modificado";
                            return false;
                        } else {
                            //verificar faltante
                            fila = sheet.getRow(3);
                            celda = fila.getCell(8);
                            if (celda.getCellType() != 2) {
                                mensajeError = "Ha sido modificada la formula de valor faltante";
                                return false;
                            } else {
                                    //Verificar Actividades diligenciadas
                                    //actividadesobra.clear();
                                    int filaActual = NUM_FILA_PRIMERA_ACTIVIDAD;
                                    int numTotalFilas = NUM_FILA_PRIMERA_ACTIVIDAD + numFilasDeActividadLlenas;
                                    fila = null;
                                    if(numFilasDeActividadLlenas < obraAnterior.getActividadobras().size()){
                                        mensajeError = "El número total de actividades ingresadas es inferior al número de actividades del cronograma anterior";
                                        return false;
                                    }
                                    
                                    while (filaActual < numTotalFilas) {
                                        fila = sheet.getRow(filaActual);
                                        celda = fila.getCell(NUM_COL_ACTIVIDAD);
                                        Cell celdauni = fila.getCell(NUM_COL_UNIDAD);
                                        Cell celdacant = fila.getCell(NUM_COL_CANTIDAD);
                                        Cell celdavalor = fila.getCell(NUM_COL_VALOR_UNI);
                                        for (int actividadActual = NUM_FILA_PRIMERA_ACTIVIDAD ; actividadActual < filaActual ; actividadActual++){
                                            Row filaAux = sheet.getRow(actividadActual);
                                            Cell celdaDescAux = filaAux.getCell(2);
                                            if (celda.getStringCellValue().equals(celdaDescAux.getStringCellValue())){
                                                mensajeError = "La actividad en la fila " + (filaActual + 1) + "se encuentra repetida";
                                                return false;
                                            }
                                        }
                                        if (!validarActividad(celda.getStringCellValue(), celdauni.getStringCellValue(), celdacant.getNumericCellValue(), celdavalor.getNumericCellValue(), filaActual + 1)) {
                                            //actividadesobra.clear();
                                            return false;
                                        } else {
                                            Cell celdafin = fila.getCell(NUM_COL_VALOR_FINAL);
                                            if (celdafin.getCellType() != 2) {
                                                mensajeError = "La fórmula de valor final ha sido modificada en la fila " + (filaActual + 1);
                                                return false;
                                            } else {
                                                if ((COL_CANTIDAD+filaActual+"*").equals(COL_VALOR_UNI+filaActual)) {
                                                    mensajeError = "La fórmula de valor final ha sido modifcada en la fila " + (filaActual + 1);
                                                    return false;
                                                }
                                            }
                                        }
                                        celdavalor = fila.getCell(NUM_COL_FALTANTE);
                                        if (celdavalor.getCellType() != 2) {
                                            mensajeError = "La fórmula de valor faltante ha sido modificada en la fila " + (filaActual + 1);
                                            return false;
                                        } 
                                        filaActual++;
                                    }

                                    int numPeriodosRestantes = plazoRestanteObra / obraAnterior.getPeriodomedida().getIntnrodiasperiomedida();

                                    if (plazoRestanteObra % obraAnterior.getPeriodomedida().getIntnrodiasperiomedida() != 0) {
                                        numPeriodosRestantes = numPeriodosRestantes + 1;
                                    }

                                    ///Conteo de periodos
                                    int numColPeriodos = 0;
                                    fila = sheet.getRow(NUM_FILA_ENCABEZADO_ACTIVIDAD);
                                    for (Iterator cit = fila.cellIterator(); cit.hasNext();) {
                                        cit.next();
                                        numColPeriodos++;
                                    }
                                    int totalcol = (numColPeriodos - 9) / 2;
                                    if (totalcol != numPeriodosRestantes) {
                                        mensajeError = "Error en número de periodos";
                                        return false;
                                    } else {
                                        //verificar la suma de totales
                                        fila = sheet.getRow(5);
                                        int colActual = NUM_COL_Q_PRIMER_PERIODO + 1;
                                        BigDecimal suma = BigDecimal.valueOf(0);
                                        colActual = 11;

                                        while (colActual <= numColPeriodos) {
                                            Cell celdaTotalPeriodo = fila.getCell(colActual);

                                            if (celdaTotalPeriodo.getCellType() != 2) {
                                                mensajeError = "La fórmula de valor del periodo " + colActual + " ha sido modificada";
                                                return false;
                                            }
                                            suma = suma.add(new BigDecimal(celdaTotalPeriodo.getNumericCellValue()));
                                            colActual = colActual + 2;
                                        }


                                        if (suma.compareTo(BigDecimal.valueOf(0)) == 0) {
                                            mensajeError = "ERROR LA SUMA DE LOS TOTALES DE LOS PERIODOS ES O";
                                            return false;
                                        }
                                        ///comparar suma con total del archivo que sean iguales
                                        fila = sheet.getRow(2);
                                        celda = fila.getCell(8);

                                        ///LLenar Matrix con los datos provenientes del excell
                                        //llenar periodos


                                        Calendar fecha = Calendar.getInstance();
                                        fecha.setTime(fechaInicialNoAlimentada);
                                        fila = sheet.getRow(5);
                                        colActual = 11;
                                        int qPeriodoActual = 0;

                                        while (colActual <= numColPeriodos) {

                                            Cell celdatotal = fila.getCell(colActual);

                                            if (celdatotal != null) {
                                                Periodo pe = new Periodo();
                                                pe.setDatefeciniperiodo(fecha.getTime());
                                                fecha.add(Calendar.DATE, +(obraAnterior.getPeriodomedida().getIntnrodiasperiomedida() - 1));
                                                pe.setDatefecfinperiodo(fecha.getTime());
                                                pe.setNumvaltotplanif(BigDecimal.valueOf(celdatotal.getNumericCellValue()));
                                                pe.setObra(obraAnterior);
                                                listadoperiodos[qPeriodoActual] = pe;
                                                fecha.add(Calendar.DATE, +1);
                                                qPeriodoActual++;
                                            }
                                            colActual++;
                                        }
                                        //validar cantidades programadas
//                                        filaActual = NUM_FILA_PRIMERA_ACTIVIDAD;
//                                        double sumaCantidadesProgramadas = 0;
//
//                                        while(filaActual < numTotalFilas){
//                                            fila = sheet.getRow(filaActual);
//                                            Cell CantidadesProgramadas = fila.getCell(9);
//                                            sumaCantidadesProgramadas = sumaCantidadesProgramadas + CantidadesProgramadas.getNumericCellValue();
//                                            filaActual++;
//                                        }
//
//                                        if(sumaCantidadesProgramadas != 0){
//                                            mensajeError = "La Cantidad programadas deben estar en 0";
//                                            return false;
//                                        }



                                        ///LLenar actividades obra
                                        filaActual = NUM_FILA_PRIMERA_ACTIVIDAD;

                                        fila = null;

                                        while (filaActual < numTotalFilas) {
                                            qPeriodoActual = NUM_COL_Q_PRIMER_PERIODO;
                                            Actividadobra actividadobra = new Actividadobra();
                                            fila = sheet.getRow(filaActual);
                                            Cell celdadesc = fila.getCell(NUM_COL_ACTIVIDAD);
                                            Cell celdauni = fila.getCell(NUM_COL_UNIDAD);
                                            Cell celdacant = fila.getCell(NUM_COL_CANTIDAD);
                                            Cell celdavalor = fila.getCell(NUM_COL_VALOR_UNI);

                                            actividadobra.setStrdescactividad(celdadesc.getStringCellValue());
                                            actividadobra.setStrtipounidadmed(celdauni.getStringCellValue());
                                            actividadobra.setFloatcantplanifao(celdacant.getNumericCellValue());
                                            actividadobra.setNumvalorplanifao(BigDecimal.valueOf(celdavalor.getNumericCellValue()));
                                            actividadobra.setUsuario(obraAnterior.getUsuario());
                                            actividadobra.setFloatcantidadejecutao(new Double(0));
                                            actividadobra.setNumvalorejecutao(new BigDecimal(0));
                                            actividadobra.setStrcodcubs("");

                                            //Si la fila perteneca a una actividad antigua:
                                            if (filaActual <= obraAnterior.getActividadobras().size() + 8){
                                                if(!validarActividadEnCronogramaAnterior(actividadobra, filaActual)){
                                                    return false;
                                                }
                                            }

                                            numColPeriodos = 0;
                                            while (numColPeriodos < numPeriodosRestantes) {
                                                celda = fila.getCell(qPeriodoActual);
                                                Relacionactividadobraperiodo relacion = new Relacionactividadobraperiodo();
                                                if (celda == null) {
                                                    relacion.setFloatcantplanif(0);
                                                } else {
                                                    if (celda.getCellType() == Cell.CELL_TYPE_STRING)
                                                        relacion.setFloatcantplanif(Double.parseDouble(celda.getStringCellValue()));
                                                    if (celda.getCellType() == Cell.CELL_TYPE_NUMERIC)
                                                        relacion.setFloatcantplanif(Double.parseDouble(""+celda.getNumericCellValue()));
                                                }
                                                celda = fila.getCell(qPeriodoActual + 1);
                                                if (celda == null) {
                                                    relacion.setNumvalplanif(BigDecimal.valueOf(0));

                                                } else {
                                                    relacion.setNumvalplanif(BigDecimal.valueOf(celda.getNumericCellValue()));
                                                }

                                                relacion.setPeriodo(listadoperiodos[numColPeriodos]);
                                                relacion.setPeriodo(listadoperiodos[numColPeriodos]);
                                                actividadobra.getRelacionactividadobraperiodos().add(relacion);
                                                numColPeriodos++;
                                                qPeriodoActual = qPeriodoActual + 2;
                                            }
                                            obraModificada.getActividadobras().add(actividadobra);
                                            filaActual++;
                                        }
                                        if (!cambioPrecio && historicoobra.cambioPrecio()) {
                                            mensajeError = "La modificación está tipificada para cambiar precio pero no se cambio ningún precio";
                                            return false;
                                        }
                                        obraModificada.setIntplazoobra(plazoObra);
                                    }
                                
                            }
                        }
                    } catch (java.lang.IllegalStateException e) {
                        mensajeError = e.getCause().toString();
                        return false;
                    }

            } else {
                mensajeError = "EL ARCHIVO NO TIENE ACTIVIDADES";
                return false;
            }
        } catch (Exception ex) {
            mensajeError = "Error desconocido al validar el archivo";
            Logger.getLogger(GestorCronogramaXLS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
 * */

}
