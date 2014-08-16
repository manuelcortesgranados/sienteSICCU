/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.obra.meta;

import co.com.interkont.cobra.hibernate.service.metas.MetasServiceAble;
import co.com.interkont.cobra.jdbc.model.CE.metas.ReporteMetasVO;
import co.com.interkont.cobra.jdbc.model.CE.metas.camposAutocalculadosMetasVO;
import co.com.interkont.cobra.jdbc.model.CE.tables_amp.MetaObraAMPVO;
import co.com.interkont.cobra.jdbc.model.CE.tables_amp.MetaRegistroAMPVO;
import co.com.interkont.cobra.to.Meta;
import co.com.interkont.cobra.to.Metaobra;
import co.com.interkont.cobra.to.Metaregistro;
import co.com.interkont.cobra.to.PdObjetivo;
import co.com.interkont.cobra.to.PdPrograma;
import co.com.interkont.cobra.to.PdSubprograma;
import co.com.interkont.cobra.to.Periodomedida;
import co.com.interkont.cobra.to.PeriodosFrecuencia;
import co.com.interkont.cobra.to.PlanDesarrollo;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import co.com.interkont.cobra.util.Utilitario;
import cobra.Supervisor.IngresarNuevaObra;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import cobra.util.Utilitario;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * Esta clase hace las funciones de managed bean para la pagina
 * MetasProyecto.xhtml
 *
 * @author Manuel Cortes Granados
 * @since Mayo 23 2014
 */
public final class MetasProyecto implements Serializable {

    boolean test = true;
    Meta meta;
    Metaobra metaobra;
    Metaregistro metaregistro;

    Meta meta_consulta;

    MetaObraAMPVO metaobraamp;
    MetaRegistroAMPVO metaregistroamp;
    PeriodosFrecuencia per;

    public List<Meta> listaMetas = new ArrayList<Meta>();
    public List<MetaObraAMPVO> listaMetasObras = new ArrayList<MetaObraAMPVO>();
    public List<MetaRegistroAMPVO> listaMetasObrasRegsitro = new ArrayList<MetaRegistroAMPVO>();
    public List<Periodomedida> listaPeriodoMedida = new ArrayList<Periodomedida>();
    public List<PeriodosFrecuencia> listaPeriodosFrecuencia = new ArrayList<PeriodosFrecuencia>();
    public List<String> listaUnidadMedida = new ArrayList<String>();
    public List<PlanDesarrollo> listaPlanDesarrollo = new ArrayList<PlanDesarrollo>();
    public List<camposAutocalculadosMetasVO> listaCamposCalculados = new ArrayList<camposAutocalculadosMetasVO>();
    public List<Meta> listaMetasDisponibles = new ArrayList<Meta>();

    // Declaracion de variables para uso como parametros de busqueda
    int p_idcodigoobra;
    int p_idmeta;
    int p_idmetaobra;
    int p_idmetaregistro;
    int p_periodomedida;
    int p_idperiodofrecuencia;
    boolean pactualizar;

    Date fecharegistroInicio;

    boolean perteneceGrupoMetas;
    boolean perteneceGrupoSupervisor;
    boolean perteneceGrupoAdministrador;

    String activePanel;

    ReporteMetasVO reportesVO;

    Set<PdObjetivo> s_objetivos = new HashSet(0);
    Set<PdPrograma> s_programas = new HashSet(0);
    Set<PdSubprograma> s_subprogramas = new HashSet(0);

    int p_idplandesarrollo;
    int p_idobjetivo;
    int p_idprograma;
    int p_idsubprograma;

    String p_nombre_plandesarrollo;
    String p_nombre_objetivo;
    String p_nombre_programa;
    String p_nombre_subprograma;

    PlanDesarrollo p_plandesarrollo;

    public boolean verPanelesPeriodos = false;
    String arregloValoresEtiquetasPeriodosAnios[][];
    boolean mostrarValoresEtiquetasPeriodos[];

    int aniosGobierno[];

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Metaobra getMetaobra() {
        return metaobra;
    }

    public void setMetaobra(Metaobra metaobra) {
        this.metaobra = metaobra;
    }

    public Metaregistro getMetaregistro() {
        return metaregistro;
    }

    public Meta getMeta_consulta() {
        return meta_consulta;
    }

    public void setMeta_consulta(Meta meta_consulta) {
        this.meta_consulta = meta_consulta;
    }

    public void setMetaregistro(Metaregistro metaregistro) {
        this.metaregistro = metaregistro;
    }

    public MetaObraAMPVO getMetaobraamp() {
        return metaobraamp;
    }

    public void setMetaobraamp(MetaObraAMPVO metaobraamp) {
        this.metaobraamp = metaobraamp;
    }

    public MetaRegistroAMPVO getMetaregistroamp() {
        return metaregistroamp;
    }

    public void setMetaregistroamp(MetaRegistroAMPVO metaregistroamp) {
        this.metaregistroamp = metaregistroamp;
    }

    public List<Meta> getListaMetas() {
        return listaMetas;
    }

    public void setListaMetas(List<Meta> listaMetas) {
        this.listaMetas = listaMetas;
    }

    public List<MetaObraAMPVO> getListaMetasObras() {
        return listaMetasObras;
    }

    public void setListaMetasObras(List<MetaObraAMPVO> listaMetasObras) {
        this.listaMetasObras = listaMetasObras;
    }

    public List<MetaRegistroAMPVO> getListaMetasObrasRegsitro() {
        return listaMetasObrasRegsitro;
    }

    public void setListaMetasObrasRegsitro(List<MetaRegistroAMPVO> listaMetasObrasRegsitro) {
        this.listaMetasObrasRegsitro = listaMetasObrasRegsitro;
    }

    public List<Periodomedida> getListaPeriodoMedida() {
        return listaPeriodoMedida;
    }

    public void setListaPeriodoMedida(List<Periodomedida> listaPeriodoMedida) {
        this.listaPeriodoMedida = listaPeriodoMedida;
    }

    public List<PeriodosFrecuencia> getListaPeriodosFrecuencia() {
        return listaPeriodosFrecuencia;
    }

    public void setListaPeriodosFrecuencia(List<PeriodosFrecuencia> listaPeriodosFrecuencia) {
        this.listaPeriodosFrecuencia = listaPeriodosFrecuencia;
    }

    public List<String> getListaUnidadMedida() {
        return listaUnidadMedida;
    }

    public void setListaUnidadMedida(List<String> listaUnidadMedida) {
        this.listaUnidadMedida = listaUnidadMedida;
    }

    public List<PlanDesarrollo> getListaPlanDesarrollo() {
        return listaPlanDesarrollo;
    }

    public void setListaPlanDesarrollo(List<PlanDesarrollo> listaPlanDesarrollo) {
        this.listaPlanDesarrollo = listaPlanDesarrollo;
    }

    public int getP_idcodigoobra() {
        return p_idcodigoobra;
    }

    public void setP_idcodigoobra(int p_idcodigoobra) {
        this.p_idcodigoobra = p_idcodigoobra;
    }

    public int getP_idmeta() {
        return p_idmeta;
    }

    public void setP_idmeta(int p_idmeta) {
        this.p_idmeta = p_idmeta;
    }

    public int getP_idmetaobra() {
        return p_idmetaobra;
    }

    public void setP_idmetaobra(int p_idmetaobra) {
        this.p_idmetaobra = p_idmetaobra;
    }

    public int getP_idmetaregistro() {
        return p_idmetaregistro;
    }

    public void setP_idmetaregistro(int p_idmetaregistro) {
        this.p_idmetaregistro = p_idmetaregistro;
    }

    public boolean isPactualizar() {
        return pactualizar;
    }

    public void setPactualizar(boolean pactualizar) {
        this.pactualizar = pactualizar;
    }

    public int getP_periodomedida() {
        return p_periodomedida;
    }

    public void setP_periodomedida(int p_periodomedida) {
        this.p_periodomedida = p_periodomedida;
    }

    public Date getFecharegistroInicio() {
        return fecharegistroInicio;
    }

    public void setFecharegistroInicio(Date fecharegistroInicio) {
        this.fecharegistroInicio = fecharegistroInicio;
    }

    public int getP_idperiodofrecuencia() {
        return p_idperiodofrecuencia;
    }

    public void setP_idperiodofrecuencia(int p_idperiodofrecuencia) {
        this.p_idperiodofrecuencia = p_idperiodofrecuencia;
    }

    public boolean isPerteneceGrupoMetas() {
        return perteneceGrupoMetas;
    }

    public void setPerteneceGrupoMetas(boolean perteneceGrupoMetas) {
        this.perteneceGrupoMetas = perteneceGrupoMetas;
    }

