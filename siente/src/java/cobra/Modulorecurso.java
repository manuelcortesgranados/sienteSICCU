/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra;

import co.com.interkont.cobra.to.JsfUsuarioGrupo;
import co.com.interkont.cobra.to.Recurso;
import cobra.Supervisor.FacesUtils;
import com.interkont.cobra.util.RenderRecurso;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author FELIPE
 */
public class Modulorecurso implements Serializable{

    private final static int SOLICITUD_ATENCION_HUMANITARIA = 1;
    private final static int SOLICITUD_OBRA = 2;
    private final static int CORAL = 3;
    private final static int SEGUIMIENTO_CONTROL = 4;
    private final static int SIENTE = 5;
    private final static int SEGUIMIENTO_SOLICITUDES_APROBADAS = 6;
    private final static int GESTION_FINANCIERA = 7;
    private final static int ALOJAMIENTOS = 8;

    public RenderRecurso validarrecurso(int modulo) {

        
        RenderRecurso renderrecurso = new RenderRecurso();
        JsfUsuarioGrupo grupo = getSessionBeanCobra().getUsuarioService().encontrarGrupoxUsuarioxModulo(getSessionBeanCobra().getUsuarioObra().getUsuId(), modulo);

        if (grupo != null) {

            List<Recurso> recursos = getSessionBeanCobra().getCobraService().encontrarRecursosGrupo(grupo.getId().getGruId());

            Iterator ite = recursos.iterator();

            while (ite.hasNext()) {

                Recurso temprecusro = (Recurso) ite.next();

                switch (modulo) {

                    case SOLICITUD_ATENCION_HUMANITARIA:

                        if (temprecusro.getRecNmbre().equals("registrar_atencion_humanitaria")) {
                            renderrecurso.setBtnregistrar_atencion_humanitaria(false);
                        }
                        if (temprecusro.getRecNmbre().equals("mostrar_datos_solicitante")) {
                            renderrecurso.setBtnmostrar_datos_solicitante(false);
                        }
                        if (temprecusro.getRecNmbre().equals("mostrar_datos_altofuncionario")) {
                            renderrecurso.setBtnmostrar_datos_altofuncionario(false);
                        }
                        if (temprecusro.getRecNmbre().equals("editar_solicitud_ah")) {
                            renderrecurso.setBtneditar_solicitud_ah(false);
                        }
                        break;

                    case SOLICITUD_OBRA:
                        break;

                    case CORAL:
                        break;

                    case SEGUIMIENTO_CONTROL:
                        break;

                    case SIENTE:
                        break;

                    case SEGUIMIENTO_SOLICITUDES_APROBADAS:
                        if (temprecusro.getRecNmbre().equals("nuevo_proyecto")) {
                            renderrecurso.setBtnproyecto(false);
                        }
                        if (temprecusro.getRecNmbre().equals("reportes")) {
                            renderrecurso.setBtnreportes(false);
                        }
                        if (temprecusro.getRecNmbre().equals("nuevo_contrato")) {
                            renderrecurso.setBtncontratos(false);
                        }
                        if (temprecusro.getRecNmbre().equals("nuevo_convenio")) {
                            renderrecurso.setBtnconvenio(false);
                        }
                        if (temprecusro.getRecNmbre().equals("indicadores")) {
                            renderrecurso.setBtnindicadores(false);
                        }
                        if (temprecusro.getRecNmbre().equals("soporte")) {
                            renderrecurso.setBtnsoporte(false);
                        }
                        if (temprecusro.getRecNmbre().equals("detalle")) {
                            renderrecurso.setBtndetalle(false);
                        }
                        if (temprecusro.getRecNmbre().equals("reporte_avance")) {
                            renderrecurso.setBtnreporte_avance(false);
                        }
                        if (temprecusro.getRecNmbre().equals("modificar")) {
                            renderrecurso.setBtnmodificar(false);
                        }
                        if (temprecusro.getRecNmbre().equals("evolucion")) {
                            renderrecurso.setBtnevolucion(false);
                        }
                        if (temprecusro.getRecNmbre().equals("reporte_obra")) {
                            renderrecurso.setBtnreporte_obra(false);
                        }
                        if (temprecusro.getRecNmbre().equals("documentos")) {
                            renderrecurso.setBtndocumentos(false);
                        }
                        if (temprecusro.getRecNmbre().equals("imagenes")) {
                            renderrecurso.setBtnimagenes(false);
                        }
                        if (temprecusro.getRecNmbre().equals("videos")) {
                            renderrecurso.setBtnvideos(false);
                        }
                        if (temprecusro.getRecNmbre().equals("finalizar")) {
                            renderrecurso.setBtnfinalizar(false);
                        }
                        if (temprecusro.getRecNmbre().equals("contrato_consultoria")) {
                            renderrecurso.setBtncontrato_consultoria(false);
                        }
                        if (temprecusro.getRecNmbre().equals("asociar_consultoria")) {
                            renderrecurso.setBtnasociar_consultoria(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btndetalleproyecto")) {
                            renderrecurso.setBtndetalleproyecto(false);
                        }
                        if (temprecusro.getRecNmbre().equals("panelperfil_control")) {
////                            renderrecurso.setPanelperfil_control(false);
                        }
                        if (temprecusro.getRecNmbre().equals("mostrar_Consolidar")) {
                            renderrecurso.setBtnmostrar_Consolidar(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btntablerocontrol")) {
                            renderrecurso.setBtntablero(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btnvalidaravance")) {
                            renderrecurso.setBtnvalidaravance(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btntablero_control")) {
                            renderrecurso.setBtnvalidaravance(false);
                        }
                        if (temprecusro.getRecNmbre().equals("panel_conozca_inversion")) {
                            renderrecurso.setBtnpanel_conozca_inversion(false);
                        }
                        if (temprecusro.getRecNmbre().equals("slider_imagenes_ciudadano")) {
                            renderrecurso.setBtnslider_imagenes_ciudadano(false);
                        }
                        
                        if (temprecusro.getRecNmbre().equals("muro_comentarios")) {
                            renderrecurso.setBtnmuro_comentarios(false);
                        }
                        
                        if (temprecusro.getRecNmbre().equals("slider_dashboard_ciudadano")) {
                            renderrecurso.setBtnslider_dashboard(false);
                        }
                        
                         if (temprecusro.getRecNmbre().equals("btn_filtrar")) {
                            renderrecurso.setBtnfiltro_ciudadano(false);
                        }
                         
                          if (temprecusro.getRecNmbre().equals("btn_facebook")) {
                            renderrecurso.setBtnfacebook(false);
                        }
                          
                        if (temprecusro.getRecNmbre().equals("btn_solicitarValInf")) {
                            renderrecurso.setBtnsolicitarValInf(false);
                        }  
                        if (temprecusro.getRecNmbre().equals("btn_devolvervig")) {
                            renderrecurso.setBtndevolvervig(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btn_eliminarAvance")) {
                            renderrecurso.setBtneliminarAvance(false);
                        }
                          if (temprecusro.getRecNmbre().equals("ver_contratos")) {
                            renderrecurso.setBtnver_contratos(false);
                        }
                        if (temprecusro.getRecNmbre().equals("ver_convenios")) {
                            renderrecurso.setBtnver_convenios(false);
                        }
                         if (temprecusro.getRecNmbre().equals("ver_proyecto_ciudadano")) {
                            renderrecurso.setVer_proyecto_ciudadano(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btn_miperfil")) {
                            renderrecurso.setBtn_miperfil(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btn_ayuda")) {
                            renderrecurso.setBtnayuda(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btn_nuevoproyecto_mgestion")) {
                            renderrecurso.setBtnproyecto_menugestion(false);
                        }
                        if (temprecusro.getRecNmbre().equals("pnlsubirimagen")) {
                            renderrecurso.setPnlsubirimagen(false);
                        }
                        if (temprecusro.getRecNmbre().equals("ver_eliminarimagen")) {
                            renderrecurso.setVer_eliminarimagen(false);
                        }
                        if (temprecusro.getRecNmbre().equals("ver_editarimagen")) {
                            renderrecurso.setVer_editarimagen(false);
                        }
                        if (temprecusro.getRecNmbre().equals("pnlsubirdocumento")) {
                            renderrecurso.setPnlsubirdocumento(false);
                        }
                        if (temprecusro.getRecNmbre().equals("ver_eliminardoc")) {
                            renderrecurso.setVer_eliminardoc(false);
                        }
                        if (temprecusro.getRecNmbre().equals("ver_editardoc")) {
                            renderrecurso.setVer_editardoc(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btnproyectos_ah")) {
                            renderrecurso.setBtnproyectos_ah(false);
                        }
                        if (temprecusro.getRecNmbre().equals("ver_beneficiarios_ah")) {
                            renderrecurso.setVerbeneficiariosah(false);
                        }
                        if (temprecusro.getRecNmbre().equals("ver_modificar_proyecto")) {
                            renderrecurso.setVermodificarproyecto(false);
                        }

                         if (temprecusro.getRecNmbre().equals("pnlmovientosfinancieros")) {
                            renderrecurso.setPnlmovientosfinancieros(false);
                        }
                         if (temprecusro.getRecNmbre().equals("pnlseguimientos")) {
                            renderrecurso.setPnlseguimientos(false);
                        }
                        if (temprecusro.getRecNmbre().equals("pnlcontratointerventoria")) {
                            renderrecurso.setPnlcontratointerventoria(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btnhistorevinformacion")) {
                            renderrecurso.setBtnhistorevinformacion(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btn_consultaproyecenconvenio")) {
                            renderrecurso.setBtn_consultaproyecenconvenio(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btn_seguirproyecto")) {
                            renderrecurso.setBtn_seguirproyecto(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btnparticipacion_ciudadano")) {
                            renderrecurso.setBtnparticipacion_ciudadano(false);
                        }
                         if (temprecusro.getRecNmbre().equals("btn_editarEntidadContrato")) {
                            renderrecurso.setBtn_editarEntidadContrato(false);
                        }
                        if (temprecusro.getRecNmbre().equals("mnu_administrador")) {
                            renderrecurso.setMnu_administrador(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btn_editarRecursosCH")) {
                            renderrecurso.setBtn_editarRecursosCH(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btn_editarRecursosPropios")) {
                            renderrecurso.setBtn_editarRecursosPropios(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btn_editarRecursosTerceros")) {
                            renderrecurso.setBtn_editarRecursosTerceros(false);
                        }

                        if (temprecusro.getRecNmbre().equals("btn_editarNumeroContrato")) {
                            renderrecurso.setBtn_editarNumeroContrato(false);
                        }

                        if (temprecusro.getRecNmbre().equals("panel_editarObjetoContrato")) {
//                            renderrecurso.setPanel_editarObjetoContrato(false);
                        }

                        if (temprecusro.getRecNmbre().equals("btn_insertarpoliza")) {
                            renderrecurso.setBtn_insertarpoliza(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btn_editarpoliza")) {
                            renderrecurso.setBtn_editarpoliza(false);
                        }
                        if (temprecusro.getRecNmbre().equals("btn_eliminarpoliza")) {
                            renderrecurso.setBtn_eliminarpoliza(false);
                        }

                        
                        if (temprecusro.getRecNmbre().equals("btn_modificarobjetoobra")) {
                            renderrecurso.setBtn_modificarobjetoobra(false);
                        }
                        
                        break;
                        

                    case GESTION_FINANCIERA:
                        break;

                    case ALOJAMIENTOS:
                        break;



                }
            }
        } else {
            renderrecurso = null;
        }
        return renderrecurso;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }
}
