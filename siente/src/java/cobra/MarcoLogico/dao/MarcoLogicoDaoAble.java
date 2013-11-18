/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.MarcoLogico.dao;

import co.com.interkont.cobra.marcologico.to.Avanceplanificacionrelacionmarcologicoindicador;
import co.com.interkont.cobra.marcologico.to.Estrategia;
import co.com.interkont.cobra.marcologico.to.Marcologico;
import co.com.interkont.cobra.marcologico.to.Relacionmarcologicoindicador;
import co.com.interkont.cobra.marcologico.vista.VistaCalculoIndicadorProyectos;
import co.com.interkont.cobra.to.Obra;
import cobra.dao.AbstractSpringDaoAble;
import com.interkont.cobra.exception.DaoException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Carlos Loaiza
 */
public interface MarcoLogicoDaoAble extends AbstractSpringDaoAble {

    public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicadorXcodigoObraByProposito(int codigoObra) throws DaoException;
    
    public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicadorXcodigoObrabyComponente(int codigoObra) throws DaoException;

    public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicadorXcodigoObrabyActividad(int codigoObra) throws DaoException;
    
    public void eliminarCrnogramaObligacion(int idobligacion) throws DaoException;
    
    public Estrategia encontrarEstrategiaProyectoMarcoLogico(int idobra) throws DaoException;
    
    public List<Marcologico> encontrarMarcologicoByCodigoObraByProposito(int codigoObra, int tipo) throws DaoException;
    
    public VistaCalculoIndicadorProyectos obtenerVistaProyectosMarcoxTipo(int tipomarcologico, int intcodigoobra) throws DaoException;
    public List<Relacionmarcologicoindicador> encontrarRelMarcoIndXcodigoObra(int codigoObra) throws DaoException;
    
    public List encontrarAvancesPlanificadosByObra(final int idobra) throws DaoException;
    
    public BigDecimal encontrarAvanceConvenioMediosVida(int incodconvenio) throws DaoException;
}