    public boolean isPerteneceGrupoSupervisor() {
        return perteneceGrupoSupervisor;
    }

    public void setPerteneceGrupoSupervisor(boolean perteneceGrupoSupervisor) {
        this.perteneceGrupoSupervisor = perteneceGrupoSupervisor;
    }

    public boolean isPerteneceGrupoAdministrador() {
        return perteneceGrupoAdministrador;
    }

    public void setPerteneceGrupoAdministrador(boolean perteneceGrupoAdministrador) {
        this.perteneceGrupoAdministrador = perteneceGrupoAdministrador;
    }

    public String getActivePanel() {
        return activePanel;
    }

    public void setActivePanel(String activePanel) {
        this.activePanel = activePanel;
    }

    public ReporteMetasVO getReportesVO() {
        return reportesVO;
    }

    public void setReportesVO(ReporteMetasVO reportesVO) {
        this.reportesVO = reportesVO;
    }

    public Set<PdObjetivo> getS_objetivos() {
        return s_objetivos;
    }

    public void setS_objetivos(Set<PdObjetivo> s_objetivos) {
        this.s_objetivos = s_objetivos;
    }

    public Set<PdPrograma> getS_programas() {
        return s_programas;
    }

    public void setS_programas(Set<PdPrograma> s_programas) {
        this.s_programas = s_programas;
    }

    public Set<PdSubprograma> getS_subprogramas() {
        return s_subprogramas;
    }

    public void setS_subprogramas(Set<PdSubprograma> s_subprogramas) {
        this.s_subprogramas = s_subprogramas;
    }

    public int getP_idobjetivo() {
        return p_idobjetivo;
    }

    public int getP_idplandesarrollo() {
        return p_idplandesarrollo;
    }

    public void setP_idplandesarrollo(int p_idplandesarrollo) {
        this.p_idplandesarrollo = p_idplandesarrollo;
    }

    public void setP_idobjetivo(int p_idobjetivo) {
        this.p_idobjetivo = p_idobjetivo;
    }

    public int getP_idprograma() {
        return p_idprograma;
    }

    public void setP_idprograma(int p_idprograma) {
        this.p_idprograma = p_idprograma;
    }

    public int getP_idsubprograma() {
        return p_idsubprograma;
    }

    public void setP_idsubprograma(int p_idsubprograma) {
        this.p_idsubprograma = p_idsubprograma;
    }

    public String getP_nombre_plandesarrollo() {
        return p_nombre_plandesarrollo;
    }

    public void setP_nombre_plandesarrollo(String p_nombre_plandesarrollo) {
        this.p_nombre_plandesarrollo = p_nombre_plandesarrollo;
    }

    public String getP_nombre_objetivo() {
        return p_nombre_objetivo;
    }

    public void setP_nombre_objetivo(String p_nombre_objetivo) {
        this.p_nombre_objetivo = p_nombre_objetivo;
    }

    public String getP_nombre_programa() {
        return p_nombre_programa;
    }

    public void setP_nombre_programa(String p_nombre_programa) {
        this.p_nombre_programa = p_nombre_programa;
    }

    public String getP_nombre_subprograma() {
        return p_nombre_subprograma;
    }

    public void setP_nombre_subprograma(String p_nombre_subprograma) {
        this.p_nombre_subprograma = p_nombre_subprograma;
    }

    public PlanDesarrollo getP_plandesarrollo() {
        return p_plandesarrollo;
    }

    public void setP_plandesarrollo(PlanDesarrollo p_plandesarrollo) {
        this.p_plandesarrollo = p_plandesarrollo;
    }

    public boolean isVerPanelesPeriodos() {
        return verPanelesPeriodos;
    }

    public void setVerPanelesPeriodos(boolean verPanelesPeriodos) {
        this.verPanelesPeriodos = verPanelesPeriodos;
    }

    public PeriodosFrecuencia getPer() {
        return per;
    }

    public void setPer(PeriodosFrecuencia per) {
        this.per = per;
    }

    public String[][] getArregloValoresEtiquetasPeriodosAnios() {
        return arregloValoresEtiquetasPeriodosAnios;
    }

    public void setArregloValoresEtiquetasPeriodosAnios(String[][] arregloValoresEtiquetasPeriodosAnios) {
        this.arregloValoresEtiquetasPeriodosAnios = arregloValoresEtiquetasPeriodosAnios;
    }

    public boolean[] getMostrarValoresEtiquetasPeriodos() {
        return mostrarValoresEtiquetasPeriodos;
    }

    public void setMostrarValoresEtiquetasPeriodos(boolean[] mostrarValoresEtiquetasPeriodos) {
        this.mostrarValoresEtiquetasPeriodos = mostrarValoresEtiquetasPeriodos;
    }

    public int[] getAniosGobierno() {
        return aniosGobierno;
    }

    public void setAniosGobierno(int[] aniosGobierno) {
        this.aniosGobierno = aniosGobierno;
    }

