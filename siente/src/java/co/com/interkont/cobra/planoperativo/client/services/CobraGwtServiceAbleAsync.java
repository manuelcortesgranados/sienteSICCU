/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.services;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author desarrollo9
 */
public interface CobraGwtServiceAbleAsync {

    void obtenerContratoDTO(AsyncCallback<ContratoDTO> call);

    void setContratoDto(ContratoDTO contratoDto, AsyncCallback<Boolean> call);

    void getContratoDto(AsyncCallback<ContratoDTO> call);

    //void casteoContrato(AsyncCallback<ContratoDTO> call);
    void setLog(String log, AsyncCallback<Void> call);

    //void ObtenerContratoDTO(int idcontrato, AsyncCallback<ContratoDTO> call);
    void obtenerActividadesObligatorias(Date fecini, int duracion, Date fecactaini, Date fechafin, AsyncCallback<ArrayList<ActividadobraDTO>> call);

    void obtenerRubros(String categoria, AsyncCallback<List<RubroDTO>> call);

    void obtenerCategoriasRubros(AsyncCallback<List<RubroDTO>> call);

    void obtenerTiposContrato(AsyncCallback<List> call);

    void setNavegacion(int navegacion, AsyncCallback<Boolean> call);

    void getNavegacion(AsyncCallback<Integer> call);

    // metodo para guardar convenio
    void getGuardarconvenio(AsyncCallback<Integer> call);

    void setGuardarconvenio(int guardarconvenio, AsyncCallback<Boolean> call);

//    void getSeCargoPlanOperativoAntes(AsyncCallback<Boolean> call);
//
//    void setSeCargoPlanOperativoAntes(boolean seCargoPlanOperativoAntes, AsyncCallback<Void> call);
    void getListaacteliminar(AsyncCallback<List<ActividadobraDTO>> call);

    void setListaacteliminar(List<ActividadobraDTO> listaacteliminar, AsyncCallback<Void> call);

    void adicionarActividadDtoEliminar(ActividadobraDTO actdto, AsyncCallback<Void> call);

    void getDependenciasEliminar(AsyncCallback<Set> call);

    void setDependenciasEliminar(Set dependenciasEliminar, AsyncCallback<Void> call);

    void adicionarDepenciatoEliminar(DependenciaDTO dep, AsyncCallback<Void> call);

    void isElimino(AsyncCallback<Boolean> call);

    void setElimino(boolean elimino, AsyncCallback<Void> call);    
}