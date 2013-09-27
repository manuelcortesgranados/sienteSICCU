package cobra;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Objeto que contiene los datos correspondientes a los archivos pertenecientes
 * a la aplicación Web
 * @author Jhon Eduard Ortiz S
 */
public class ArchivoWeb {

    /**
     * Ubicación del archivo relativa a la aplicación Web.
     */
    private String rutaWeb;

    /**
     * Nombre del archivo (incluida la extensión)
     */
    private String nombre;
    
    /**
     * Contenido del archivo
     */
    private byte[] data;

    /**
     * Archivo temporal creado en el servidor inmediatemente después de que el
     * archivo es subido
     */
    private File archivoTmp;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
            this.nombre = nombre;
    }
    
    /**
     * Cambia el nombre del archivo por el nuevo nombre proporcionado.
     * @param nombreSinExt Nuevo nombre sin extensión. Si es null entonces solo
     * adiciona el subfijo de tiempo
     * @param subfijoTiempo Si true, se adiciona el sufijo de la fecha con el 
     * siguiente formato yyyyMMddHHmmss, de esta manera se disminuye la 
     * probabilidad de que los nombres se repitan
     */
    public void cambiarNombre(String nombreSinExt, boolean subfijoTiempo) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        if(this.nombre == null) {
            if(nombreSinExt != null) {
                if(subfijoTiempo) {
                    this.nombre = nombreSinExt+"_"+df.format(new Date());
                } else {
                    this.nombre = nombreSinExt;
                }
            } else {
                if(subfijoTiempo) {
                    this.nombre = df.format(new Date());
                }
            }
        } else if(!this.nombre.contains(".")) {
            if (nombreSinExt != null) {
                if(subfijoTiempo) {
                    this.nombre = nombreSinExt+"_"+df.format(new Date());
                } else {
                    this.nombre = nombreSinExt;
                }
            } else {
                if(subfijoTiempo) {
                    this.nombre = this.nombre+"_"+df.format(new Date());
                }
            }
        } else {
            String ext = this.nombre.substring(this.nombre.lastIndexOf("."));
            if (nombreSinExt != null) {
                if(subfijoTiempo) {
                    this.nombre = nombreSinExt+"_"+df.format(new Date())+ext;
                } else {
                    this.nombre = nombreSinExt+ext;
                }
            } else {
                if(subfijoTiempo) {
                    this.nombre = this.nombre.substring(0, this.nombre.lastIndexOf(".")) +"_"+df.format(new Date())+ext;
                }
            }
        }
    }

    public String getRutaWeb() {
        return rutaWeb;
    }

    public void setRutaWeb(String ruta) {
        this.rutaWeb = ruta;
    }

    public File getArchivoTmp() {
        return archivoTmp;
    }

    public void setArchivoTmp(File archivoTmp) {
        this.archivoTmp = archivoTmp;
    }

    public long getNumBytes() {
        long numBytes = 0;
        if(archivoTmp != null) {
            numBytes = archivoTmp.length();
        }
        return numBytes;
    }
}
