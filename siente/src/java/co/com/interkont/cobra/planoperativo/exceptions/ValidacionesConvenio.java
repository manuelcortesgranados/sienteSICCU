/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.exceptions;

import co.com.interkont.cobra.planoperativo.exceptionspo.ConvenioException;
import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import static co.com.interkont.cobra.planoperativo.exceptionspo.ValidacionesPO.obtenerFecha;
import co.com.interkont.cobra.to.Actividadobra;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import cobra.PlanOperativo.ProyectoPlanOperativo;
import com.interkont.cobra.util.CobraUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        if (valorConvenio != null) {
            if (valorConvenio.compareTo(BigDecimal.ZERO) > 0) {
                if (valorCuota != null) {
                    if (valorCuota.compareTo(valorConvenio) > 0) {
                        throw new ConvenioException("El valor de la cuota de gerencia debe ser menor o igual al valor global del convenio.");
                    }
                }
            }

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
            throw new ConvenioException("La suma de las fuentes de recursos supera al valor estimado del convenio");
        }
    }

    public static void validarAgregarPolizas(Date fechainicontrato, Date fechafincontrato, Date fechapoliza, String tipocontrato) {
//       Calendar fechatemppoliza = Calendar.getInstance();
//       fechatemppoliza.setTime(fechafincontrato);
//       fechatemppoliza.add(Calendar.DATE, -(30));
        if (fechapoliza.compareTo(fechafincontrato) <= 0) {
            throw new ConvenioException("La fecha de la póliza debe ser mayor o igual a la fecha  fin del " +tipocontrato + " "+  obtenerFecha(fechafincontrato) +".");
        }
    }

    public static void validarAgregarPolizasContrato(Date fechainicontrato, Date fechafincontrato, Date fechapoliza, String tipocontrato) {
        Calendar fechatemppoliza = Calendar.getInstance();
        fechatemppoliza.setTime(fechafincontrato);
        fechatemppoliza.add(Calendar.DATE, -(1));
        if (fechapoliza.compareTo(fechatemppoliza.getTime()) <= 0) {
            throw new ConvenioException("La fecha de la póliza debe ser mayor o igual a la fecha  fin del " +tipocontrato + " "+   obtenerFecha(fechafincontrato) +".");
        } 
    }

    public static void validarDistribucionFinalFuenteRecursos(BigDecimal valorcontrato, BigDecimal sumafuentes) {
        if (valorcontrato.compareTo(sumafuentes) != 0) {
            throw new ConvenioException("La suma de las fuentes de recursos (" + CobraUtil.getInstance().parserCurrencyLocale(sumafuentes.doubleValue()) + "), debe ser igual al valor del convenio (" + CobraUtil.getInstance().parserCurrencyLocale(valorcontrato.doubleValue()) + ")");
        }
    }

    public static void validarDistribucionFinalCuotaGerencia(BigDecimal valorcuotagerenciacontrato, BigDecimal sumacuotasgerencia) {
        if (valorcuotagerenciacontrato.compareTo(sumacuotasgerencia) != 0) {
            throw new ConvenioException("La distribución de las cuotas de gerencia de las fuentes de recursos (" + CobraUtil.getInstance().parserCurrencyLocale(sumacuotasgerencia.doubleValue()) + "), debe ser igual al valor de la cuota de gerencia del convenio (" + CobraUtil.getInstance().parserCurrencyLocale(valorcuotagerenciacontrato.doubleValue()) + ")");
        }
    }

    public static void validarProyectosPlanOperativo(List<ProyectoPlanOperativo> listadoproyectos) {
        if (listadoproyectos.isEmpty()) {
            throw new ConvenioException("Debe adicionar al menos un proyecto al Plan Operativo");
        }
//        else
//        {
//            
//            int i=0;
//            while(i<listadoproyectos.size())            
//            {
//                if(listadoproyectos.get(i).getValorDisponible().compareTo(BigDecimal.ZERO)!=0)
//                {
//                    throw new ConvenioException("El Proyecto : "+listadoproyectos.get(i).getStrnombreobra()+", aún posee valor sin distribuir ("+listadoproyectos.get(i).getValorDisponible()+");");
//                   
//                }    
//                i++;
//            }    
//        }    
    }

    public static void validarDisponibilidadFuentesRecursos(List<Fuenterecursosconvenio> listarecursos) {
//        for (Fuenterecursosconvenio fue : listarecursos) {
//            if (fue.getValorDisponible().compareTo(BigDecimal.ZERO) != 0) {
//                throw new ConvenioException("La entidad " + fue.getTercero().getStrnombrecompleto() + ", en la vigencia " + fue.getVigencia()
//                        + " posee un valor disponible de ($" + fue.getValorDisponible() + ") para ser distribuido en proyectos. ");
//            }
//        }
    }
    
    
    public static void validarFechaActaInicio(List<ActividadobraDTO> lstTodasActividadesDTO, ContratoDTO contratoDto) {
        
        
//        ActividadobraDTO actividadActaInici = null;
//        ActividadobraDTO actividadReglamento = null;
//        for (ActividadobraDTO actividad : lstTodasActividadesDTO) {
//            if (actividad.getName().equals("Acta de Inicio del Convenio")) {
//                actividadActaInici = actividad;
//            } else if (actividad.getName().equals("Reglamento Comité Operativo")) {
//                actividadReglamento = actividad;
//            }
//        }
//        if (actividadActaInici != null) {
//            if (contratoDto.getDatefechaactaini().compareTo(actividadActaInici.getStartDateTime()) != 0) {
//                if (actividadReglamento != null) {
//                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
//                    if (contratoDto.getDatefechaactaini().compareTo(actividadReglamento.getStartDateTime()) > 0) {
//                        throw new ConvenioException("La fecha del acta de inicio no puede ser superior a la fecha del reglamento del comite operativo:" + sdf.format(actividadReglamento.getStartDateTime()));
//
//                    } else {
//                        Calendar cale = new GregorianCalendar();
//                        cale.setLenient(false);
//                        cale.setTime(contratoDto.getDatefechaactaini());
//                        Date fechaCopiaActa = cale.getTime();
//                       
//                        Calendar cal = new GregorianCalendar();
//                        cal.setLenient(false);
//                        cal.setTime(fechaCopiaActa);
//                        cal.add(Calendar.DAY_OF_MONTH, actividadActaInici.getDuration());
//                        Date fechaFin = cal.getTime();
//                        
//                        if (fechaFin.compareTo(actividadReglamento.getStartDateTime()) > 0) {
//                            throw new ConvenioException("La fecha fin del acta de inicio no puede ser superior a la fecha del reglamento del comite operativo:" + sdf.format(actividadReglamento.getStartDateTime()));
//                        } else {
//                            actividadActaInici.setStartDateTime(fechaCopiaActa);
//                            actividadActaInici.setEndDateTime(fechaFin);
//                        }
//                    }
//
//                }
//            }
//        }
    }
    
    public static void validarFechaActaInicioTO(List<Actividadobra> lstTodasActividades, Contrato contrato) {
        
//        Actividadobra actividadActaInici = null;
//        Actividadobra actividadReglamento = null;
//        for (Actividadobra actividad : lstTodasActividades) {
//            if (actividad.getStrdescactividad().equals("Acta de Inicio del Convenio")) {
//                actividadActaInici = actividad;
//            } else if (actividad.getStrdescactividad().equals("Reglamento Comité Operativo")) {
//                actividadReglamento = actividad;
//            }
//        }
//        if (actividadActaInici != null) {
//            if (contrato.getFechaactaini().compareTo(actividadActaInici.getFechaInicio()) != 0) {
//                if (actividadReglamento != null) {
//                      java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
//                    if (contrato.getFechaactaini().compareTo(actividadReglamento.getFechaInicio()) > 0) {
//                        throw new ConvenioException("La fecha del acta de inicio no puede ser superior a la fecha del reglamento del comite operativo:" + sdf.format(actividadReglamento.getFechaInicio()));
//
//                    } else {
//                        Calendar cale = new GregorianCalendar();
//                        cale.setLenient(false);
//                        cale.setTime(contrato.getFechaactaini());
//                        Date fechaCopiaActa = cale.getTime();
//                        Calendar cal = new GregorianCalendar();
//                        cal.setLenient(false);
//                        cal.setTime(fechaCopiaActa);
//                        cal.add(Calendar.DAY_OF_MONTH, actividadActaInici.getDuracion());
//                        Date fechaFin = cal.getTime();
//                        
//                        if (fechaFin.compareTo(actividadReglamento.getFechaInicio()) > 0) {
//                            throw new ConvenioException("La fecha fin del acta de inicio no puede ser superior a la fecha del reglamento del comite operativo:" + sdf.format(actividadReglamento.getFechaInicio()));
//                        } else {
//                            actividadActaInici.setFechaInicio(fechaCopiaActa);
//                            actividadActaInici.setFechaFin(fechaFin);
//                        }
//                    }
//
//                }
//            }
//        }
    }
}
