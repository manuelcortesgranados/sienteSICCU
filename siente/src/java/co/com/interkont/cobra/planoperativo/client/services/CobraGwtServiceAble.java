/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.services;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author desarrollo9
 */
@RemoteServiceRelativePath("springGwtServices/cobraGwtServiceAble")
public interface CobraGwtServiceAble extends RemoteService {

    public ContratoDTO obtenerContratoDTO();

    public Boolean setContratoDto(ContratoDTO contrato);

    public ContratoDTO getContratoDto();
    //public ContratoDTO casteoContrato() throws Exception;    

    public void setLog(String log) throws Exception;
    //    Metodo Para obtener el contrato 
    //public ContratoDTO ObtenerContratoDTO(int idcontrato) throws  Exception;

    public ArrayList<ActividadobraDTO> obtenerActividadesObligatorias(Date fecini, int duracion, Date fecactaini, Date fechafin) throws Exception;

    public List<RubroDTO> obtenerRubros(String categoria) throws Exception;

    public List<RubroDTO> obtenerCategoriasRubros() throws Exception;

    public List obtenerTiposContrato() throws Exception;

    public Boolean setNavegacion(int navegacion);

    public int getNavegacion();

    public int getGuardarconvenio();

    public Boolean setGuardarconvenio(int guardarconvenio);
//    public boolean getSeCargoPlanOperativoAntes();
//    public void setSeCargoPlanOperativoAntes(boolean seCargoPlanOperativoAntes);

    public List<ActividadobraDTO> getListaacteliminar();

    public void setListaacteliminar(List<ActividadobraDTO> listaacteliminar);

    public void adicionarActividadDtoEliminar(ActividadobraDTO actdto);

    public Set getDependenciasEliminar();

    public void setDependenciasEliminar(Set dependenciasEliminar);

    public void adicionarDepenciatoEliminar(DependenciaDTO dep);

//    public void agregarContratoTemporal(ContratoDTO contratoDto)throws Exception;
//    public void agregarTareaTemporal(ActividadObraDTO actividadDto) throws Exception;
//    public void actualizarTareaTemporal(ActividadObraDTO actividadDto)throws Exception;
//    public void agregarDependencia(DependenciaDTO dependenciaDto)throws Exception;
//    public void eliminarDependencia(DependenciaDTO dependenciaDto)throws Exception;
    public boolean isElimino();
    public void setElimino(boolean elimino);
    public boolean isFullScreen();
    public void setFullScreen(boolean fullScreen);
}