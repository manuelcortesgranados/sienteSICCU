/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Rolentidad;
import co.com.interkont.cobra.to.Tercero;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import cobra.Supervisor.NuevoContratoBasico;
import cobra.service.CobraServiceAble;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.model.SelectItem;
import javax.mail.Session;
import org.richfaces.component.UIDataTable;

/**
 *
 * @author Carlos Loaiza
 */
public class RecursosConvenio implements Serializable {

    private Fuenterecursosconvenio fuenteRecursoConvenio;
    private boolean boolguardofuente;
    private List<Fuenterecursosconvenio> lstFuentesRecursos;
    private SelectItem[] tipoAporte;
    private BigDecimal sumafuentes;
    private UIDataTable tableFuente = new UIDataTable();
    /**
     * Lista para el manejo de roles
     *
     * @return
     *
     */
    private List<Rolentidad> lstRoles = new ArrayList<Rolentidad>();

    public List<Rolentidad> getLstRoles() {
        return lstRoles;
    }

    public void setLstRoles(List<Rolentidad> lstRoles) {
        this.lstRoles = lstRoles;
    }

    public RecursosConvenio(Contrato contrato, CobraServiceAble cobraService) {
        fuenteRecursoConvenio = new Fuenterecursosconvenio(new Tercero(), contrato, new Rolentidad());
        lstFuentesRecursos = new ArrayList<Fuenterecursosconvenio>();
        sumafuentes = BigDecimal.ZERO;
        llenarTipoAporte();
        llenarRoles(cobraService);
    }

    public void limpiarFuenteRecurso() {
        fuenteRecursoConvenio = new Fuenterecursosconvenio();
        fuenteRecursoConvenio.setRolentidad(new Rolentidad());
        fuenteRecursoConvenio.setTercero(new Tercero());

    }

    /**
     * @return the tipoAporte
     */
    public SelectItem[] getTipoAporte() {
        return tipoAporte;
    }

    /**
     * @param tipoAporte the tipoAporte to set
     */
    public void setTipoAporte(SelectItem[] tipoAporte) {
        this.tipoAporte = tipoAporte;
    }

    /**
     * @return the boolguardofuente
     */
    public boolean isBoolguardofuente() {
        System.out.println("this = " + boolguardofuente);
        return boolguardofuente;
    }

    /**
     * @param boolguardofuente the boolguardofuente to set
     */
    public void setBoolguardofuente(boolean boolguardofuente) {
        this.boolguardofuente = boolguardofuente;
    }

    /**
     * @return the lstFuentesRecursos
     */
    public List<Fuenterecursosconvenio> getLstFuentesRecursos() {
        return lstFuentesRecursos;
    }

    /**
     * @param lstFuentesRecursos the lstFuentesRecursos to set
     */
    public void setLstFuentesRecursos(List<Fuenterecursosconvenio> lstFuentesRecursos) {
        this.lstFuentesRecursos = lstFuentesRecursos;
    }

    /**
     * @return the fuenteRecursoConvenio
     */
    public Fuenterecursosconvenio getFuenteRecursoConvenio() {
        return fuenteRecursoConvenio;
    }

    /**
     * @param fuenteRecursoConvenio the fuenteRecursoConvenio to set
     */
    public void setFuenteRecursoConvenio(Fuenterecursosconvenio fuenteRecursoConvenio) {
        this.fuenteRecursoConvenio = fuenteRecursoConvenio;
    }

    /*
     *metodo que se encarga de eliminar una fuente de recurso a las
     * fuente de recursos del convenio.
     *      
     */
    public void eliminarFuenteRecursos() {
        Fuenterecursosconvenio f = (Fuenterecursosconvenio) tableFuente.getRowData();
        lstFuentesRecursos.remove(f);
        sumafuentes = sumafuentes.subtract(f.getValoraportado());
        if (lstFuentesRecursos.isEmpty()) {
            sumafuentes = BigDecimal.ZERO;
        }
    }

    

    /*
     *metodo que se encarga de adicionar una fuente de recurso a la
     * fuente de recursos del convenio.
     *      
     */
    public void adicionarFuenteRecursos() {
        NuevoContratoBasico n = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
        Tercero tercero = n.obtenerTerceroXcodigo(n.getRecursosconvenio().getFuenteRecursoConvenio().getTercero().getIntcodigo());
        if (getFuenteRecursoConvenio().getOtrasreservas().add(getFuenteRecursoConvenio().getReservaiva())
                .add(getFuenteRecursoConvenio().getValorcuotagerencia()).compareTo(getFuenteRecursoConvenio().getValoraportado()) < 1) {
            fuenteRecursoConvenio.setTercero(tercero);
            fuenteRecursoConvenio.setRolentidad(obtenerRolXcodigo(this.getFuenteRecursoConvenio().getRolentidad().getIdrolentidad()));
            fuenteRecursoConvenio.setValorDisponible(fuenteRecursoConvenio.getValoraportado());
            System.out.println("fuente R v = " + fuenteRecursoConvenio.getValorDisponible());
            calcularCuotaGerencia();
            lstFuentesRecursos.add(fuenteRecursoConvenio);
            sumafuentes = sumafuentes.add(fuenteRecursoConvenio.getValoraportado());
            limpiarFuenteRecurso();
        } else {
            FacesUtils.addErrorMessage("El valor de la suma de las reservas y la cuota de gerencia no puede superar el Valor Global Aportado.");
        }
    }
    
