/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Obrafuenterecursosconvenios;
import co.com.interkont.cobra.to.Planificacionmovconvenioentidad;
import co.com.interkont.cobra.to.Rolentidad;
import co.com.interkont.cobra.to.Tercero;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import cobra.Supervisor.NuevoContratoBasico;
import cobra.service.CobraServiceAble;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.model.SelectItem;
import org.richfaces.component.UIDataTable;

/**
 *
 * @author Carlos Loaiza
 */
public class RecursosConvenio implements Serializable {

    private Fuenterecursosconvenio fuenteRecursoConvenio;
    private boolean boolguardofuente;
    private List<Fuenterecursosconvenio> lstFuentesRecursos = new ArrayList<Fuenterecursosconvenio>();
    private SelectItem[] tipoAporte;
    private BigDecimal sumafuentes;
    private BigDecimal reservaIva;
    private BigDecimal otrasReservas;
    private BigDecimal cuotaGerencia;
    private UIDataTable tableFuente = new UIDataTable();
    private List<Fuenterecursosconvenio> lstFuentesRecursosEliminar = new ArrayList<Fuenterecursosconvenio>();
    private SessionBeanCobra sbc = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    private ResourceBundle bundle = sbc.getBundle();
    private boolean verAgregarRecurso = true;
    private boolean verEliminarRecurso = true;

    public boolean isVerEliminarRecurso() {
        return verEliminarRecurso;
    }

    public void setVerEliminarRecurso(boolean verEliminarRecurso) {
        this.verEliminarRecurso = verEliminarRecurso;
    }

    public boolean isVerAgregarRecurso() {
        return verAgregarRecurso;
    }

    public void setVerAgregarRecurso(boolean verAgregarRecurso) {
        this.verAgregarRecurso = verAgregarRecurso;
    }

    public List<Fuenterecursosconvenio> getLstFuentesRecursosEliminar() {
        return lstFuentesRecursosEliminar;
    }

    public void setLstFuentesRecursosEliminar(List<Fuenterecursosconvenio> lstFuentesRecursosEliminar) {
        this.lstFuentesRecursosEliminar = lstFuentesRecursosEliminar;
    }
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
    private List<Integer> lstVigencia = new ArrayList<Integer>();

    public RecursosConvenio() {
    }

    public RecursosConvenio(Contrato contrato, CobraServiceAble cobraService) {
        fuenteRecursoConvenio = new Fuenterecursosconvenio(new Tercero(), contrato, new Rolentidad());
        lstFuentesRecursos = new ArrayList<Fuenterecursosconvenio>();
        sumafuentes = BigDecimal.ZERO;
        reservaIva = BigDecimal.ZERO;
        otrasReservas = BigDecimal.ZERO;
        cuotaGerencia = BigDecimal.ZERO;
        llenarTipoAporte();
        llenarRoles(cobraService);
        llenarVigencia();
        verAgregarRecurso = true;
        verEliminarRecurso = true;
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

        NuevoContratoBasico n = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
        Fuenterecursosconvenio f = (Fuenterecursosconvenio) tableFuente.getRowData();
        if (f.getIdfuenterecursosconvenio() == 0) {
            lstFuentesRecursosEliminar.add(f);
            lstFuentesRecursos.remove(f);
            recalcularValoresFuenteRecursos(n,f);

        } else {

            List<Obrafuenterecursosconvenios> obraFuenteRecursosConvenios;
            List<Planificacionmovconvenioentidad> planificaMovConvenioEntidad;

            obraFuenteRecursosConvenios = sbc.getCobraService().encontrarObrafuenteRecursosConveniosPorIdFuenteRecursos(f);
            planificaMovConvenioEntidad = sbc.getCobraService().encontraPlanificacionMovConvenioEntidadPorIdFuenteRecursos(f);

            //  if (!f.isEstaEnFuenteRecurso()) { // Se remplaza este encabezado del if por otro. 
            if (obraFuenteRecursosConvenios.isEmpty() && planificaMovConvenioEntidad.isEmpty()) {
                lstFuentesRecursosEliminar.add(f);
                lstFuentesRecursos.remove(f);
                recalcularValoresFuenteRecursos(n,f);
            } else {
                FacesUtils.addErrorMessage(bundle.getString("msgerrorvalidacionfuenterecurso"));

            }
        }
    }

    /**
     * Metodo que recalcula valores al momento de eliminar una fuente de recursos. 
     * @param n
     * @param f 
     */
    private void recalcularValoresFuenteRecursos( NuevoContratoBasico n, Fuenterecursosconvenio f ) {
        n.getContrato().setValorDisponible(n.getContrato().getValorDisponible().add(f.getValoraportado()));
        n.getContrato().setValorDisponibleCuotaGerencia(n.getContrato().getValorDisponibleCuotaGerencia().add(f.getValorcuotagerencia()));
        sumafuentes = sumafuentes.subtract(f.getValoraportado());
        reservaIva = reservaIva.subtract(f.getReservaiva());
        otrasReservas = otrasReservas.subtract(f.getOtrasreservas());
        cuotaGerencia = cuotaGerencia.subtract(f.getValorcuotagerencia());

        if (lstFuentesRecursos.isEmpty()) {
            sumafuentes = BigDecimal.ZERO;
            reservaIva = BigDecimal.ZERO;
            otrasReservas = BigDecimal.ZERO;
            cuotaGerencia = BigDecimal.ZERO;
        }
    }

