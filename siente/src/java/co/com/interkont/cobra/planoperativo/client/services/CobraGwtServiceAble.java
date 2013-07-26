/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.services;

//import co.com.interkont.cobra.to.Tipoobra;





import co.com.interkont.cobra.planoperativo.client.dto.AlarmaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.to.Contrato;
//import co.com.interkont.cobra.planoperativo.shared.dto.Sector;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.interkont.cobra.dto.ActividadObraDTO;





/**
 *
 * @author desarrollo9
 */
@RemoteServiceRelativePath("springGwtServices/cobraGwtServiceAble")
public interface CobraGwtServiceAble extends RemoteService{    
  
    public Contrato getContrato();
    public void setContrato(Contrato contrato);
      
    public AlarmaDTO findAlarma(int id_alarma) throws Exception;
    public void pruebacomGWTJSF(int cont)  throws Exception;
//    public void agregarContratoTemporal(ContratoDTO contratoDto)throws Exception;
//    public void agregarTareaTemporal(ActividadObraDTO actividadDto) throws Exception;
//    public void actualizarTareaTemporal(ActividadObraDTO actividadDto)throws Exception;
//    public void agregarDependencia(DependenciaDTO dependenciaDto)throws Exception;
//    public void eliminarDependencia(DependenciaDTO dependenciaDto)throws Exception;
}
