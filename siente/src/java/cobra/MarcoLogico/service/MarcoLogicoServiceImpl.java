/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.MarcoLogico.service;

import co.com.interkont.cobra.marcologico.to.Avanceplanificacionrelacionmarcologicoindicador;
import co.com.interkont.cobra.marcologico.to.Contratoestrategia;
import co.com.interkont.cobra.marcologico.to.Cronogramaobligacionesejecutada;
import co.com.interkont.cobra.marcologico.to.Estrategia;
import co.com.interkont.cobra.marcologico.to.Indicador;
import co.com.interkont.cobra.marcologico.to.Marcologico;
import co.com.interkont.cobra.marcologico.to.Obligacion;
import co.com.interkont.cobra.marcologico.to.Planificacionrelacionmarcologicoindicador;
import co.com.interkont.cobra.marcologico.to.Relacionmarcologicoindicador;
import co.com.interkont.cobra.marcologico.vista.VistaCalculoIndicadorProyectos;
import cobra.MarcoLogico.dao.MarcoLogicoDaoAble;
import com.interkont.cobra.exception.DaoException;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author desarrollo6
 */
public class MarcoLogicoServiceImpl implements MarcoLogicoServiceAble {
    //variables de marco logico

    private MarcoLogicoDaoAble marcoLogicoDao;
    private Log log = LogFactory.getLog(this.getClass());

    public MarcoLogicoDaoAble getMarcoLogicoDao() {
        return marcoLogicoDao;
    }

    public void setMarcoLogicoDao(MarcoLogicoDaoAble marcoLogicoDao) {
        this.marcoLogicoDao = marcoLogicoDao;
    }

    //Metodos de marco logico
    public void guardarContratoEstrategia(Contratoestrategia contratoestrategia) {
        log.debug("#DDD############ guardarContratoEstrategia in Service Cobra Marco Logico");
        marcoLogicoDao.guardarOrActualizar(contratoestrategia);
    }

    public List<Obligacion> encontrarObligacionesxContrato(int intcontrato) throws DaoException {
        return marcoLogicoDao.encontrarTodoPorCampo(Obligacion.class, "fkIntidcontrato", intcontrato,"intidobligacion");
    }

    public List<Cronogramaobligacionesejecutada> encontrarCronogramaObligacionEjecutadaxObligacion(Obligacion obligacion) throws DaoException {
        log.debug("#DDD############ encontrarCronogramaObligacionEjecutadaxObligacion() in Service Cobra");
        return marcoLogicoDao.encontrarConJoin(Cronogramaobligacionesejecutada.class, "obligacion", "intidobligacion", obligacion.getIntidobligacion(), "datefechaavance");

    }

    public List<Marcologico> encontrarMarcoLogicoxProyecto(int intcodigoobra) throws DaoException {
        log.debug("#DDD############ encontrarMarcoLogicoxProyecto() in Service Cobra");
        return marcoLogicoDao.encontrarTodoPorCampo(Marcologico.class, "fkIntcodigoobra", intcodigoobra);

    }
    
    public List<Marcologico> encontrarMarcoLogicoPropositoXCodigoProyecto(int intcodigoobra, int tipo) throws DaoException {
        log.debug("#DDD############ encontrarMarcoLogicoPropositoXCodigoProyecto() in Service Cobra");
        return marcoLogicoDao.encontrarMarcologicoByCodigoObraByProposito(intcodigoobra, tipo);

    }
    
    

    public List<Indicador> encontrarIndicador(int intcodigoobra) throws DaoException {
        log.debug("#DDD############ encontrarIndicador() in Service Cobra");
        return marcoLogicoDao.encontrarTodoPorCampo(Indicador.class, "fkIntcodigoobra", intcodigoobra);
    }

    public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicador() throws DaoException {
        log.debug("#DDD############ encontrarIndicador() in Service Cobra");
        return marcoLogicoDao.encontrarTodoOrdenadoporcampo(Relacionmarcologicoindicador.class, "intidrelacionmarcologicoindicador");
    }

    public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicadorXcodigoObra(int codigoObra) throws DaoException {
        log.debug("#DDD############ encontrarRelacionmarcologicoindicadorXcodigoObra() in Service Cobra");
        return marcoLogicoDao.encontrarRelacionmarcologicoindicadorXcodigoObraByProposito(codigoObra);
    }
    
    public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicadorXcodigoObraByComponente(int codigoObra) throws DaoException {
        log.debug("#DDD############ encontrarRelacionmarcologicoindicadorXcodigoObraByComponente() in Service Cobra");
        return marcoLogicoDao.encontrarRelacionmarcologicoindicadorXcodigoObrabyComponente(codigoObra);
    }
    