    //lstFuentesRecursos
    public BigDecimal sumaFuenteRecursos(BigDecimal valorNuevo) {
        BigDecimal valorSuma = BigDecimal.ZERO;
        for (Fuenterecursosconvenio f : lstFuentesRecursos) {
            valorSuma = valorSuma.add(f.getValoraportado());
        }
        valorSuma = valorSuma.add(valorNuevo);
        return valorSuma;
    }
    /*
     *metodo que se encarga de adicionar una fuente de recurso a la
     * fuente de recursos del convenio.
     *      
     */

    public String adicionarFuenteRecursos() {
        if (fuenteRecursoConvenio.getTipoaporte() == 2) {
            fuenteRecursoConvenio.setPorcentajegerencia(BigDecimal.valueOf(fuenteRecursoConvenio.getPorcentajecuotagerencia()).setScale(2, RoundingMode.HALF_UP));
        }
        if (fuenteRecursoConvenio.getTipoaporte() == 1) {
            fuenteRecursoConvenio.setPorcentajegerencia(fuenteRecursoConvenio.getValorcuotagerencia().setScale(2, RoundingMode.HALF_UP));
        }
        try {
            NuevoContratoBasico n = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
            n.validarPuedeEditarValorFuente();
            Tercero tercero = n.obtenerTerceroXcodigo(n.getRecursosconvenio().getFuenteRecursoConvenio().getTercero().getIntcodigo());
            //Se pone la condicion si la lista tiene alguna entidad y se valida a que no agreguen la misma entidad en un conveio
            if (!lstFuentesRecursos.isEmpty()) {
                for (Fuenterecursosconvenio f : lstFuentesRecursos) {
                    if (fuenteRecursoConvenio.getTercero().getIntcodigo() == f.getTercero().getIntcodigo() && fuenteRecursoConvenio.getVigencia().equals(f.getVigencia())) {
                        FacesUtils.addErrorMessage(bundle.getString("msgerrorfuenteconigualvigencia"));
                        return null;

                    }

                }
            }

            if (fuenteRecursoConvenio.getValoraportado().compareTo(n.getContrato().getValorDisponible()) > 0) {
                FacesUtils.addErrorMessage(bundle.getString("msgerrorvaloraportadomayor") + n.getContrato().getValorDisponible());
                return null;
            }

            if (fuenteRecursoConvenio.getTipoaporte() == 1) {
                BigDecimal valorCuotaCalculado = new BigDecimal((fuenteRecursoConvenio.getValoraportado().doubleValue() * getFuenteRecursoConvenio().getValorcuotagerencia().doubleValue()) / 100);
                if (valorCuotaCalculado.compareTo(n.getContrato().getValorDisponibleCuotaGerencia()) > 0) {
                    FacesUtils.addErrorMessage(bundle.getString("msgerrorsuperiorcuotagerencia") + n.getContrato().getValorDisponibleCuotaGerencia());
                    return null;
                }
            } else {
                if (fuenteRecursoConvenio.getValorcuotagerencia().compareTo(n.getContrato().getValorDisponibleCuotaGerencia()) > 0) {
                    FacesUtils.addErrorMessage(bundle.getString("msgerrorsuperiorcuotagerencia") + n.getContrato().getValorDisponibleCuotaGerencia());
                    return null;
                }
            }
//                       
//            if(n.getContrato().getAuxiliarValorGerencia().compareTo(fuenteRecursoConvenio.getValoraportado())<0){
//                    FacesUtils.addErrorMessage("El valor de la cuota de gerencia es superior al valor disponible de la cuota de gerencia:" + n.getContrato().getAuxiliarValorGerencia());
//                return null;
//            }
//            Validar la suma de las fuentes de recursos al valor estimado del convenio
//            ValidacionesConvenio.validarSumaFuentesxValorAporte(n.getContrato().getNumvlrcontrato(), fuenteRecursoConvenio.getValoraportado(), sumafuentes);
            if (getFuenteRecursoConvenio().getOtrasreservas().add(getFuenteRecursoConvenio().getReservaiva())
                    .add(getFuenteRecursoConvenio().getValorcuotagerencia()).compareTo(getFuenteRecursoConvenio().getValoraportado()) < 1) {
                // if (fuenteRecursoConvenio.getValoraportado().compareTo(n.getContrato().getNumvlrcontrato()) <= 0) {

                fuenteRecursoConvenio.setTercero(tercero);
                fuenteRecursoConvenio.setRolentidad(obtenerRolXcodigo(this.getFuenteRecursoConvenio().getRolentidad().getIdrolentidad()));
                calcularCuotaGerencia();
                fuenteRecursoConvenio.setValorDisponible(fuenteRecursoConvenio.getValoraportado().subtract(fuenteRecursoConvenio.getReservaiva().add(fuenteRecursoConvenio.getOtrasreservas()).add(fuenteRecursoConvenio.getValorcuotagerencia())));
                System.out.println("fuente dispo " + fuenteRecursoConvenio.getValorDisponible());
                lstFuentesRecursos.add(fuenteRecursoConvenio);
                System.out.println("dispo = " + n.getContrato().getValorDisponible());
                n.getContrato().setValorDisponible(n.getContrato().getValorDisponible().subtract(fuenteRecursoConvenio.getValoraportado()));
                n.getContrato().setValorDisponibleCuotaGerencia(n.getContrato().getValorDisponibleCuotaGerencia().subtract(fuenteRecursoConvenio.getValorcuotagerencia()));
                System.out.println("nuevo disponible = " + n.getContrato().getValorDisponible());
                fuenteRecursoConvenio.setContrato(n.getContrato());
                sumafuentes = sumafuentes.add(fuenteRecursoConvenio.getValoraportado());
                reservaIva = reservaIva.add(fuenteRecursoConvenio.getReservaiva());
                otrasReservas = otrasReservas.add(fuenteRecursoConvenio.getOtrasreservas());
                cuotaGerencia = cuotaGerencia.add(fuenteRecursoConvenio.getValorcuotagerencia());
                limpiarFuenteRecurso();
                //}
            } else {
                FacesUtils.addErrorMessage(bundle.getString("msgerrorsumacuotaivamayor"));
                return null;
            }
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return null;
    }

    public void calcularCuotaGerencia() {
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
        getFuenteRecursoConvenio().setStrporcentajecuotagerencia("");

        switch (getFuenteRecursoConvenio().getTipoaporte()) {
            case 1://porcentual
                try {
                    if (getFuenteRecursoConvenio().getValorcuotagerencia().doubleValue() <= 100) {
                        getFuenteRecursoConvenio().setPorcentajecuotagerencia(
                                getFuenteRecursoConvenio().getValoraportado().doubleValue() * getFuenteRecursoConvenio().getValorcuotagerencia().doubleValue() / 100);
                        BigDecimal valorConverPorcentajeGerencia = new BigDecimal(getFuenteRecursoConvenio().getPorcentajecuotagerencia(), MathContext.DECIMAL64);
                        DecimalFormat valorConDecimal = new DecimalFormat("'$' ####,###.00");
                        getFuenteRecursoConvenio().setStrporcentajecuotagerencia(valorConDecimal.format(valorConverPorcentajeGerencia));
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

    public void llenarVigencia() {
        int vigenciaInicio = Integer.parseInt(bundle.getString("inicio_vigencia"));
        int vigenciaHasta = Integer.parseInt(bundle.getString("fin_vigencia"));
        for (int i = vigenciaInicio; i <= vigenciaHasta; i++) {
            lstVigencia.add(i);
        }

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

    public void sumaFuentesRecursos() {
        sumafuentes = BigDecimal.ZERO;
        reservaIva = BigDecimal.ZERO;
        otrasReservas = BigDecimal.ZERO;
        cuotaGerencia = BigDecimal.ZERO;
        for (Fuenterecursosconvenio f : lstFuentesRecursos) {
            sumafuentes = sumafuentes.add(f.getValoraportado());
            reservaIva = reservaIva.add(f.getReservaiva());
            otrasReservas = otrasReservas.add(f.getOtrasreservas());
            cuotaGerencia = cuotaGerencia.add(f.getValorcuotagerencia());
        }
    }

    /**
     * @return the lstVigencia
     */
    public List<Integer> getLstVigencia() {
        return lstVigencia;
    }

    /**
     * @param lstVigencia the lstVigencia to set
     */
    public void setLstVigencia(List<Integer> lstVigencia) {
        this.lstVigencia = lstVigencia;
    }

    /**
     * @return the reservaIva
     */
    public BigDecimal getReservaIva() {
        return reservaIva;
    }

    /**
     * @param reservaIva the reservaIva to set
     */
    public void setReservaIva(BigDecimal reservaIva) {
        this.reservaIva = reservaIva;
    }

    /**
     * @return the otrasReservas
     */
    public BigDecimal getOtrasReservas() {
        return otrasReservas;
    }

    /**
     * @param otrasReservas the otrasReservas to set
     */
    public void setOtrasReservas(BigDecimal otrasReservas) {
        this.otrasReservas = otrasReservas;
    }

    /**
     * @return the cuotaGerencia
     */
    public BigDecimal getCuotaGerencia() {
        return cuotaGerencia;
    }

    /**
     * @param cuotaGerencia the cuotaGerencia to set
     */
    public void setCuotaGerencia(BigDecimal cuotaGerencia) {
        this.cuotaGerencia = cuotaGerencia;
    }
}
