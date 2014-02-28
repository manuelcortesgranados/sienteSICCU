/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.server.services;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import co.com.interkont.cobra.planoperativo.client.dto.TipocontratoDTO;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import cobra.dao.CobraDaoAble;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cobra.util.CasteoGWT;
import co.com.interkont.cobra.to.Parametricaactividadesobligatorias;
import co.com.interkont.cobra.to.Rubro;
import co.com.interkont.cobra.to.Tipocontrato;
import com.gantt.client.config.GanttConfig;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.util.DateWrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author desarrollo9
 */
@Service("cobraGwtServiceAble")
public class CobraGwtServiceImpl extends RemoteServiceServlet implements CobraGwtServiceAble {

    @Autowired
    private CobraDaoAble cobraDao;
    private ContratoDTO contratoDto;
    private final Log log = LogFactory.getLog(this.getClass());
    private int navegacion = 1;
    private int guardarconvenio = 0;
    private boolean elimino = false;
    //private boolean seCargoPlanOperativoAntes = false;
    private List<ActividadobraDTO> listaacteliminar = new ArrayList<ActividadobraDTO>();
    private Set dependenciasEliminar = new LinkedHashSet(0);   
    private boolean fullScreen=false;
    /**
     * @return the dependenciasEliminar
     */
    @Override
    public Set getDependenciasEliminar() {
        return dependenciasEliminar;
    }
    /**
     * @param dependenciasEliminar the dependenciasEliminar to set
     */
    @Override
    public void setDependenciasEliminar(Set dependenciasEliminar) {
        this.dependenciasEliminar = dependenciasEliminar;
    }

    @Override
    public List<ActividadobraDTO> getListaacteliminar() {
        return listaacteliminar;
    }

    @Override
    public void setListaacteliminar(List<ActividadobraDTO> listaacteliminar) {
        this.listaacteliminar = listaacteliminar;
    }

    public CobraDaoAble getCobraDao() {
        return cobraDao;
    }

    public void setCobraDao(CobraDaoAble cobraDao) {
        this.cobraDao = cobraDao;
    }