    public void calcularCuotaGerencia(){
        BigDecimal valorConverPorcentajeGerencia = new BigDecimal(getFuenteRecursoConvenio().getPorcentajecuotagerencia(), MathContext.DECIMAL64);
        switch (getFuenteRecursoConvenio().getTipoaporte()) {
                    case 1://porcentual                                                      
                         fuenteRecursoConvenio.setValorcuotagerencia(valorConverPorcentajeGerencia);
                         break;
                     case 2://valor
                         fuenteRecursoConvenio.setValorcuotagerencia(getFuenteRecursoConvenio().getValorcuotagerencia());
                         break;                    
                 }
    }
    
    public void calcularValorGerencia() {
        SessionBeanCobra sbc = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
        ResourceBundle bundle = sbc.getBundle();
        getFuenteRecursoConvenio().setStrporcentajecuotagerencia("");
       
        switch (getFuenteRecursoConvenio().getTipoaporte()) {
            case 1://porcentual
                try {
                    if (getFuenteRecursoConvenio().getValorcuotagerencia().doubleValue() < 100) {
                        getFuenteRecursoConvenio().setPorcentajecuotagerencia(
                                getFuenteRecursoConvenio().getValoraportado().doubleValue() * getFuenteRecursoConvenio().getValorcuotagerencia().doubleValue() / 100);
                        BigDecimal valorConverPorcentajeGerencia = new BigDecimal(getFuenteRecursoConvenio().getPorcentajecuotagerencia(), MathContext.DECIMAL64);
                        DecimalFormat valorConDecimal = new DecimalFormat("0.00");                        
                        getFuenteRecursoConvenio().setStrporcentajecuotagerencia("$ " + valorConDecimal.format(valorConverPorcentajeGerencia));
                    } else {
                        FacesUtils.addErrorMessage(bundle.getString("validarporcentajefuente"));
                    }
                } catch (ArithmeticException a) {
                    getFuenteRecursoConvenio().setStrporcentajecuotagerencia("$ 0.0000");
                }
                break;
            case 2://Valor
                try {
                    if (getFuenteRecursoConvenio().getValorcuotagerencia().doubleValue() < getFuenteRecursoConvenio().getValoraportado().doubleValue()) {
                        getFuenteRecursoConvenio().setPorcentajecuotagerencia(getFuenteRecursoConvenio().getValorcuotagerencia().doubleValue() / getFuenteRecursoConvenio().getValoraportado().doubleValue() * 100);
                        getFuenteRecursoConvenio().setStrporcentajecuotagerencia(getFuenteRecursoConvenio().getPorcentajecuotagerencia() + " %");
                    } else {
                        FacesUtils.addErrorMessage(bundle.getString("validarvalorfuente"));
                    }
                } catch (ArithmeticException a) {
                    getFuenteRecursoConvenio().setStrporcentajecuotagerencia("0.0000 %");
                }
                break;
        }

    }

    /*
     *metodo que  carga los tipos de aportes en la lista de seleccion
     *      
     */
    public void llenarTipoAporte() {
        setTipoAporte(new SelectItem[]{new SelectItem(1, "Porcentual"), new SelectItem(2, "Valor")});
    }

    public BigDecimal getSumafuentes() {
        return sumafuentes;
    }

    public void setSumafuentes(BigDecimal sumafuentes) {
        this.sumafuentes = sumafuentes;
    }

    /*
     *metodo que  carga los roles de las entidades en la lista de seleccion
     *      
     */
    public void llenarRoles(CobraServiceAble cobraService) {
        lstRoles = cobraService.encontrarRolesEntidad();

    }

    public Rolentidad obtenerRolXcodigo(int intcodigo) {
        for (Rolentidad rol : lstRoles) {
            if (rol.getIdrolentidad() == intcodigo) {
                return rol;
            }
        }
        return null;
    }

    /**
     * @return the tableFuente
     */
    public UIDataTable getTableFuente() {
        return tableFuente;
    }

    /**
     * @param tableFuente the tableFuente to set
     */
    public void setTableFuente(UIDataTable tableFuente) {
        this.tableFuente = tableFuente;
    }
}
