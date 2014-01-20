/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.exceptionspo;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author interkont08
 */
public class ValidacionesPO {

    public static void validarTareadentroFechasConvenio(Date fechaini, Date fechafin, Date fechainiconvenio, Date fechafinconvenio, String formulario) {

        if (fechaini.compareTo(fechainiconvenio) < 0) {
            throw new ConvenioException("La fecha de inicio de" + formulario + " (" + obtenerFecha(fechaini) + ") debe ser mayor o "
                    + "igual a la fecha de inicio del convenio (" + obtenerFecha(fechainiconvenio) + ")");
        }
        if (fechafin.compareTo(fechafinconvenio) > 0) {
            throw new ConvenioException("La fecha de finalización de" + formulario + " (" + obtenerFecha(fechafin) + ") debe ser menor o "
                    + "igual a la fecha de finalización del convenio (" + obtenerFecha(fechafinconvenio) + ") ");
        }
        if (fechafin.compareTo(fechaini) < 0) {
            throw new ConvenioException("La fecha de finalización (" + obtenerFecha(fechafin) + ") debe ser mayor o igual a la fecha inicial (" + obtenerFecha(fechaini) + ")");
        }

    }

    public static void validarDuracionActividad(int duracionactividad, int duracionconvenio, String formulario) {
        if (duracionactividad <= 0) {
            throw new ConvenioException("La duración de" + formulario + " debe ser mayor a 0");
        }
        if (duracionactividad > duracionconvenio) {
            throw new ConvenioException("La duración de" + formulario + " (" + duracionactividad + " días) debe ser menor o igual a la duración del convenio (" + duracionconvenio + " días)");
        }
    }

    public static void validarRedimensionamientoFasesprincipales(List<ActividadobraDTO> listpar) {
        int i = 0, j = 0;
        while (i < listpar.size() - 1) {
            j = i + 1;
            while (j < listpar.size()) {

                if (listpar.get(i).getEndDateTime().compareTo(listpar.get(j).getStartDateTime()) >= 0) {
                    throw new ConvenioException("La modificación genera sobreposición entre las fases principales, por lo cual no se puede realizar.");
                }
                j++;
            }
            i++;
        }
    }


    public static void validarRedimensionamientoFases(ActividadobraDTO actividadnueva, TreeStore<ActividadobraDTO> taskStore, ListStore<DependenciaDTO> depStore, ContratoDTO conv) {
        ActividadobraDTO actpadre = taskStore.getParent(actividadnueva);
        if (actividadnueva.getEndDateTime().compareTo(actpadre.getEndDateTime()) > 0) {
            ActividadobraDTO actraiz = taskStore.getRootItems().get(0);
            List<ActividadobraDTO> listafases=actraiz.getChildren();
            List<ActividadobraDTO> listafasesanalisis= new ArrayList<ActividadobraDTO>();
            for(ActividadobraDTO fase:listafases)
            {
                if(fase.getId().compareTo(actpadre.getId())==0)
                {
                    fase.setEndDateTime(actividadnueva.getEndDateTime());
                }
                listafasesanalisis.add(fase);
            }
            validarRedimensionamientoFasesprincipales(listafasesanalisis);
            //throw new ConvenioException("Validar redimensionamiento"); 
        }
    }
    
    
    public static String obtenerFecha(Date fecha) {
        DateWrapper dw = new DateWrapper(fecha).clearTime();
        String mes, dia;
        if (dw.getMonth() + 1 < 10) {
            mes = "0" + String.valueOf(dw.getMonth() + 1);
        } else {
            mes = String.valueOf(dw.getMonth() + 1);
        }
        if (dw.getDate() < 10) {
            dia = "0" + String.valueOf(dw.getDate());
        } else {
            dia = String.valueOf(dw.getDate());
        }
        return String.valueOf(dw.getFullYear()) + "-" + mes + "-" + dia;
    }
}
