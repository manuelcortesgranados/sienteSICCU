
package cobra.util;

/**
 * Archivo que contiene constantes correspondientes a las rutas de los archivos
 * de la aplicación web
 * @author Jhon Eduard Ortiz S
 */
public class RutasWebArchivos {
    
    /**
     * Constante que corresponde a la ubicación en la que se almacena el
     * cronograma de
     */
    public static String DOCS_MODI ="/resources/Documentos/ObrasVigentes/{0}/Modificacion-{1}/";

    /**
     * Constante que corresponde a la ubicación web en la que se almacenan los
     * documentos de una obra. El parámetro {0} debe reemplazarse por el
     * identificador de la obra
     */
    public static String DOCS_OBRA ="/resources/Documentos/ObrasVigentes/{0}/";
    
    /**
     * Constante que corresponde a la ubicación web en la que se almacenan los
     * documentos de un contrato. El parámetro {0} debe reemplazarse por el
     * identificador de la obra
     */
    public static String DOCS_CONTRATO ="/resources/Documentos/Contrato/{0}/";
    
    /**
     * Constante que corresponde a la ubicación web en la que se almacenan los
     * documentos de modificaciones de un contrato. El parámetro {0} debe 
     * reemplazarse por el identificador de la obra
     */
    public static String DOCS_MODI_CONTRATO ="/resources/Documentos/Contrato/{0}/ModificacionesContrato/";
    
    /**
     * Constante que corresponde a la ubicación web en la que se almacenan los
     * documentos de suspensión de una obra. El parámetro {0} debe reemplazarse por el
     * identificador de la obra
     */
    public static String DOCS_SUSPENSION_OBRA ="/resources/Documentos/ObrasVigentes/{0}/Suspension/";

    /**
     * Constante que corresponde a la ubicación web en la que se almacenan los
     * documentos de una obra en proceso. El parámetro {0} debe reemplazarse por el
     * identificador de la obra
     */
    public static String DOCS_OBRA_PROCESO ="/resources/Documentos/ObrasenProceso/{0}/";

    /**
     * Constante que corresponde a la ubicación web en la que se almacenan las
     * imágenes de una obra en proceso. El parámetro {0} debe reemplazarse por el
     * identificador de la obra
     */
    public static String IMGS_OBRA_PROCESO ="/resources/Documentos/ObrasenProceso/{0}/ImagenesObra/";

    /**
     * Constante que corresponde a la ubicación web de la carpeta temporal de la
     * aplicación.
     */
    public static String TMP ="/resources/Documentos/TMP/";

    /**
     * Constante que corresponde a la ubicación web en la que se almacenan las
     * imágenes de una obra. El parámetro {0} debe reemplazarse por el
     * identificador de la obra
     */
    public static String IMGS_OBRA ="/resources/Documentos/ObrasVigentes/{0}/ImagenesEvolucion/";
    
    public static String DOC_MATRIZ="/resources/Documentos/ValidadorE/";
    
    /**
     * Constante que corresponde a la ubicación web en la que se almacenan los
     * videos de alimentación de una obra. El parámetro {0} debe reemplazarse por el
     * identificador de la obra
     */
    public static String VIDEOS_ALIMENTACION_OBRA ="/resources/Documentos/ObrasVigentes/{0}/VideosAlimentacion/";
    
    /**
     * Constante que corresponde a la ubicación web en la que se almacenan las
     * imágenes del ciudadano.
     * El parámetro {0} debe reemplazarse por el identificador de la obra
     */
    public static String IMAGENES_CIUDADANO ="/resources/Documentos/Ciudadano/{0}/Imgs/";
    
    /**
     * Constante que corresponde a la ubicación web en la que se almacenan los
     * archivos relacionados a la opinión de un ciudadano.
     * El parámetro {0} debe reemplazarse por el identificador de la obra
     */
    public static String OPINIONES_CIUDADANO ="/resources/Documentos/ObrasVigentes/{0}/OpinionesCiudadano/";
    
    /**
     * Constante que corresponde a la ubicación web en la que se almacenan las
     * imágenes de alimentación de una obra. El parámetro {0} debe reemplazarse por el
     * identificador de la obra
     */
    public static String IMGS_OBRA_ALIMENTACION ="/resources/Documentos/ObrasVigentes/{0}/ImgsAlimentacion/";

    /**
     * Constante que corresponde a la ruta web de la imágen no disponible
     */
    public static String IMG_NO_DISPONIBLE ="/resources/imgs/bt_nodisponible.png";
    
    /**
     * Constante que corresponde a la ruta web de la foto no disponible
     */
    public static String FOTO_NO_DISPONIBLE ="/resources/imgs/login/foto_perfil.jpg";
    
    /**
     * Constante que corresponde a la ruta web del archivo de fiduprevisora
     */
    public static String FINANCIERA ="/resources/Documentos/financiera/";
    
    public static String SOLICITUD="/resources/Documentos/Solicitud/Temporal/";

}