    /**
     * Esta implementacion mantiene que cuando se abra otra ventana, permanezca
     * activa el tab en el cual se estaba trabajando
     *
     * @author Manuel Cortes Granados
     * @param idpanel
     */
    public void cambiarActivePanel(int idpanel) {
        switch (idpanel) {
            case 1:
                activePanel = "tabParametrizacion";
                break;
            case 2:
                activePanel = "tabAsociacion";
                break;
            case 3:
                activePanel = "tabConsultaRegistroAvance";
                break;
            case 4:
                activePanel = "tabGraficos";
                break;
            case 5:
                activePanel = "tabReporte";
                break;
        }
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 23 2014 11:40 AM
     */
    public MetasProyecto() throws Exception {

        Utilitario util = new Utilitario();

        this.listaUnidadMedida.add("METRO");
        this.listaUnidadMedida.add("KILOMETRO");
        this.listaUnidadMedida.add("UNIDAD");
        this.listaUnidadMedida.add("METRO CUADRADO");
        this.listaUnidadMedida.add("LITRO");

        this.getPlanDesarrollo();
        cargarListaPeriodoMedida(); // Carga los valores de la tabla public.periodomedida para el cuadro combinado
        consultarMetaRegistro();

        this.perteneceGrupoMetas = false;
        this.perteneceGrupoSupervisor = false;
        this.perteneceGrupoAdministrador = false;
        reportesVO = new ReporteMetasVO();
        arregloValoresEtiquetasPeriodosAnios = new String[4][12];
        mostrarValoresEtiquetasPeriodos = new boolean[12];
        for (int i = 0; i < 12; i++) {
            mostrarValoresEtiquetasPeriodos[1] = false;
        }

        aniosGobierno = new int[4];
        this.getAniosGobierno()[0] = util.getYEARfromDate(this.getP_plandesarrollo().getFechaInicialPeriodo());
        this.getAniosGobierno()[1] = this.getAniosGobierno()[0] + 1;
        this.getAniosGobierno()[2] = this.getAniosGobierno()[0] + 2;
        this.getAniosGobierno()[3] = this.getAniosGobierno()[0] + 3;

        /*for(int anio=0;anio<3;anio++){
         arregloValoresEtiquetasPeriodosAnios[anio]=new String[];
         }*/
    }

    /**
     * @author Manuel Cortes Granados
     * @throws java.lang.Exception
     * @since Julio 24 2014 2:29 PM
     */
    public void consultarPermisosUsuario() throws Exception {
        this.perteneceGrupoMetas = this.getSessionBeanCobra().getMetasService().verificarUsuarioYGrupo(getSessionBeanCobra().getUsuarioObra().getUsuId(), 33);
        this.perteneceGrupoSupervisor = this.getSessionBeanCobra().getMetasService().verificarUsuarioYGrupo(getSessionBeanCobra().getUsuarioObra().getUsuId(), 23);
        this.perteneceGrupoAdministrador = this.getSessionBeanCobra().getMetasService().verificarUsuarioYGrupo(getSessionBeanCobra().getUsuarioObra().getUsuId(), 1);

        if (perteneceGrupoAdministrador) { // Si es Administrador, pasa por alto los render o sencillamente muestra todo
            perteneceGrupoMetas = perteneceGrupoSupervisor = true;
        }
    }

    /**
     * @author Manuel Cortes Granados
     * @since Julio 18 2014 1:31 PM
     */
    public void cargarListaPeriodoMedida() {
        this.listaPeriodoMedida = getSessionBeanCobra().getMetasService().consultarPeriodosMedidaVigentes();
    }

    /**
     * @author Manuel Cortes Granados
     * @since 18 Julio 2014 13:24
     * @param periodomedida
     * @throws Exception
     */
    public void cargarListaPeriodosFrecuencia(int periodomedida) throws Exception {
        this.listaPeriodosFrecuencia = this.getSessionBeanCobra().getMetasService().selectPeriodosFrecuencia(periodomedida);
    }

    /**
     * Este metodo tiene por objeto asignar las fehas correspondientes a los
     * rangos de frecuencia correspondientes
     *
     * @author Manuel Cortes Granados
     * @since 19 Julio 2014 3:32 AM
     */
    public void cargarYAsignarFechasInicioFinal(ValueChangeEvent event) throws Exception {
        Utilitario util = new Utilitario();
        String idpfrecuencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idperiodosFrecuencia");
        p_idperiodofrecuencia = Integer.valueOf(event.getNewValue().toString()).intValue();
        PeriodosFrecuencia per = this.getSessionBeanCobra().getMetasService().select_by_idperiodos_frecuencia(p_idperiodofrecuencia);
        String fechaInicial = new String("2014/" + per.getMesInicial() + "/" + per.getDiaInicial());
        String fechaFinal = new String("2014/" + per.getMesFinal() + "/" + per.getDiaFinal());
        metaregistro = new Metaregistro();
        metaregistro.setFechaInicio(util.convertirStringtoDate(fechaInicial));
        metaregistro.setFechaFinal(util.convertirStringtoDate(fechaFinal));
        FacesContext.getCurrentInstance().renderResponse();
    }

    /**
     * @author Manuel Cortes Granados
     * @since 9 Agosto 2014 21:38
     * @param event
     */
    public void actualizarPanelesPeriodos(ValueChangeEvent event) {

        switch (Integer.valueOf(event.getNewValue().toString()).intValue()) {
            case 3:
                for (int anio = 0; anio < 4; anio++) {
                    arregloValoresEtiquetasPeriodosAnios[anio][0] = "Valor Proyectado Enero " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][1] = "Valor Proyectado Febrero " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][2] = "Valor Proyectado Marzo " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][3] = "Valor Proyectado Abril " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][4] = "Valor Proyectado Mayo " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][5] = "Valor Proyectado Junio " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][6] = "Valor Proyectado Julio " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][7] = "Valor Proyectado Agosto " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][8] = "Valor Proyectado Septiembre  " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][9] = "Valor Proyectado Octubre " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][10] = "Valor Proyectado Noviembre " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][11] = "Valor Proyectado Diciembre " + this.getAniosGobierno()[anio] + " : ";
                }
                mostrarValoresEtiquetasPeriodos[0] = true;
                mostrarValoresEtiquetasPeriodos[1] = true;
                mostrarValoresEtiquetasPeriodos[2] = true;
                mostrarValoresEtiquetasPeriodos[3] = true;
                mostrarValoresEtiquetasPeriodos[4] = true;
                mostrarValoresEtiquetasPeriodos[5] = true;
                mostrarValoresEtiquetasPeriodos[6] = true;
                mostrarValoresEtiquetasPeriodos[7] = true;
                mostrarValoresEtiquetasPeriodos[8] = true;
                mostrarValoresEtiquetasPeriodos[9] = true;
                mostrarValoresEtiquetasPeriodos[10] = true;
                mostrarValoresEtiquetasPeriodos[11] = true;
                break;
            case 4:
                for (int anio = 0; anio < 4; anio++) {
                    arregloValoresEtiquetasPeriodosAnios[anio][0] = "Valor Proyectado Bimestre Enero/Febrero " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][1] = "Valor Proyectado Bimestre Marzo/Abril " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][2] = "Valor Proyectado Bimestre Mayo/Junio " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][3] = "Valor Proyectado Bimestre Julio/Agosto " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][4] = "Valor Proyectado Bimestre Septiembre/Octubre " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][5] = "Valor Proyectado Bimestre Noviembre/Diciembre " + this.getAniosGobierno()[anio] + " : ";
                }
                mostrarValoresEtiquetasPeriodos[0] = true;
                mostrarValoresEtiquetasPeriodos[1] = true;
                mostrarValoresEtiquetasPeriodos[2] = true;
                mostrarValoresEtiquetasPeriodos[3] = true;
                mostrarValoresEtiquetasPeriodos[4] = true;
                mostrarValoresEtiquetasPeriodos[5] = true;
                mostrarValoresEtiquetasPeriodos[6] = false;
                mostrarValoresEtiquetasPeriodos[7] = false;
                mostrarValoresEtiquetasPeriodos[8] = false;
                mostrarValoresEtiquetasPeriodos[9] = false;
                mostrarValoresEtiquetasPeriodos[10] = false;
                mostrarValoresEtiquetasPeriodos[11] = false;
                break;
            case 5:
                for (int anio = 0; anio < 4; anio++) {
                    arregloValoresEtiquetasPeriodosAnios[anio][0] = "Valor Proyectado Trimestre Enero-Marzo " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][1] = "Valor Proyectado Trimestre Abril-Junio " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][2] = "Valor Proyectado Trimestre Julio-Septiembre " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][3] = "Valor Proyectado Trimestre Octubre-Diciembre " + this.getAniosGobierno()[anio] + " : ";
                }
                mostrarValoresEtiquetasPeriodos[0] = true;
                mostrarValoresEtiquetasPeriodos[1] = true;
                mostrarValoresEtiquetasPeriodos[2] = true;
                mostrarValoresEtiquetasPeriodos[3] = true;
                mostrarValoresEtiquetasPeriodos[4] = false;
                mostrarValoresEtiquetasPeriodos[5] = false;
                mostrarValoresEtiquetasPeriodos[6] = false;
                mostrarValoresEtiquetasPeriodos[7] = false;
                mostrarValoresEtiquetasPeriodos[8] = false;
                mostrarValoresEtiquetasPeriodos[9] = false;
                mostrarValoresEtiquetasPeriodos[10] = false;
                mostrarValoresEtiquetasPeriodos[11] = false;
                break;
            case 6:
                for (int anio = 0; anio < 4; anio++) {
                    arregloValoresEtiquetasPeriodosAnios[anio][0] = "Valor Proyectado Cuatrimestre Enero-Abril " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][1] = "Valor Proyectado Cuatrimestre Mayo-Agosto " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][2] = "Valor Proyectado Cuatrimestre Septiembre-Diciembre " + this.getAniosGobierno()[anio] + " : ";
                }
                mostrarValoresEtiquetasPeriodos[0] = true;
                mostrarValoresEtiquetasPeriodos[1] = true;
                mostrarValoresEtiquetasPeriodos[2] = true;
                mostrarValoresEtiquetasPeriodos[3] = false;
                mostrarValoresEtiquetasPeriodos[4] = false;
                mostrarValoresEtiquetasPeriodos[5] = false;
                mostrarValoresEtiquetasPeriodos[6] = false;
                mostrarValoresEtiquetasPeriodos[7] = false;
                mostrarValoresEtiquetasPeriodos[8] = false;
                mostrarValoresEtiquetasPeriodos[9] = false;
                mostrarValoresEtiquetasPeriodos[10] = false;
                mostrarValoresEtiquetasPeriodos[11] = false;
                break;
            case 7:
                for (int anio = 0; anio < 4; anio++) {
                    arregloValoresEtiquetasPeriodosAnios[anio][0] = "Valor Proyectado Semestre Enero-Junio " + this.getAniosGobierno()[anio] + " : ";
                    arregloValoresEtiquetasPeriodosAnios[anio][1] = "Valor Proyectado Semestre Julio-Diciembre " + this.getAniosGobierno()[anio] + " : ";
                }
                mostrarValoresEtiquetasPeriodos[0] = true;
                mostrarValoresEtiquetasPeriodos[1] = true;
                mostrarValoresEtiquetasPeriodos[2] = false;
                mostrarValoresEtiquetasPeriodos[3] = false;
                mostrarValoresEtiquetasPeriodos[4] = false;
                mostrarValoresEtiquetasPeriodos[5] = false;
                mostrarValoresEtiquetasPeriodos[6] = false;
                mostrarValoresEtiquetasPeriodos[7] = false;
                mostrarValoresEtiquetasPeriodos[8] = false;
                mostrarValoresEtiquetasPeriodos[9] = false;
                mostrarValoresEtiquetasPeriodos[10] = false;
                mostrarValoresEtiquetasPeriodos[11] = false;
                break;
        }
    }

    /**
     * @author Manuel Cortes Granados
     * @since 19 Julio 2014 3:56 PM
     * @param event
     * @throws Exception
     */
    public void actualizarCampoPorcentaje(ValueChangeEvent event) throws Exception {
        //metaregistro.setPorcentaje((metaregistro.getMetaAcumulada() / metaregistro.getMetaProyectada()) * 100);
        FacesContext.getCurrentInstance().renderResponse();
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 23 2014 11:40 AM
     * @return
     */
    public String irApagina_Metas() throws Exception {
        meta = new Meta();
        metaobra = new Metaobra();
        metaregistro = new Metaregistro();
        metaobraamp = new MetaObraAMPVO();
        metaregistroamp = new MetaRegistroAMPVO();
        Utilitario util = new Utilitario();
        this.consultarMetaObra();
        this.consultarMetaRegistro();
        listaMetas = getSessionBeanCobra().getCobraService().consultarMetaObras();
        generarArchivoXMLFusionChart_MetaRegistro();
        consultarPermisosUsuario();
        getPlanDesarrollo();
        return "Metas";
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 23 2014 11:40 AM
     * @return
     */
    public String irApagina_ParametrizacionMetas() throws Exception {
        getPlanDesarrollo();
        return "MetasParametrizacion";
    }

    /**
     * Inicializa los valores de cada valor proyectado de cada periodo en cero
     * por defecto, ya que algunos se requieren que queden cero (menos cuando la
     * frecuencia de medicion sea mensual)
     *
     * @author Manuel Cortes GranadoFs
     * @since Mayo 23 2014 11:40 AM
     * @return
     */
    public String irApagina_IngresarMeta() throws Exception {
        meta = new Meta();
        this.getMeta().setNomp(0);
        this.getMeta().setNombreindicador("Nombre Indicador");
        this.getMeta().setDescripcionmetaproducto("Descripcion Meta Producto");
        this.getMeta().setTipometa(0);
        return "IngresarMeta";
    }

    /**
     * @author Manuel Cortes Granados
     * @throws java.lang.Exception
     * @since Mayo 23 2014 11:40 AM
     * @return
     */
    public String irApagina_ActualizarMeta() throws Exception {
        if (validarActualizacionEliminacionMeta(this.p_idmeta)) {
            consultarMetaparaActualizar();
            return "ActualizarMeta";
        } else {
            FacesUtils.addErrorMessage("No es posible actualizar la Meta ya que tiene Proyectos Asociados");
            return "Metas";
        }
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 23 2014 11:40 AM
     * @return
     */
    public String irApagina_IngresarMetaObra() throws Exception {
        consultarMetaparaConsulta();
        return "IngresarMetaObra";
    }

    /**
     * @author Manuel Cortes Granados
     * @since Julio 07 2014 9:28 PM
     * @throws Exception
     */
    public void consultarMetaparaConsulta() throws Exception {
        meta_consulta = this.getSessionBeanCobra().getMetasService().selectMeta(p_idmeta);
    }

    /**
     * @author Manuel Cortes Granados
     * @throws java.lang.Exception
     * @since Mayo 23 2014 11:40 AM
     * @return
     */
    public String irApagina_ActualizarMetaObra() throws Exception {
        if (validarActualizacionEliminacionMetaObra(this.p_idmetaobra)) {
            this.consultarMetaObraparaActualizar();
            consultarMetaparaConsulta();
            return "ActualizarMetaObra";
        } else {
            FacesUtils.addErrorMessage("No es posible actualizar la asociacion de la Meta con el Proyecto, ya que tiene registro de programacion asociados");
            return "Metas";
        }
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 23 2014 11:40 AM
     * @return
     */
    public String irApagina_IngresarMetaRegistro() throws Exception {
        this.cargarListaPeriodosFrecuencia(this.p_periodomedida);
        consultarMetaparaConsulta();
        return "IngresarMetaRegistro";
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 23 2014 11:40 AM
     * @return
     */
    public String irApagina_ActualizarMetaRegistro() throws Exception {
        consultarMetaRegistroparaActualizar();
        cargarListaPeriodosFrecuencia(metaobra.getPeriodomedida().getIntidperiomedida());
        return "ActualizarMetaRegistro";
    }

    /**
     * @author Manuel Cortes Granados
     * @throws java.sql.SQLException
     * @since Mayo 24 2014 11:40 AM
     * @return
     */
    public String ingresarMeta() throws Exception {
        if (validarDatosMetaAntesInsercion()) {
            meta.setPlanDesarrollo(p_plandesarrollo);
            meta.setIdobjetivo(p_idobjetivo);
            meta.setIdprograma(p_idprograma);
            meta.setIdsubprograma(p_idsubprograma);
            this.getSessionBeanCobra().getMetasService().guardarMeta(meta);
            FacesUtils.addInfoMessage("La meta ha sido ingresada con exito");
        }
        return null;
    }

    /**
     * @author Manuel Cortes Granados
     * @since 15 Agosto 2014 12:22
     * @return
     */
    public boolean validarDatosMetaAntesInsercion() {

        double total_anio1 = 0;
        double total_anio2 = 0;
        double total_anio3 = 0;
        double total_anio4 = 0;

        double total_cuatrenio_por_anios = meta.getProyectadoAnio1() + meta.getProyectadoAnio2() + meta.getProyectadoAnio3() + meta.getProyectadoAnio4();
        if (meta.getValorEsperadoIndicador() == total_cuatrenio_por_anios) {
            FacesUtils.addErrorMessage("El total de lo proyectado de los cuatro años debe ser estrictamente IGUAL al valor esperado del cuatrenio");
            return false;
        }
        switch (meta.getFrecuenciaMedicion()) {
            case 3: // SI LA FRECUENCIA DE MEDICIO ES MENSUAL
                total_anio1 = meta.getProyectadoPeriodo1Anio1()
                        + meta.getProyectadoPeriodo2Anio1()
                        + meta.getProyectadoPeriodo3Anio1()
                        + meta.getProyectadoPeriodo4Anio1()
                        + meta.getProyectadoPeriodo5Anio1()
                        + meta.getProyectadoPeriodo6Anio1()
                        + meta.getProyectadoPeriodo7Anio1()
                        + meta.getProyectadoPeriodo8Anio1()
                        + meta.getProyectadoPeriodo9Anio1()
                        + meta.getProyectadoPeriodo10Anio1()
                        + meta.getProyectadoPeriodo11Anio1()
                        + meta.getProyectadoPeriodo12Anio1();
                if (total_anio1 != this.getMeta().getProyectadoAnio1()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 12 meses del año 1 o periodos debe ser igual al Proyectado anual del año 1, si la frecuencia de medicion es mensual");
                    return false;
                }
                total_anio2 = meta.getProyectadoPeriodo1Anio2()
                        + meta.getProyectadoPeriodo2Anio2()
                        + meta.getProyectadoPeriodo3Anio2()
                        + meta.getProyectadoPeriodo4Anio2()
                        + meta.getProyectadoPeriodo5Anio2()
                        + meta.getProyectadoPeriodo6Anio2()
                        + meta.getProyectadoPeriodo7Anio2()
                        + meta.getProyectadoPeriodo8Anio2()
                        + meta.getProyectadoPeriodo9Anio2()
                        + meta.getProyectadoPeriodo10Anio2()
                        + meta.getProyectadoPeriodo11Anio2()
                        + meta.getProyectadoPeriodo12Anio2();
                if (total_anio2 != this.getMeta().getProyectadoAnio2()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 12 meses o periodos del Año 2 debe ser igual al Proyectado anual del año 2, si la frecuencia de medicion es mensual");
                    return false;
                }
                total_anio3 = meta.getProyectadoPeriodo1Anio3()
                        + meta.getProyectadoPeriodo2Anio3()
                        + meta.getProyectadoPeriodo3Anio3()
                        + meta.getProyectadoPeriodo4Anio3()
                        + meta.getProyectadoPeriodo5Anio3()
                        + meta.getProyectadoPeriodo6Anio3()
                        + meta.getProyectadoPeriodo7Anio3()
                        + meta.getProyectadoPeriodo8Anio3()
                        + meta.getProyectadoPeriodo9Anio3()
                        + meta.getProyectadoPeriodo10Anio3()
                        + meta.getProyectadoPeriodo11Anio3()
                        + meta.getProyectadoPeriodo12Anio3();
                if (total_anio3 != this.getMeta().getProyectadoAnio3()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 12 meses o periodos del Año 3 debe ser igual al Proyectado anual del año 3, si la frecuencia de medicion es mensual");
                    return false;
                }
                total_anio4 = meta.getProyectadoPeriodo1Anio4()
                        + meta.getProyectadoPeriodo2Anio4()
                        + meta.getProyectadoPeriodo3Anio4()
                        + meta.getProyectadoPeriodo4Anio4()
                        + meta.getProyectadoPeriodo5Anio4()
                        + meta.getProyectadoPeriodo6Anio4()
                        + meta.getProyectadoPeriodo7Anio4()
                        + meta.getProyectadoPeriodo8Anio4()
                        + meta.getProyectadoPeriodo9Anio4()
                        + meta.getProyectadoPeriodo10Anio4()
                        + meta.getProyectadoPeriodo11Anio4()
                        + meta.getProyectadoPeriodo12Anio4();
                if (total_anio4 != this.getMeta().getProyectadoAnio4()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 12 meses o periodos del Año 4 debe ser igual al Proyectado anual del año 4, si la frecuencia de medicion es mensual");
                    return false;
                }
                break;
            case 4: // SI LA FRECUENCIA DE MEDICIO ES BIMESTRAL
                total_anio1 = meta.getProyectadoPeriodo1Anio1()
                        + meta.getProyectadoPeriodo2Anio1()
                        + meta.getProyectadoPeriodo3Anio1()
                        + meta.getProyectadoPeriodo4Anio1()
                        + meta.getProyectadoPeriodo5Anio1();
                if (total_anio1 != this.getMeta().getProyectadoAnio1()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 6 bimestres del año 1 o periodos debe ser igual al Proyectado anual del año 1, si la frecuencia de medicion es bimestral");
                    return false;
                }
                total_anio2 = meta.getProyectadoPeriodo1Anio2()
                        + meta.getProyectadoPeriodo2Anio2()
                        + meta.getProyectadoPeriodo3Anio2()
                        + meta.getProyectadoPeriodo4Anio2()
                        + meta.getProyectadoPeriodo5Anio2();
                if (total_anio2 != this.getMeta().getProyectadoAnio2()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 6 bimestres o periodos del Año 2 debe ser igual al Proyectado anual del año 2, si la frecuencia de medicion es bimestral");
                    return false;
                }
                total_anio3 = meta.getProyectadoPeriodo1Anio3()
                        + meta.getProyectadoPeriodo2Anio3()
                        + meta.getProyectadoPeriodo3Anio3()
                        + meta.getProyectadoPeriodo4Anio3()
                        + meta.getProyectadoPeriodo5Anio3();
                if (total_anio3 != this.getMeta().getProyectadoAnio3()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 6 meses o periodos del Año 3 debe ser igual al Proyectado anual del año 3, si la frecuencia de medicion es bimestral");
                    return false;
                }
                total_anio4 = meta.getProyectadoPeriodo1Anio4()
                        + meta.getProyectadoPeriodo2Anio4()
                        + meta.getProyectadoPeriodo3Anio4()
                        + meta.getProyectadoPeriodo4Anio4()
                        + meta.getProyectadoPeriodo5Anio4();
                if (total_anio4 != this.getMeta().getProyectadoAnio4()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 6 bimestres o periodos del Año 4 debe ser igual al Proyectado anual del año 4, si la frecuencia de medicion es bimestral");
                    return false;
                }
                break;
            case 6: // SI LA FRECUENCIA DE MEDICIO ES TRIMESTRAL
                total_anio1 = meta.getProyectadoPeriodo1Anio1()
                        + meta.getProyectadoPeriodo2Anio1()
                        + meta.getProyectadoPeriodo3Anio1()
                        + meta.getProyectadoPeriodo4Anio1();
                if (total_anio1 != this.getMeta().getProyectadoAnio1()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 4 trimestres del año 1 o periodos debe ser igual al Proyectado anual del año 1, si la frecuencia de medicion es trimestral");
                    return false;
                }
                total_anio2 = meta.getProyectadoPeriodo1Anio2()
                        + meta.getProyectadoPeriodo2Anio2()
                        + meta.getProyectadoPeriodo3Anio2()
                        + meta.getProyectadoPeriodo4Anio2();
                if (total_anio2 != this.getMeta().getProyectadoAnio2()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 4 trimestres o periodos del Año 2 debe ser igual al Proyectado anual del año 2, si la frecuencia de medicion es trimestral");
                    return false;
                }
                total_anio3 = meta.getProyectadoPeriodo1Anio3()
                        + meta.getProyectadoPeriodo2Anio3()
                        + meta.getProyectadoPeriodo3Anio3()
                        + meta.getProyectadoPeriodo4Anio3();
                if (total_anio3 != this.getMeta().getProyectadoAnio3()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 4 trimestres o periodos del Año 3 debe ser igual al Proyectado anual del año 3, si la frecuencia de medicion es trimestral");
                    return false;
                }
                total_anio4 = meta.getProyectadoPeriodo1Anio4()
                        + meta.getProyectadoPeriodo2Anio4()
                        + meta.getProyectadoPeriodo3Anio4()
                        + meta.getProyectadoPeriodo4Anio4();
                if (total_anio4 != this.getMeta().getProyectadoAnio4()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 4 trimestres o periodos del Año 4 debe ser igual al Proyectado anual del año 4, si la frecuencia de medicion es trimestral");
                    return false;
                }
                break;
            case 7: // SI LA FRECUENCIA DE MEDICIO ES CUATRIMESTRAL
                total_anio1 = meta.getProyectadoPeriodo1Anio1()
                        + meta.getProyectadoPeriodo2Anio1()
                        + meta.getProyectadoPeriodo3Anio1();
                if (total_anio1 != this.getMeta().getProyectadoAnio1()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 3 cuatrimestres del año 1 o periodos debe ser igual al Proyectado anual del año 1, si la frecuencia de medicion es cuatrimestral");
                    return false;
                }
                total_anio2 = meta.getProyectadoPeriodo1Anio2()
                        + meta.getProyectadoPeriodo2Anio2()
                        + meta.getProyectadoPeriodo3Anio2();
                if (total_anio2 != this.getMeta().getProyectadoAnio2()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 3 cuatrimestres o periodos del Año 2 debe ser igual al Proyectado anual del año 2, si la frecuencia de medicion es cuatrimestral");
                    return false;
                }
                total_anio3 = meta.getProyectadoPeriodo1Anio3()
                        + meta.getProyectadoPeriodo2Anio3()
                        + meta.getProyectadoPeriodo3Anio3();
                if (total_anio3 != this.getMeta().getProyectadoAnio3()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 3 cuatrimestres o periodos del Año 3 debe ser igual al Proyectado anual del año 3, si la frecuencia de medicion es cuatrimestral");
                    return false;
                }
                total_anio4 = meta.getProyectadoPeriodo1Anio4()
                        + meta.getProyectadoPeriodo2Anio4()
                        + meta.getProyectadoPeriodo3Anio4();
                if (total_anio4 != this.getMeta().getProyectadoAnio4()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 3 cuatrimestres o periodos del Año 4 debe ser igual al Proyectado anual del año 4, si la frecuencia de medicion es cuatrimestral");
                    return false;
                }
                break;
            case 8: // SI LA FRECUENCIA DE MEDICIO ES SEMESTRAL
                total_anio1 = meta.getProyectadoPeriodo1Anio1()
                        + meta.getProyectadoPeriodo2Anio1();
                if (total_anio1 != this.getMeta().getProyectadoAnio1()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 2 semestres del año 1 o periodos debe ser igual al Proyectado anual del año 1, si la frecuencia de medicion es semestral");
                    return false;
                }
                total_anio2 = meta.getProyectadoPeriodo1Anio2()
                        + meta.getProyectadoPeriodo2Anio2();
                if (total_anio2 != this.getMeta().getProyectadoAnio2()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 2 semestres o periodos del Año 2 debe ser igual al Proyectado anual del año 2, si la frecuencia de medicion es semestral");
                    return false;
                }
                total_anio3 = meta.getProyectadoPeriodo1Anio3()
                        + meta.getProyectadoPeriodo2Anio3();
                if (total_anio3 != this.getMeta().getProyectadoAnio3()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 2 semestres o periodos del Año 3 debe ser igual al Proyectado anual del año 3, si la frecuencia de medicion es semestral");
                    return false;
                }
                total_anio4 = meta.getProyectadoPeriodo1Anio4()
                        + meta.getProyectadoPeriodo2Anio4();
                if (total_anio4 != this.getMeta().getProyectadoAnio4()) {
                    FacesUtils.addErrorMessage("La suma de lo proyectado de cada uno de los 2 semestres o periodos del Año 4 debe ser igual al Proyectado anual del año 4, si la frecuencia de medicion es cuatrimestral");
                    return false;
                }
                break;
        }
        return true;
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 24 2014 11:40 AM
     * @return
     */
    public String actualizarMeta() {
        PlanDesarrollo planDesarrollo = new PlanDesarrollo();
        planDesarrollo.setIntidplandesarrollo(p_idplandesarrollo);
        meta.setPlanDesarrollo(planDesarrollo);
        meta.setIdprograma(p_idprograma);
        meta.setIdsubprograma(p_idsubprograma);
        this.getSessionBeanCobra().getMetasService().actualizarMeta(meta);
        return null;
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 27 2014 11:26 AM
     * @return
     * @throws Exception
     */
    public String eliminarMeta() throws Exception {
        Utilitario util = new Utilitario();
        try {
            if (this.p_idmeta == 0) {
                FacesUtils.addErrorMessage("El registro o fila no ha sido debidamente bien seleccionada.");
            } else if (validarActualizacionEliminacionMeta(this.p_idmeta)) {
                this.getSessionBeanCobra().getMetasService().eliminarMeta(meta);
                FacesUtils.addInfoMessage("La meta con id " + this.getP_idmeta() + " ha sido eliminada con exito");
            } else {
                FacesUtils.addErrorMessage("No es posible eliminar la Meta ya que tiene Proyectos Asociados");
            }
            return null;
        } catch (SQLException se) {
            procesarError(se);
        }
        return null;
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 27 2014 11:26 AM
     * @return
     * @throws Exception
     */
    public void consultarMetaparaActualizar() throws Exception {
        meta_consulta = this.getSessionBeanCobra().getMetasService().selectMeta(p_idmeta);
    }

    /**
     * @author Manuel Cortes Granados
     * @since 1 Junio 2014 8:31 PM
     * @param idmeta
     * @return
     * @throws Exception
     */
    public boolean validarActualizacionEliminacionMeta(int idmeta) throws Exception {
        boolean resultado = true;
        List<Metaobra> l_resultado = this.getSessionBeanCobra().getMetasService().getMetaObrabyMeta(idmeta);

        if (l_resultado.isEmpty() == false) {
            resultado = false;
        }
        return resultado;
    }

    /**
     * @author Manuel Cortes Granados
     * @param metaobra
     * @throws java.lang.Exception
     * @since 1 Junio 2014 8:31 PM
     * @return
     */
    public boolean validarInsercionMeta(Metaobra metaobra) throws Exception {
        boolean resultado = true;
        return resultado;
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 24 2014 11:40 AM
     * @return
     */
    public String ingresarMetaObra() throws Exception {
        Utilitario util = new Utilitario();
        try {
            Meta meta_1 = new Meta();
            meta_1.setId(p_idmeta);
            metaobra.setMeta(meta_1);
            metaobra.setIdproyecto(this.p_idcodigoobra);
            if (validarInsercionMeta(metaobra)) {
                getSessionBeanCobra().getCobraService().guardarMetaObra(metaobra);
                FacesUtils.addInfoMessage("La meta ha sido asociada a la obra o proyecto con exito");
                return null;
            } else {
                FacesUtils.addInfoMessage("No es posible asociar la meta con el proyecto, al año especificado, ya que el registro ya existe");
            }
        } catch (SQLException se) {
            procesarError(se);
        }
        return null;
    }

    /**
     *
     * @author Manuel Cortes Granados
     * @since Mayo 24 2014 11:40 AM
     * @return
     */
    public String actualizarMetaObra() throws Exception {
        this.getSessionBeanCobra().getMetasService().actualizarMetaObra(metaobra);
        FacesUtils.addInfoMessage("La meta ha sido actualizada con exito");
        return null;
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 27 2014 11:26 AM
     * @return
     * @throws Exception
     */
    public String eliminarMetaObra() throws Exception {
        Utilitario util = new Utilitario();
        try {
            if (p_idmetaobra == 0) {
                FacesUtils.addErrorMessage("El registro o fila no ha sido debidamente bien seleccionada.");
            } else if (this.validarActualizacionEliminacionMetaObra(p_idmetaobra)) {
                Metaobra metaobra = new Metaobra();
                metaobra.setIdmetaobra(this.getP_idmetaobra());
                this.getSessionBeanCobra().getMetasService().eliminarMetaObra(metaobra);
                FacesUtils.addInfoMessage("La asociacion con Id. " + this.getP_idmetaobra() + " ha sido eliminada con exito");
                return null;
            } else {
                FacesUtils.addErrorMessage("No es posible eliminar la asociacion ya que tiene un registro relacionado en la programacion de registro de metas.");
            }
        } catch (SQLException se) {
            procesarError(se);
        }
        return null;
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 27 2014 11:26 AM
     * @return
     * @throws Exception
     */
    public void consultarMetaObra() throws Exception {
        this.listaMetasObras = this.getSessionBeanCobra().getMetasService().select_amp(p_idcodigoobra);
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 27 2014 11:26 AM
     * @return
     * @throws Exception
     */
    public void consultarMetaObraparaActualizar() throws Exception {
        metaobra = this.getSessionBeanCobra().getMetasService().selectMetaObra(p_idmetaobra);
    }

    /**
     * @author Manuel Cortes Granados
     * @since 1 Junio 2014 8:31 PM
     * @param idmeta
     * @return
     * @throws Exception
     */
    public boolean validarActualizacionEliminacionMetaObra(int idmetaobra) throws Exception {
        boolean resultado = true;

        List<Metaregistro> l_resultado = this.getSessionBeanCobra().getMetasService().getMetaRegistrobyMetaObra(idmetaobra);
        if (l_resultado.isEmpty() == false) {
            resultado = false;
        }
        return resultado;
    }

    /**
     * @author Manuel Cortes Granados
     * @since 1 Junio 2014 8:31 PM
     * @param idmeta
     */
    public void filtrarBusquedaMetaObra() {
        FacesUtils.addInfoMessage("click sobre la fila" + this.p_idmeta);
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 24 2014 11:40 AM
     * @return
     */
    public String ingresarMetaRegistro() throws Exception {
        Utilitario util = new Utilitario();
        Metaobra metaobra_1 = new Metaobra();
        metaobra_1.setIdmetaobra(p_idmetaobra);
        metaregistro.setMetaobra(metaobra_1);
        metaregistro.setPorcentaje((metaregistro.getMetaAcumulada() / metaregistro.getMetaProyectada()) * 100);
        getSessionBeanCobra().getCobraService().guardarMetaRegistro(metaregistro);
        actualizarAcumuladosenMetasRegistros();
        FacesUtils.addInfoMessage("El registro de la meta ha sido ingresada con exito");
        irApagina_ActualizarMetaRegistro();
        return null;
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 24 2014 11:40 AM
     * @return
     */
    public String actualizarMetaRegistro() throws Exception {
        Metaregistro metaregistro = new Metaregistro();
        this.getSessionBeanCobra().getMetasService().actualizarMetaRegistro(metaregistro);
        FacesUtils.addInfoMessage("La programacion del registro de la meta ha sido actualizada con eixto.");
        return null;
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 27 2014 11:26 AM
     * @return
     * @throws Exception
     */
    public String eliminarMetaRegistro(int idmetaregistro) throws Exception {
        Metaregistro metaregistro = new Metaregistro();
        metaregistro.setIdregistrometaobra(this.p_idmetaregistro);
        this.getSessionBeanCobra().getMetasService().eliminarMetaRegistro(metaregistro);
        FacesUtils.addInfoMessage("El Avance de Meta con ID " + this.p_idmetaregistro + " ha sido eliminado con exito");
        FacesContext.getCurrentInstance().renderResponse();
        return null;
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 27 2014 11:26 AM
     * @return
     * @throws Exception
     */
    public void consultarMetaRegistro() {
        listaMetasObrasRegsitro = this.getSessionBeanCobra().getMetasService().select_ampMetaRegistro(p_idcodigoobra);
    }

    /**
     * @author Manuel Cortes Granados
     * @since Mayo 27 2014 11:26 AM
     * @return
     * @throws Exception
     */
    public void consultarMetaRegistroparaActualizar() throws Exception {
        metaregistro = this.getSessionBeanCobra().getMetasService().selectMetaRegistro(this.p_idmetaregistro);
    }

    /**
     * @author Manuel Cortes Granados
     * @since Junio 2 2014 11:26 AM
     * @throws IOException
     */
    public void generarReporteExcelTabla1() throws IOException, Exception {
        try {
            int fila = 0;
            HSSFWorkbook my_workbook = new HSSFWorkbook();
            HSSFSheet my_sheet = my_workbook.createSheet("Bold Style example");
            HSSFCellStyle my_style = my_workbook.createCellStyle();
            HSSFFont my_font = my_workbook.createFont();
            //my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            my_style.setFont(my_font);

            Row row = my_sheet.createRow(fila);
            Cell cell = row.createCell(0);
            cell.setCellValue("Id.");
            cell.setCellStyle(my_style);
            cell = row.createCell(1);
            cell.setCellValue("Objetivo");
            cell.setCellStyle(my_style);
            cell = row.createCell(2);
            cell.setCellValue("Programa");
            cell.setCellStyle(my_style);
            cell = row.createCell(3);
            cell.setCellValue("Subprograma");
            cell.setCellStyle(my_style);
            cell = row.createCell(4);
            cell.setCellValue("Int.Nom.Pro");
            cell.setCellStyle(my_style);
            cell = row.createCell(5);
            cell.setCellValue("Descripcion Meta Producto");
            cell.setCellStyle(my_style);
            cell = row.createCell(6);
            cell.setCellValue("Unidad de Medida");
            cell.setCellStyle(my_style);
            cell = row.createCell(7);
            cell.setCellValue("Nombre Indicador");
            cell.setCellStyle(my_style);
            cell = row.createCell(8);
            cell.setCellValue("IPO Meta");
            cell.setCellStyle(my_style);
            fila++;

            //DataSourceFactory ds = new DataSourceFactory();
            //MetasDAO metDAO = new MetasDAO(getSessionBeanCobra().getDataSourceFactory().getConnection());
            //List<Meta> l_metas = metDAO.select();
            List<Meta> l_metas = null;
            //ds.closeConnection();

            for (Meta meta_1 : l_metas) {
                row = my_sheet.createRow(fila);
                cell = row.createCell(0);
                cell.setCellValue(meta_1.getId());
                cell.setCellStyle(my_style);
                cell = row.createCell(1);
                //cell.setCellValue(meta_1.getObjetivo());
                cell.setCellStyle(my_style);
                cell = row.createCell(2);
                //cell.setCellValue(meta_1.getPrograma());
                cell.setCellStyle(my_style);
                cell = row.createCell(3);
                //cell.setCellValue(meta_1.getSubprograma());
                cell.setCellStyle(my_style);
                cell = row.createCell(4);
                cell.setCellValue(meta_1.getNomp());
                cell.setCellStyle(my_style);
                cell = row.createCell(5);
                cell.setCellValue(meta_1.getDescripcionmetaproducto());
                cell.setCellStyle(my_style);
                cell = row.createCell(6);
                cell.setCellValue(meta_1.getUnidadmedida());
                cell.setCellStyle(my_style);
                cell = row.createCell(7);
                cell.setCellValue(meta_1.getNombreindicador());
                cell.setCellStyle(my_style);
                cell = row.createCell(8);
                cell.setCellValue(meta_1.getTipometa());
                cell.setCellStyle(my_style);
                fila++;
            }

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();

            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-disposition", "attachment;filename=metas.xls");

            ServletOutputStream out = res.getOutputStream();
            my_workbook.write(out);
            out.flush();
            out.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * @author Manuel Cortes Granados
     * @since Junio 2 2014 11:26 AM
     * @throws IOException
     */
    public void generarReporteExcelTabla2() throws IOException {
        try {
            int fila = 0;
            HSSFWorkbook my_workbook = new HSSFWorkbook();
            HSSFSheet my_sheet = my_workbook.createSheet("Bold Style example");
            HSSFCellStyle my_style = my_workbook.createCellStyle();
            HSSFFont my_font = my_workbook.createFont();
            my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            my_style.setFont(my_font);

            Row row = my_sheet.createRow(fila);
            Cell cell = row.createCell(0);
            cell.setCellValue("Id. Meta");
            cell.setCellStyle(my_style);
            cell = row.createCell(1);
            cell.setCellValue("Id. Meta Obra");
            cell.setCellStyle(my_style);
            cell = row.createCell(2);
            cell.setCellValue("Descripcion Meta");
            cell.setCellStyle(my_style);
            cell = row.createCell(2);
            cell.setCellValue("Nombre Proyecto");
            cell.setCellStyle(my_style);
            cell = row.createCell(2);
            cell.setCellValue("A");
            cell.setCellStyle(my_style);
            cell = row.createCell(2);
            cell.setCellValue("Id. Meta Registro");
            cell.setCellStyle(my_style);

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();

            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-disposition", "attachment;filename=metasobras.xls");

            ServletOutputStream out = res.getOutputStream();
            my_workbook.write(out);
            out.flush();
            out.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * @author Manuel Cortes Granados
     * @since Junio 2 2014 11:26 AM
     * @throws IOException
     */
    public void generarReporteExcelTabla3() throws IOException {
        try {
            Utilitario util = new Utilitario();
            int fila = 0;
            HSSFWorkbook my_workbook = new HSSFWorkbook();
            HSSFSheet my_sheet = my_workbook.createSheet("Bold Style example");
            HSSFCellStyle my_style = my_workbook.createCellStyle();
            HSSFFont my_font = my_workbook.createFont();
            my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            my_style.setFont(my_font);

            Row row = my_sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("Id. Meta");
            cell.setCellStyle(my_style);

            cell = row.createCell(1);
            cell.setCellValue("Id. Meta Obra");
            cell.setCellStyle(my_style);

            cell = row.createCell(2);
            cell.setCellValue("Id. Meta Registro");
            cell.setCellStyle(my_style);

            cell = row.createCell(3);
            cell.setCellValue("Meta Proyectada");
            cell.setCellStyle(my_style);

            cell = row.createCell(4);
            cell.setCellValue("Meta Acumulada");
            cell.setCellStyle(my_style);

            cell = row.createCell(5);
            cell.setCellValue("Porcentaje");
            cell.setCellStyle(my_style);

            cell = row.createCell(6);
            cell.setCellValue("Observaciones");
            cell.setCellStyle(my_style);

            cell = row.createCell(7);
            cell.setCellValue("Fecha");
            cell.setCellStyle(my_style);
            fila++;

            my_font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            my_style.setFont(my_font);

            for (Meta meta : this.listaMetas) {
                List<Metaobra> l_metaobra = util.fromSetToList(meta.getMetaobras());
                for (Metaobra metaobra : l_metaobra) {
                    List<Metaregistro> l_metaregistro = util.fromSetToList(metaobra.getMetaregistros());
                    for (Metaregistro metaregistro : l_metaregistro) {
                        row = my_sheet.createRow(fila);

                        cell = row.createCell(0);
                        cell.setCellValue(meta.getId());
                        cell.setCellStyle(my_style);

                        cell = row.createCell(1);
                        cell.setCellValue(metaobra.getIdmetaobra());
                        cell.setCellStyle(my_style);

                        cell = row.createCell(2);
                        cell.setCellValue(metaregistro.getIdregistrometaobra());
                        cell.setCellStyle(my_style);

                        cell = row.createCell(3);
                        cell.setCellValue(metaregistro.getMetaProyectada());
                        cell.setCellStyle(my_style);

                        cell = row.createCell(4);
                        cell.setCellValue(metaregistro.getMetaAcumulada());
                        cell.setCellStyle(my_style);

                        cell = row.createCell(5);
                        cell.setCellValue(metaregistro.getPorcentaje());
                        cell.setCellStyle(my_style);

                        cell = row.createCell(6);
                        cell.setCellValue(metaregistro.getObservaciones());
                        cell.setCellStyle(my_style);

                        cell = row.createCell(7);
                        cell.setCellValue(((metaregistro.getFecha()) == null) ? "" : metaregistro.getFecha().toString());
                        cell.setCellStyle(my_style);
                        fila++;
                    }
                }
            }

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();

            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-disposition", "attachment;filename=metasregistros.xls");

            ServletOutputStream out = res.getOutputStream();
            my_workbook.write(out);
            out.flush();
            out.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     *
     * @return
     */
    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    /**
     * @author Manuel Cortes Granados
     * @since 26 Julio 2014 22:53
     * @return
     */
    protected IngresarNuevaObra getIngresarNuevaObra() {
        return (IngresarNuevaObra) FacesUtils.getManagedBean("IngresarNuevaObra");
    }

    public void test() {
        List<Meta> l_metas = getSessionBeanCobra().getCobraService().consultarMetaObras();
    }

    public List<Metaobra> consultarMetaobrasDetalle(int idmeta) {
        return getSessionBeanCobra().getCobraService().consultarMetaObrasDetalle(idmeta);
    }

    public List<Metaregistro> consultaMetaRegistroDetalle(int idmetaobra) {
        return getSessionBeanCobra().getCobraService().consultaMetaRegistroDetalle(idmetaobra);
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
        if (test) {
            FacesUtils.addErrorMessage("Codigo del error en la base de datos : " + se.getErrorCode());
            FacesUtils.addErrorMessage(se.toString());
        }
        throw se;
    }

    /**
     * @author Manuel Cortes Granados
     * @since 30 Junio 2014 12:10 PM
     * @param e
     * @throws Exception
     */
    public void procesarErrorGeneral(Exception se) throws Exception {
        Utilitario util = new Utilitario();
        FacesUtils.addErrorMessage("Ha ocurrido un error en el Sistema");
        if (test) {
            FacesUtils.addErrorMessage(se.toString());
        }
        throw se;
    }

    /**
     * @author Manuel Cortes Granados
     * @since 25 Julio 20114 12:06 PM
     */
    public void actualizarAcumuladosenMetasRegistros() throws Exception {

        double acumulado_meta_proyectada = 0;
        double acumulado_meta_acumulada = 0;
        double acumulado_porcentaje = 0;

        for (Meta meta : this.listaMetas) {
            List<Metaobra> l_metaobra = this.consultarMetaobrasDetalle(meta.getId());
            for (Metaobra metaobra : l_metaobra) {
                List<Metaregistro> l_metaregistro = this.consultaMetaRegistroDetalle(metaobra.getIdmetaobra());
                for (Metaregistro metaregistro : l_metaregistro) {
                    acumulado_meta_proyectada = acumulado_meta_proyectada + metaregistro.getMetaProyectada();
                    acumulado_meta_acumulada = acumulado_meta_acumulada + metaregistro.getMetaAcumulada();
                    acumulado_porcentaje = (acumulado_meta_acumulada / acumulado_meta_proyectada) * 100;
                    metaregistro.setMetaProyectadaAcum(acumulado_meta_proyectada);
                    metaregistro.setMetaAcumuladaAcum(acumulado_meta_acumulada);
                    metaregistro.setPorcentajeAcum(acumulado_porcentaje);
                    this.getSessionBeanCobra().getMetasService().actualizarMetaRegistro(metaregistro);
                }
                acumulado_meta_proyectada = 0;
                acumulado_meta_acumulada = 0;
                acumulado_porcentaje = 0;
            }
        }
        //ds.closeConnection();
    }

    /**
     * @author Manuel Cortes Granados
     * @since
     */
    public void generarArchivoXMLFusionChart_MetaRegistro() throws IOException, Exception {
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String directorio = servletContext.getRealPath("/");

            String contenido = "";

            for (Meta meta : this.listaMetas) {
                List<Metaobra> l_metaobra = this.consultarMetaobrasDetalle(meta.getId());
                for (Metaobra metaobra : l_metaobra) {
                    List<Metaregistro> l_metaregistro = this.consultaMetaRegistroDetalle(metaobra.getIdmetaobra());
                    double maxMetaProyectada = this.getSessionBeanCobra().getMetasService().getSumaMetaProyectada(metaobra.getIdmetaobra());
                    contenido = contenido + "<graph caption=\"Grafico\" subcaption=\"Meta Proyectada Vs Meta Acumulada por Periodo y Acumulados\" hovercapbg=\"FFECAA\" hovercapborder=\"F47E00\" formatNumberScale=\"0\" decimalPrecision=\"0\" showvalues=\"0\" numdivlines=\"3\" numVdivlines=\"0\" yaxisminvalue=\"0\" yaxismaxvalue=\"" + maxMetaProyectada + "\"  rotateNames=\"1\">";
                    contenido = contenido + "<categories >";
                    for (Metaregistro metaregistro : l_metaregistro) {
                        contenido = contenido + "<category name=\"" + metaregistro.getIdregistrometaobra() + "\" />";
                    }
                    contenido = contenido + "</categories>";
                    contenido = contenido + "";
                    contenido = contenido + "<dataset seriesName=\"Meta Acumulada Periodo\" color=\"1D8BD1\" anchorBorderColor=\"1D8BD1\" anchorBgColor=\"1D8BD1\">";
                    for (Metaregistro metaregistro : l_metaregistro) {
                        contenido = contenido + "	<set value=\"" + metaregistro.getMetaAcumulada() + "\" />";
                    }
                    contenido = contenido + "	</dataset>";
                    contenido = contenido + "";
                    contenido = contenido + "<dataset seriesName=\"Meta Proyectada Periodo\" color=\"F1683C\" anchorBorderColor=\"F1683C\" anchorBgColor=\"F1683C\">";
                    for (Metaregistro metaregistro : l_metaregistro) {
                        contenido = contenido + "	<set value=\"" + metaregistro.getMetaProyectada() + "\" />";
                    }
                    contenido = contenido + "</dataset>";
                    contenido = contenido + "";
                    contenido = contenido + "<dataset seriesName=\"Meta Acumulada GLOTAL (Asi vamos)\" color=\"2AD62A\" anchorBorderColor=\"2AD62A\" anchorBgColor=\"2AD62A\">";
                    for (Metaregistro metaregistro : l_metaregistro) {
                        contenido = contenido + "	<set value=\"" + metaregistro.getMetaAcumuladaAcum() + "\" />";
                    }
                    contenido = contenido + "</dataset>";
                    contenido = contenido + "";
                    contenido = contenido + "<dataset seriesName=\"Meta Proyectada GLOBAL (Asi deberiamos ir)\" color=\"DBDC25\" anchorBorderColor=\"DBDC25\" anchorBgColor=\"DBDC25\">";
                    for (Metaregistro metaregistro : l_metaregistro) {
                        contenido = contenido + "	<set value=\"" + metaregistro.getMetaProyectadaAcum() + "\" />";
                    }
                    contenido = contenido + "</dataset>";
                    contenido = contenido + "";
                    contenido = contenido + "</graph>";
                    File file = new File(directorio + "/XML/Metaregistro_" + metaobra.getIdmetaobra() + ".xml");
                    BufferedWriter output = new BufferedWriter(new FileWriter(file));
                    output.write(contenido);
                    output.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Manuel Cortes Granados
     * @since 26 Julio 2014 22:44
     * @param idmeta
     * @param idproyecto
     * @return
     */
    public ReporteMetasVO generarReporteGlobalMetas(int idmeta, int idproyecto) throws Exception {
        ReporteMetasVO repVO = new ReporteMetasVO();
        repVO = this.getSessionBeanCobra().getMetasService().generarReporteGlobalMetas(idmeta, idproyecto);
        return repVO;
    }

    /**
     * @author Manuel Cortes Granados
     * @since 28 Julio 2014 17:45
     * @param idplandesarrollo
     * @return
     */
    public Set<PdObjetivo> consultarObjetivosPlanDesarrollo(int idplandesarrollo) throws Exception {
        s_objetivos = this.getSessionBeanCobra().getMetasService().selectPdObjetivos(idplandesarrollo);
        return s_objetivos;
    }

    /**
     * @author Manuel Cortes Granados
     * @since 28 Julio 2014 17:45
     * @param idplandesarrollo
     * @return
     */
    public Set<PdPrograma> consultarProgramasPlanDesarrollo(int idplandesarrollo, int idobjetivo) throws Exception {
        s_programas = this.getSessionBeanCobra().getMetasService().selectPdPrograma(idplandesarrollo, idobjetivo);
        return s_programas;
    }

    /**
     * @author Manuel Cortes Granados
     * @since 28 Julio 2014 17:45
     * @param idplandesarrollo
     * @return
     */
    public Set<PdSubprograma> consultarSubprogramaPlanDesarrollo(int idplandesarrollo, int idobjetivo, int idprograma) throws Exception {
        s_subprogramas = this.getSessionBeanCobra().getMetasService().selectPdSubPrograma(idplandesarrollo, idobjetivo, idprograma);
        return s_subprogramas;
    }

    /**
     * @author Manuel Cortes Granados
     * @since 29 Julio 2014 12:46
     * @throws Exception
     */
    public void getPlanDesarrollo() throws Exception {
        p_plandesarrollo = this.getSessionBeanCobra().getMetasService().getPlanDesarrollo();
    }

    /**
     * @author Manuel Cortes Granados
     * @since 29 Julio 2014 12:46
     * @param idobjetivo
     * @param idprograma
     * @param idsubprograma
     * @throws Exception
     */
    public List<Meta> consultarMetasDisponibles(int idobjetivo, int idprograma, int idsubprograma) throws Exception {
        List<Meta> l_resultado = this.getSessionBeanCobra().getMetasService().selectMeta(idobjetivo, idprograma, idsubprograma);
        return l_resultado;
    }

    /**
     * @author Manuel Cortes Granados
     * @since 30 Julio 2014 9:08 AM
     * @return
     */
    public List<ReporteMetasVO> getConsolidadoReporteTodaslasMetasyProyectos() {
        List<ReporteMetasVO> l_resultado = new ArrayList<ReporteMetasVO>();
        l_resultado = this.getSessionBeanCobra().getMetasService().getConsolidadoReporteTodaslasMetasyProyectos();
        return l_resultado;
    }

}
