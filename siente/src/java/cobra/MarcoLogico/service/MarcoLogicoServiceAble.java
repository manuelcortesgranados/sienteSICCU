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
import co.com.interkont.cobra.marcologico.to.Obligacion;
import co.com.interkont.cobra.marcologico.to.Marcologico;
import co.com.interkont.cobra.marcologico.to.Planificacionrelacionmarcologicoindicador;
import co.com.interkont.cobra.marcologico.to.Relacionmarcologicoindicador;
import co.com.interkont.cobra.marcologico.vista.VistaCalculoIndicadorProyectos;
import co.com.interkont.cobra.to.Obra;
import com.interkont.cobra.exception.DaoException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author desarrollo6
 */
public interface MarcoLogicoServiceAble {

    public void guardarContratoEstrategia(Contratoestrategia contratoestrategia) throws DaoException;

    public List<Obligacion> encontrarObligacionesxContrato(int intcontrato) throws DaoException;

    public void guardarObligacion(Obligacion obligacion, String usulogin) throws DaoException;

    public void guardarAvanceObligacion(Cronogramaobligacionesejecutada cronogramaobligacionesejecutada, String usulogin) throws DaoException;

    public void borrarObligacion(Obligacion obligacion) throws DaoException;

    public List<Cronogramaobligacionesejecutada> encontrarCronogramaObligacionEjecutadaxObligacion(Obligacion obligacion) throws DaoException;

    public List<Marcologico> encontrarMarcoLogicoxProyecto(int intcodigoobra) throws DaoException;

    public List<Indicador> encontrarIndicador(int intcodigoobra) throws DaoException;

    public void guardarAsociacionMarcoLogicoIndicador(Relacionmarcologicoindicador relacionmarcologicoindicador) throws DaoException;

    public void guardarAvancesByAsociaciones(Avanceplanificacionrelacionmarcologicoindicador avanceplanificacionrelacionmarcologicoindicador) throws DaoException;
    
    public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicador() throws DaoException;

   
    public List<Marcologico> encontrarMarcoLogicoAsociado(int intmarcologico) throws DaoException;
    
    public List<Indicador> encontrarIndicadorAsociado(int intindicador) throws DaoException;
    
    public void guardarAvancePlanificacionRelacionMarcolIndicador(Avanceplanificacionrelacionmarcologicoindicador avanceplanificacionrelacionmarcologicoindicador, String usulogin) throws DaoException;
    
    public void guardarPlanificacionRelacionMarcolIndicador(Planificacionrelacionmarcologicoindicador planificacionrelacionmarcologicoindicador, String usulogin) throws DaoException;
    
    public List<Planificacionrelacionmarcologicoindicador> encontrarPlanificacionRelacionMarcoLindicador(Relacionmarcologicoindicador relacionmarcologicoindicador) throws DaoException;
    
    public List<Avanceplanificacionrelacionmarcologicoindicador> encontrarAvancePlanificacionRelacionMarcoLindicador(Relacionmarcologicoindicador relacionmarcologicoindicador) throws DaoException;
    
    public Indicador encontrarIndicadorXId(int intidIndicador);
    
     public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicadorXcodigoObra( int codigoObra) throws DaoException;
     
     public void eliminarCronogramaObligacion(int idobligacion) throws DaoException;
     
     
    public Estrategia encontrarEstrategiaProyectoMarcoLogico(int idobra) throws DaoException;
     
     public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicadorXcodigoObraByComponente(int codigoObra) throws DaoException;
     
     public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicadorXcodigoObraByActividad(int codigoObra) throws DaoException;
     
     public List<Marcologico> encontrarMarcoLogicoPropositoXCodigoProyecto(int intcodigoobra,int tipo) throws DaoException;
     
     public VistaCalculoIndicadorProyectos obtenerVistaProyectosMarcoxTipo(int tipomarcologico, int intcodigoobra) throws DaoException;
     
     public List<Relacionmarcologicoindicador> encontrarRelMarcoIndXcodigoObra(int codigoObra) throws DaoException;
     
     public List<Avanceplanificacionrelacionmarcologicoindicador> encontrarAvancePlanificadosByObra(int idObra) throws DaoException;
     
     public BigDecimal encontrarAvanceConvenioMediosVida(int incodconvenio) throws DaoException;
     
     public List<Avanceplanificacionrelacionmarcologicoindicador> encontrarAvancePlanificadosXIdAvance(int idrelacionMarcolIndicador) throws DaoException ;
}
