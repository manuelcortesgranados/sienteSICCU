/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.services;

import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 *
 * @author desarrollo9
 */
@RemoteServiceRelativePath("springGwtServices/cobraGwtServiceAble")
public interface CobraGwtServiceAble extends RemoteService{    
    
    public ContratoDTO getContratoDTO();
    public void setContratoDTO(ContratoDTO contrato);
    public ContratoDTO casteoContrato() throws Exception;    
    public void setLog(String log) throws Exception;
//    public void agregarContratoTemporal(ContratoDTO contratoDto)throws Exception;
//    public void agregarTareaTemporal(ActividadObraDTO actividadDto) throws Exception;
//    public void actualizarTareaTemporal(ActividadObraDTO actividadDto)throws Exception;
//    public void agregarDependencia(DependenciaDTO dependenciaDto)throws Exception;
//    public void eliminarDependencia(DependenciaDTO dependenciaDto)throws Exception;
}