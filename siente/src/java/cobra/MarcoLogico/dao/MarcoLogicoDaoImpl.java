/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.MarcoLogico.dao;

import co.com.interkont.cobra.marcologico.to.Avanceplanificacionrelacionmarcologicoindicador;
import co.com.interkont.cobra.marcologico.to.Contratoestrategia;
import co.com.interkont.cobra.marcologico.to.Estrategia;
import co.com.interkont.cobra.marcologico.to.Marcologico;
import co.com.interkont.cobra.marcologico.to.Relacionmarcologicoindicador;
import co.com.interkont.cobra.marcologico.vista.VistaCalculoIndicadorProyectos;
import co.com.interkont.cobra.to.Obra;
import cobra.dao.AbstractSpringDao;
import com.interkont.cobra.exception.DaoException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author desarrollo2
 */
public class MarcoLogicoDaoImpl extends AbstractSpringDao implements MarcoLogicoDaoAble {

    private Log log = LogFactory.getLog(this.getClass());

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicadorXcodigoObraByProposito(int codigoObra) throws DaoException {
        log.debug("#DDD######### getRelacionMarcologicoIndicadorXCodigoObra CobraDao");
        DetachedCriteria crit = DetachedCriteria.forClass(Relacionmarcologicoindicador.class).
                addOrder(Order.asc("intidrelacionmarcologicoindicador")).
                createCriteria("marcologico").add(Restrictions.eq("fkIntcodigoobra", codigoObra));
        crit.add(Restrictions.eq("tipomarcologico.intidtipomarcologico", 1));

        return getHibernateTemplate().findByCriteria(crit);

    }

    public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicadorXcodigoObrabyComponente(int codigoObra) throws DaoException {
        log.debug("#DDD######### getRelacionMarcologicoIndicadorXCodigoObra CobraDao");
        DetachedCriteria crit = DetachedCriteria.forClass(Relacionmarcologicoindicador.class).
                addOrder(Order.asc("intidrelacionmarcologicoindicador")).
                createCriteria("marcologico").add(Restrictions.eq("fkIntcodigoobra", codigoObra));
        crit.add(Restrictions.eq("tipomarcologico.intidtipomarcologico", 2));
        return getHibernateTemplate().findByCriteria(crit);

    }

    public List<Relacionmarcologicoindicador> encontrarRelacionmarcologicoindicadorXcodigoObrabyActividad(int codigoObra) throws DaoException {
        log.debug("#DDD######### getRelacionMarcologicoIndicadorXCodigoObra CobraDao");
        DetachedCriteria crit = DetachedCriteria.forClass(Relacionmarcologicoindicador.class).
                addOrder(Order.asc("intidrelacionmarcologicoindicador")).
                createCriteria("marcologico").add(Restrictions.eq("fkIntcodigoobra", codigoObra));
        crit.add(Restrictions.eq("tipomarcologico.intidtipomarcologico", 3));
        return getHibernateTemplate().findByCriteria(crit);

    }

    public List<Marcologico> encontrarMarcologicoByCodigoObraByProposito(int codigoObra, int tipo) throws DaoException {
        log.debug("#DDD######### encontrarMarcologicoByCodigoObraByProposito CobraDao");
        DetachedCriteria crit = DetachedCriteria.forClass(Marcologico.class).
                add(Restrictions.eq("fkIntcodigoobra", codigoObra))
                .add(Restrictions.eq("tipomarcologico.intidtipomarcologico", tipo));
        return getHibernateTemplate().findByCriteria(crit);

    }

    public void eliminarCrnogramaObligacion(int idobligacion) throws DaoException {
        Query query = getSession().createSQLQuery("delete from marco_logico.cronogramaobligacionesejecutada where fk_intidobligacion"
                + " =" + idobligacion);
        query.executeUpdate();
    }

    public Estrategia encontrarEstrategiaProyectoMarcoLogico(int idobra) throws DaoException {

        Query query1 = getSession().createSQLQuery("select estrategia.* from vista_proyectos_convenios_excel_ch "
                + "join marco_logico.contratoestrategia on codconvenio=fk_intidcontrato "
                + "join marco_logico.estrategia on fk_intidestrategia=intidestrategia "
                + "where codigoobrasiente= " + idobra).addEntity(Estrategia.class);

        List<Estrategia> list = query1.list();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }

    }

    public VistaCalculoIndicadorProyectos obtenerVistaProyectosMarcoxTipo(int tipomarcologico, int intcodigoobra) throws DaoException {
        log.debug("#DDD######### obtenerVistaProyectosMarcoxTipo CobraDao");
        DetachedCriteria crit = DetachedCriteria.forClass(VistaCalculoIndicadorProyectos.class);
        crit.add(Restrictions.eq("intcodigoobra", intcodigoobra));
        crit.add(Restrictions.eq("tipomarcologico.intidtipomarcologico", tipomarcologico));

        List<VistaCalculoIndicadorProyectos> list = getHibernateTemplate().findByCriteria(crit);
        if (list.isEmpty()) {
            return new VistaCalculoIndicadorProyectos();
        } else {
            return list.get(0);
        }

    }

    public List<Relacionmarcologicoindicador> encontrarRelMarcoIndXcodigoObra(int codigoObra) throws DaoException {
        log.debug("#DDD######### getRelacionMarcologicoIndicadorXCodigoObra CobraDao");
        DetachedCriteria crit = DetachedCriteria.forClass(Relacionmarcologicoindicador.class).
                addOrder(Order.asc("intidrelacionmarcologicoindicador")).
                createCriteria("marcologico").add(Restrictions.eq("fkIntcodigoobra", codigoObra));

        return getHibernateTemplate().findByCriteria(crit);

    }

    /**
     *
     * @param idobra
     * @return
     */
    public List encontrarAvancesPlanificadosByObra(final int idobra) throws DaoException {
        log.debug("#DDD######### obtenerAlarmasporTipoObra CobraDao ");
        List<Avanceplanificacionrelacionmarcologicoindicador> lista = new ArrayList<Avanceplanificacionrelacionmarcologicoindicador>();

        lista = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery("SELECT * FROM marco_logico.avanceplanificacionrelacionmarcologicoindicador "
                        + "JOIN marco_logico.relacionmarcologicoindicador ON avanceplanificacionrelacionmarcologicoindicador.fk_intidrelacionmarcologicoindicador = relacionmarcologicoindicador.intidrelacionmarcologicoindicador "
                        + "JOIN marco_logico.marcologico ON relacionmarcologicoindicador.fk_intidmarcologico = marcologico.intidmarcologico "
                        + "where marcologico.fk_intcodigoobra=" + idobra);
                return query.list();
            }
        });
        return lista;
    }

    public BigDecimal encontrarAvanceConvenioMediosVida(final int incodconvenio) throws DaoException {
        log.debug("#DDD######### encontrarAvanceConvenioMediosVida CobraDao ");        
       List<Double> avance= getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery("SELECT colvalavance FROM marco_logico.vista_calculo_avance_convenio_medios_vida where codigoconvsiente='"+incodconvenio+"'");
                return query.list();
            }
        });
        if(avance.isEmpty())
        {
            return BigDecimal.ZERO;
        }
        else
        {
        return BigDecimal.valueOf(avance.get(0));
        }
    }

}
