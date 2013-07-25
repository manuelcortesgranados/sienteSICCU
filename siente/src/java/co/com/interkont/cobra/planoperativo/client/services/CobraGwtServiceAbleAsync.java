/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.services;

import co.com.interkont.cobra.planoperativo.client.dto.AlarmaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
//import co.com.interkont.cobra.planoperativo.shared.dto.Sector;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.interkont.cobra.dto.ActividadObraDTO;

/**
 *
 * @author desarrollo9
 */
public interface CobraGwtServiceAbleAsync {

//    void getContratoDto(AsyncCallback<ContratoDTO> call);
//
//    void setContratoDto(ContratoDTO contratoDto,AsyncCallback<Void> call);

    void findAlarma(int id_alarma, AsyncCallback<AlarmaDTO> call);

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