    @Override
    public ContratoDTO obtenerContratoDTO() {
        if (contratoDto != null) {
            if (contratoDto.getActividadobras().isEmpty()) {
                try {
                    contratoDto.setActividadobras(new LinkedHashSet(obtenerActividadesObligatorias(contratoDto.getDatefechaini(), contratoDto.getIntduraciondias(), contratoDto.getDatefechaactaini(), contratoDto.getDatefechafin())));
                    obtenerDependenciasObligatorias();
//seCargoPlanOperativoAntes = true;
                } catch (Exception ex) {
                    Logger.getLogger(CobraGwtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return this.contratoDto;
    }

    @Override
    public Boolean setContratoDto(ContratoDTO contrato) {

        this.contratoDto = contrato;
        return true;
    }

    @Override
    public ContratoDTO getContratoDto() {
        return contratoDto;
    }

    @Override
    public void setLog(String log) {
        this.log.info(log);
    }

    public void obtenerDependenciasObligatorias() {
        List<ActividadobraDTO> lstActiObligatorias = new ArrayList<ActividadobraDTO>(contratoDto.getActividadobras());
        for (ActividadobraDTO actiHija : lstActiObligatorias.get(0).getChildren()) {
            if (actiHija.getName().equals("Acta de Inicio del Convenio")) {
                //System.out.println("aca" + actiHija.getName());
                //enlazarDependenciasIniciales("Reglamento de Plan Operativo", lstActiObligatorias.get(0).getChildren(), actiHija);
            } else if (actiHija.getName().equals("Reglamento de Plan Operativo")) {
                //System.out.println("aca" + actiHija.getName());
                //enlazarDependenciasIniciales("Aprobación de Plan Operativo", lstActiObligatorias.get(0).getChildren(), actiHija);
            } else if (actiHija.getName().equals("Planeación del Convenio")) {
                enlazarDependenciasIniciales(lstActiObligatorias.get(0).getChildren().get(1), actiHija);
            } else if (actiHija.getName().equals("Ejecución del Convenio")) {
                enlazarDependenciasIniciales(lstActiObligatorias.get(0).getChildren().get(2), actiHija);
            }
        }
        // System.out.println("lstDependencias:" + contratoDto.getDependenciasGenerales().size());
    }

    public void enlazarDependenciasIniciales(ActividadobraDTO actividadTo, ActividadobraDTO actividadFrom) {
        if (actividadTo != null) {
            contratoDto.getDependenciasGenerales().add(crearDependencia(actividadFrom, actividadTo));
        }
    }

    public DependenciaDTO crearDependencia(ActividadobraDTO actividadFrom, ActividadobraDTO actividadTo) {
        DependenciaDTO dep = new DependenciaDTO();
        dep.setId("" + dep.hashCode());
        dep.setFromId(actividadFrom.getId());
        dep.setToId(actividadTo.getId());
        dep.setActividadFrom(actividadFrom);
        dep.setActividadTo(actividadTo);
        dep.setType(GanttConfig.DependencyType.ENDtoSTART);
        dep.setIsobligatoria(true);
        return dep;
    }

    public ActividadobraDTO buscarActividad(String nombreActividad, List<ActividadobraDTO> lstActividades) {
        for (ActividadobraDTO act : lstActividades) {
            if (act.getName().equals(nombreActividad)) {
                return act;
            }
        }
        return null;
    }

    @Override
    public ArrayList<ActividadobraDTO> obtenerActividadesObligatorias(Date fecini, int duracion, Date fecactaini, Date fechafin) throws Exception {
        int numeracion = 1;
        Date fechaPlaneacion = new Date();
        int dura=CalendarUtil.getDaysBetween(fecactaini, fechafin);
        ActividadobraDTO t = new ActividadobraDTO(contratoDto.getStrnumcontrato(), fecactaini, dura+1,
                0, GanttConfig.TaskType.PARENT, 1, false);
        t.setTipoActividad(1);
        t.setEditable(true);
        t.setNumeracion(numeracion);
        numeracion++;


        List<Parametricaactividadesobligatorias> listapar = cobraDao.encontrarTodoOrdenadoporcampo(Parametricaactividadesobligatorias.class, "idparametrica");
        Iterator itparametricas = listapar.iterator();
        ArrayList<ActividadobraDTO> listaactobligatorias = new ArrayList<ActividadobraDTO>();
        while (itparametricas.hasNext()) {
            Parametricaactividadesobligatorias par = (Parametricaactividadesobligatorias) itparametricas.next();           
           
            if (par.getParametricaactividadesobligatorias() == null) {
                ActividadobraDTO actdto = null;
                if (par.getIdparametrica() == 3) {
                     DateWrapper dw = new DateWrapper(contratoDto.getDatefechafin()).clearTime();                
                             
                    actdto = CasteoGWT.castearParametricaactividadesobligatoriasToActividadobraDTO(par,  dw.asDate(), 1, 0);                    
                    actdto.setEditable(par.getEditable());    
                    
                    actdto.setNumeracion(numeracion);
                    numeracion++;
                } else if (par.getIdparametrica() == 1) {
                     DateWrapper dw = new DateWrapper(contratoDto.getDatefechaactaini()).clearTime(); 
                    actdto = CasteoGWT.castearParametricaactividadesobligatoriasToActividadobraDTO(par, dw.asDate(), 1, 0);
                    //actdto.setEndDateTime(CalendarUtil.copyDate(actdto.getStartDateTime()));
                    actdto.setEditable(par.getEditable());                    
                    actdto.setNumeracion(numeracion);
                    numeracion++;
                    //CalendarUtil.addDaysToDate(actdto.getEndDateTime(), 1);
                    fechaPlaneacion = dw.addDays(1).asDate();


                } else if (par.getIdparametrica() == 2) {
                    
                    int dur=CalendarUtil.getDaysBetween(fechaPlaneacion,fechafin);                    
                    actdto = CasteoGWT.castearParametricaactividadesobligatoriasToActividadobraDTO(par, fechaPlaneacion, dur, 0);
                    
                    actdto.setEditable(par.getEditable());
                    actdto.setNumeracion(numeracion);
                    numeracion++;
                    
                } else {
                    actdto = CasteoGWT.castearParametricaactividadesobligatoriasToActividadobraDTO(par, fecini, 1, 0);
                    actdto.setNumeracion(numeracion);
                    actdto.setEditable(par.getEditable());
                    numeracion++;
                }
                for (Parametricaactividadesobligatorias parhija : listapar) {

                    if (parhija.getParametricaactividadesobligatorias() != null && parhija.getParametricaactividadesobligatorias().getIdparametrica() == par.getIdparametrica()) {
                        //Coloca 1 dia para Acta de Inicio de convenio
                        if (parhija.getIdparametrica() == 4) {
                            ActividadobraDTO actiHija = CasteoGWT.castearParametricaactividadesobligatoriasToActividadobraDTO(parhija, CalendarUtil.copyDate(contratoDto.getDatefechaactaini()), 1, 0);
                            actiHija.setNumeracion(numeracion);
                            numeracion++;
                            actiHija.setEditable(parhija.getEditable());                            
                            actdto.addChild(actiHija);
                        } else if (parhija.getIdparametrica() == 7) {                            
                            DateWrapper dw = new DateWrapper(contratoDto.getDatefechafin()).clearTime();                
                            ActividadobraDTO actiHija=CasteoGWT.castearParametricaactividadesobligatoriasToActividadobraDTO(parhija, dw.asDate(), 1, 0);
                            actiHija.setNumeracion(numeracion);
                            numeracion++;
                            actiHija.setEditable(parhija.getEditable());                            
                            actdto.addChild(actiHija);
                        } else {
                            ActividadobraDTO actiHija = CasteoGWT.castearParametricaactividadesobligatoriasToActividadobraDTO(parhija, fecactaini, 1, 0);
                            actiHija.setNumeracion(numeracion);
                            numeracion++;
                            actiHija.setEditable(parhija.getEditable());                            
                            actdto.addChild(actiHija);
                        }
                    }
                }
                listaactobligatorias.add(actdto);
            }
        }

        t.setChildren(new ArrayList<ActividadobraDTO>(listaactobligatorias));
        ArrayList<ActividadobraDTO> list = new ArrayList<ActividadobraDTO>();
        list.add(t);

        return list;
    }

    @Override
    public List<RubroDTO> obtenerRubros(String categoria) throws Exception {
        List<Rubro> lstRubros = cobraDao.encontrarPorcadenailikeinicial(Rubro.class, "idrubro", categoria);
        List<RubroDTO> lstRubrosDTO = new ArrayList<RubroDTO>(lstRubros.size());
        for (Rubro rubro : lstRubros) {
            lstRubrosDTO.add(new RubroDTO(rubro.getIdrubro(), rubro.getStrdescripcion()));
        }
        return lstRubrosDTO;
    }

    @Override
    public int getNavegacion() {
        return navegacion;
    }

    @Override
    public Boolean setNavegacion(int navegacion) {
        this.navegacion = navegacion;
        return true;
    }

    @Override
    public int getGuardarconvenio() {
        return guardarconvenio;
    }

    @Override
    public Boolean setGuardarconvenio(int guardarconvenio) {
        this.guardarconvenio = guardarconvenio;
        return true;
    }

    @Override
    public List obtenerTiposContrato() throws Exception {
        List<Tipocontrato> lstTipoContrato = cobraDao.obtenerTipoContrato();
        List<TipocontratoDTO> lstTipoContratoDto = new ArrayList<TipocontratoDTO>(lstTipoContrato.size());
        for (Tipocontrato tc : lstTipoContrato) {
            lstTipoContratoDto.add(new TipocontratoDTO(tc.getInttipocontrato(), tc.getStrdesctipocontrato()));
        }

        return lstTipoContratoDto;

    }

    @Override
    public List<RubroDTO> obtenerCategoriasRubros() throws Exception {
        List<Rubro> lstRubros = cobraDao.obtenerCategoriasRubros();

        List<RubroDTO> lstRubrosDTO = new ArrayList<RubroDTO>();
        for (Rubro rubro : lstRubros) {
            lstRubrosDTO.add(new RubroDTO(rubro.getIdrubro(), rubro.getStrdescripcion()));
        }
        return lstRubrosDTO;
    }

//    @Override
//    public boolean getSeCargoPlanOperativoAntes() {
//        return seCargoPlanOperativoAntes;
//    }
//
//    @Override
//    public void setSeCargoPlanOperativoAntes(boolean seCargoPlanOperativoAntes) {
//        this.seCargoPlanOperativoAntes = seCargoPlanOperativoAntes;
//    }
    @Override
    public void adicionarActividadDtoEliminar(ActividadobraDTO actdto) {
        getListaacteliminar().add(actdto);
    }

    @Override
    public void adicionarDepenciatoEliminar(DependenciaDTO dep) {
        getDependenciasEliminar().add(dep);
    }

    /**
     * @return the elimino
     */
    @Override
    public boolean isElimino() {
        return elimino;
    }

    /**
     * @param elimino the elimino to set
     */
    @Override
    public void setElimino(boolean elimino) {
        this.elimino = elimino;
    }    

    /**
     * @return the fullScreen
     */
    public boolean isFullScreen() {
        return fullScreen;
}

    /**
     * @param fullScreen the fullScreen to set
     */
    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }
}
