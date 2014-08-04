/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.obra.seguimiento;

import co.com.interkont.cobra.exception.CobraExceptionBean;
import co.com.interkont.cobra.jdbc.entity.datasource.DataSourceFactory;
import co.com.interkont.cobra.jdbc.entity.tables.Seguimiento_obra_detalleDAO;
import co.com.interkont.cobra.jdbc.entity.tables.seguimiento_obra_encabDAO;
import co.com.interkont.cobra.jdbc.entity.tables.Seguimiento_obra_encab_histDAO;
import co.com.interkont.cobra.to.SeguimientoObraDetalle;
import co.com.interkont.cobra.to.SeguimientoObraDetalleId;
import co.com.interkont.cobra.to.SeguimientoObraEncab;
import co.com.interkont.cobra.to.SeguimientoObraEncabHist;
import co.com.interkont.cobra.to.SeguimientoObraEncabHistId;
import co.com.interkont.cobra.util.Utilitario;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import cobra.Supervisor.IngresarNuevaObra;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * Modalidad/funcionalidad : SEGUIMIENTO CONTRATOS
 *
 *
 * @author Manuel Cortes Granados
 * @since Junio 12 2014
 */
public class SeguimientosContrato implements Serializable {

    boolean existe_registro_codigoobra;
    SeguimientoObraEncab seg_encab;
    SeguimientoObraDetalle seg_detalle;
    int p_idcodigoobra;
    int p_usu_id;
    
    List<SeguimientoObraDetalle> listaSeguimientoObraDetalle;
    List<SeguimientoObraEncabHist> listaSeguimientoObraEncabHist;

    /**
     * @author Manuel Cortes Granados
     * @since 23 Junio 2014 7:10 AM
     * @throws Exception
     */
    public SeguimientosContrato() throws Exception {
        seg_encab = new SeguimientoObraEncab();
        seg_detalle = new SeguimientoObraDetalle();
        existe_registro_codigoobra = false;
        p_usu_id = getSessionBeanCobra().getUsuarioObra().getUsuId();
    }

    public boolean isExiste_registro_codigoobra() {
        return existe_registro_codigoobra;
    }

    public void setExiste_registro_codigoobra(boolean existe_registro_codigoobra) {
        this.existe_registro_codigoobra = existe_registro_codigoobra;
    }

    public SeguimientoObraEncab getSeg_encab() {
        return seg_encab;
    }

    public void setSeg_encab(SeguimientoObraEncab seg_encab) {
        this.seg_encab = seg_encab;
    }

    public SeguimientoObraDetalle getSeg_detalle() {
        return seg_detalle;
    }

    public void setSeg_detalle(SeguimientoObraDetalle seg_detalle) {
        this.seg_detalle = seg_detalle;
    }

    public int getP_idcodigoobra() {
        return p_idcodigoobra;
    }

    public void setP_idcodigoobra(int p_idcodigoobra) {
        this.p_idcodigoobra = p_idcodigoobra;
    }

    public int getP_usu_id() {
        return p_usu_id;
    }

    public void setP_usu_id(int p_usu_id) {
        this.p_usu_id = p_usu_id;
    }

    public List<SeguimientoObraDetalle> getListaSeguimientoObraDetalle() {
        return listaSeguimientoObraDetalle;
    }

    public void setListaSeguimientoObraDetalle(List<SeguimientoObraDetalle> listaSeguimientoObraDetalle) {
        this.listaSeguimientoObraDetalle = listaSeguimientoObraDetalle;
    }

    public List<SeguimientoObraEncabHist> getListaSeguimientoObraEncabHist() {
        return listaSeguimientoObraEncabHist;
    }

    public void setListaSeguimientoObraEncabHist(List<SeguimientoObraEncabHist> listaSeguimientoObraEncabHist) {
        this.listaSeguimientoObraEncabHist = listaSeguimientoObraEncabHist;
    }
    
    
    
    

