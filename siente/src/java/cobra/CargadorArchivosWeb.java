package cobra;

import cobra.util.ArchivoWebUtil;
import com.interkont.cobra.exception.ArchivoExistenteException;
import com.interkont.cobra.exception.ArchivoNoExistenteException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

/**
 * Clase encargada de proporcionar los métodos necesarios para el funcionamiénto
 * del componente de Subida de Archivos de JSF
 *
 * @author Jhon Eduard Ortiz S
 */
public class CargadorArchivosWeb implements Serializable {

    /**
     * Listado de archivos cargados en el servidor
     */
    private ArrayList<ArchivoWeb> archivos = new ArrayList<ArchivoWeb>();

    public CargadorArchivosWeb() {
    }

    public ArrayList<ArchivoWeb> getArchivos() {
        return archivos;
    }

    public void setArchivos(ArrayList<ArchivoWeb> archivos) {
        this.archivos = archivos;
    }

    /**
     * Retorna el número de archivos cargados
     *
     * @return
     */
    public int getNumArchivos() {
        if (archivos.size() > 0) {
            return archivos.size();
        } else {
            return 0;
        }
    }

    /**
     * Obtiene el archivo ubicado en la posición 0 de la lista de archivos
     *
     * @return Primer archivo de la lista
     */
    public ArchivoWeb getArchivoWeb() {
        if (!archivos.isEmpty()) {
            return archivos.get(0);
        } else {
            return null;
        }
    }

    public synchronized void paint(OutputStream stream, Object object) throws IOException {
        stream.write(archivos.get((Integer) object).getData());
    }

    /**
     * Módifica el nombre de archivo eliminando espacios y caracteres especiales
     *
     * @param nombreArchivo Nombre del archivo incluida la extensión
     * @return Nombre del archivo normalizado incluida la extensión
     */
    public static String normalizarNombreArchivo(String nombreArchivo) {
        String temp = Normalizer.normalize(nombreArchivo, Normalizer.Form.NFD);
        String extension = null;
        if (temp.lastIndexOf(".") != -1) {
            extension = temp.substring(temp.lastIndexOf("."));
            temp = temp.substring(0, temp.lastIndexOf("."));
        }
        return temp.replaceAll("(%20|\\s|\\-)", "_").replaceAll("[__]+", "_")
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("[^\\w_()]", "")
                .concat(extension);
    }

    /**
     * Método que se ejecuta al momento de subir un archivo web mediante el el
     * componente rich:fileUpload. Aquí se puede manejar la información de los
     * archivos subidos
     *
     * @param event evento que representa la subida de un archivo al servidor
     * mediante el CargadorArchivosWeb
     * @throws Exception
     */
    public synchronized void listener(FileUploadEvent event) throws Exception {
        final UploadedFile item = event.getUploadedFile();
        ArchivoWeb archivoWeb = new ArchivoWeb();
        archivoWeb.setArchivoTmp(ArchivoWebUtil.obtenerFile(item.getName(), item.getInputStream()));
        archivoWeb.setNombre(normalizarNombreArchivo(item.getName()));
        archivoWeb.setData(item.getData());
        archivos.add(archivoWeb);
    }

    /**
     * Método encargado de eliminar los archivos subidos temporalmente al
     * servidor
     *
     * @return
     */
    public String borrarDatosSubidos() {
        Iterator<ArchivoWeb> itArchivos = archivos.iterator();
        while (itArchivos.hasNext()) {
            File temp = itArchivos.next().getArchivoTmp();
            if (temp != null) {
                temp.delete();
            }
        }
        archivos.clear();
        return null;
    }

    /**
     * Este método se encarga de copiar los archivos temporales cargados
     * mediante el file upload en la carpeta real donde quedarán almacenados.
     * Dicha ubicación debe persistirse para futuras consultas del archivo
     *
     * @param rutaCarpetaWeb Corresponde a la ruta de la caréta (incluído el
     * último /) en la cual se almacenará el archivo; relativa a la aplicación
     * web
     * @param sobreescrivir Si true, se sobreescrivira el archivo en caso que
     * este ya exista de lo contrario lanzará una excepción
     * @throws ArchivoExistenteException Se lanza si el archivo destino ya
     * existe y sobreescrivir = false
     */
    public void guardarArchivosTemporales(String rutaCarpetaWeb, boolean sobreescrivir) throws ArchivoExistenteException {
        Iterator<ArchivoWeb> itArchivos = archivos.iterator();
        while (itArchivos.hasNext()) {
            ArchivoWeb archivoWeb = itArchivos.next();
            archivoWeb.setRutaWeb(rutaCarpetaWeb + archivoWeb.getNombre());
            try {
                if (archivoWeb.getArchivoTmp() != null) {
                    ArchivoWebUtil.copiarArchivo(archivoWeb.getArchivoTmp(), archivoWeb.getRutaWeb(), true, sobreescrivir);
                    archivoWeb.setArchivoTmp(null);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CargadorArchivosWeb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Carga el archivo correspondiente a la ruta especificada
     *
     * @param rutaArchivoWeb ruta del archivo (relativa a la aplicación web)
     * @throws ArchivoNoExistenteException Se lanza cuando el archivo
     * especificado no existe
     */
    public void cargarArchivo(String rutaArchivoWeb) throws ArchivoNoExistenteException {
        ArchivoWeb archivoWeb = new ArchivoWeb();
        archivoWeb.setArchivoTmp(ArchivoWebUtil.obtenerArchivo(rutaArchivoWeb));
        archivoWeb.setRutaWeb(rutaArchivoWeb);
        archivoWeb.setNombre(archivoWeb.getArchivoTmp().getName());
        archivos.add(archivoWeb);
    }
}
