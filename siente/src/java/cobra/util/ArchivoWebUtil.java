package cobra.util;

import co.com.interkont.cobra.to.utilidades.ArchivoUtil;
import com.interkont.cobra.exception.ArchivoExistenteException;
import com.interkont.cobra.exception.ArchivoNoExistenteException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * Clase de utilidades para el manejo de archivos en el contexto de una
 * aplicación web
 *
 * @author Jhon Eduard Ortiz S
 */
public class ArchivoWebUtil {

    public static File obtenerFile(String name, InputStream entrada) {
        return ArchivoUtil.crearArchivo(name, entrada);
    }

    /**
     * Obtiene el contexto correspondiente a la petición web incolucrada
     *
     * @return Contexto Web
     */
    public static ServletContext getServletContext() {
        return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    }

    /**
     * Obtiene la ruta absoluta de un archivo, correspondiente a una ruta
     * relativa a una aplicación web
     *
     * @param rutaWebRelativa Ruta telativa a la aplicación Web
     * @return Ruta absoluta en el servidor web
     */
    public static String obtenerRutaAbsoluta(String rutaWebRelativa) {
        return getServletContext().getRealPath(rutaWebRelativa);
    }

    /**
     * Copia un archivo relativo a la aplicación web en otra ubicación relativa
     * a la aplicación web. Si la ubicación destino corresponde a una carpeta,
     * el archivo conserva el nombre del orígen
     *
     * @param origen Ubicación origen
     * @param destino Ubicación destino
     * @param cortar Si true, se elimina el archivo origen después de copiarlo
     * @param sobreescrivir Si true, se sobreescrivira el archivo en caso que
     * este ya exista de los contrario lanzará la excepción
     * @return Ruta web destino (Incluido el nombre del archivo)
     * @throws FileNotFoundException Se lanza si el archivo origen no se
     * encuentra
     * @throws ArchivoExistenteException Se lanza si el archivo destino ya
     * existe y sobreescrivir = false
     */
    public static String copiarArchivo(String origen, String destino, boolean cortar, boolean sobreescrivir) throws FileNotFoundException, ArchivoExistenteException {
        if (destino.substring(destino.length() - 1).equals("/")) {
            destino = destino + origen.substring(origen.lastIndexOf("/") + 1);
        }
        ArchivoUtil.copiarArchivo(obtenerRutaAbsoluta(origen), obtenerRutaAbsoluta(destino), cortar, sobreescrivir);
        return destino;
    }

    /**
     * Copia un archivo fisico del sistema a una ubicación relativa a la
     * aplicación web
     *
     * @param origen Ubicación origen
     * @param destino Ubicación destino
     * @param cortar Si true, se elimina el archivo origen después de copiarlo
     * @param sobreescrivir Si true, se sobreescrivira el archivo en caso que
     * este ya exista de los contrario lanzará una excepción
     * @throws FileNotFoundException Se lanza si el archivo origen no se
     * encuentra
     * @throws ArchivoExistenteException Se lanza si el archivo destino ya
     * existe y sobreescrivir = false
     */
    public static void copiarArchivo(File origen, String destino, boolean cortar, boolean sobreescrivir) throws FileNotFoundException, ArchivoExistenteException {
        destino = obtenerRutaAbsoluta(destino);
        ArchivoUtil.copiarArchivo(origen.getAbsolutePath(), destino, cortar, sobreescrivir);
    }

    /**
     * Elimina el archivo en la ubicación proporcionada en el contexto de la
     * aplicación web
     *
     * @param origen Ubicación absoluta del archivo
     * @return verdadero si el archivo se eliminpo correctamente
     */
    public static boolean eliminarArchivo(String origen) {
        origen = obtenerRutaAbsoluta(origen);
        return ArchivoUtil.eliminarArchivo(origen);
    }

    /**
     * Obtiene los archivos contenidos en una carpeta en el contexto de la
     * aplicación web
     *
     * @param ubicacionCarpeta Ubicación absoluta de la carpeta
     * @return Lista de archivos contenidos en la carpeta
     */
    public static List<File> obtenerArchivos(String ubicacionCarpeta) {
        ubicacionCarpeta = obtenerRutaAbsoluta(ubicacionCarpeta);
        return ArchivoUtil.obtenerArchivos(ubicacionCarpeta);
    }

    /**
     * Obtiene el archivo correspondiente a la ruta web especificada
     *
     * @param ubicacion ruta del archivo relativa a la aplicación web
     * @return Archivo o carpeta
     * @throws ArchivoNoExistenteException Se lanza cuando el archivo
     * especificado no existe
     */
    public static File obtenerArchivo(String ubicacion) throws ArchivoNoExistenteException {
        return ArchivoUtil.obtenerArchivo(obtenerRutaAbsoluta(ubicacion));
    }

    /**
     * Obtiene el tipo del reporte dada la URL del mismo (pdf, xls, html...)
     *
     * @param url Url del reporte
     * @return
     */
    public static String obtenerTipoReporteBirt(String url) {
        int indiceIniFormato = url.indexOf("__format=") + 9;
        int indiceFinFormato = url.substring(indiceIniFormato).indexOf("&");

        if (indiceFinFormato == -1) {
            indiceFinFormato = url.substring(indiceIniFormato).length();
        }

        return url.substring(indiceIniFormato, indiceIniFormato + indiceFinFormato);
    }

    /**
     * Obtiene el nombre del reporte dada la URL del mismo
     *
     * @param url Url del reporte
     * @return
     */
    public static String obtenerNombreReporteBirt(String url) {
        int indiceIniNombreReporte = url.lastIndexOf("/") + 1;
        int indiceFinNombreReporte = url.indexOf(".rptdesign");
        return url.substring(indiceIniNombreReporte, indiceFinNombreReporte);
    }
}
