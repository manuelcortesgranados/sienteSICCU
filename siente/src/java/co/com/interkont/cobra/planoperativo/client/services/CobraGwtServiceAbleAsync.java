/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.services;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.AlarmaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.to.Contrato;
//import co.com.interkont.cobra.planoperativo.shared.dto.Sector;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 *
 * @author desarrollo9
 */
public interface CobraGwtServiceAbleAsync {

   void getContrato(AsyncCallback<Contrato> call);
//
    void setContrato(Contrato contratoDto,AsyncCallback<Void> call);

    void findAlarma(int id_alarma, AsyncCallback<AlarmaDTO> call);
  
    void pruebacomGWTJSF(int cont,AsyncCallback<Void> call);
    void getActividadObraDTO(AsyncCallback<ActividadobraDTO> call );
    void setActividadObraDTO(ActividadobraDTO actividadObraDTO, AsyncCallback<Void> call);
    void casteoContrato(AsyncCallback<ContratoDTO> call);

//    void agregarContratoTemporal(ContratoDTO contratoDto, AsyncCallback<Void> call) throws Exception;
//
//    void agregarTareaTemporal(ActividadObraDTO actividadDto, AsyncCallback<Void> call) throws Exception;
//
//    void actualizarTareaTemporal(ActividadObraDTO actividadDto, AsyncCallback<Void> call) throws Exception;
//
//    void agregarDependencia(DependenciaDTO dependenciaDto, AsyncCallback<Void> call) throws Exception;
//
//    void eliminarDependencia(DependenciaDTO dependenciaDto, AsyncCallback<Void> call) throws Exception;
}
