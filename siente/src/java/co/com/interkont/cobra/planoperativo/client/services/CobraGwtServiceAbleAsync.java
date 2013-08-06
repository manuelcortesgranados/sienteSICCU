/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.services;

import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author desarrollo9
 */
public interface CobraGwtServiceAbleAsync {

    void getContratoDTO(AsyncCallback<ContratoDTO> call);

    void setContratoDTO(ContratoDTO contratoDto, AsyncCallback<Void> call);

    void casteoContrato(AsyncCallback<ContratoDTO> call);

    void setLog(String log, AsyncCallback<Void> call);

    void ObtenerContratoDTO(int idcontrato, AsyncCallback<ContratoDTO> call);
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