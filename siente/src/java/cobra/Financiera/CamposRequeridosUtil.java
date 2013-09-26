/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Financiera;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author desarrollo5
 */
public class CamposRequeridosUtil {

    private static CamposRequeridosUtil instance = null;

    private CamposRequeridosUtil() {
    }

    /**
     * Logger configurado de acuerdo al archivo properties.
     *
     * @return Logger configurado de acuerdo al archivo properties.
     */
    public static synchronized CamposRequeridosUtil getInstance() {
        synchronized (CamposRequeridosUtil.class) {
            if (instance == null) {
                instance = new CamposRequeridosUtil();
            }
        }
        return instance;
    }

    /**
     *
     * Valida que los campos ingresados en el VO, marcados como obligatorios, no incluyan un valor NULL, o vacio.
     * Los atributos obligatorios son definidos en el arreglo "nombreGetters".
     * En este parametros se incluyen los nombres de los getters (metodos GET)
     *
     * @param in
     * @param nombreGetters
     * @return mensaje de usuario, que enuncia los valores requeridos NO incluidos dentro del DTO.
     * 			Si el mensaje es NULL, la validacion fue exitosa.
     */
    public void validarAtributosRequeridos(Object in, String[] nombreGetters) throws RequiredAttributesException {
        ArrayList<String> camposNoIngresados = new ArrayList<String>();
        String msgResult = null;

        if (in != null && nombreGetters != null && nombreGetters.length > 0) {
            Class<? extends Object> clazz = in.getClass();

            for (int i = 0; i < nombreGetters.length; i++) {
                String getter = nombreGetters[i];

                if (getter != null && !getter.trim().equals("")) {

                    try {
                        Method method = clazz.getMethod(getter, new Class[0]);

                        Object dtoValue = method.invoke(in, new Object[0]);

                        if (dtoValue == null) {
                            camposNoIngresados.add(method.getName());
                        } else if (dtoValue instanceof String && ((String) dtoValue).trim().equals("")) {
                            camposNoIngresados.add(method.getName());
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(CamposRequeridosUtil.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }

        msgResult = crearMensajeError(camposNoIngresados);
        if (msgResult != null) {
            throw new RequiredAttributesException(msgResult);
        }
    }

    /**
     * Metodo utilitaria para crear el mensaje de error basado en un Arraylist con los campos NO ingresados.
     * @param camposNoIngresados
     * @return
     */
    private String crearMensajeError(ArrayList<String> camposNoIngresados) {
        if (camposNoIngresados != null && camposNoIngresados.size() > 0) {
            String outMessage = "Los siguientes datos son requeridos:";

            for (Iterator iterator = camposNoIngresados.iterator(); iterator.hasNext();) {
                String metodo = (String) iterator.next();
                if (metodo != null) {
                    metodo = metodo.trim();
                    if (metodo.startsWith("get")) {
                        metodo = metodo.substring(3);
                    } else if (metodo.startsWith("is")) {
                        metodo = metodo.substring(2);
                    }
                    outMessage = outMessage + metodo + ", ";
                }
            }

            outMessage = outMessage.substring(0, outMessage.lastIndexOf(", "));
            return outMessage;
        }
        return null;

    }
}
