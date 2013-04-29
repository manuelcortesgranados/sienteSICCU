/**
 * 
 */
package cobra;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.Normalizer;
import java.util.ArrayList;

import java.util.Iterator;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

/**
 * @author Carlos Loaiza
 *
 */
public class SubirArchivoBean {

    private ArrayList<Archivo> files = new ArrayList<Archivo>();
    private ArrayList<File> archivosSubidos = new ArrayList<File>();
    private int cantidadSubidas = 1;
    private int tempcant = 1;
    private boolean autoSubida = false;
    private boolean useFlash = false;
    private String UrlArchivo = "";

    public String getUrlArchivo() {
        return UrlArchivo;
    }

    public void setUrlArchivo(String UrlArchivo) {
        this.UrlArchivo = UrlArchivo;
    }

    public int getSize() {
        if (getFiles().size() > 0) {
            return getFiles().size();
        } else {
            return 0;
        }
    }

    public SubirArchivoBean() {
    }

    public SubirArchivoBean(int cantidadSubidas, boolean autoSubida, boolean useFlash) {
        this.cantidadSubidas = cantidadSubidas;
        this.autoSubida = autoSubida;
        this.useFlash = useFlash;
        tempcant = cantidadSubidas;
    }

    public synchronized void paint(OutputStream stream, Object object) throws IOException {
        stream.write(getFiles().get((Integer) object).getData());
    }

    //metodo que valida que todo sea normalizado
    public static String formatString(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        return temp.replaceAll("[^\\p{ASCII}]", "");
    }

    public synchronized void listener(FileUploadEvent event) throws Exception {
       final UploadedFile item = event.getUploadedFile();
        Archivo file = new Archivo();


        File archivoTemporal = File.createTempFile(item.getName(), ".tmp");
        file.setLength(archivoTemporal.length());
        String nombreimaforma = formatString(item.getName());
        file.setName(nombreimaforma);
        //file.setName(item.getFileName());
        file.setData(item.getData());
        files.add(file);
        archivosSubidos.add(archivoTemporal);
        //archivoTemporal.renameTo(new File("/home/Desarrollo3/"+item.getFileName()));
        //archivoTemporal.renameTo(new File("/home/"+nombreimaforma));


        //guardar_action(file);
        cantidadSubidas--;
    }

    public void adicionarArchivo(File item) throws Exception {

        Archivo file = new Archivo();

        File archivoTemporal = item;
        file.setLength(item.length());
        file.setName(item.getName());
        files.add(file);
        archivosSubidos.add(archivoTemporal);
        cantidadSubidas--;
    }

    public String borrarDatosSubidos() {

        Iterator arch = archivosSubidos.iterator();

        while (arch.hasNext()) {
            File temp = (File) arch.next();
            temp.delete();
        }

        files.clear();
        archivosSubidos.clear();

        setCantidadSubidas(tempcant);


        return null;
    }

    public void guardarArchivosTemporales(String path, boolean indice) {
        Iterator arch = archivosSubidos.iterator();
        Iterator archtemp = files.iterator();
        int i = 1;

        while (arch.hasNext()) {
            File temp = (File) arch.next();
            Archivo archiv = (Archivo) archtemp.next();

            if (indice) {

                temp.renameTo(new File(i + "_" + path + "/" + archiv.getOnlyName()));
            } else {
                temp.renameTo(new File(path + File.separator + archiv.getOnlyName()));

            }
            i++;

        }
    }

    /**
     * Obtiene el primer archivo del fileuoload
     * @return
     */
    public File obtenerPrimerArchivo() {
        File temp = null;
        Iterator arch = archivosSubidos.iterator();
        if (arch.hasNext()) {
            temp = (File) arch.next();
        }
        return temp;
    }

    public long getTimeStamp() {
        return System.currentTimeMillis();


    }

    public ArrayList<Archivo> getFiles() {
        return files;


    }

    public void setFiles(ArrayList<Archivo> files) {
        this.files = files;


    }

    public int getCantidadSubidas() {
        return cantidadSubidas;


    }

    public void setCantidadSubidas(int cantidadSubidas) {
        this.cantidadSubidas = cantidadSubidas;


    }

    public boolean isAutoSubida() {
        return autoSubida;


    }

    public void setAutoSubida(boolean autoSubida) {
        this.autoSubida = autoSubida;


    }

    public boolean isUseFlash() {
        return useFlash;


    }

    public void setUseFlash(boolean useFlash) {
        this.useFlash = useFlash;


    }

    public ArrayList<File> getArchivosSubidos() {
        return archivosSubidos;


    }

    public void setArchivosSubidos(ArrayList<File> archivosSubidos) {
        this.archivosSubidos = archivosSubidos;

    }
    /*
    public ArrayList<UploadedFile> getArchivos() {
    return archivos;
    }

    public void setArchivos(ArrayList<UploadedFile> archivos) {
    this.archivos = archivos;
    }
     */
}