    /**
     * @author Manuel Cortes Granados
     * @since Junio 12 2014
     * @return
     */
    public String irApagina_SeguimientosContrato() {
        consultarSeguimientoObraDetalle();
        return "IrSeguimientosContrato";
    }

    /**
     * @author Manuel Cortes Granados
     * @param event
     * @since Junio 12 2014 3:24 PM
     */
    public void actualizarValoresBooleanos(ValueChangeEvent event) {
        Boolean isChecked = (Boolean) event.getNewValue();
        if (isChecked) {
            FacesUtils.addInfoMessage("Se ha marcado Contrato como confirmado.");
        }
    }

    /**
     * @author Manuel Cortes Granados
     * @since Junio 22 2014 20:45
     * @param valor
     */
    public void actualizarRegistroSeguimientoEncabezado() {
        try {
            
            seguimiento_obra_encabDAO segDAO = new seguimiento_obra_encabDAO(getSessionBeanCobra().getDataSourceFactory().getConnection());
            Seguimiento_obra_encab_histDAO seghistDAO = new Seguimiento_obra_encab_histDAO(getSessionBeanCobra().getDataSourceFactory().getConnection());
            segDAO.update(seg_encab);
            int usuId=getSessionBeanCobra().getUsuarioObra().getUsuId();
            SeguimientoObraEncabHist seg_encab_hist = new SeguimientoObraEncabHist(seg_encab,usuId,"MODIFICACION ");
            seghistDAO.insert(seg_encab_hist);
            consultarSeguimientoObraDetalle();
            FacesUtils.addInfoMessage("El Registro ha sido actualizado con exito");
            
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.toString());
        }
    }

    /**
     * Cuando no existe un registro en la tabla
     *
     * @author Manuel Cortes Granados
     * @since Junio 17 2014 4:49
     */
    public void irApagina_SeguimientosContrato_ActivarRegistro() throws Exception {
        existe_registro_codigoobra = true;
    }

    /**
     * @author Manuel Cortes Granados
     * @since Junio 22 2014 4:17 AM
     * @return
     * @throws Exception
     */
    public void generarRegistroPrimeraVez(int codigoobra) throws Exception {
        try {
            String fecha = "1900/01/01";
            p_idcodigoobra = codigoobra;
            
            seguimiento_obra_encabDAO segDAO = new seguimiento_obra_encabDAO(getSessionBeanCobra().getDataSourceFactory().getConnection());
            SeguimientoObraEncab seg = segDAO.select(p_idcodigoobra);
            if (seg != null) {
                this.setSeg_encab(seg);
                FacesUtils.addInfoMessage("Ya existe un seguimiento relacionado con la obra con codigo " + this.p_idcodigoobra);
            } else {
                insertarRegistroSeguimientoObraEncab_PrimeraVez(); 
            }
            irApagina_SeguimientosContrato();
            
        } catch (Exception e) {
            FacesUtils.addErrorMessage("ERROR EN EL SISTEMA : " + e.toString());
        }
    }
    
    /**
     * @author Manuel Cortes Granados
     * @since Junio 24 2014 8:43 AM
     * @throws SQLException
     * @throws Exception 
     */

    public void insertarRegistroSeguimientoObraEncab_PrimeraVez() throws SQLException, Exception {
        String fecha = "2000/01/01";
        
        SimpleDateFormat dt = new SimpleDateFormat("yyyy/mm/dd");
        seguimiento_obra_encabDAO segDAO = new seguimiento_obra_encabDAO(getSessionBeanCobra().getDataSourceFactory().getConnection());
        Seguimiento_obra_encab_histDAO seghistDAO = new Seguimiento_obra_encab_histDAO(getSessionBeanCobra().getDataSourceFactory().getConnection());
        SeguimientoObraEncab seg = segDAO.select(p_idcodigoobra);
        SeguimientoObraEncabHist seghist = new SeguimientoObraEncabHist();
        
        seg = new SeguimientoObraEncab();
        seg.setCodigoobra(this.getP_idcodigoobra());
        seg.setRepresentanteLegal("REPRESENTANTE LEGAL");
        seg.setNit("NIT");
        seg.setInfraestructuraCons(0);
        seg.setContrato(false);
        seg.setPlanInversion(false);
        seg.setCronogramaActividades(false);
        seg.setCertificacionBancaria(false);
        seg.setResolucionAdjudicacion(false);
        seg.setCronogramaProcesoSeleccion(false);
        seg.setPolizas(false);
        seg.setEstado(false);
        seg.setActaInicioFirmadaAlc(false);
        seg.setActaInicioFirmadaIng(false);
        seg.setTramiteCompleto(false);
        seg.setFechaContrato(dt.parse(fecha));
        seg.setFechaPlanInversion(dt.parse(fecha));
        seg.setFechaCronogramaActividades(dt.parse(fecha));
        seg.setFechaCertificacionBancaria(dt.parse(fecha));
        seg.setFechaResolucionAdjudicacion(dt.parse(fecha));
        seg.setFechaCronogramaProcesoSeleccion(dt.parse(fecha));
        seg.setFechaPolizas(dt.parse(fecha));
        seg.setFechaEstado(dt.parse(fecha));
        seg.setFechaActaInicioFirmadaAlc(dt.parse(fecha));
        seg.setFechaActaInicioFirmadaIng(dt.parse(fecha));
        seg.setFechaTramiteCompleto(dt.parse(fecha));
        seg.setUsuId(this.getP_usu_id());
        segDAO.insert(seg);
        
        SeguimientoObraEncabHistId seg_id = new SeguimientoObraEncabHistId();
        seg_id.setIdseguimientoEncab(seg.getIdseguimientoEncab());
        seg_id.setCodigoobra(this.getP_idcodigoobra());
        
        seghist.setId(seg_id);
        seghist.setNit(seg.getNit());
        seghist.setInfraestructuraCons(seg.getInfraestructuraCons());
        seghist.setContrato(seg.getContrato());
        seghist.setPlanInversion(seg.getPlanInversion());
        seghist.setCronogramaActividades(seg.getCronogramaActividades());
        seghist.setPolizas(seg.getPolizas());
        seghist.setEstado(seg.getEstado());
        seghist.setActaInicioFirmadaAlc(seg.getActaInicioFirmadaAlc());
        seghist.setActaInicioFirmadaIng(seg.getActaInicioFirmadaIng());
        seghist.setTramiteCompleto(seg.getTramiteCompleto());
        seghist.setFechaContrato(seg.getFechaContrato());
        seghist.setFechaPlanInversion(seg.getFechaPlanInversion());
        seghist.setFechaCronogramaActividades(seg.getFechaCronogramaActividades());
        seghist.setFechaCertificacionBancaria(seg.getFechaCertificacionBancaria());
        seghist.setFechaResolucionAdjudicacion(seg.getFechaResolucionAdjudicacion());
        seghist.setFechaCronogramaProcesoSeleccion(seg.getFechaCronogramaProcesoSeleccion());
        seghist.setFechaPolizas(seg.getFechaPolizas());
        seghist.setFechaEstado(seg.getFechaEstado());
        seghist.setFechaActaInicioFirmadaAlc(seg.getFechaActaInicioFirmadaAlc());
        seghist.setFechaActaInicioFirmadaIng(seg.getFechaActaInicioFirmadaIng());
        seghist.setFechaTramiteCompleto(seg.getFechaTramiteCompleto());
        seghist.setUsuId(seg.getUsuId());
        seghistDAO.insert(seghist);
        
        
        FacesUtils.addInfoMessage("Se ha generado un registro para seguimientos con respecto a la obra con codigo " + this.p_idcodigoobra);
    }
    
    /**
     * @author Manuel Cortes Granados
     * @since 18 Julio 2014 11:10 AM
     * @throws Exception 
     */
    
    public void ingresarSeguimientoObraDetalle() throws Exception{
        
        Seguimiento_obra_detalleDAO segDAO = new Seguimiento_obra_detalleDAO(getSessionBeanCobra().getDataSourceFactory().getConnection());
        SeguimientoObraDetalleId segId = new SeguimientoObraDetalleId();
        segId.setIdseguimientoEncab(seg_encab.getIdseguimientoEncab());
        seg_detalle.setId(segId);
        segDAO.insert(seg_detalle);
        
        FacesUtils.addInfoMessage("Se ha generado un registro de seguimiento con exito");
    }
    
    /**
     * @author Manuel Cortes Granados
     * @since Junio 23 2014 7:09 AM
     * @param campo
     */
    public void validarCamposFechasBooleanos(int campo) {
        switch (campo) {
            case 1:
                if ((this.getSeg_encab().getContrato()) && (this.getSeg_encab().getFechaContrato().toString().trim().length() > 0)) {
                    this.getSeg_encab().setFechaContrato(null);
                }
                break;
        }
    }

    /**
     * @author Manuel Cortes Granados
     * @since Junio 23 2014 7:09 AM
     * @return
     */
    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }
    
    /**
     * @author Manuel Cortes Granados
     * @since Julio 18 2014 10:14 AM
     * @return 
     */
    
    protected CobraExceptionBean getCobraExceptionBean() {
        return (CobraExceptionBean) FacesUtils.getManagedBean("CobraExceptionBean");
    }

    /**
     * @author Manuel Cortes Granados
     * @since Junio 23 2014 7:09 AM
     * @return
     */
    protected IngresarNuevaObra getIngresarNuevaObra() {
        return (IngresarNuevaObra) FacesUtils.getManagedBean("IngresarNuevaObra");
    }

    public void mostrarMensaje(ValueChangeEvent evento) {
        FacesUtils.addInfoMessage("mostrarMensaje " + this.p_idcodigoobra);
    }
    
    /**
     * Este metodo tiene por objeto consultar los seguimientos en detalle los cuales se muestran
     * en la segunda pestaña llamada [Seguimiento en detalle].  Se llama cuando se carga
     * toda la forma principal o pagina
     * 
     * @author Manuel Cortes Granados
     * @since 19 JUliO 2014 9:00 AM
     */
    
    public void consultarSeguimientoObraDetalle(){
        try {
            
            Seguimiento_obra_detalleDAO segDAO = new Seguimiento_obra_detalleDAO(getSessionBeanCobra().getDataSourceFactory().getConnection());
            Seguimiento_obra_encab_histDAO seghistDAO = new Seguimiento_obra_encab_histDAO(getSessionBeanCobra().getDataSourceFactory().getConnection());
            this.listaSeguimientoObraDetalle = segDAO.select(seg_encab.getIdseguimientoEncab());
            //this.listaSeguimientoObraEncabHist = seghistDAO.select(p_idcodigoobra);
            
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.toString());
        }        
    }
    
    /**
     * @author Manuel Cortes Granados
     * @since 30 Junio 2014 12:10 PM
     * @param e
     * @throws Exception
     */
    public void procesarError(SQLException se) throws Exception {
        Utilitario util = new Utilitario();
        FacesUtils.addErrorMessage("Ha ocurrido un error en el Sistema");
        if (getCobraExceptionBean().isAmbiente_pruebas()) {
            FacesUtils.addErrorMessage("Codigo del error en la base de datos : " + se.getErrorCode());
            FacesUtils.addErrorMessage(se.toString());
            System.out.println(util.getMessageStackException(se));
        }
        throw se;
    }
    
    /**
     * @author Manuel Cortes Granados
     * @since 18 Julio 2014 10:25 AM
     * @return 
     */
    
    public String irApagina_SeguimientoObraDetalle() {
        return "IrPaginaIngresarSeguimientoObraDetalle";
    }
}