/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.server.services;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
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
    private ContratoDTO contratoDto = new ContratoDTO();
    private final Log log = LogFactory.getLog(this.getClass());
    private int navegacion=1;

    public CobraDaoAble getCobraDao() {
        return cobraDao;
    }

    public void setCobraDao(CobraDaoAble cobraDao) {
        this.cobraDao = cobraDao;
    }

//    @Override
//    public ContratoDTO casteoContrato() throws Exception {
//
//        //    ContratoDTO contratodto = new ContratoDTO(contrato);
////        Set<Fuenterecursosconvenio> fuenterecursos= contrato.getFuenterecursosconvenios();
////        Set<FuenterecursosconvenioDTO> fuenterecursosdto= new HashSet<FuenterecursosconvenioDTO>();
////        if(fuenterecursos!=null){
////            fuenterecursosdto = CobraUtil.convertirSet(fuenterecursos,"FuenterecursosconvenioDTO",  "Fuenterecursosconvenio", VAR_DTO, contratodto, "contrato");
////        }
////            contratoDto.setRelacionobrafuenterecursoscontratos(fuenterecursosdto);
//        return null;
//    }

    @Override
    public ContratoDTO obtenerContratoDTO() {
        if (contratoDto.getActividadobras().isEmpty()) {
            try {
                contratoDto.setActividadobras(new LinkedHashSet(obtenerActividadesObligatorias(contratoDto.getDatefechaini(), contratoDto.getIntduraciondias(), contratoDto.getDatefechaactaini())));
            } catch (Exception ex) {
                Logger.getLogger(CobraGwtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
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

//    @Override
//    public ContratoDTO ObtenerContratoDTO(int idcontrato) throws Exception {
//        return CasteoGWT.castearContratoToContratoDTO((Contrato) cobraDao.encontrarPorId(Contrato.class, idcontrato));
//    }

    @Override
    public ArrayList<ActividadobraDTO> obtenerActividadesObligatorias(Date fecini, int duracion, Date fecactaini) throws Exception {
        List<Parametricaactividadesobligatorias> listapar = cobraDao.encontrarTodoOrdenadoporcampo(Parametricaactividadesobligatorias.class, "idparametrica");
        Iterator itparametricas = listapar.iterator();
        ArrayList<ActividadobraDTO> listaactobligatorias = new ArrayList<ActividadobraDTO>();
        while (itparametricas.hasNext()) {            
            Parametricaactividadesobligatorias par = (Parametricaactividadesobligatorias) itparametricas.next();            
            if (par.getParametricaactividadesobligatorias() == null) {
                ActividadobraDTO actdto = CasteoGWT.castearParametricaactividadesobligatoriasToActividadobraDTO(par, fecini, 1, 0);

                for (Parametricaactividadesobligatorias parhija : listapar) {
                    if (parhija.getParametricaactividadesobligatorias() != null && parhija.getParametricaactividadesobligatorias().getIdparametrica() == par.getIdparametrica()) {
                        //Coloca 1 dia para Acta de Inicio de convenio

                        if (parhija.getIdparametrica() == 4) {
                            actdto.addChild(CasteoGWT.castearParametricaactividadesobligatoriasToActividadobraDTO(parhija, fecactaini, 1, 0));
                        } else {
                            actdto.addChild(CasteoGWT.castearParametricaactividadesobligatoriasToActividadobraDTO(parhija, fecini, 1, 0));
                        }
                    }
                }
                listaactobligatorias.add(actdto);
            }
        }
        return listaactobligatorias;
    }

    @Override
    public List<RubroDTO> obtenerRubros(String categoria) throws Exception {
        List<Rubro> lstRubros = cobraDao.encontrarPorcadenailikeinicial(Rubro.class,"idrubro",categoria);
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
    

}
