package cobra;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Objeto que contiene los datos correspondientes a los archivos pertenecientes
 * a la aplicación Web
 * @author Jhon Eduard Ortiz S
 */
public class ArchivoWeb {
    
    /**
     * Formato de tiempo utilizado para el subfijo del nombre del archivo
     */
    public static final String FORMATO_TIEMPO = "yyyyMMddHHmmss";
    
    /**
     * Formateador utilizado para el subfijo del nombre del archivo
     */
    private static final SimpleDateFormat subfijoTiempoDateFormat = new SimpleDateFormat(FORMATO_TIEMPO);
    
    /**
     * Separador del subfijo de tiempo
     */
    public static final String SEPARADOR_TIEMPO = "_";

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
    
    static {
        subfijoTiempoDateFormat.setLenient(false);
    }
    
    /**
     * Obtiene el nombre del archivo sin extensión
     * @return nombre del archivo sin extensión
     */
    public String getNombreSinExtension() {
        if(this.nombre != null) {
            if(this.nombre.contains(".")) {
                return this.nombre.substring(0, this.nombre.lastIndexOf("."));
            } else {
                return this.nombre;
            }
        } else {
            return null;
        }
    }
  
    /**
     * Verifica si el nombre del archivo contiene ya un subfijo de tiempo
     * @return true si el nombre del archivo contiene ya un subfijo de tiempo
     * de lo contrario false
     */
    private boolean tieneSubfijotiempo() {
        if(this.nombre != null) {
            if(getNombreSinExtension().length() >= FORMATO_TIEMPO.length() + SEPARADOR_TIEMPO.length()) {
                int posSeparadorTiempo = getNombreSinExtension().length() - FORMATO_TIEMPO.length() - SEPARADOR_TIEMPO.length();
                String separadorTiempo = getNombreSinExtension().substring(posSeparadorTiempo, posSeparadorTiempo + SEPARADOR_TIEMPO.length());
                if(separadorTiempo.equals(SEPARADOR_TIEMPO)) {
                    try {
                        subfijoTiempoDateFormat.parse(getNombreSinExtension().substring(getNombreSinExtension().length() - FORMATO_TIEMPO.length()));
                        return true;
                    } catch (ParseException pe) {
                        return false;
                    } catch (Exception e) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    /**
     * Cambia el nombre del archivo por el nuevo nombre proporcionado.
     * @param nuevoNombreSinExt Nuevo nombre sin extensión. Si es null entonces solo
     * adiciona el subfijo de tiempo
     * @param subfijoTiempo Si true, se adiciona el sufijo de la fecha al nombre
     * del archivo si este no lo tiene aún; de esta manera se disminuye la 
     * probabilidad de que los nombres se repitan
     */
    public void cambiarNombre(String nuevoNombreSinExt, boolean subfijoTiempo) {
        if(this.nombre == null) {
            if(nuevoNombreSinExt != null) {
                if(subfijoTiempo && !tieneSubfijotiempo()) {
                    this.nombre = nuevoNombreSinExt+SEPARADOR_TIEMPO+subfijoTiempoDateFormat.format(new Date());
                } else {
                    this.nombre = nuevoNombreSinExt;
                }
            } else {
                if(subfijoTiempo && !tieneSubfijotiempo()) {
                    this.nombre = subfijoTiempoDateFormat.format(new Date());
                }
            }
        } else if(!this.nombre.contains(".")) { // Si el nombre del archivo NO tiene extensión
            if (nuevoNombreSinExt != null) {
                if(subfijoTiempo && !tieneSubfijotiempo()) {
                    this.nombre = nuevoNombreSinExt+SEPARADOR_TIEMPO+subfijoTiempoDateFormat.format(new Date());
                } else {
                    this.nombre = nuevoNombreSinExt;
                }
            } else {
                if(subfijoTiempo && !tieneSubfijotiempo()) {
                    this.nombre = this.nombre+SEPARADOR_TIEMPO+subfijoTiempoDateFormat.format(new Date());
                }
            }
        } else { // Si el nombre del archivo tiene extensión
            String extensionConPunto = this.nombre.substring(this.nombre.lastIndexOf("."));
            if (nuevoNombreSinExt != null) {
                if(subfijoTiempo && !tieneSubfijotiempo()) {
                    this.nombre = nuevoNombreSinExt+SEPARADOR_TIEMPO+subfijoTiempoDateFormat.format(new Date())+extensionConPunto;
                } else {
                    this.nombre = nuevoNombreSinExt+extensionConPunto;
                }
            } else {
                if(subfijoTiempo && !tieneSubfijotiempo()) {
                    this.nombre = getNombreSinExtension() +SEPARADOR_TIEMPO+subfijoTiempoDateFormat.format(new Date())+extensionConPunto;
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
