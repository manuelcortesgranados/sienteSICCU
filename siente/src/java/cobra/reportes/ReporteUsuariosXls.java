/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.reportes;

import co.com.interkont.cobra.to.excel.ConstructorWorkbook;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import cobra.Supervisor.ModificarProyecto;
import cobra.util.ArchivoWebUtil;
import com.interkont.cobra.exception.ArchivoExistenteException;
import com.interkont.cobra.exception.ArchivoNoExistenteException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author OTHAN
 */
public class ReporteUsuariosXls extends ConstructorWorkbook implements Serializable {

    public static final int FILA_UNO = 4;
    public static final int COL_CODIGO_SIENTE = 0;
    public static final int COL_NOMBRE_SOLICITUD = 1;
    public static final int COL_CONSECUTIVO = 2;
    public static final int COL_ENCARGO_FIDUCIARIO = 3;
    public static final int COL_MUNICIPIO = 4;
    public static final int COL_DEPARTAMENTO = 5;
    public static final int COL_NOMBRE = 6;
    public static final int COL_EMAIL = 7;
    public static final int COL_USUARIO = 8;
    public static final int COL_PASSWORD = 9;
    public static final int COL_MAX_FILAS = 65535;
    public static final int INICIO_HOJAS_EXCEL = 0;

    public ReporteUsuariosXls() {
        construirWorkbook();
    }

    private Workbook obtenerPlantilla() {
        File archivo = null;
        HSSFWorkbook excel = null;
        InputStream archivoInputStream = null;
        try {
            archivo = ArchivoWebUtil.obtenerArchivo(Propiedad.getValor("ubicacionplantillarepinfousuarios"));
        } catch (ArchivoNoExistenteException ex) {
            Logger.getLogger(ReporteUsuariosXls.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (archivo.exists()) {
            try {
                archivoInputStream = new FileInputStream(archivo);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ReporteUsuariosXls.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {

            excel = new HSSFWorkbook(archivoInputStream);
        } catch (IOException ex) {
            Logger.getLogger(ReporteUsuariosXls.class.getName()).log(Level.SEVERE, null, ex);
        }
        return excel;
    }

    public void ingresarDatos(int fila, int columna, String valor) {
        cell = crearCelda(fila, columna);
        cell.setCellValue(valor);
    }

    public void ingresarDatos(int fila, int columna, int valor) {
        cell = crearCelda(fila, columna);
        cell.setCellValue(valor);
    }

    public void almacenarArchivo() {
        FileOutputStream CronogramaOutputStream = null;
        try {
            ArchivoWebUtil.copiarArchivo(Propiedad.getValor("ubicacionplantillarepinfousuarios"), Propiedad.getValor("ubicacionrepinfousuarios"), false, true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReporteUsuariosXls.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(ReporteUsuariosXls.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            CronogramaOutputStream = new FileOutputStream(ArchivoWebUtil.obtenerRutaAbsoluta(Propiedad.getValor("ubicacionrepinfousuarios")));
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

    @Override
    public void construirWorkbook() {
        workbook = obtenerPlantilla();
        sheet = workbook.getSheetAt(ReporteUsuariosXls.INICIO_HOJAS_EXCEL);
    }
}