    public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicadorXcodigoObraByActividad(int codigoObra) throws DaoException {
        log.debug("#DDD############ encontrarRelacionmarcologicoindicadorXcodigoObraByActividad() in Service Cobra");
        return marcoLogicoDao.encontrarRelacionmarcologicoindicadorXcodigoObrabyActividad(codigoObra);
    }
    
    

    public List<Marcologico> encontrarMarcoLogicoAsociado(int intmarcologico) throws DaoException {
        log.debug("#DDD############ encontrarMarcoLogicoAsociado() in Service Cobra");
        return marcoLogicoDao.encontrarTodoPorCampo(Marcologico.class, "intidmarcologico", intmarcologico);

    }

    public List<Indicador> encontrarIndicadorAsociado(int intindicador) throws DaoException {
        log.debug("#DDD############ encontrarIndicadorAsociado() in Service Cobra");
        return marcoLogicoDao.encontrarTodoPorCampo(Indicador.class, "intidindicador", intindicador);

    }

    public List<Planificacionrelacionmarcologicoindicador> encontrarPlanificacionRelacionMarcoLindicador(Relacionmarcologicoindicador relacionmarcologicoindicador) throws DaoException {
        log.debug("#DDD############ encontrarIndicadorAsociado() in Service Cobra");
        return marcoLogicoDao.encontrarConJoin(Planificacionrelacionmarcologicoindicador.class, "relacionmarcologicoindicador", "intidrelacionmarcologicoindicador", relacionmarcologicoindicador.getIntidrelacionmarcologicoindicador(), "datefechaplanificada");

    }
    
     public List<Avanceplanificacionrelacionmarcologicoindicador> encontrarAvancePlanificacionRelacionMarcoLindicador(Relacionmarcologicoindicador relacionmarcologicoindicador) throws DaoException {
        log.debug("#DDD############ encontrarAvancePlanificacionRelacionMarcoLindicador() in Service Cobra");
        return marcoLogicoDao.encontrarConJoin(Avanceplanificacionrelacionmarcologicoindicador.class, "relacionmarcologicoindicador", "intidrelacionmarcologicoindicador", relacionmarcologicoindicador.getIntidrelacionmarcologicoindicador(), "datefechaplanificada");

    }

    public Indicador encontrarIndicadorXId(int intidIndicador) {
        log.debug("#DDD############ encontrarTerceroPorId() in Service Cobra");
        return (Indicador) marcoLogicoDao.obtenerPorId(Indicador.class, intidIndicador);

    }

    public void guardarObligacion(Obligacion obligacion, String usulogin) throws DaoException {
        log.debug("#DDD############ guardarObligacion() in Service Cobra");
        marcoLogicoDao.guardarOrActualizar(obligacion);
        log.info("guardarObligacion(" + obligacion.getIntidobligacion() + ", idcontrato " + obligacion.getFkIntidcontrato()+ " Usuario: "+usulogin+")" );
    }

    public void guardarAvanceObligacion(Cronogramaobligacionesejecutada cronogramaobligacionesejecutada, String usulogin) throws DaoException {
        log.debug("#DDD############ guardarAvanceObligacion() in Service Cobra");
        marcoLogicoDao.guardarOrActualizar(cronogramaobligacionesejecutada);
        log.info("guardarAvance("+cronogramaobligacionesejecutada.getIntidcronogobligejecutadas()+
                ", Obligación: " +cronogramaobligacionesejecutada.getObligacion().getIntidobligacion() + 
                ", idcontrato" + cronogramaobligacionesejecutada.getObligacion().getFkIntidcontrato()+ " Usuario: "+usulogin+")");
    }

    public void borrarObligacion(Obligacion obligacion) throws DaoException {
        //log.debug("#DDD############ borrarObligacionConvenio() in Service Cobra");
        marcoLogicoDao.borrar(obligacion);
    }

    public void guardarAsociacionMarcoLogicoIndicador(Relacionmarcologicoindicador relacionmarcologicoindicador) throws DaoException {
        log.debug("#DDD############ guardarAsociacionMarcoLogicoIndicador() in Service Cobra");
        marcoLogicoDao.guardarOrActualizar(relacionmarcologicoindicador);
    }

