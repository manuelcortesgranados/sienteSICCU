/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.exceptions;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author desarrollo2
 */
public class ValidacionesConvenio {
    
   public static void validarFechasConvenio(Date fechaini, Date fechafin) {
        if(fechaini == null || fechafin ==null)
        {
           throw new ConvenioException("Debe Ingresar las fechas del convenio");     
        }
        
        if (fechafin.compareTo(fechaini) < 0) {
            throw new ConvenioException("La fecha de finalización debe ser mayor a la fecha inicial");  
        }       
        
    }
   
   
   public static void validarValorPositivo(BigDecimal valor, String nombredelcampo) {
        if(valor == null )
        {
           throw new ConvenioException("Debe Ingresar el valor del "+nombredelcampo);     
        }
        
        if (valor.compareTo(BigDecimal.ZERO) <=0) {
            throw new ConvenioException("Debe ingresar un valor al "+nombredelcampo);  
        }       
        
    }
   
   public static void validarFechasPlanOperativo(Date fechaactaini, Date fechaini, Date fechafin) {
       validarFechasConvenio(fechaini, fechafin);
       if(fechaactaini == null )
        {
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
            if(lista.isEmpty())
            {
                throw new ConvenioException("Debe diligenciar la "+nombrelista);  
            }    
        
    }
   
   public static void validarDistribucionFuenteRecursos(BigDecimal valorcontrato, BigDecimal sumafuentes)
   {
       if(valorcontrato.compareTo(sumafuentes)<0)
       {
           throw new ConvenioException("Las fuentes de recursos, superan el valor del contrato ");  
       }    
   }
   
   
   
   
    
    
    
    
    
}
