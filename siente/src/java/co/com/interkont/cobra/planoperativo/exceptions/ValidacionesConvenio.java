/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.exceptions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author desarrollo2
 */
public class ValidacionesConvenio {

    public static void validarFechasConvenio(Date fechaini, Date fechafin) {
        if (fechaini == null || fechafin == null) {
            throw new ConvenioException("Debe Ingresar las fechas del convenio");
        }

        if (fechafin.compareTo(fechaini) < 0) {
            throw new ConvenioException("La fecha de finalización debe ser mayor a la fecha inicial");
        }

    }

    public static void validarValorPositivo(BigDecimal valor, String nombredelcampo) {
        if (valor == null) {
            throw new ConvenioException("Debe Ingresar el valor del " + nombredelcampo);
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ConvenioException("Debe ingresar un valor al " + nombredelcampo);
        }

    }

    public static void validarValorCuotaGerencia(BigDecimal valorConvenio, BigDecimal valorCuota) {
        if (valorCuota.compareTo(valorConvenio) > 0) {
            throw new ConvenioException("El valor de la cuota de gerencia debe ser menor o igual al valor global del convenio.");
        }

    }

    public static void validarFechasPlanOperativo(Date fechaactaini, Date fechaini, Date fechafin) {
        validarFechasConvenio(fechaini, fechafin);
        if (fechaactaini == null) {
            throw new ConvenioException("Debe Ingresar la fecha del acta de inicio");
        }

        if (fechaactaini.compareTo(fechaini) < 0) {
            throw new ConvenioException("La fecha del acta de inicio debe ser mayor o igual a la fecha inicial");
        }
        if (fechaactaini.compareTo(fechafin) >= 0) {
            throw new ConvenioException("La fecha del acta de inicio debe ser menor o igual a la fecha final");
        }
        //Acta de inicio superior al final

    }

    public static void validarTamanoLista(List lista, String nombrelista) {
        if (lista.isEmpty()) {
            throw new ConvenioException("Debe diligenciar la " + nombrelista);
        }

    }

    public static void validarDistribucionFuenteRecursos(BigDecimal valorcontrato, BigDecimal sumafuentes) {
        if (valorcontrato.compareTo(sumafuentes) < 0) {
            throw new ConvenioException("Las fuentes de recursos, superan el valor del contrato ");
        }
    }

    public static void validarSumaFuentesxValorAporte(BigDecimal valorcontrato, BigDecimal valoraporte, BigDecimal sumafuentes) {
        BigDecimal totalvalor = new BigDecimal(BigInteger.ZERO);
        totalvalor = totalvalor.add(valoraporte).add(sumafuentes);
        if (totalvalor.compareTo(valorcontrato) > 0) {
            throw new ConvenioException("La suma de las fuentes de recursos superan al valor estimado del convenio");
        }
    }

    public static void validarAgregarPolizas(Date fechainicontrato, Date fechafincontrato, Date fechapoliza) {
//       Calendar fechatemppoliza = Calendar.getInstance();
//       fechatemppoliza.setTime(fechafincontrato);
//       fechatemppoliza.add(Calendar.DATE, -(30));
        if (fechapoliza.compareTo(fechafincontrato) <= 0) {
            throw new ConvenioException("La fecha debe ser mayor o igual a la fecha fin del convenio");
        }
    }

    public static void validarAgregarPolizasContrato(Date fechainicontrato, Date fechafincontrato, Date fechapoliza) {
        Calendar fechatemppoliza = Calendar.getInstance();
        fechatemppoliza.setTime(fechafincontrato);
        fechatemppoliza.add(Calendar.DATE, -(1));
        if (fechapoliza.compareTo(fechatemppoliza.getTime()) <= 0) {
            throw new ConvenioException("La fecha debe ser mayor o igual a la fecha fin del contrato");
        }
    }

    public static void validarDistribucionFinalFuenteRecursos(BigDecimal valorcontrato, BigDecimal sumafuentes) {
        if (valorcontrato.compareTo(sumafuentes) == 0) {
            throw new ConvenioException("La suma de las fuentes de recursos, debe ser igual al valor del contrato ");
        }
    }
}