    public void guardarAvancesByAsociaciones(Avanceplanificacionrelacionmarcologicoindicador avanceplanificacionrelacionmarcologicoindicador)throws DaoException {
        log.debug("#DDD############ guardarAvancesByAsociaciones() in Service Cobra");
        marcoLogicoDao.guardarOrActualizar(avanceplanificacionrelacionmarcologicoindicador);
    }
    
    
    public void guardarAvancePlanificacionRelacionMarcolIndicador(Avanceplanificacionrelacionmarcologicoindicador avanceplanificacionrelacionmarcologicoindicador, String usulogin) throws DaoException {
        log.debug("#DDD############ guardarAvancePlanificacionRelacionMarcolIndicador() in Service Cobra");
        marcoLogicoDao.guardarOrActualizar(avanceplanificacionrelacionmarcologicoindicador);
        log.info("guardarAvancePlanificacionRelacionMarcolIndicador(" + avanceplanificacionrelacionmarcologicoindicador.getIntidavanceplanificacionrelacionmarcologicoindicador() + ", "
                + "idRelacionmarcologicoindicador " + avanceplanificacionrelacionmarcologicoindicador.getRelacionmarcologicoindicador() + "fechacreacion " + avanceplanificacionrelacionmarcologicoindicador.getDatefechacreacion()+"Usuario: "+usulogin+")" );
    }
    
     public void guardarPlanificacionRelacionMarcolIndicador(Planificacionrelacionmarcologicoindicador planificacionrelacionmarcologicoindicador, String usulogin) throws DaoException {
        log.debug("#DDD############ guardarAvancePlanificacionRelacionMarcolIndicador() in Service Cobra");
        marcoLogicoDao.guardarOrActualizar(planificacionrelacionmarcologicoindicador);
        log.info("guardarAvancePlanificacionRelacionMarcolIndicador(" + planificacionrelacionmarcologicoindicador.getIntidplanificacionrelacionmarcologicoindicador() + ", "
                + "idRelacionmarcologicoindicador " + planificacionrelacionmarcologicoindicador.getRelacionmarcologicoindicador() + "fechacreacion " + planificacionrelacionmarcologicoindicador.getDatefechacreacion()+"Usuario: "+usulogin+")" );
    }

    public void eliminarCronogramaObligacion(int idobligacion) throws DaoException {
        log.debug("#DDD############ guardarAvancePlanificacionRelacionMarcolIndicador() in Service Cobra");
        marcoLogicoDao.eliminarCrnogramaObligacion(idobligacion);
    }

    public Estrategia encontrarEstrategiaProyectoMarcoLogico(int idobra) throws DaoException{        
        log.debug("#DDD############ encontrarProyectoMarcoLogico(int idobr) in Service Cobra");
        return marcoLogicoDao.encontrarEstrategiaProyectoMarcoLogico(idobra);
    }

    public VistaCalculoIndicadorProyectos obtenerVistaProyectosMarcoxTipo(int tipomarcologico, int intcodigoobra) throws DaoException {
        log.debug("#DDD############ encontrarProyectoMarcoLogico(int idobr) in Service Cobra");
        
      return marcoLogicoDao.obtenerVistaProyectosMarcoxTipo(tipomarcologico,intcodigoobra);       
       
        
    }
    
    
    public List<Relacionmarcologicoindicador> encontrarRelMarcoIndXcodigoObra(int codigoObra) throws DaoException {
        log.debug("#DDD############ encontrarRelacionmarcologicoindicadorXcodigoObra() in Service Cobra");
        return marcoLogicoDao.encontrarRelMarcoIndXcodigoObra(codigoObra);
    }
    
    public List<Avanceplanificacionrelacionmarcologicoindicador> encontrarAvancePlanificadosByObra(int idObra) throws DaoException {
        log.debug("#DDD############ encontrarAvancePlanificadosByObra() in Service Codigoobra");
        return marcoLogicoDao.encontrarAvancesPlanificadosByObra(idObra);

    }

    public BigDecimal encontrarAvanceConvenioMediosVida(int incodconvenio) throws DaoException {
        log.debug("#DDD############ encontrarAvanceConvenioMediosVida() ");
        return marcoLogicoDao.encontrarAvanceConvenioMediosVida(incodconvenio);
    }
    
     public List<Avanceplanificacionrelacionmarcologicoindicador> encontrarAvancePlanificadosXIdAvance(int idrelacionMarcolIndicador) throws DaoException {
        log.debug("#DDD############ encontrarAvancePlanificadosByObra() in Service Codigoobra");
        return marcoLogicoDao.encontrarTodoPorCampo(Avanceplanificacionrelacionmarcologicoindicador.class, "relacionmarcologicoindicador.intidrelacionmarcologicoindicador", idrelacionMarcolIndicador);

    }
     
    
}
